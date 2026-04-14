package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.potion.PotionType;

public class KitAlchemist extends Kit {
    public KitAlchemist() {
        super(LanguageManager.kits.alchemist.name, Material.BREWING_STAND);

        setMasterDescription(LanguageManager.kits.alchemist.description);
        setPrice(300);
        addItems(ItemManager.createPotionItem(Material.SPLASH_POTION, PotionType.SWIFTNESS,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.alchemist.items.speed).toString()),
                ItemManager.createPotionItem(Material.SPLASH_POTION, PotionType.HEALING,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.alchemist.items.health).toString()),
                ItemManager.createPotionItem(Material.SPLASH_POTION, PotionType.HEALING,
                        new ColoredMessage(ChatColor.GREEN, LanguageManager.kits.alchemist.items.health).toString()));
    }
}
