package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.data.GameDataManager;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import org.bukkit.Material;

public class InfoDashboard extends LocationDashboard {
    public InfoDashboard() {
        super("&6&lInfo Board", "Info Board", Material.BIRCH_SIGN, GameDataManager.getInfoBoardIDs(),
                GameDataManager::getInfoBoardLocation, GameManager::setInfoBoard, GameManager::newInfoBoardID);
    }
}
