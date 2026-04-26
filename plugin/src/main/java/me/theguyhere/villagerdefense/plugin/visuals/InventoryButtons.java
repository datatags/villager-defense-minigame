package me.theguyhere.villagerdefense.plugin.visuals;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.Main;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class InventoryButtons {
    private static final NamespacedKey INVENTORY_BUTTON = new NamespacedKey(Main.plugin, "inventoryButton");

    public enum Type {
        NO,
        YES,
        EXIT,
        REMOVE,
        CREATE,
        RELOCATE,
        TELEPORT,
        CENTER,
        NEW_ADD,
        PREVIOUS_PAGE,
        NEXT_PAGE,
        ;
        private ItemStack apply(ItemStack item) {
            if (item != null && item.getItemMeta() != null) {
                ItemMeta meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(INVENTORY_BUTTON, PersistentDataType.INTEGER, ordinal());
                item.setItemMeta(meta);
            }
            return item;
        }

        public boolean matches(ItemStack item) {
            if (item == null || !item.hasItemMeta()) {
                return false;
            }
            Integer val = item.getItemMeta().getPersistentDataContainer().get(INVENTORY_BUTTON, PersistentDataType.INTEGER);
            return val != null && val == ordinal();
        }
    };

    // "No" button
    public static ItemStack no() {
        return Type.NO.apply(ItemManager.createItem(Material.RED_CONCRETE, CommunicationManager.format("&c&lNO")));
    }

    // "Yes" button
    public static ItemStack yes() {
        return Type.YES.apply(ItemManager.createItem(Material.LIME_CONCRETE, CommunicationManager.format("&a&lYES")));
    }

    // "Exit" button
    public static ItemStack exit() {
        return Type.EXIT.apply(ItemManager.createItem(Material.BARRIER, CommunicationManager.format("&c&l" +
                LanguageManager.messages.exit)));
    }

    // "Remove x" button
    public static ItemStack remove(String x) {
        return Type.REMOVE.apply(ItemManager.createItem(Material.LAVA_BUCKET, CommunicationManager.format("&4&lREMOVE " + x)));
    }

    // "Create x" button
    public static ItemStack create(String x) {
        return Type.CREATE.apply(ItemManager.createItem(Material.END_PORTAL_FRAME, CommunicationManager.format("&a&lCreate " + x)));
    }

    // "Relocate x" button
    public static ItemStack relocate(String x) {
        return Type.RELOCATE.apply(ItemManager.createItem(Material.END_PORTAL_FRAME, CommunicationManager.format("&a&lRelocate " + x)));
    }

    // "Teleport to x" button
    public static ItemStack teleport(String x) {
        return Type.TELEPORT.apply(ItemManager.createItem(Material.ENDER_PEARL, CommunicationManager.format("&9&lTeleport to " + x)));
    }

    // "Center x" button
    public static ItemStack center(String x) {
        return Type.CENTER.apply(ItemManager.createItem(
                Material.TARGET,
                CommunicationManager.format("&f&lCenter " + x),
                CommunicationManager.format("&7Center the x and z coordinates")
        ));
    }

    // "New x" button
    public static ItemStack newAdd(String x) {
        return Type.NEW_ADD.apply(ItemManager.createItem(Material.NETHER_STAR, CommunicationManager.format("&a&lNew " + x)));
    }

    // "Previous Page" button
    public static ItemStack previousPage() {
        return Type.PREVIOUS_PAGE.apply(ItemManager.createItem(Material.PRISMARINE_SHARD, CommunicationManager.format("&e&lPrevious Page")));
    }

    // "Next Page" button
    public static ItemStack nextPage() {
        return Type.NEXT_PAGE.apply(ItemManager.createItem(Material.FEATHER, CommunicationManager.format("&d&lNext Page")));
    }
}
