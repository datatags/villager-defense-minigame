package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import org.bukkit.Material;

public class KitVampire extends Kit {
    public KitVampire() {
        super(LanguageManager.kits.vampire.name, KitCategory.EFFECT, Material.GHAST_SPAWN_EGG);

        setMasterDescription(LanguageManager.kits.vampire.description);
        setPrice(6000);
    }
}
