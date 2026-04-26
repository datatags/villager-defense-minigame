package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.ArenaDataManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.exceptions.ArenaNotFoundException;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import me.theguyhere.villagerdefense.plugin.visuals.layout.FrozenPagedDynamicSizeLayout;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CopySettingsMenu extends ArenaMenu {
    public CopySettingsMenu(Arena arena) {
        super("&8&lCopy Game Settings", arena, new FrozenPagedDynamicSizeLayout(true));

        List<ItemStack> frozenButtons = new ArrayList<>();

        // Options to choose any of the other arenas
        ArenaDataManager.getArenaIDs()
                .forEach(id -> {
                    if (id != arena.getId()) {
                        try {
                            Arena other = GameManager.getArena(id);
                            addButton(Material.GRAY_GLAZED_TERRACOTTA, p -> arena.copy(other),
                                            "&a&lCopy " + other.getName());
                        } catch (ArenaNotFoundException ignored) {}
                    }
                });

        addPreset(frozenButtons, Material.LIME_CONCRETE, "&a&lEasy", () -> {
            arena.setMaxWaves(45);
            arena.setWaveTimeLimit(5);
            arena.setDifficultyMultiplier(1);
            arena.setDynamicCount(false);
            arena.setDynamicDifficulty(false);
            arena.setDynamicLimit(true);
            arena.setDynamicPrices(false);
            arena.setDifficultyLabel("Easy");
        });

        addPreset(frozenButtons, Material.YELLOW_CONCRETE, "&e&lMedium", () -> {
            arena.setMaxWaves(50);
            arena.setWaveTimeLimit(4);
            arena.setDifficultyMultiplier(2);
            arena.setDynamicCount(false);
            arena.setDynamicDifficulty(false);
            arena.setDynamicLimit(true);
            arena.setDynamicPrices(true);
            arena.setDifficultyLabel("Medium");
        });

        addPreset(frozenButtons, Material.RED_CONCRETE, "&c&lHard", () -> {
            arena.setMaxWaves(60);
            arena.setWaveTimeLimit(3);
            arena.setDifficultyMultiplier(3);
            arena.setDynamicCount(true);
            arena.setDynamicDifficulty(true);
            arena.setDynamicLimit(true);
            arena.setDynamicPrices(true);
            arena.setDifficultyLabel("Hard");
        });

        addPreset(frozenButtons, Material.MAGENTA_CONCRETE, "&d&lInsane", () -> {
            arena.setMaxWaves(-1);
            arena.setWaveTimeLimit(3);
            arena.setDifficultyMultiplier(4);
            arena.setDynamicCount(true);
            arena.setDynamicDifficulty(true);
            arena.setDynamicLimit(false);
            arena.setDynamicPrices(true);
            arena.setDifficultyLabel("Insane");
        });

        ((FrozenPagedDynamicSizeLayout)layout).setFrozenRowButtons(frozenButtons);
    }

    private void addPreset(List<ItemStack> frozen, Material mat, String displayName, Runnable runnable) {
        ItemStack item = ItemManager.createItem(mat, CommunicationManager.format(displayName + " Preset"));
        frozen.add(item);
        addClickHandler(item, p -> runnable.run());
    }
}
