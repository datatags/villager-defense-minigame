package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import lombok.Getter;
import lombok.Setter;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.Main;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryMeta;
import me.theguyhere.villagerdefense.plugin.visuals.layout.Layout;
import me.theguyhere.villagerdefense.plugin.visuals.layout.PagedLayout;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Menu {
    private static final NamespacedKey ACTION_KEY = new NamespacedKey(Main.plugin, "action");
    protected static final Consumer<Player> NO_OP = p -> {};
    @Getter
    private final String name;
    protected final Layout layout;
    @Setter
    private Menu previous = null;
    private final List<Consumer<Player>> buttonActions = new ArrayList<>();
    protected final Map<ItemStack,Consumer<ItemStack>> buttonUpdaters = new HashMap<>();
    private Inventory inv = null;
    public Menu(String name, Layout layout) {
        this.name = CommunicationManager.format(name);
        this.layout = layout;
    }

    protected void addClickHandler(ItemStack item, Consumer<Player> onClick) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(ACTION_KEY, PersistentDataType.INTEGER, buttonActions.size());
            buttonActions.add(onClick);
            item.setItemMeta(meta);
        }
    }

    protected void addButton(ItemStack item, Consumer<Player> onClick) {
        addClickHandler(item, onClick);
        layout.add(item);
    }

    protected void addNavigation(ItemStack item, Supplier<Menu> other) {
        addButton(item, p -> open(p, other.get()));
    }

    protected ItemStack addButton(Material mat, Consumer<Player> onClick, Supplier<String> name, String... lore) {
        ItemStack stack = addButton(mat, onClick, name.get(), lore);
        buttonUpdaters.put(stack, i -> {
            ItemMeta meta = i.getItemMeta();
            meta.setDisplayName(CommunicationManager.format(name.get()));
            i.setItemMeta(meta);
        });
        return stack;
    }

    protected ItemStack addButton(Material mat, Consumer<Player> onClick, String name, String... lore) {
        List<String> loreList = new ArrayList<>();
        for (String s : lore) {
            loreList.add(CommunicationManager.format(s));
        }
        ItemStack item = ItemManager.createItem(mat, CommunicationManager.format(name), ItemManager.BUTTON_FLAGS, null, loreList);
        addButton(item, onClick);
        return item;
    }

    protected ItemStack addNavigation(Material mat, Supplier<Menu> other, Supplier<String> nameGetter, String... lore) {
        return addButton(mat, p -> open(p, other.get()), nameGetter, lore);
    }

    protected ItemStack addNavigation(Material mat, Supplier<Menu> other, String name, String... lore) {
        return addButton(mat, p -> open(p, other.get()), name, lore);
    }

    public void open(Player player) {
        Bukkit.getScheduler().runTask(Main.plugin, () -> player.openInventory(getInventory()));
    }

    protected void open(Player player, @Nullable Menu other) {
        if (other != null) {
            other.setPrevious(this);
            other.open(player);
        }
    }

    public Inventory getInventory() {
        updateInventory();
        return inv;
    }

    protected void updateInventory() {
        buttonUpdaters.forEach((stack, action) -> action.accept(stack));
        Map<Integer, ItemStack> slots = layout.getSlots();
        if (inv == null) {
            inv = Bukkit.createInventory(new InventoryMeta(this), layout.getInventorySize(),
                    CommunicationManager.format(getName()));
        }
        inv.clear();
        slots.forEach(inv::setItem);
    }

    protected void goBack(Player player) {
        Bukkit.getScheduler().runTask(Main.plugin, previous == null ? player::closeInventory : () -> previous.open(player));
    }

    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (item.equals(InventoryButtons.exit())) {
            goBack(player);
            return;
        }
        if (layout instanceof PagedLayout) {
            PagedLayout l = (PagedLayout) layout;
            if (item.equals(InventoryButtons.previousPage())) {
                l.prevPage();
                updateInventory();
                return;
            }
            if (item.equals(InventoryButtons.nextPage())) {
                l.nextPage();
                updateInventory();
                return;
            }
        }
        Integer val = item.getItemMeta().getPersistentDataContainer().get(ACTION_KEY, PersistentDataType.INTEGER);
        if (val != null) {
            buttonActions.get(val).accept(player);
            updateInventory();
        }
    }

    // Easy way to get a string for a toggle status
    protected static String getToggleStatus(boolean status) {
        if (status) {
            return "&a&l" + LanguageManager.messages.onToggle;
        } else {
            return "&c&l" + LanguageManager.messages.offToggle;
        }
    }

    // Modify the price of an item
    protected static ItemStack modifyPrice(ItemStack itemStack, double modifier) {
        ItemStack item = itemStack.clone();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        assert lore != null;
        int price = (int) Math.round(Integer.parseInt(lore.get(lore.size() - 1).substring(10)) * modifier / 5) * 5;
        lore.set(lore.size() - 1, CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
                price));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    protected static void sort(List<ItemStack> list) {
        list.sort(Comparator.comparingInt(itemStack -> {
            List<String> lore = Objects.requireNonNull(itemStack.getItemMeta()).getLore();
            assert lore != null;
            return Integer.parseInt(lore.get(lore.size() - 1).substring(10));
        }));
    }

    /**
     * Closes the inventory of a player without creating a ghost item artifact.
     * @param player Player to close inventory.
     */
    protected void closeInv(Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, player::closeInventory, 1);
    }

    protected void success(Player player) {
        PlayerManager.notifySuccess(player, "Success!");
    }

    protected void swapMeta(ItemStack item, ItemMeta newMeta) {
        if (newMeta == null) {
            return;
        }
        item.getItemMeta().getPersistentDataContainer().copyTo(newMeta.getPersistentDataContainer(), true);
        item.setItemMeta(newMeta);
    }
}
