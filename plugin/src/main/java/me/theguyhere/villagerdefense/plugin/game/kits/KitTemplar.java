package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class KitTemplar extends PotionAbilityKit {
    public KitTemplar() {
        super(LanguageManager.kits.templar.name, Material.GOLDEN_SWORD, GameItems.templar(), PotionEffectType.ABSORPTION);

        setMasterDescription(LanguageManager.kits.templar.description);

        setPrice(1, 3500);
        setPrice(2, 8000);
        setPrice(3, 12500);
    }
}
