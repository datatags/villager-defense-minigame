package me.theguyhere.villagerdefense.plugin.data;

import lombok.Getter;

import java.util.Map;

public class Pair<K,V> {
    @Getter
    private final K key;
    @Getter
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Pair(Map.Entry<K,V> entry) {
        this(entry.getKey(), entry.getValue());
    }
}
