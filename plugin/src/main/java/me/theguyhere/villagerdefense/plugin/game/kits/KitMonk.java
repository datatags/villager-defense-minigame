package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class KitMonk extends PotionAbilityKit {
    public KitMonk() {
        super(LanguageManager.kits.monk.name, Material.BELL, GameItems.monk(), PotionEffectType.HASTE);

        setMasterDescription(LanguageManager.kits.monk.description);

        setPrice(1, 3000);
        setPrice(2, 7000);
        setPrice(3, 11000);
    }
}
