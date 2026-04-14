package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class KitKnight extends PotionAbilityKit {
    public KitKnight() {
        super(LanguageManager.kits.knight.name, Material.SHIELD, GameItems.knight(), PotionEffectType.RESISTANCE);

        setMasterDescription(LanguageManager.kits.knight.description);

        setPrice(1, 4500);
        setPrice(2, 8500);
        setPrice(3, 13000);
    }
}
