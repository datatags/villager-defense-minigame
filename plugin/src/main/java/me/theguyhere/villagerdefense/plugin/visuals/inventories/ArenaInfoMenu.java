package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.common.Constants;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ArenaInfoMenu extends ArenaMenu {
    public ArenaInfoMenu(Arena arena) {
        super("&6&l" + String.format(LanguageManager.messages.arenaInfo, arena.getName()), arena, new DynamicSizeLayout(true));

        addButton(Material.NETHERITE_HELMET, NO_OP,
                () -> "&4&l" + LanguageManager.arenaStats.maxPlayers.name + ": &4" + arena.getMaxPlayers(),
                format(LanguageManager.arenaStats.maxPlayers.description));

        addButton(Material.NETHERITE_BOOTS, NO_OP,
                () -> "&2&l" + LanguageManager.arenaStats.minPlayers.name + ": &2" + arena.getMinPlayers(),
                format(LanguageManager.arenaStats.minPlayers.description));

        addButton(Material.GOLDEN_SWORD, NO_OP,
                () -> "&3&l" + LanguageManager.arenaStats.maxWaves.name + ": &3"
                        + (arena.getMaxWaves() > 0 ? arena.getMaxWaves() : LanguageManager.messages.unlimited),
                format(LanguageManager.arenaStats.maxWaves.description));

        String limit;
        if (arena.getWaveTimeLimit() < 0) {
            limit = LanguageManager.messages.unlimited;
        } else {
            limit = arena.getWaveTimeLimit() + " minute(s)";
        }
        addButton(Material.CLOCK, NO_OP,
                () -> "&9&l" + LanguageManager.arenaStats.timeLimit.name + ": &9"
                        + (arena.getWaveTimeLimit() > 0 ? arena.getWaveTimeLimit() + " minute(s)" : LanguageManager.messages.unlimited),
                format(LanguageManager.arenaStats.timeLimit.description));

        addButton(Material.BONE, NO_OP,
                () -> "&6&l" + LanguageManager.arenaStats.wolfCap.name + ": &6" + arena.getWolfCap(),
                format(LanguageManager.arenaStats.wolfCap.description));

        addButton(Material.IRON_INGOT, NO_OP,
                () -> "&e&l" + LanguageManager.arenaStats.golemCap.name + ": &e" + arena.getGolemCap(),
                format(LanguageManager.arenaStats.golemCap.description));

        addNavigation(Material.ENDER_CHEST, () -> new AllowedKitsMenu(arena, true),
                "&9&l" + LanguageManager.messages.allowedKits);

        addNavigation(Material.NETHER_STAR, () -> new ForcedChallengesMenu(arena, true),
                "&9&l" + LanguageManager.messages.forcedChallenges);

        addButton(Material.SLIME_BALL, NO_OP,
                () -> "&e&l" + LanguageManager.arenaStats.dynamicMobCount.name + ": " + getToggleStatus(arena.hasDynamicCount()),
                format(LanguageManager.arenaStats.dynamicMobCount.description));

        addButton(Material.MAGMA_CREAM, NO_OP,
                () -> "&6&l" + LanguageManager.arenaStats.dynamicDifficulty.name + ": " + getToggleStatus(arena.hasDynamicDifficulty()),
                format(LanguageManager.arenaStats.dynamicDifficulty.description));

        addButton(Material.NETHER_STAR, NO_OP,
                () -> "&b&l" + LanguageManager.arenaStats.dynamicPrices.name + ": " + getToggleStatus(arena.hasDynamicPrices()),
                format(LanguageManager.arenaStats.dynamicPrices.description));

        addButton(Material.SNOWBALL, NO_OP,
                () -> "&a&l" + LanguageManager.arenaStats.dynamicTimeLimit.name + ": " + getToggleStatus(arena.hasDynamicLimit()),
                format(LanguageManager.arenaStats.dynamicTimeLimit.description));

        addButton(Material.DAYLIGHT_DETECTOR, NO_OP,
                () -> "&e&l" + LanguageManager.arenaStats.lateArrival.name + ": " + getToggleStatus(arena.hasLateArrival()),
                format(LanguageManager.arenaStats.lateArrival.description));

        addButton(Material.EMERALD, NO_OP,
                () -> "&9&l" + LanguageManager.arenaStats.gemDrop.name + ": " + getToggleStatus(arena.hasGemDrop()),
                format(LanguageManager.arenaStats.gemDrop.description));

        addButton(Material.EXPERIENCE_BOTTLE, NO_OP,
                () -> "&b&l" + LanguageManager.arenaStats.expDrop.name + ": " + getToggleStatus(arena.hasExpDrop()),
                format(LanguageManager.arenaStats.expDrop.description));

        addButton(Material.FIREWORK_ROCKET, NO_OP,
                () -> "&e&l" + LanguageManager.names.playerSpawnParticles + ": " + getToggleStatus(arena.hasSpawnParticles()));

        addButton(Material.FIREWORK_ROCKET, NO_OP,
                () -> "&d&l" + LanguageManager.names.monsterSpawnParticles + ": " + getToggleStatus(arena.hasMonsterParticles()));

        addButton(Material.FIREWORK_ROCKET, NO_OP,
                () -> "&a&l" + LanguageManager.names.villagerSpawnParticles + ": " + getToggleStatus(arena.hasVillagerParticles()));

        addButton(Material.EMERALD_BLOCK, NO_OP,
                () -> "&6&l" + LanguageManager.names.defaultShop + ": " + getToggleStatus(arena.hasNormal()));

        addButton(Material.QUARTZ_BLOCK, NO_OP,
                () -> "&2&l" + LanguageManager.names.customShop + ": " + getToggleStatus(arena.hasCustom()));

        addButton(Material.BOOKSHELF, NO_OP,
                () -> "&3&l" + LanguageManager.names.enchantShop + ": " + getToggleStatus(arena.hasEnchants()));

        addButton(Material.CHEST, NO_OP,
                () -> "&d&l" + LanguageManager.names.communityChest + ": " + getToggleStatus(arena.hasCommunity()));

        addNavigation(Material.QUARTZ, () -> new CustomShopEditorMenu(arena, true),
                CommunicationManager.format("&f&l" + LanguageManager.messages.customShopInv));

        addButton(Material.TURTLE_HELMET, NO_OP,
                () -> "&4&l" + LanguageManager.arenaStats.difficultyMultiplier.name + ": &4" + arena.getDifficultyMultiplier(),
                format(LanguageManager.arenaStats.difficultyMultiplier.description));

        List<String> records = new ArrayList<>();
        arena.getSortedDescendingRecords().forEach(arenaRecord -> {
            records.add(CommunicationManager.format("&f" + LanguageManager.messages.wave + " " +
                    arenaRecord.getWave()));
            for (int i = 0; i < arenaRecord.getPlayers().size() / 4 + 1; i++) {
                StringBuilder players = new StringBuilder(CommunicationManager.format("&7"));
                if (i * 4 + 4 < arenaRecord.getPlayers().size()) {
                    for (int j = i * 4; j < i * 4 + 4; j++)
                        players.append(arenaRecord.getPlayers().get(j)).append(", ");
                    records.add(CommunicationManager.format(players.substring(0, players.length() - 1)));
                } else {
                    for (int j = i * 4; j < arenaRecord.getPlayers().size(); j++)
                        players.append(arenaRecord.getPlayers().get(j)).append(", ");
                    records.add(CommunicationManager.format(players.substring(0, players.length() - 2)));
                }
            }
        });
        addButton(ItemManager.createItem(Material.GOLDEN_HELMET,
                CommunicationManager.format("&e&l" + LanguageManager.messages.arenaRecords), records),
                NO_OP);
    }

    private String[] format(String desc) {
        return CommunicationManager.formatDescriptionArr(ChatColor.GRAY, desc, Constants.LORE_CHAR_LIMIT);
    }
}
