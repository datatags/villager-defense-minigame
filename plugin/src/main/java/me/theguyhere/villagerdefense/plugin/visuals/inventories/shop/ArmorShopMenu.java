package me.theguyhere.villagerdefense.plugin.visuals.inventories.shop;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.items.GameItems;

public class ArmorShopMenu extends LevelledShopMenu {
    public ArmorShopMenu(Arena arena, int level) {
        super("&5&l", LanguageManager.names.armorShop, arena, level);

        generate(GameItems::helmet);
        generate(GameItems::chestplate);
        generate(GameItems::leggings);
        generate(GameItems::boots);
        addExit();
    }
}
