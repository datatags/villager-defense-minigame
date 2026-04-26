package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.Material;

public class GameSettingsMenu extends ArenaMenu {
    public GameSettingsMenu(Arena arena) {
        super("&8&lGame Settings", arena, new DynamicSizeLayout(true));

        // Option to change max waves
        addNavigation(Material.NETHERITE_SWORD,
                () -> new IncrementMenu("&3&lMaximum Waves", arena::getMaxWaves, arena::setMaxWaves),
                "&3&lMax Waves");

        // Option to wave time limit
        addNavigation(Material.CLOCK,
                () -> new IncrementMenu("&2&lWave Time Limit", arena::getWaveTimeLimit, arena::setWaveTimeLimit),
                "&2&lWave Time Limit");

        // Option to toggle dynamic wave time limit
        addButton(Material.SNOWBALL,
                ifClosed(p -> arena.setDynamicLimit(!arena.hasDynamicLimit())),
                () -> "&a&lDynamic Time Limit: " + getToggleStatus(arena.hasDynamicLimit()),
                        "&7Wave time limit adjusting based on",
                        "&7in-game difficulty");

        // Option to edit allowed kits
        addNavigation(Material.ENDER_CHEST,
                () -> new AllowedKitsMenu(arena, false),
                "&9&lAllowed Kits");

        // Option to edit forced challenges
        addNavigation(Material.NETHER_STAR,
                () -> new ForcedChallengesMenu(arena, false),
                "&9&lForced Challenges");

        // Option to edit difficulty label
        addNavigation(Material.NAME_TAG,
                DifficultyLabelMenu::new,
                "&6&lDifficulty Label");

        // Option to adjust overall difficulty multiplier
        addNavigation(Material.TURTLE_HELMET,
                DifficultyMultiplierMenu::new,
                "&4&lDifficulty Multiplier",
                "&7Determines difficulty increase rate");

        // Option to toggle dynamic difficulty
        addButton(Material.MAGMA_CREAM,
                ifClosed(p -> arena.setDynamicDifficulty(!arena.hasDynamicDifficulty())),
                () -> "&6&lDynamic Difficulty: " + getToggleStatus(arena.hasDynamicDifficulty()),
                "&7Difficulty adjusting based on",
                "&7number of players");

        // Option to toggle late arrival
        addButton(Material.DAYLIGHT_DETECTOR,
                ifClosed(p -> arena.setLateArrival(!arena.hasLateArrival())),
                () -> "&e&lLate Arrival: " + getToggleStatus(arena.hasLateArrival()),
                "&7Allows players to join after",
                "&7the game has started");

        // Option to toggle experience drop
        addButton(Material.EXPERIENCE_BOTTLE,
                ifClosed(p -> arena.setExpDrop(!arena.hasExpDrop())),
                () -> "&b&lExperience Drop: " + getToggleStatus(arena.hasExpDrop()),
                "&7Change whether experience drop or go",
                "&7straight into the killer's experience bar");

        // Option to toggle item dropping
        addButton(Material.EMERALD,
                ifClosed(p -> arena.setGemDrop(!arena.hasGemDrop())),
                () -> "&9&lItem Drop: " + getToggleStatus(arena.hasGemDrop()),
                "&7Change whether gems and loot drop",
                "&7as physical items or go straight",
                "&7into the killer's balance/inventory");

        // Option to set arena bounds
        addNavigation(Material.BEDROCK,
                ArenaBoundsMenu::new,
                "&4&lArena Bounds",
                "&7Bounds determine where players are",
                "&7allowed to go and where the game will",
                "&7function. Avoid building past arena bounds.");

        // Option to edit wolf cap
        addNavigation(Material.BONE,
                () -> new IncrementMenu("&6&lWolf Cap", arena::getWolfCap, arena::setWolfCap, true),
                "&6&lWolf Cap",
                "&7Maximum wolves a player can have");

        // Option to edit golem cap
        addNavigation(Material.IRON_INGOT,
                () -> new IncrementMenu("&e&lIron Golem Cap", arena::getGolemCap, arena::setGolemCap, true),
                "&e&lIron Golem Cap",
                "&7Maximum iron golems an arena can have");

        // Option to edit sounds
        addNavigation(Material.MUSIC_DISC_13,
                SoundsMenu::new,
                "&d&lSounds");

        // Option to copy game settings from another arena or a preset
        addButton(Material.WRITABLE_BOOK,
                ifClosed(p -> open(p, new CopySettingsMenu(arena))),
                "&f&lCopy Game Settings",
                "&7Copy settings of another arena or",
                "&7choose from a menu of presets");
    }
}
