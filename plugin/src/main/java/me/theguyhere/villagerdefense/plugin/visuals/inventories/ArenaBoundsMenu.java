package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.SingleRowLayout;
import org.bukkit.Material;

public class ArenaBoundsMenu extends ArenaMenu {
    public ArenaBoundsMenu(Arena arena) {
        super("&4&lArena Bounds: ", arena, new SingleRowLayout(true));

        addNavigation(Material.TORCH,
                () -> new LocationMenu("&b&lCorner 1: " + arena.getName(), arena::getCorner1, arena::setCorner1, this::checkClosed, true),
                () -> "&b&lCorner 1: " + (arena.getCorner1() == null ? "&c&lMissing" : "&a&lSet"));

        addNavigation(Material.SOUL_TORCH,
                () -> new LocationMenu("&9&lCorner 2: " + arena.getName(), arena::getCorner2, arena::setCorner2, this::checkClosed, true),
                () -> "&9&lCorner 2: " + (arena.getCorner2() == null ? "&c&lMissing" : "&a&lSet"));

        // Option to toggle arena border particles
        addButton(Material.FIREWORK_ROCKET,
                ifClosed(p -> arena.setBorderParticles(!arena.hasBorderParticles())),
                () -> "&4&lBorder Particles: " + getToggleStatus(arena.hasBorderParticles()));
    }
}
