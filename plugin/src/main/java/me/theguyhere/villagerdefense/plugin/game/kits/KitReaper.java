package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class KitReaper extends Kit {
    public KitReaper() {
        super(LanguageManager.kits.reaper.name, Material.NETHERITE_HOE);

        addLevelDescriptions(1, String.format(LanguageManager.kits.reaper.description, "III"));
        addLevelDescriptions(2, String.format(LanguageManager.kits.reaper.description, "V"));
        addLevelDescriptions(3, String.format(LanguageManager.kits.reaper.description, "VIII"));

        setPrice(1, 750);
        setPrice(2, 2000);
        setPrice(3, 4000);

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.SHARPNESS, 3);

        clearItems();
        addItems(1, ItemManager.createItem(
                Material.NETHERITE_HOE,
                new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.reaper.items.scythe).toString(),
                ItemManager.NORMAL_FLAGS,
                enchants
        ));

        enchants.put(Enchantment.SHARPNESS, 5);
        addItems(2, ItemManager.createItem(
                Material.NETHERITE_HOE,
                new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.reaper.items.scythe).toString(),
                ItemManager.NORMAL_FLAGS,
                enchants
        ));

        enchants.put(Enchantment.SHARPNESS, 8);
        addItems(3, ItemManager.createItem(
                Material.NETHERITE_HOE,
                new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.reaper.items.scythe).toString(),
                ItemManager.NORMAL_FLAGS,
                enchants
        ));
    }
}
