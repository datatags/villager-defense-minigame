package me.theguyhere.villagerdefense.plugin.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

public class LevelledWeightedRandom<T> {
    private final T defaultValue;
    private final int totalWeight;
    private final List<Map<Function<Integer, T>, Integer>> levels;

    public LevelledWeightedRandom(T defaultValue, int totalWeight, List<Map<Function<Integer, T>, Integer>> levels) {
        this.defaultValue = defaultValue;
        this.totalWeight = totalWeight;
        this.levels = levels;
        if (totalWeight == -1) {
            return;
        }

        for (int i = 0; i < levels.size(); i++) {
            int levelTotalWeight = levels.get(i).values().stream().reduce(0, Integer::sum);
            if (levelTotalWeight > totalWeight) {
                throw new IllegalArgumentException("Level " + i + " total weight is " + levelTotalWeight + " which is greater than the weight limit " + totalWeight);
            }
            if (defaultValue == null && levelTotalWeight < totalWeight) {
                throw new IllegalArgumentException("Level " + i + " total weight is " + levelTotalWeight + " which is less than the fixed weight " + totalWeight);
            }
        }
    }

    public T getRandom(int level) {
        if (level < 1) {
            level = 1;
        }
        if (level > levels.size()) {
            level = levels.size();
        }
        // sums all values in map
        Map<Function<Integer, T>, Integer> map = levels.get(level - 1);
        int totalWeight;
        if (this.totalWeight == -1 && defaultValue == null) {
            totalWeight = map.values().stream().reduce(0, Integer::sum);
        } else {
            totalWeight = this.totalWeight;
        }
        if (totalWeight > 0) {
            int index = ThreadLocalRandom.current().nextInt(totalWeight);
            for (Map.Entry<Function<Integer, T>, Integer> entry : map.entrySet()) {
                index -= entry.getValue();
                if (index < 0) {
                    return entry.getKey().apply(level);
                }
            }
        }
        return defaultValue;
    }

    public int levelCount() {
        return levels.size();
    }

    public static class Builder<T> {
        private final List<Map<Function<Integer, T>,Integer>> levels = new ArrayList<>();
        private final T defaultValue;
        private final int totalWeight;
        private int currentLevel = 0;

        /**
         * Create a LevelledWeightedRandom builder that will use a default value up to the given total weight.
         * @param defaultValue A default value that will fill all remaining weight up to `totalWeight`
         * @param totalWeight The total weight. Added weights must not exceed this.
         */
        public Builder(T defaultValue, int totalWeight) {
            this.defaultValue = defaultValue;
            this.totalWeight = totalWeight;
            levels.add(new HashMap<>());
        }

        /**
         * Create a LevelledWeightedRandom builder with a fixed weight. {@link #build()} will fail if the sum of the
         * weights are not equal to the given value.
         * @param totalWeight The total required weight of the collection.
         */
        public Builder(int totalWeight) {
            this(null, totalWeight);
        }

        /**
         * Create a LevelledWeightedRandom builder with default settings.
         */
        public Builder() {
            this(null, -1);
        }

        public Builder<T> nextLevel() {
            levels.add(new HashMap<>());
            currentLevel++;
            return this;
        }

        public Builder<T> add(T item, int weight) {
            levels.get(currentLevel).put(level -> item, weight);
            return this;
        }

        public Builder<T> add(Supplier<T> item, int weight) {
            levels.get(currentLevel).put(level -> item.get(), weight);
            return this;
        }

        public Builder<T> add(Function<Integer, T> item, int weight) {
            levels.get(currentLevel).put(item, weight);
            return this;
        }

        public LevelledWeightedRandom<T> build() {
            return new LevelledWeightedRandom<>(defaultValue, totalWeight, levels);
        }
    }
}
