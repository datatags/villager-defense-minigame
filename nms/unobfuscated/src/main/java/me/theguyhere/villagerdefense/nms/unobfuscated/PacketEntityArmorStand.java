package me.theguyhere.villagerdefense.nms.unobfuscated;

import me.theguyhere.villagerdefense.nms.common.EntityID;
import me.theguyhere.villagerdefense.nms.common.PacketGroup;
import me.theguyhere.villagerdefense.nms.common.entities.TextPacketEntity;
import org.bukkit.Location;

/**
 * An armor stand entity constructed out of packets.
 */
class PacketEntityArmorStand implements TextPacketEntity {
    private final EntityID armorStandID = new EntityID();

    @Override
    public PacketGroup newDestroyPackets() {
        return new EntityDestroyPacket(armorStandID);
    }

    @Override
    public PacketGroup newSpawnPackets(Location location, String text) {
        return PacketGroup.of(
            new SpawnEntityPacket(armorStandID, VDEntityTypes.ARMOR_STAND, location),
            EntityMetadataPacket.builder(armorStandID)
                .setArmorStandMarker()
                .setCustomName(text)
                .build()
        );
    }
}
