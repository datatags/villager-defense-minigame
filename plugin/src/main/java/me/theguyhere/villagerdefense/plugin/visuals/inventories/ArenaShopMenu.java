package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.Main;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArenaShopMenu extends ArenaMenu {
    private final String disabled = " &4&l[" + LanguageManager.messages.disabled + "]";
    private final int level;
    private ArenaShopMenu(Arena arena, int level) {
        super("", arena, new ManualLayout()); // name specified in getName() override
        this.level = level;

        addShop(0, Material.GOLDEN_SWORD, '4', true, LanguageManager.names.weaponShop, Arena::hasNormal, Arena::getWeaponShop);
        addShop(1, Material.GOLDEN_CHESTPLATE, '5', true, LanguageManager.names.armorShop, Arena::hasArmor, Arena::getArmorShop);
        addShop(2, Material.GOLDEN_APPLE, '3', true, LanguageManager.names.consumableShop, Arena::hasConsumables, Arena::getConsumeShop);
        addShop(4, Material.BOOKSHELF, 'a', false, LanguageManager.names.enchantShop, Arena::hasEnchants, Arena::getEnchantShop);
        addShop(6, Material.QUARTZ, '6', false, LanguageManager.names.customShop, Arena::hasCustom, Arena::getCustomShop);
        addShopInv(8, Material.CHEST, 'd', false, LanguageManager.names.communityChest, Arena::hasCommunity, p -> p.openInventory(arena.getCommunityChest()));
    }

    public ArenaShopMenu(Arena arena) {
        this(arena, arena.getCurrentWave() / 10 + 1);
    }

    private void addShop(int slot, Material mat, char color, boolean levelled, String name, Predicate<Arena> enabled, Function<Arena, Menu> menuGetter) {
        addShopInv(slot, mat, color, levelled, name, enabled, p -> open(p, menuGetter.apply(arena)));
    }

    private void addShopInv(int slot, Material mat, char color, boolean levelled, String name, Predicate<Arena> enabled, Consumer<Player> opener) {
        String colorStr = "&" + color + "&l";
        final StringBuilder displayName = new StringBuilder();
        if (levelled) {
            displayName.append(colorStr).append(LanguageManager.messages.level).append(" &9&l").append(level).append(' ');
        }
        displayName.append(colorStr).append(name);
        ((ManualLayout)layout).setNextSlot(slot);

        ItemStack item = addButton(mat, p -> {
            if (enabled.test(arena)) {
                Bukkit.getScheduler().runTask(Main.plugin, () -> opener.accept(p));
            }
        }, displayName.toString());

        buttonUpdaters.put(item, i -> {
            ItemMeta meta = i.getItemMeta();
            boolean isEnabled = enabled.test(arena);
            String itemName = displayName.toString();
            if (isEnabled) {
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                ItemManager.glow().forEach((ench, lvl) -> meta.addEnchant(ench, lvl, true));
            } else {
                itemName += disabled;
                meta.removeEnchantments();
            }
            meta.setDisplayName(CommunicationManager.format(itemName));
            i.setItemMeta(meta);
        });
    }

    @Override
    public String getName() {
        // override so the arena name doesn't get added
        return "&2&l" + LanguageManager.messages.level + " &9&l" + level + " &2&l" + LanguageManager.names.itemShop;
    }
}
