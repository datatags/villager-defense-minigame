package me.theguyhere.villagerdefense.plugin.structures.listeners;

import me.theguyhere.villagerdefense.plugin.Main;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.events.JoinArenaEvent;
import me.theguyhere.villagerdefense.plugin.structures.events.LeftClickNPCEvent;
import me.theguyhere.villagerdefense.plugin.structures.events.RightClickNPCEvent;
import me.theguyhere.villagerdefense.plugin.visuals.inventories.ArenaInfoMenu;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InteractionListener implements Listener {
    private Arena getArenaByNpcId(int id) {
        for (Arena arena : GameManager.getArenas().values()) {
            if (arena.getPortal() != null && arena.getPortal().getNpc().getEntityID() == id) {
                return arena;
            }
        }
        // Not sure why this happens
        return null;
    }

	@EventHandler
	public void onRightClick(RightClickNPCEvent e) {
        Arena arena = getArenaByNpcId(e.getNpcId());
        if (arena != null) {
            // Send out event of player joining
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () ->
                    Bukkit.getPluginManager().callEvent(new JoinArenaEvent(e.getPlayer(), arena)));
        }
	}

	@EventHandler
	public void onLeftClick(LeftClickNPCEvent e) {
		Arena arena = getArenaByNpcId(e.getNpcId());
        if (arena != null) {
            // Open inventory
            new ArenaInfoMenu(arena).open(e.getPlayer());
        }
	}
}
