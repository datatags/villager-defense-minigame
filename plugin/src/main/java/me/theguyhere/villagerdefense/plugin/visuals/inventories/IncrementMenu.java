package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.visuals.layout.SingleRowLayout;
import org.bukkit.Material;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IncrementMenu extends Menu {
    private final Supplier<Integer> getter;
    IncrementMenu(String name, Supplier<Integer> getter, Consumer<Integer> setter, boolean simple) {
        super(name, new SingleRowLayout(true));
        this.getter = getter;
        // we use `safeSetter` when incrementing/decrementing to ensure that we don't go below 1 and that if the value
        // is currently unlimited, it gets set to a real value.
        final Consumer<Integer> safeSetter = val -> setter.accept(Math.max(val, 1));

        addButton(Material.RED_CONCRETE, p -> safeSetter.accept(getter.get() - 1), "&4&lDecrease");

        if (!simple) {
            addButton(Material.ORANGE_CONCRETE, p -> setter.accept(-1), "&6&lUnlimited");
            addButton(Material.LIGHT_BLUE_CONCRETE, p -> setter.accept(1), "&3&lReset to 1");
        }

        addButton(Material.LIME_CONCRETE, p -> safeSetter.accept(getter.get() + 1), "&2&lIncrease");
    }

    IncrementMenu(String name, Supplier<Integer> getter, Consumer<Integer> setter) {
        this(name, getter, setter, false);
    }

    @Override
    public String getName() {
        int val = getter.get();
        return super.getName() + ": " + (val == -1 ? "Unlimited" : val);
    }
}
