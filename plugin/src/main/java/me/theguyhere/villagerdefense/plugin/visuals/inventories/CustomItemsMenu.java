package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.ArenaDataManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CustomItemsMenu extends ArenaMenu {
    private final int id;
    private final ItemStack shopItem;
    private int price = -1;
    public CustomItemsMenu(Arena arena, int itemId) {
        super("&6&lEdit Item", arena, new ManualLayout());
        this.id = itemId;

        ManualLayout layout = (ManualLayout) this.layout;
        // Item of interest
        this.shopItem = arena.getCustomShopItems().get(id);
        layout.add(4, shopItem);

        if (shopItem.hasItemMeta() && shopItem.getItemMeta().hasLore()) {
            List<String> lore = shopItem.getItemMeta().getLore();
            try {
                price = Integer.parseInt(lore.get(lore.size() - 1)
                        .substring(6 + LanguageManager.messages.gems.length()));
            }
            catch (NumberFormatException | IndexOutOfBoundsException ignored) {}
        }

        ItemStack noPurchase = ItemManager.createItem(Material.BEDROCK, CommunicationManager.format("&5&lToggle Un-purchasable"));
        layout.add(8, noPurchase);
        addClickHandler(noPurchase, p -> setNewPrice(p, price == -1 ? 0 : -1));

        ItemStack delete = ItemManager.createItem(Material.LAVA_BUCKET, CommunicationManager.format("&4&lDELETE"));
        layout.add(17, delete);
        addClickHandler(delete, ifClosed(p -> open(p, new ConfirmationMenu("&4&lRemove Custom Item?", pl -> {
            ArenaDataManager.removeCustomShopItem(arena.getId(), id);
            goBack(p);
        }, false))));

        addGemsModifier(9,  +1);
        addGemsModifier(11, +10);
        addGemsModifier(13, +100);
        addGemsModifier(15, +1000);
        addGemsModifier(18, -1);
        addGemsModifier(20, -10);
        addGemsModifier(22, -100);
        addGemsModifier(24, -1000);

        layout.add(26, InventoryButtons.exit());
    }

    private void addGemsModifier(int slot, int amount) {
        Material mat = amount > 0 ? Material.LIME_CONCRETE : Material.RED_CONCRETE;
        String color = amount > 0 ? "&a&l" : "&c&l";
        String value = String.valueOf(amount);
        if (amount >= 0) {
            value = '+' + value;
        }
        ItemStack item = ItemManager.createItem(mat, CommunicationManager.format(color + value));
        ((ManualLayout)layout).add(slot, item);
        addClickHandler(item, p -> setNewPrice(p, Math.max(0, price) + amount));
    }

    private void setNewPrice(Player player, int amount) {
        if (!checkClosed(player)) {
            return;
        }
        ItemMeta meta = shopItem.getItemMeta();
        List<String> lore = meta.getLore();
        // unpurchaseable items do not have price lore
        if (price != -1 && !lore.isEmpty()) {
            lore.remove(lore.size() - 1);
        }
        if (amount == -1) {
            price = -1;
        } else {
            price = Math.min(99999, Math.max(0, amount));
            String last = CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" + price);
            lore.add(last);
        }
        meta.setLore(lore);
        shopItem.setItemMeta(meta);
        ArenaDataManager.setCustomShopItem(arena.getId(), id, shopItem);
    }
}
