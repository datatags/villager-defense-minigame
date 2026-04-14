package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class KitSummoner extends Kit {
    public KitSummoner() {
        super(LanguageManager.kits.summoner.name, KitCategory.GIFT, Material.POLAR_BEAR_SPAWN_EGG);

        addLevelDescriptions(1, LanguageManager.kits.summoner.description1);
        addLevelDescriptions(2, LanguageManager.kits.summoner.description2);
        addLevelDescriptions(3, LanguageManager.kits.summoner.description3);

        setPrice(1, 750);
        setPrice(2, 1750);
        setPrice(3, 4500);

        addItems(1, ItemManager.createItem(Material.WOLF_SPAWN_EGG,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.summoner.items.wolf).toString()));
        addItems(2, ItemManager.createItems(Material.WOLF_SPAWN_EGG, 2,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.summoner.items.wolf).toString()));
        addItems(3, ItemManager.createItem(Material.GHAST_SPAWN_EGG,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.summoner.items.golem).toString()));
    }
}
