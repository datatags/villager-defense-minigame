package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.Material;

public class ShopSettingsMenu extends ArenaMenu {
    public ShopSettingsMenu(Arena arena) {
        super("&e&lShop Settings", arena, new DynamicSizeLayout(true));

        // Option to create a custom shop
        addNavigation(Material.QUARTZ, () -> new CustomShopEditorMenu(arena, false), "&a&lEdit Custom Shop");

        // Option to toggle default shop
        addButton(Material.EMERALD_BLOCK, p -> arena.setNormal(!arena.hasNormal()),
                () -> "&6&lDefault Shop: " + getToggleStatus(arena.hasNormal()),
                "&7Turn default shop on and off");

        // Option to toggle custom shop
        addButton(Material.QUARTZ_BLOCK, p -> arena.setCustom(!arena.hasCustom()),
                () -> "&2&lCustom Shop: " + getToggleStatus(arena.hasCustom()),
                "&7Turn custom shop on and off");

        // Option to toggle custom shop
        addButton(Material.BOOKSHELF, p -> arena.setEnchants(!arena.hasEnchants()),
                () -> "&3&lEnchant Shop: " + getToggleStatus(arena.hasEnchants()),
                "&7Turn enchants shop on and off");

        // Option to toggle community chest
        addButton(Material.CHEST, p -> arena.setCommunity(!arena.hasCommunity()),
                () -> "&d&lCommunity Chest: " + getToggleStatus(arena.hasCommunity()),
                "&7Turn community chest on and off");

        // Option to toggle dynamic prices
        addButton(Material.NETHER_STAR, p -> arena.setDynamicPrices(!arena.hasDynamicPrices()),
                () -> "&b&lDynamic Prices: " + getToggleStatus(arena.hasDynamicPrices()),
                "&7Prices adjusting based on number of", "&7players in the game");
    }
}
