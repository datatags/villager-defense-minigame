package me.theguyhere.villagerdefense.plugin.visuals.layout;

import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ManualLayout implements Layout {
    private final Map<Integer, ItemStack> slots = new HashMap<>();
    @Setter
    private int nextSlot = -1;

    public void add(int slot, ItemStack item) {
        slots.put(slot, item);
    }

    @Override
    public void add(ItemStack item) {
        if (nextSlot != -1) {
            slots.put(nextSlot++, item);
            return;
        }
        for (int i = 0; i < 54; i++) {
            if (!slots.containsKey(i)) {
                slots.put(i, item);
                return;
            }
        }
    }

    @Override
    public void clear() {
        slots.clear();
        nextSlot = -1;
    }

    @Override
    public Map<Integer, ItemStack> getSlots() {
        return Collections.unmodifiableMap(slots);
    }

    @Override
    public int getInventorySize() {
        int max = 1;
        for (Integer slot : slots.keySet()) {
            max = Math.max(max, slot);
        }
        return Math.min(54, PagedLayout.ceilDiv(max, 9));
    }

    @Override
    public void sort(Comparator<ItemStack> comparator) {
    }
}
