package me.theguyhere.villagerdefense.nms.v1_16_r3;

import me.theguyhere.villagerdefense.common.Calculator;
import me.theguyhere.villagerdefense.nms.common.EntityID;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;

/**
 * Packet class for spawning living entities.
 */
class SpawnEntityLivingPacket extends VersionNMSPacket {
    private final Packet<?> rawPacket;

    SpawnEntityLivingPacket(EntityID entityID, int entityTypeID, Location location) {
        this(entityID, entityTypeID, location, 0);
    }

    SpawnEntityLivingPacket(EntityID entityID, int entityTypeID, Location location, float headPitch) {
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
        packetSetter.writeByte(Calculator.degreesToByte(location.getYaw()));
        packetSetter.writeByte(Calculator.degreesToByte(location.getPitch()));

        // Head pitch
        packetSetter.writeByte(Calculator.degreesToByte(headPitch));

        // Velocity
        packetSetter.writeShort(0);
        packetSetter.writeShort(0);
        packetSetter.writeShort(0);

        rawPacket = writeData(new PacketPlayOutSpawnEntityLiving(), packetSetter);
    }


    @Override
    Packet<?> getRawPacket() {
        return rawPacket;
    }
}
