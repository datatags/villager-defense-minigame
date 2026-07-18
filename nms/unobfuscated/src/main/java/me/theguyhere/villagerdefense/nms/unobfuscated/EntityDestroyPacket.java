package me.theguyhere.villagerdefense.nms.unobfuscated;

import me.theguyhere.villagerdefense.nms.common.EntityID;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;

/**
 * Packet class for destroying entities.
 */
class EntityDestroyPacket extends VersionNMSPacket {
    private final Packet<?> rawPacket;

    EntityDestroyPacket(EntityID entityID) {
        rawPacket = new ClientboundRemoveEntitiesPacket(entityID.getNumericID());
    }

    @Override
    Packet<?> getRawPacket() {
        return rawPacket;
    }
}
