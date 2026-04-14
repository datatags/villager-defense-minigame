package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class KitPriest extends PotionAbilityKit {
    public KitPriest() {
        super(LanguageManager.kits.priest.name, Material.TOTEM_OF_UNDYING, GameItems.priest(), PotionEffectType.REGENERATION);

        setMasterDescription(LanguageManager.kits.priest.description);

        setPrice(1, 5000);
        setPrice(2, 9000);
        setPrice(3, 15000);
    }
}
