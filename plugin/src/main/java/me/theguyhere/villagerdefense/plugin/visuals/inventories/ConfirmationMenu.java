package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.SingleRowLayout;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ConfirmationMenu extends Menu {
    public ConfirmationMenu(@NotNull String name, @NotNull Consumer<Player> onConfirm, boolean goBackOnConfirm) {
        super(name, new SingleRowLayout(false));

        addButton(InventoryButtons.no(), this::goBack);
        addButton(InventoryButtons.yes(), p -> {
            onConfirm.accept(p);
            success(p);
            if (goBackOnConfirm) {
                goBack(p);
            }
        });
    }

    public ConfirmationMenu(@NotNull String name, @NotNull Consumer<Player> onConfirm) {
        this(name, onConfirm, true);
    }
}
