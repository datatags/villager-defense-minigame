package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;

public class KitOrc extends Kit {
    public KitOrc() {
        super(LanguageManager.kits.orc.name, Material.STICK);

        setMasterDescription(LanguageManager.kits.orc.description);
        setPrice(0);

        Map<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.KNOCKBACK, 5);
        addItems(ItemManager.createItem(Material.STICK, new ColoredMessage(ChatColor.GREEN,
                        LanguageManager.kits.orc.items.club).toString(), ItemManager.NORMAL_FLAGS, enchants));
    }
}
