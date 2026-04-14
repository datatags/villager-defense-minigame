package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import org.bukkit.Material;

public class KitWitch extends Kit {
    public KitWitch() {
        super(LanguageManager.kits.witch.name, KitCategory.EFFECT, Material.CAULDRON);

        setMasterDescription(LanguageManager.kits.witch.description);
        setPrice(2500);
    }
}
