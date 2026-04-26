package me.theguyhere.villagerdefense.plugin.visuals.layout;

import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DynamicSizeLayout implements Layout {
    private final List<ItemStack> buttons = new ArrayList<>();
    private final boolean exitButton;
    public DynamicSizeLayout(boolean exitButton) {
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
        int invSize = getInventorySize();
        int lines = invSize / 9;
        int fullSizedLines = buttons.size() / 9;
        int hangingButtons = buttons.size() % 9;
        Iterator<ItemStack> buttonIterator = buttons.iterator();

        // Ensure valid number of lines
        if (lines > 6) {
            throw new IllegalArgumentException("Too many items");
        }
        Map<Integer,ItemStack> slots = new HashMap<>();

        for (int i = 0; i < fullSizedLines; i++)
            for (int j = 0; j < 9; j++)
                slots.put(i * 9 + j, buttonIterator.next());
        for (int i = 0; i < hangingButtons; i++)
            slots.put((lines - 1) * 9 + (9 - hangingButtons) / 2 + i, buttonIterator.next());

        // Set exit button
        if (exitButton) {
            if (hangingButtons == 0)
                slots.put(invSize - 5, InventoryButtons.exit());
            else
                slots.put(invSize - 1, InventoryButtons.exit());
        }

        return slots;
    }

    @Override
    public int getInventorySize() {
        int buttonCount = buttons.size() + (exitButton ? 1 : 0);
        return (int)Math.ceil(buttonCount / 9.0) * 9;
    }

    @Override
    public void sort(Comparator<ItemStack> comparator) {
        buttons.sort(comparator);
    }
}
