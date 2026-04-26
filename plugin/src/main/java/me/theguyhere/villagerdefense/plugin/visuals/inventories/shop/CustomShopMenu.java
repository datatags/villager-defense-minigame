package me.theguyhere.villagerdefense.plugin.visuals.inventories.shop;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;

public class CustomShopMenu extends ShopMenu {
    public CustomShopMenu(Arena arena) {
        super("&6&l" + LanguageManager.names.customShop, arena);

        // Set exit option
        ((ManualLayout)layout).add(49, InventoryButtons.exit());

        // Get items from stored inventory
        arena.getCustomShopItems().forEach(this::addItem);
    }
}
