package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.listeners.ChatListener;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.game.exceptions.InvalidNameException;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ArenaEditMenu extends ArenaMenu {
    public ArenaEditMenu(Arena arena) {
        super("&2&lEdit", arena, new DynamicSizeLayout(true));

        addButton(Material.NAME_TAG, ifClosed(this::editName), "&6&lEdit Name");
        addNavigation(Material.END_PORTAL_FRAME, () ->
                        new LocationMenu("&5&lPortal: " + arena.getName(), arena::getPortalLocation, arena::setPortal),
                "&5&lArena Portal");

        addNavigation(Material.TOTEM_OF_UNDYING,
                () -> new LocationMenu("&a&lLeaderboard: " + arena.getName(), arena::getArenaBoardLocation, arena::setArenaBoard),
                "&a&lArena Leaderboard");

        addNavigation(Material.PLAYER_HEAD, PlayersMenu::new, "&d&lPlayer Settings");
        addNavigation(Material.ZOMBIE_SPAWN_EGG, MobsMenu::new, "&2&lMob Settings");
        addNavigation(Material.GOLD_BLOCK, ShopSettingsMenu::new, "&e&lShop Settings");
        addNavigation(Material.REDSTONE, GameSettingsMenu::new, "&7&lGame Settings");
        addButton(Material.NETHER_BRICK_FENCE, this::toggleOpen,
                () -> "&9&lClose Arena: " + (arena.isClosed() ? "&c&lCLOSED" : "&a&lOPEN"));

        addButton(InventoryButtons.remove("ARENA"), ifClosed(pl -> open(pl, new ConfirmationMenu("&4&lRemove " + arena.getName() + '?', p -> {
            GameManager.removeArena(arena.getId());
            goBack(p); // return to arena list
        }, false))));
    }

    private void editName(Player player) {
        if (!arena.isClosed()) {
            PlayerManager.notifyFailure(player, "Arena must be closed to modify this!");
            return;
        }
        // Close current inventory
        closeInv(player);

        ChatListener.ChatTask task = (msg) -> {
            // Check for cancelling
            if (msg.equalsIgnoreCase("cancel")) {
                open(player);
                return;
            }

            // Try updating name
            try {
                arena.setName(msg.trim());
                CommunicationManager.debugInfo(CommunicationManager.DebugLevel.VERBOSE, "Name changed for arena %s!",
                        String.valueOf(arena.getId())
                );
                updateInventory();
                open(player);
            }
            catch (InvalidNameException err) {
                if (arena.getName() == null)
                    GameManager.removeArena(arena.getId());
                PlayerManager.notifyFailure(player, "Invalid arena name!");
            }
        };
        ChatListener.addTask(
                player, task, "Enter the new unique name for the arena chat, or type CANCEL to " +
                        "quit:");
    }

    private void toggleOpen(Player player) {
        if (!arena.isClosed()) {
            arena.setClosed(true);
            return;
        }
        String reason = arena.getClosedReason();
        if (reason != null) {
            PlayerManager.notifyFailure(player, reason);
            return;
        }
        arena.setClosed(false);
    }
}
