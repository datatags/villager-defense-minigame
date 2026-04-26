package me.theguyhere.villagerdefense.plugin.visuals.layout;

import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.Map;

public interface Layout {
    public void add(ItemStack item);

    public void clear();

    public Map<Integer, ItemStack> getSlots();

    public default int getInventorySize() {
        return 54;
    }

    public void sort(Comparator<ItemStack> comparator);
}
