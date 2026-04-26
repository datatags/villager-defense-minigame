package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.ArenaDataManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomShopEditorMenu extends ArenaMenu {
    private final boolean display;
    public CustomShopEditorMenu(Arena arena, boolean display) {
        super("&6&lCustom Shop Editor", arena, new ManualLayout());
        this.display = display;
    }

    @Override
    protected void updateInventory() {
        ManualLayout l = ((ManualLayout) layout);
        l.clear();
        // Set exit options
        for (int i = 45; i < 54; i++) {
            l.add(i, InventoryButtons.exit());
        }

        // Get items from stored inventory
        arena.getCustomShopItems().forEach(l::add);
        super.updateInventory();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (display) {
            e.setCancelled(true);
            return;
        }
        CommunicationManager.debugInfo(CommunicationManager.DebugLevel.VERBOSE, "Custom shop editor being used.");
        ItemStack cursor = e.getCursor();
        ItemStack current = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (InventoryButtons.exit().equals(current)) {
            e.setCancelled(true);
            goBack(player);
            return;
        }

        // Check for arena closure
        if (!arena.isClosed()) {
            e.setCancelled(true);
            PlayerManager.notifyFailure(player, "Arena must be closed to modify this!");
            return;
        }

        if (e.getClick().isShiftClick()) {
            // Tracking shift-clicks is a little trickier, maybe someday...
            e.setCancelled(true);
            return;
        }

        if (e.getClickedInventory() != e.getView().getTopInventory()) {
            return;
        }

        // Add item
        if (cursor != null && cursor.getType() != Material.AIR) {
            e.setCancelled(true);
            ItemMeta itemMeta = cursor.getItemMeta();
            assert itemMeta != null;
            List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
            assert lore != null;
            lore.add(CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a0"));
            itemMeta.setLore(lore);
            ItemStack copy = cursor.clone();
            copy.setItemMeta(itemMeta);
            ArenaDataManager.setCustomShopItem(arena.getId(), slot, copy);
            PlayerManager.giveItem(player, cursor.clone());
            player.setItemOnCursor(new ItemStack(Material.AIR));
            open(player, new CustomItemsMenu(arena, slot));
            return;
        }

        // Only open inventory for valid click
        if (current != null && current.getType() != Material.AIR) {
            e.setCancelled(true);
            open(player, new CustomItemsMenu(arena, slot));
        }
    }
}
