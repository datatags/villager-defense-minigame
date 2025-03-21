package me.theguyhere.villagerdefense.nms.v1_20_r3;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

/**
 * A class to help with setting up a packet for a specific version.
 */
class PacketSetter extends FriendlyByteBuf {

    // Singleton instance
    private static final PacketSetter INSTANCE = new PacketSetter();

    /**
     * Get the instance of {@link PacketSetter} for this version.
     *
     * @return {@link PacketSetter} for specific version.
     */
    static PacketSetter get() {
        INSTANCE.clear();
        return INSTANCE;
    }

    // Create instance once on class load
    private PacketSetter() {
        super(Unpooled.buffer());
    }

    public FriendlyByteBuf writeVarIntArray(int... is) {
        return super.writeVarIntArray(is);
    }

    <T> void writeDataWatcherEntry(DataWatcherKey<T> key, T value) {
        writeByte(key.getIndex());
        writeVarInt(key.getSerializerTypeID());
        key.getSerializer().write(this, value);
    }

    void writeDataWatcherEntriesEnd() {
        writeByte(0xFF);
    }
}
