package me.theguyhere.villagerdefense.plugin.visuals.layout;

import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FrozenPagedDynamicSizeLayout extends PagedDynamicSizeLayout {
    private final boolean exitButton;
    private List<ItemStack> frozenRowButtons;
    public FrozenPagedDynamicSizeLayout(boolean exitButton) {
        this.exitButton = exitButton;
    }

    public void setFrozenRowButtons(List<ItemStack> frozenButtons) {
        this.frozenRowButtons = new ArrayList<>(frozenButtons);
    }

    @Override
    public Map<Integer, ItemStack> getSlots() {
        int freeSpaces = 36;
        int pages = PagedLayout.ceilDiv(buttons.size(), freeSpaces);
        List<ItemStack> visibleButtons = buttons.subList(
                (page - 1) * freeSpaces,
                Math.min(page * freeSpaces, buttons.size())
        );

        Map<Integer, ItemStack> slots = new HashMap<>();
        int lines = (visibleButtons.size() + 8) / 9 + 2;
        int invSize = lines * 9;
        int fullSizedLines = visibleButtons.size() / 9;
        int hangingButtons = visibleButtons.size() % 9;
        Iterator<ItemStack> buttonIterator = visibleButtons.iterator();

        // Set buttons
        for (int i = 0; i < fullSizedLines; i++)
            for (int j = 0; j < 9; j++)
                slots.put(i * 9 + j, buttonIterator.next());
        for (int i = 0; i < hangingButtons; i++)
            slots.put((lines - 3) * 9 + (9 - hangingButtons) / 2 + i, buttonIterator.next());

        Iterator<ItemStack> freezeButtonIterator = frozenRowButtons.iterator();
        int frozenButtonsNum = frozenRowButtons.size();

        switch (frozenButtonsNum) {
            case 1:
                slots.put((lines - 2) * 9 + 4, freezeButtonIterator.next());
                break;
            case 2:
                slots.put((lines - 2) * 9 + 2, freezeButtonIterator.next());
                slots.put((lines - 2) * 9 + 6, freezeButtonIterator.next());
                break;
            case 3:
                slots.put((lines - 2) * 9 + 2, freezeButtonIterator.next());
                slots.put((lines - 2) * 9 + 4, freezeButtonIterator.next());
                slots.put((lines - 2) * 9 + 6, freezeButtonIterator.next());
                break;
            case 4:
                for (int j = 0; j < frozenButtonsNum; j++)
                    slots.put((lines - 2) * 9 + j * 2 + 1, freezeButtonIterator.next());
                break;
            case 5:
                for (int j = 0; j < frozenButtonsNum; j++)
                    slots.put((lines - 2) * 9 + j * 2, freezeButtonIterator.next());
                break;
            case 6:
            case 7:
                for (int j = 0; j < frozenButtonsNum; j++)
                    slots.put((lines - 2) * 9 + j + 1, freezeButtonIterator.next());
                break;
            default:
                for (int j = 0; j < frozenButtonsNum; j++)
                    slots.put((lines - 2) * 9 + j, freezeButtonIterator.next());
        }

        // Set exit button
        if (exitButton) {
            if (newButton != null) {
                slots.put(invSize - 1, InventoryButtons.exit());
            } else {
                slots.put(invSize - 5, InventoryButtons.exit());
            }
        }

        // Set new button
        if (newButton != null) {
            slots.put(invSize - 5, newButton);
        }

        // Set page navigation buttons
        if (page > 1)
            slots.put(invSize - 7, InventoryButtons.previousPage());

        if (page < pages)
            slots.put(invSize - 3, InventoryButtons.nextPage());

        return slots;
    }

    @Override
    public int totalPages() {
        return ceilDiv(buttons.size(), 36);
    }
}
