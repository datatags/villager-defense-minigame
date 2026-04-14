package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import org.bukkit.Material;

public class KitNone extends Kit {
    public KitNone() {
        super(LanguageManager.names.none, KitCategory.NONE, Material.LIGHT_GRAY_CONCRETE);
    }
}
