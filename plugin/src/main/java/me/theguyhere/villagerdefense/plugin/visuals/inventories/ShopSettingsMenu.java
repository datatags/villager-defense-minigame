package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.Material;

public class ShopSettingsMenu extends ArenaMenu {
    public ShopSettingsMenu(Arena arena) {
        super("&e&lShop Settings", arena, new DynamicSizeLayout(true));

        addNavigation(Material.QUARTZ, () -> new CustomShopEditorMenu(arena, false), "&a&lEdit Custom Shop");

        addButton(Material.EMERALD_BLOCK, p -> arena.setNormal(!arena.hasNormal()),
                () -> "&6&lDefault Shop: " + getToggleStatus(arena.hasNormal()),
                "&7Turn default shop on and off");

        addButton(Material.GOLDEN_CHESTPLATE, p -> arena.setArmor(!arena.hasArmor()),
                () -> "&6&lArmor Shop: " + getToggleStatus(arena.hasArmor()),
                "&7Turn armor shop on and off");

        addButton(Material.GOLDEN_APPLE, p -> arena.setConsumables(!arena.hasConsumables()),
                () -> "&6&lConsumables Shop: " + getToggleStatus(arena.hasConsumables()),
                "&7Turn consumables shop on and off");

        addButton(Material.QUARTZ_BLOCK, p -> arena.setCustom(!arena.hasCustom()),
                () -> "&2&lCustom Shop: " + getToggleStatus(arena.hasCustom()),
                "&7Turn custom shop on and off");

        addButton(Material.BOOKSHELF, p -> arena.setEnchants(!arena.hasEnchants()),
                () -> "&3&lEnchant Shop: " + getToggleStatus(arena.hasEnchants()),
                "&7Turn enchants shop on and off");

        addButton(Material.CHEST, p -> arena.setCommunity(!arena.hasCommunity()),
                () -> "&d&lCommunity Chest: " + getToggleStatus(arena.hasCommunity()),
                "&7Turn community chest on and off");

        addButton(Material.NETHER_STAR, p -> arena.setDynamicPrices(!arena.hasDynamicPrices()),
                () -> "&b&lDynamic Prices: " + getToggleStatus(arena.hasDynamicPrices()),
                "&7Prices adjusting based on number of", "&7players in the game");
    }
}
