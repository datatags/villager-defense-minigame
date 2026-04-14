package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import org.bukkit.Material;

public class KitMerchant extends Kit {
    public KitMerchant() {
        super(LanguageManager.kits.merchant.name, KitCategory.EFFECT, Material.EMERALD_BLOCK);

        setMasterDescription(String.format(LanguageManager.kits.merchant.description, "10%"));
        setPrice(4000);
    }
}
