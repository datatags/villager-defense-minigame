package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class KitSoldier extends Kit {
    public KitSoldier() {
        super(LanguageManager.kits.soldier.name, Material.STONE_SWORD);

        setMasterDescription(LanguageManager.kits.soldier.description);
        setPrice(250);
        clearItems(); // clear wooden sword
        addItems(ItemManager.createItem(Material.STONE_SWORD, new ColoredMessage(ChatColor.GREEN,
                LanguageManager.kits.soldier.items.sword).toString()));
    }
}
