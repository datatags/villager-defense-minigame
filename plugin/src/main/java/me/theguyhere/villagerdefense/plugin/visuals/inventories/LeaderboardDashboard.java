package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.data.GameDataManager;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.visuals.layout.SingleRowLayout;
import org.bukkit.Material;

public class LeaderboardDashboard extends Menu {
    public LeaderboardDashboard() {
        super("&e&lLeaderboards", new SingleRowLayout(true));

        // Option to modify total kills leaderboard
        add(Material.DRAGON_HEAD, "&4&lTotal Kills Leaderboard", "totalKills");

        // Option to modify top kills leaderboard
        add(Material.ZOMBIE_HEAD, "&c&lTop Kills Leaderboard", "topKills");

        // Option to modify total gems leaderboard
        add(Material.EMERALD_BLOCK, "&2&lTotal Gems Leaderboard", "totalGems");

        // Option to modify top balance leaderboard
        add(Material.EMERALD, "&a&lTop Balance Leaderboard", "topBalance");

        // Option to modify top wave leaderboard
        add(Material.GOLDEN_SWORD, "&9&lTop Wave Leaderboard", "topWave");
    }

    private void add(Material mat, String displayName, String locName) {
        addNavigation(mat, () -> new LocationMenu(displayName, () -> GameDataManager.getLeaderboardLocation(locName),
                loc -> GameManager.setLeaderboard(locName, loc)), displayName);
    }
}
