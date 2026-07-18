package me.theguyhere.villagerdefense.nms.unobfuscated;

import me.theguyhere.villagerdefense.nms.common.EntityID;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;

/**
 * Packet class for spawning entities.
 */
class SpawnEntityPacket extends VersionNMSPacket {
    private final Packet<?> rawPacket;

    SpawnEntityPacket(EntityID entityID, VDEntityTypes entityType, Location location) {
        this(entityID, entityType, location, 0, 0);
    }

    SpawnEntityPacket(EntityID entityID, VDEntityTypes entityType, Location location, float headPitch) {
        this(entityID, entityType, location, headPitch, 0);
    }

    SpawnEntityPacket(EntityID entityID, VDEntityTypes entityType, Location location, float headPitch, int objectData) {
        rawPacket = new ClientboundAddEntityPacket(
                entityID.getNumericID(),
                entityID.getUUID(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getPitch(),
                location.getYaw(),
                entityType.get(),
                objectData,
                Vec3.ZERO,
                headPitch
        );
    }


    @Override
    Packet<?> getRawPacket() {
        return rawPacket;
    }
}
