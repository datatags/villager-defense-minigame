package me.theguyhere.villagerdefense.nms.common;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class EntityID {

    // Lazy initialization
    private @Nullable Integer numericID;
    private @Nullable UUID uuid;

    public int getNumericID() {
        if (numericID == null) {
            numericID = ThreadLocalRandom.current().nextInt();
        }
        return numericID;
    }

    public UUID getUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }

    public boolean hasInitializedNumericID() {
        return numericID != null;
    }
}
