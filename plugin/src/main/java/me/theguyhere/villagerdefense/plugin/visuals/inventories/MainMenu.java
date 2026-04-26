package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.data.GameDataManager;
import me.theguyhere.villagerdefense.plugin.visuals.layout.SingleRowLayout;
import org.bukkit.Material;

public class MainMenu extends Menu {
    public MainMenu() {
        super("&2&lVillager Defense", new SingleRowLayout(true));
        // Option to set lobby location
        addNavigation(Material.BELL, () -> new LocationMenu("&2&lLobby", GameDataManager::getLobbyLocation, GameDataManager::setLobbyLocation),
                "&2&lLobby", "&7Manage minigame lobby");

        // Option to set info boards
        addNavigation(Material.OAK_SIGN, InfoDashboard::new, "&6&lInfo Boards", "&7Manage info boards");

        // Option to set leaderboards
        addNavigation(Material.GOLDEN_HELMET, LeaderboardDashboard::new, "&e&lLeaderboards", "&7Manage leaderboards");

        // Option to edit arenas
        addNavigation(Material.NETHERITE_AXE, ArenaDashboard::new, "&9&lArenas", "&7Manage arenas");
    }
}
