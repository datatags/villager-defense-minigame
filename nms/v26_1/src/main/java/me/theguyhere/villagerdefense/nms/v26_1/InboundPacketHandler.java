package me.theguyhere.villagerdefense.nms.v26_1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.theguyhere.villagerdefense.nms.common.PacketListener;
import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.world.InteractionHand;
import org.bukkit.entity.Player;

/**
 * A class to handle server bound packets.
 */
class InboundPacketHandler extends ChannelInboundHandlerAdapter {
    public static final String HANDLER_NAME = "villager_defense_listener";
    private final Player player;
    private final PacketListener packetListener;

    InboundPacketHandler(Player player, PacketListener packetListener) {
        this.player = player;
        this.packetListener = packetListener;
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
        if (packet instanceof ServerboundAttackPacket) {
            ServerboundAttackPacket p = (ServerboundAttackPacket) packet;
            packetListener.onAttack(player, p.entityId());
        } else if (packet instanceof ServerboundInteractPacket) {
            ServerboundInteractPacket p = (ServerboundInteractPacket) packet;
            if (p.hand() == InteractionHand.MAIN_HAND) {
                packetListener.onInteractMain(player, p.entityId());
            }
        }
        super.channelRead(context, packet);
    }
}
