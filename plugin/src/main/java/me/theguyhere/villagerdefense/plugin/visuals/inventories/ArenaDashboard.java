package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.Main;
import me.theguyhere.villagerdefense.plugin.data.listeners.ChatListener;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.game.exceptions.InvalidNameException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ArenaDashboard extends DataStructureDashboard<Arena> {
    public ArenaDashboard() {
        super("&9&lArenas", "Arena", Material.EMERALD_BLOCK, GameManager.getArenas().values().stream(), ArenaEditMenu::new);
    }

    @Override
    protected void createNew(Player player) {
        // Prompt for name
        ChatListener.ChatTask task = msg -> {
            // Check for cancelling
            if (msg.equalsIgnoreCase("cancel")) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> open(player));
                return;
            }

            // Create new arena
            Arena arena = new Arena(GameManager.newArenaID());
            GameManager.addArena(GameManager.newArenaID(), arena);

            // Try updating name
            try {
                arena.setName(msg.trim());
                CommunicationManager.debugInfo(CommunicationManager.DebugLevel.VERBOSE, "Name set for arena %s!",
                        String.valueOf(arena.getId()));
                open(player, new ArenaEditMenu(arena));
            } catch (InvalidNameException err) {
                if (arena.getName() == null)
                    GameManager.removeArena(arena.getId());
                PlayerManager.notifyFailure(player, "Invalid arena name!");
            }
        };
        closeInv(player);
        ChatListener.addTask(player, task, "Enter the new unique name for the arena in chat, or type CANCEL to quit:");
    }
}
