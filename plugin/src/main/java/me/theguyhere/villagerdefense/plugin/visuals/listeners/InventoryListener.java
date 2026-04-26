package me.theguyhere.villagerdefense.plugin.visuals.listeners;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.Main;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.game.exceptions.ArenaNotFoundException;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import me.theguyhere.villagerdefense.plugin.visuals.CommunityChestMeta;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryMeta;
import me.theguyhere.villagerdefense.plugin.visuals.inventories.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {
	// Prevent losing items by drag clicking in custom inventory
	@EventHandler
	public void onDrag(InventoryDragEvent e) {
        if (e.getInventory().getType() == InventoryType.PLAYER) {
            return;
        }

        // Cancel if it's a plugin owned inventory, or a non-plugin-owned inventory by a player in a game.
		if (!(e.getInventory().getHolder() instanceof InventoryMeta)) {
            Player player = (Player) e.getWhoClicked();
            try {
                GameManager.getArena(player);
            } catch (ArenaNotFoundException err) {
                return;
            }
        }
        e.setCancelled(true);
    }

	@EventHandler
	public void onClickOther(InventoryClickEvent e) {
		// Ignore plugin inventories
		if (e.getInventory().getHolder() instanceof InventoryMeta) {
            return;
        }

		// Ignore players that aren't part of an arena
		Player player = (Player) e.getWhoClicked();
		try {
			GameManager.getArena(player);
		} catch (ArenaNotFoundException err) {
            return;
		}
        e.setCancelled(true);
	}

	// All click events in the inventories
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		// Get inventory title
		String title = e.getView().getTitle();

		// Ignore non-plugin inventories
		if (!(e.getInventory().getHolder() instanceof InventoryMeta)) {
            return;
        }

        // Debugging info
        CommunicationManager.debugInfo(CommunicationManager.DebugLevel.VERBOSE, "Inventory Item: " + e.getCurrentItem());
        CommunicationManager.debugInfo(CommunicationManager.DebugLevel.VERBOSE, "Cursor Item: " + e.getCursor());
        CommunicationManager.debugInfo(CommunicationManager.DebugLevel.VERBOSE, "Clicked Inventory: " + e.getClickedInventory());
        CommunicationManager.debugInfo(CommunicationManager.DebugLevel.VERBOSE, "Inventory Name: " + title);

        InventoryMeta meta = (InventoryMeta) e.getInventory().getHolder();
        Menu menu = meta.getMenu();
        menu.onClick(e);

        String newName = CommunicationManager.format(menu.getName());
        final Inventory openInv = e.getView().getTopInventory();
        HumanEntity player = e.getWhoClicked();
        if (!e.getView().getTitle().equals(newName)) {
            Bukkit.getScheduler().runTask(Main.plugin, () -> {
                if (player.getOpenInventory().getTopInventory() == openInv) {
                    e.getView().setTitle(newName);
                }
            });
        }
	}

	// Ensures closing inventory doesn't mess up data
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		// Ignore non-plugin inventories
		if (!(e.getInventory().getHolder() instanceof CommunityChestMeta)) {
            return;
        }

		// Check for community chest with shop inside it
		if (e.getInventory().contains(GameItems.shop())) {
			e.getInventory().removeItem(GameItems.shop());
			PlayerManager.giveItem((Player) e.getPlayer(), GameItems.shop());
		}
	}
}
