package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import org.bukkit.Material;

public class KitGiant extends Kit {
    public KitGiant() {
        super(LanguageManager.kits.giant.name, KitCategory.EFFECT, Material.DARK_OAK_SAPLING);

        addLevelDescriptions(1, String.format(LanguageManager.kits.giant.description, "10%"));
        addLevelDescriptions(2, String.format(LanguageManager.kits.giant.description, "20%"));

        setPrice(1, 5000);
        setPrice(2, 8000);
    }
}
