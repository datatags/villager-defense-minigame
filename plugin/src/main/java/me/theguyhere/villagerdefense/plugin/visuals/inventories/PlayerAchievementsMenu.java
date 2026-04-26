package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.data.PlayerDataManager;
import me.theguyhere.villagerdefense.plugin.game.achievements.Achievement;
import me.theguyhere.villagerdefense.plugin.visuals.layout.PagedDynamicSizeLayout;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerAchievementsMenu extends Menu {
    private final List<String> achievements;
    public PlayerAchievementsMenu(Player player) {
        super("&6&l" + player.getName() + " " + LanguageManager.messages.achievements, new PagedDynamicSizeLayout());
        this.achievements = PlayerDataManager.getPlayerAchievements(player.getUniqueId());

        add(Achievement.topBalance1());
        add(Achievement.topBalance2());
        add(Achievement.topBalance3());
        add(Achievement.topBalance4());
        add(Achievement.topBalance5());
        add(Achievement.topBalance6());
        add(Achievement.topBalance7());
        add(Achievement.topBalance8());
        add(Achievement.topBalance9());

        add(Achievement.topKills1());
        add(Achievement.topKills2());
        add(Achievement.topKills3());
        add(Achievement.topKills4());
        add(Achievement.topKills5());
        add(Achievement.topKills6());
        add(Achievement.topKills7());
        add(Achievement.topKills8());
        add(Achievement.topKills9());

        add(Achievement.topWave1());
        add(Achievement.topWave2());
        add(Achievement.topWave3());
        add(Achievement.topWave4());
        add(Achievement.topWave5());
        add(Achievement.topWave6());
        add(Achievement.topWave7());
        add(Achievement.topWave8());
        add(Achievement.topWave9());

        add(Achievement.totalGems1());
        add(Achievement.totalGems2());
        add(Achievement.totalGems3());
        add(Achievement.totalGems4());
        add(Achievement.totalGems5());
        add(Achievement.totalGems6());
        add(Achievement.totalGems7());
        add(Achievement.totalGems8());
        add(Achievement.totalGems9());

        add(Achievement.totalKills1());
        add(Achievement.totalKills2());
        add(Achievement.totalKills3());
        add(Achievement.totalKills4());
        add(Achievement.totalKills5());
        add(Achievement.totalKills6());
        add(Achievement.totalKills7());
        add(Achievement.totalKills8());
        add(Achievement.totalKills9());

        add(Achievement.amputeeAlone());
        add(Achievement.blindAlone());
        add(Achievement.clumsyAlone());
        add(Achievement.dwarfAlone());
        add(Achievement.explosiveAlone());
        add(Achievement.featherweightAlone());
        add(Achievement.nakedAlone());
        add(Achievement.pacifistAlone());
        add(Achievement.uhcAlone());

        add(Achievement.amputeeBalance());
        add(Achievement.blindBalance());
        add(Achievement.clumsyBalance());
        add(Achievement.dwarfBalance());
        add(Achievement.explosiveBalance());
        add(Achievement.featherweightBalance());
        add(Achievement.nakedBalance());
        add(Achievement.pacifistBalance());
        add(Achievement.uhcBalance());

        add(Achievement.amputeeKills());
        add(Achievement.blindKills());
        add(Achievement.clumsyKills());
        add(Achievement.dwarfKills());
        add(Achievement.explosiveKills());
        add(Achievement.featherweightKills());
        add(Achievement.nakedKills());
        add(Achievement.pacifistKills());
        add(Achievement.uhcKills());

        add(Achievement.amputeeWave());
        add(Achievement.blindWave());
        add(Achievement.clumsyWave());
        add(Achievement.dwarfWave());
        add(Achievement.explosiveWave());
        add(Achievement.featherweightWave());
        add(Achievement.nakedWave());
        add(Achievement.pacifistWave());
        add(Achievement.uhcWave());

        add(Achievement.alone());
        add(Achievement.pacifistUhc());
        add(Achievement.allChallenges());
        add(Achievement.allGift());
        add(Achievement.allAbility());
        add(Achievement.maxedAbility());
        add(Achievement.allMaxedAbility());
        add(Achievement.allEffect());
        add(Achievement.allKits());
    }

    private void add(Achievement achievement) {
        addButton(achievement.getButton(achievements.contains(achievement.getName())), NO_OP);
    }
}
