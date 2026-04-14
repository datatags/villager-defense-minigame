package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import org.bukkit.Material;

public class KitTrader extends Kit {
    public KitTrader() {
        super(LanguageManager.kits.trader.name, Material.EMERALD);

        setMasterDescription(CommunicationManager.format(LanguageManager.kits.trader.description));
        setPrice(500);
    }
}
