package me.theguyhere.villagerdefense.plugin.visuals.inventories.shop;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.items.GameItems;

public class ConsumableShopMenu extends LevelledShopMenu {
    public ConsumableShopMenu(Arena arena, int level) {
        super("&3&l", LanguageManager.names.consumableShop, arena, level);

        generate(GameItems.FOOD::getRandom);
        generate(GameItems.OTHER::getRandom);
        addExit();
    }
}
