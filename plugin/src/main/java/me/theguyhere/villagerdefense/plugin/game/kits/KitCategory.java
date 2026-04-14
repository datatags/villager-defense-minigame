package me.theguyhere.villagerdefense.plugin.game.kits;

import lombok.Getter;
import org.bukkit.ChatColor;

/**
 * Kit types in Villager Defense. Possible types:<ul>
 * <li>{@link #NONE}</li>
 * <li>{@link #GIFT}</li>
 * <li>{@link #ABILITY}</li>
 * <li>{@link #EFFECT}</li>
 * </ul>
 */
public enum KitCategory {
    /**
     * The default kit with no benefits.
     */
    NONE(ChatColor.WHITE),
    /**
     * Kits give one-time benefits per game or respawn.
     */
    GIFT(ChatColor.GREEN),
    /**
     * Kits give a special ability per respawn.
     */
    ABILITY(ChatColor.LIGHT_PURPLE),
    /**
     * Kits give players a permanent special effect.
     */
    EFFECT(ChatColor.YELLOW),
    ;
    @Getter
    private final String prefix;

    private KitCategory(ChatColor color) {
        this.prefix = "&" + color.getChar() + "&l";
    }
}
