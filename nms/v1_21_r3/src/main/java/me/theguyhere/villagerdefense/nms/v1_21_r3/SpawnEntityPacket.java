package me.theguyhere.villagerdefense.nms.v1_21_r3;

import me.theguyhere.villagerdefense.common.Calculator;
import me.theguyhere.villagerdefense.nms.common.EntityID;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Location;

/**
 * Packet class for spawning entities.
 */
@SuppressWarnings("deprecation")
class SpawnEntityPacket extends VersionNMSPacket {
    private final Packet<?> rawPacket;

    SpawnEntityPacket(EntityID entityID, int entityTypeID, Location location) {
        this(entityID, entityTypeID, location, 0, 0);
    }

    SpawnEntityPacket(EntityID entityID, int entityTypeID, Location location, float headPitch) {
        this(entityID, entityTypeID, location, headPitch, 0);
    }

    SpawnEntityPacket(EntityID entityID, int entityTypeID, Location location, float headPitch, int objectData) {
        PacketSetter packetSetter = PacketSetter.get();

        // Entity info
        packetSetter.writeVarInt(entityID.getNumericID());
        packetSetter.writeUUID(entityID.getUUID());
        packetSetter.writeVarInt(entityTypeID);

        // Position
        packetSetter.writeDouble(location.getX());
        packetSetter.writeDouble(location.getY());
        packetSetter.writeDouble(location.getZ());

        // Rotation
        packetSetter.writeByte(Calculator.degreesToByte(location.getPitch()));
        packetSetter.writeByte(Calculator.degreesToByte(location.getYaw()));

        // Head pitch
        packetSetter.writeByte(Calculator.degreesToByte(headPitch));

        // Object data
        packetSetter.writeInt(objectData);

        // Velocity
        packetSetter.writeShort(0);
        packetSetter.writeShort(0);
        packetSetter.writeShort(0);

        rawPacket = ClientboundAddEntityPacket.STREAM_CODEC.decode(new RegistryFriendlyByteBuf(packetSetter, MinecraftServer
            .getServer().registryAccess()));
    }


    @Override
    Packet<?> getRawPacket() {
        return rawPacket;
    }
}
