package me.theguyhere.villagerdefense.plugin.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class LevelledWeightedRandom<T> {
    private final T defaultValue;
    private final int totalWeight;
    private final List<Map<T, Integer>> levels;

    public LevelledWeightedRandom(T defaultValue, int totalWeight, List<Map<T, Integer>> levels) {
        this.defaultValue = defaultValue;
        this.totalWeight = totalWeight;
        this.levels = levels;
        if (totalWeight == -1 && defaultValue == null) {
            return;
        }

        for (int i = 0; i < levels.size(); i++) {
            int levelTotalWeight = levels.get(i).values().stream().reduce(0, Integer::sum);
            if (levelTotalWeight > totalWeight) {
                throw new IllegalArgumentException("Level " + i + " total weight is " + levelTotalWeight + " which is greater than the weight limit " + totalWeight);
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
        Map<T, Integer> map = levels.get(level - 1);
        int totalWeight;
        if (this.totalWeight == -1 && defaultValue == null) {
            totalWeight = map.values().stream().reduce(0, Integer::sum);
        } else {
            totalWeight = this.totalWeight;
        }
        if (totalWeight > 0) {
            int index = ThreadLocalRandom.current().nextInt(totalWeight);
            for (Map.Entry<T, Integer> entry : map.entrySet()) {
                index -= entry.getValue();
                if (index < 0) {
                    return entry.getKey();
                }
            }
        }
        return defaultValue;
    }

    public int levelCount() {
        return levels.size();
    }

    public static class Builder<T> {
        private final List<Map<T,Integer>> levels = new ArrayList<>();
        private final T defaultValue;
        private final int totalWeight;
        private int currentLevel = 0;

        public Builder(T defaultValue, int totalWeight) {
            this.defaultValue = defaultValue;
            this.totalWeight = totalWeight;
            levels.add(new HashMap<>());
        }

        public Builder() {
            this(null, -1);
        }

        public Builder<T> nextLevel() {
            levels.add(new HashMap<>());
            currentLevel++;
            return this;
        }

        public Builder<T> add(T item, int weight) {
            levels.get(currentLevel).put(item, weight);
            return this;
        }

        public LevelledWeightedRandom<T> build() {
            return new LevelledWeightedRandom<>(defaultValue, totalWeight, levels);
        }
    }
}
