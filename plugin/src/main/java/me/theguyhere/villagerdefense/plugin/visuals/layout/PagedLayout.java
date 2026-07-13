package me.theguyhere.villagerdefense.plugin.visuals.layout;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class PagedLayout implements Layout {
    protected final List<ItemStack> buttons = new ArrayList<>();
    protected int page = 1;

    public void prevPage() {
        if (page > 1) {
            page--;
        }
    }

    public void nextPage() {
        if (page < totalPages()){
            page++;
        }
    }

    public abstract int totalPages();

    @Override
    public void sort(Comparator<ItemStack> comparator) {
        buttons.sort(comparator);
    }

    static int ceilDiv(int val, int divisor) {
        // Floating-point math was causing me problems here so we do it the manual way.
        int result = val / divisor;
        if (result * divisor < val) {
            result++;
        }
        return result;
    }
}
