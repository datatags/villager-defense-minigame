package me.theguyhere.villagerdefense.plugin.visuals.inventories.shop;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.items.GameItems;

public class WeaponShopMenu extends LevelledShopMenu {
    public WeaponShopMenu(Arena arena, int level) {
        super("&4&l", LanguageManager.names.weaponShop, arena, level);

        generate(GameItems::sword);
        generate(GameItems::axe);
        generate(GameItems.RANGED::getRandom);
        generate(GameItems.AMMO::getRandom);
        addExit();
    }
}
