package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class KitPhantom extends Kit {
    public KitPhantom() {
        super(LanguageManager.kits.phantom.name, Material.PHANTOM_MEMBRANE);

        setMasterDescription(CommunicationManager.format(
                new ColoredMessage(ChatColor.GRAY, LanguageManager.kits.phantom.description),
                "/vd join")
        );
        setPrice(6000);
    }
}
