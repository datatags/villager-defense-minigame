package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class KitWarrior extends PotionAbilityKit {
    public KitWarrior() {
        super(LanguageManager.kits.warrior.name, Material.NETHERITE_HELMET, GameItems.warrior(), PotionEffectType.STRENGTH);

        setMasterDescription(LanguageManager.kits.warrior.description);

        setPrice(1, 5000);
        setPrice(2, 9000);
        setPrice(3, 14000);
    }
}
