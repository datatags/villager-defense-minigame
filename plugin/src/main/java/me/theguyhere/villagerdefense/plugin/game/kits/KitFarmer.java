package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class KitFarmer extends Kit {
    public KitFarmer() {
        super(LanguageManager.kits.farmer.name, Material.CARROT);

        setMasterDescription(LanguageManager.kits.farmer.description);
        setPrice(0);
        addItems(ItemManager.createItems(Material.CARROT, 5, new ColoredMessage(ChatColor.GREEN,
                        LanguageManager.kits.farmer.items.carrot).toString()));
    }
}
