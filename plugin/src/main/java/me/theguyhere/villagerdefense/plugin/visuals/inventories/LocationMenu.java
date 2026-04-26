package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.exceptions.BadDataException;
import me.theguyhere.villagerdefense.plugin.data.exceptions.NoSuchPathException;
import me.theguyhere.villagerdefense.plugin.structures.LocationGetter;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.SingleRowLayout;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class LocationMenu extends Menu {
    LocationMenu(@NotNull String name, LocationGetter getter, Consumer<Location> setter, Function<Player,Boolean> isModifyAllowed, boolean simple) {
        super(name, new SingleRowLayout(true));

        // Option to create or relocate the location
        boolean exists;
        try {
            getter.get();
            exists = true;
        } catch (BadDataException | NoSuchPathException e) {
            exists = false;
        }

        ItemStack setItem = exists ? InventoryButtons.relocate(getName()) : InventoryButtons.create(getName());
        addButton(setItem, p -> {
            if (!isModifyAllowed.apply(p)) {
                return;
            }
            setter.accept(p.getLocation());
            success(p);
        });

        if (exists) {
            // Option to teleport to the location
            addButton(InventoryButtons.teleport(getName()), p -> {
                try {
                    p.teleport(getter.get());
                } catch (BadDataException | NoSuchPathException e) {
                    CommunicationManager.debugErrorShouldNotHappen();
                }
            });

            if (!simple) {
                // Option to center the location
                addButton(InventoryButtons.center(getName()), p -> {
                    if (!isModifyAllowed.apply(p)) {
                        return;
                    }
                    try {
                        Location loc = getter.get();
                        loc.setX(loc.getBlockX() + 0.5);
                        loc.setZ(loc.getBlockZ() + 0.5);
                        setter.accept(loc);
                    } catch (BadDataException | NoSuchPathException e){
                        CommunicationManager.debugErrorShouldNotHappen();
                        return;
                    }
                    success(p);
                });
            }

            // Option to remove the location
            addButton(InventoryButtons.remove(getName()), p -> {
                if (isModifyAllowed.apply(p)) {
                    open(p, new ConfirmationMenu("&4&lRemove " + getName() + "?", pl -> {
                        setter.accept(null);
                        success(pl);
                    }));
                }
            });
        }
    }

    LocationMenu(@NotNull String name, LocationGetter getter, Consumer<Location> setter, Function<Player,Boolean> isModifyAllowed) {
        this(name, getter, setter, isModifyAllowed, false);
    }

    LocationMenu(@NotNull String name, LocationGetter getter, Consumer<Location> setter) {
        this(name, getter, setter, p -> true);
    }
}
