package me.theguyhere.villagerdefense.plugin.game.events;

import lombok.Getter;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class JoinArenaEvent extends Event implements Cancellable {
    @Getter
    private final Player player;
    @Getter
    private final Arena arena;
    private boolean isCancelled;
    private static final HandlerList HANDLERS = new HandlerList();

    public JoinArenaEvent(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
    }
}
