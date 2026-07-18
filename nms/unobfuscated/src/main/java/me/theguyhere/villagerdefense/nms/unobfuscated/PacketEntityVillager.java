package me.theguyhere.villagerdefense.nms.unobfuscated;

import me.theguyhere.villagerdefense.nms.common.EntityID;
import me.theguyhere.villagerdefense.nms.common.PacketGroup;
import me.theguyhere.villagerdefense.nms.common.entities.VillagerPacketEntity;
import org.bukkit.Location;

/**
 * A villager entity constructed out of packets.
 */
class PacketEntityVillager implements VillagerPacketEntity {
    private final EntityID villagerID = new EntityID();

    @Override
    public PacketGroup newDestroyPackets() {
        return new EntityDestroyPacket(villagerID);
    }

    @Override
    public PacketGroup newSpawnPackets(Location location) {
        return PacketGroup.of(
            new SpawnEntityPacket(villagerID, VDEntityTypes.VILLAGER, location, location.getPitch()),
            new EntityHeadRotationPacket(villagerID, location.getYaw())
        );
    }

    @Override
    public int getEntityID() {
        return villagerID.getNumericID();
    }
}
