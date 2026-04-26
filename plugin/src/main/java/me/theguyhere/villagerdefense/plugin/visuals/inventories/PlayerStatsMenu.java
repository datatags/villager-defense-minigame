package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.data.PlayerDataManager;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerStatsMenu extends Menu {
    private final Player player;
    public PlayerStatsMenu(Player player) {
        super("&2&l" + String.format(LanguageManager.messages.playerStatistics, player.getName()), new ManualLayout());
        this.player = player;

        ManualLayout l = (ManualLayout) layout;

        addStat(0, Material.DRAGON_HEAD, '4', "totalKills", LanguageManager.playerStats.totalKills.description);
        addStat(10, Material.ZOMBIE_HEAD, 'c', "topKills", LanguageManager.playerStats.topKills.description);
        addStat(2, Material.EMERALD_BLOCK, '2', "totalGems", LanguageManager.playerStats.totalGems.description);
        addStat(12, Material.EMERALD, 'a', "topBalance", LanguageManager.playerStats.topBalance.description);
        addStat(4, Material.GOLDEN_SWORD, '3', "topWave", LanguageManager.playerStats.topWave.description);

        l.setNextSlot(14);
        addNavigation(Material.GOLDEN_HELMET, () -> new PlayerAchievementsMenu(player),
                "&6&l" + LanguageManager.messages.achievements);

        // Kits
        l.setNextSlot(6);
        addNavigation(Material.ENDER_CHEST, () -> new PlayerKitsMenu(player, true),
                CommunicationManager.format("&9&l" + LanguageManager.messages.kits));

        // Reset stats
        l.setNextSlot(16);
        addNavigation(Material.LAVA_BUCKET, () -> new ConfirmationMenu("&4&l" + LanguageManager.messages.reset + "?", p -> {
                    PlayerDataManager.deletePlayerData(player.getUniqueId());
                    GameManager.refreshLeaderboards();
                    PlayerManager.notifySuccess(player, LanguageManager.confirms.reset);
                }),
                "&d&l" + LanguageManager.messages.reset,
                "&5&l" + LanguageManager.messages.resetWarning);

        // Crystal balance
        l.setNextSlot(8);
        addButton(Material.DIAMOND, NO_OP,
                "&b&l" + LanguageManager.messages.crystalBalance + ": &b" + PlayerDataManager.getPlayerCrystals(player.getUniqueId()));
    }

    private void addStat(int slot, Material mat, char color, String name, String displayName) {
        ((ManualLayout)layout).setNextSlot(slot);
        addButton(mat, NO_OP,
                "&" + color + "&l" + displayName + ": &" + color + PlayerDataManager.getPlayerStat(player.getUniqueId(), name),
                "&7" + LanguageManager.playerStats.topWave.description);
    }
}
