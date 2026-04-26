package me.theguyhere.villagerdefense.plugin.visuals;

import lombok.Getter;
import me.theguyhere.villagerdefense.plugin.visuals.inventories.Menu;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class InventoryMeta implements InventoryHolder {
    @Getter
    private final Menu menu;
    public InventoryMeta(Menu menu) {
        this.menu = menu;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return Bukkit.createInventory(this, 0);
    }
}
