package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class KitTailor extends Kit {
    public KitTailor() {
        super(LanguageManager.kits.tailor.name, Material.LEATHER_CHESTPLATE);

        setMasterDescription(CommunicationManager.format(LanguageManager.kits.tailor.description));
        setPrice(400);
        addItems(ItemManager.createItem(Material.LEATHER_HELMET,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.tailor.items.helmet).toString()),
                ItemManager.createItem(Material.LEATHER_CHESTPLATE,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.tailor.items.chestplate).toString()),
                ItemManager.createItem(Material.LEATHER_LEGGINGS,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.tailor.items.leggings).toString()),
                ItemManager.createItem(Material.LEATHER_BOOTS,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.tailor.items.boots).toString()));
    }
}
