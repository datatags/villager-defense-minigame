package me.theguyhere.villagerdefense.plugin.visuals.layout;

import lombok.Setter;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PagedDynamicSizeLayout extends PagedLayout {
    @Setter
    protected ItemStack newButton;

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
        List<ItemStack> visibleButtons = buttons.subList(
                (page - 1) * 45,
                Math.min(page * 45, buttons.size())
        );

        int lines = (visibleButtons.size() + 8) / 9 + 1;
        int invSize = 54;
        int fullSizedLines = visibleButtons.size() / 9;
        int hangingButtons = visibleButtons.size() % 9;
        Iterator<ItemStack> buttonIterator = visibleButtons.iterator();
        Map<Integer,ItemStack> slots = new HashMap<>();

        for (int i = 0; i < fullSizedLines; i++)
            for (int j = 0; j < 9; j++)
                slots.put(i * 9 + j, buttonIterator.next());
        for (int i = 0; i < hangingButtons; i++)
            slots.put((lines - 2) * 9 + (9 - hangingButtons) / 2 + i, buttonIterator.next());

        if (newButton != null) {
            slots.put(invSize - 1, InventoryButtons.exit());
            slots.put(invSize - 5, newButton);
        } else {
            slots.put(invSize - 5, InventoryButtons.exit());
        }

        // Set page navigation buttons
        if (page > 1)
            slots.put(invSize - 7, InventoryButtons.previousPage());

        if (page < totalPages())
            slots.put(invSize - 3, InventoryButtons.nextPage());

        return slots;
    }

    @Override
    public int totalPages() {
        return ceilDiv(buttons.size(), 45);
    }
}
