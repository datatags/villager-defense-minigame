package me.theguyhere.villagerdefense.plugin.visuals.layout;

import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SingleRowLayout implements Layout {
    private static final int[][] WITHOUT_EXIT = new int[][]{
            { 4 },
            { 2, 6 },
            { 2, 4, 6 },
            { 1, 3, 5, 7 },
            { 0, 2, 4, 6, 8 },
            { 1, 2, 3, 4, 5, 6 },
            { 1, 2, 3, 4, 5, 6, 7 },
            { 0, 1, 2, 3, 4, 5, 6, 7 },
            { 0, 1, 2, 3, 4, 5, 6, 7, 8 },
    };
    private static final int[][] WITH_EXIT = new int[][]{
            { 4 },
            { 2, 5 },
            { 1, 3, 5 },
            { 0, 2, 4, 6 },
            { 1, 2, 3, 4, 5 },
            { 1, 2, 3, 4, 5, 6 },
            { 0, 1, 2, 3, 4, 5, 6 },
            { 0, 1, 2, 3, 4, 5, 6, 7 },
    };
    private final List<ItemStack> buttons = new ArrayList<>();
    private final boolean exitButton;

    public SingleRowLayout(boolean exitButton) {
        this.exitButton = exitButton;
    }

    @Override
    public void add(ItemStack item) {
        buttons.add(item);
    }

    @Override
    public void clear() {
        buttons.clear();
    }

    @Override
    public Map<Integer, ItemStack> getSlots() {
        Map<Integer, ItemStack> slots = new HashMap<>();
        int buttonCount = buttons.size() + (exitButton ? 1 : 0);
        if (buttonCount > 9) {
            throw new IllegalArgumentException("Only 9 items can fit in a single row, but got " + buttonCount);
        }
        int[] arrangement = exitButton ? WITH_EXIT[buttons.size() - 1] : WITHOUT_EXIT[buttons.size() - 1];
        for (int i = 0; i < buttons.size(); i++) {
            slots.put(arrangement[i], buttons.get(i));
        }
        // Set exit button
        if (exitButton) {
            slots.put(8, InventoryButtons.exit());
        }

        return slots;
    }

    @Override
    public int getInventorySize() {
        return 9;
    }

    @Override
    public void sort(Comparator<ItemStack> comparator) {
        buttons.sort(comparator);
    }
}
