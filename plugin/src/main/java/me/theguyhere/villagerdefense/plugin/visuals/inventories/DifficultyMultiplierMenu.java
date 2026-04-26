package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.SingleRowLayout;
import org.bukkit.Material;

public class DifficultyMultiplierMenu extends ArenaMenu {
    public DifficultyMultiplierMenu(Arena arena) {
        super("&4&lDifficulty Multiplier", arena, new SingleRowLayout(true));

        addLabel('b', 1, Material.LIGHT_BLUE_CONCRETE);
        addLabel('a', 2, Material.LIME_CONCRETE);
        addLabel('6', 3, Material.YELLOW_CONCRETE);
        addLabel('4', 4, Material.RED_CONCRETE);
    }

    private void addLabel(char color, int value, Material mat) {
        addButton(mat, p -> arena.setDifficultyMultiplier(value), "&" + color + "&l" + value);
    }

    @Override
    public String getName() {
        return super.getName() + ": " + arena.getDifficultyMultiplier();
    }
}
