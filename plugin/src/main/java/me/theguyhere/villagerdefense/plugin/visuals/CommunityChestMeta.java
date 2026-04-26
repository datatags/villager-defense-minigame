package me.theguyhere.villagerdefense.plugin.visuals;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CommunityChestMeta implements InventoryHolder {
    @Getter
    @Setter
    private Inventory inventory;
}
