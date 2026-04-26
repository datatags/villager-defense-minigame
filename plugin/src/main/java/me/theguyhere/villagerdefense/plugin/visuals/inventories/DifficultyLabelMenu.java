package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.Material;

public class DifficultyLabelMenu extends ArenaMenu {
    public DifficultyLabelMenu(Arena arena) {
        super("&6&lDifficulty Label", arena, new DynamicSizeLayout(true));

        addDifficulty("Easy", Material.LIME_CONCRETE);
        addDifficulty("Medium", Material.YELLOW_CONCRETE);
        addDifficulty("Hard", Material.RED_CONCRETE);
        addDifficulty("Insane", Material.MAGENTA_CONCRETE);
        addDifficulty(null, Material.LIGHT_GRAY_CONCRETE);
    }

    private void addDifficulty(String name, Material mat) {
        addButton(mat, ifClosed(p -> arena.setDifficultyLabel(name)), labelDisplayName(name, true));
    }

    private String labelDisplayName(String label, boolean renderNone) {
        if (label == null) {
            label = "None";
        }
        switch (label) {
            case "Easy":
                return "&a&l" + LanguageManager.names.easy;
            case "Medium":
                return "&e&l" + LanguageManager.names.medium;
            case "Hard":
                return "&c&l" + LanguageManager.names.hard;
            case "Insane":
                return "&d&l" + LanguageManager.names.insane;
            default:
                return renderNone ? "&7&l" + LanguageManager.names.none : "";
        }
    }

    @Override
    public String getName() {
        return super.getName() + ": " + labelDisplayName(arena.getDifficultyLabel(), false);
    }
}
