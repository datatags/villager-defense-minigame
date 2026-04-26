package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.Material;

public class SoundsMenu extends ArenaMenu {
    public SoundsMenu(Arena arena) {
        super("&d&lSounds", arena, new DynamicSizeLayout(true));

        addButton(Material.MUSIC_DISC_PIGSTEP, p -> arena.setWinSound(!arena.hasWinSound()),
                () -> "&a&lWin Sound: " + getToggleStatus(arena.hasWinSound()),
                "&7Played when game ends and players win");

        addButton(Material.MUSIC_DISC_11, p -> arena.setLoseSound(!arena.hasLoseSound()),
                () -> "&e&lLose Sound: " + getToggleStatus(arena.hasLoseSound()),
                "&7Played when game ends and players lose");

        addButton(Material.MUSIC_DISC_CAT, p -> arena.setWaveStartSound(!arena.hasWaveStartSound()),
                () -> "&2&lWave Start Sound: " + getToggleStatus(arena.hasWaveStartSound()),
                "&7Played when a wave starts");

        addButton(Material.MUSIC_DISC_BLOCKS, p -> arena.setWaveEndSound(!arena.hasWaveEndSound()),
                () -> "&9&lWave Finish Sound: " + getToggleStatus(arena.hasWaveEndSound()),
                "&7Played when a wave ends");

        addNavigation(Material.MUSIC_DISC_MELLOHI, WaitSoundMenu::new,
                () -> "&6&lWaiting Sound: &b&l" + arena.getWaitingSoundName(),
                "&7Played while players wait",
                "&7for the game to start");

        addButton(Material.MUSIC_DISC_FAR, p -> arena.setGemSound(!arena.hasGemSound()),
                () -> "&b&lGem Pickup Sound: " + getToggleStatus(arena.hasGemSound()),
                "&7Played when players pick up gems");

        addButton(Material.MUSIC_DISC_CHIRP, p -> arena.setPlayerDeathSound(!arena.hasPlayerDeathSound()),
                () -> "&4&lPlayer Death Sound: " + getToggleStatus(arena.hasPlayerDeathSound()),
                "&7Played when a player dies");

        addButton(Material.MUSIC_DISC_MALL, p -> arena.setAbilitySound(!arena.hasAbilitySound()),
                () -> "&d&lAbility Sound: " + getToggleStatus(arena.hasAbilitySound()),
                "&7Played when a player uses their ability");
    }
}
