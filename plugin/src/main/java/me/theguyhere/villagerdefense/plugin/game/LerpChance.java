package me.theguyhere.villagerdefense.plugin.game;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LerpChance<T> {
    private final List<LerpEntry<T>> entries = new ArrayList<>();

    public LerpChance() {
    }

    /**
     * Add an item to the selector.
     * @param position The position at which the item is guaranteed to be chosen.
     * @param value The item to place at this position
     * @return This object
     */
    public LerpChance<T> add(double position, T value) {
        // Keep entries sorted by position
        LerpEntry<T> entry = new LerpEntry<>(position, value);
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).compareTo(entry) > 0) {
                entries.add(i, entry);
                return this;
            }
        }
        // List exhauted, add it on the end
        entries.add(entry);
        return this;
    }

    /**
     * Choose an item from the list given the desired position. If position is lower than any entry in the list,
     * the first item is returned. If {@code position} is higher than any entry in the list, the last item is returned.
     * Otherwise, the option is randomly chosen based on a linearly-interpolated chance from the two items
     * {@code position} falls between.
     *
     * @param position The position to choose from in the list.
     * @return The chosen item, or null if none applies
     */
    public T choose(double position) {
        if (entries.isEmpty()) {
            return null;
        }
        if (position < entries.get(0).position) {
            return entries.get(0).value;
        }
        for (int i = 0; i < entries.size() - 1; i++) {
            double floor = entries.get(i).position;
            double ceiling = entries.get(i + 1).position;
            if (position > ceiling) {
                continue;
            }
            double range = ceiling - floor;
            if (ThreadLocalRandom.current().nextDouble() < (position - floor) / range) {
                return entries.get(i).value;
            } else {
                return entries.get(i + 1).value;
            }
        }
        // Past the top of the list, return the last option
        return entries.get(entries.size() - 1).value;
    }

    private static class LerpEntry<T> implements Comparable<LerpEntry<T>> {
        final double position;
        final T value;

        LerpEntry(double position, T value) {
            this.position = position;
            this.value = value;
        }

        @Override
        public int compareTo(@NotNull LerpChance.LerpEntry<T> o) {
            return Double.compare(this.position, o.position);
        }
    }
}
