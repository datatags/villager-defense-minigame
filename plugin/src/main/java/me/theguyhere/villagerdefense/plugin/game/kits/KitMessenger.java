package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class KitMessenger extends PotionAbilityKit {
    public KitMessenger() {
        super(LanguageManager.kits.messenger.name, Material.FEATHER, GameItems.messenger(), PotionEffectType.SPEED);

        setMasterDescription(LanguageManager.kits.messenger.description);

        setPrice(1, 4000);
        setPrice(2, 8000);
        setPrice(3, 12000);
    }
}
