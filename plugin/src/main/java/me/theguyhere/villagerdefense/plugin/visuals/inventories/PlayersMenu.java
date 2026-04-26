package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.Material;

public class PlayersMenu extends ArenaMenu {
    public PlayersMenu(Arena arena) {
        super("&d&lPlayer Settings", arena, new DynamicSizeLayout(true));

        // Option to edit player spawn
        addNavigation(Material.END_PORTAL_FRAME,
                () -> new LocationMenu("&d&lPlayer Spawn: " + arena.getName(), () -> arena.getPlayerSpawn().getLocation(), arena::setPlayerSpawn),
                "&5&lPlayer Spawn");

        // Option to toggle player spawn particles
        addButton(Material.FIREWORK_ROCKET, ifClosed(p -> arena.setSpawnParticles(!arena.hasSpawnParticles())),
                () -> "&d&lSpawn Particles: " + getToggleStatus(arena.hasSpawnParticles()),
                "&7Particles showing where the spawn is",
                "&7(Visible in-game)");

        // Option to edit waiting room
        addNavigation(Material.CLOCK, () -> new LocationMenu("&b&lWaiting Room: " + arena.getName(), arena::getWaitingRoom, arena::setWaitingRoom),
                "&b&lWaiting Room",
                "&7An optional room to wait in before",
                "&7the game starts");

        // Option to edit max players
        addButton(Material.NETHERITE_HELMET, ifClosed(p -> open(p, new IncrementMenu("&4&lMaximum Players", arena::getMaxPlayers, arena::setMaxPlayers))),
                () -> "&4&lMaximum Players: " + arena.getMaxPlayers(),
                "&7Maximum players the game will have");

        // Option to edit min players
        addButton(Material.NETHERITE_BOOTS, ifClosed(p -> open(p, new IncrementMenu("&2&lMinimum Players", arena::getMinPlayers, arena::setMinPlayers))),
                () -> "&2&lMinimum Players" + arena.getMinPlayers(),
                "&7Minimum players needed for game to start");
    }
}
