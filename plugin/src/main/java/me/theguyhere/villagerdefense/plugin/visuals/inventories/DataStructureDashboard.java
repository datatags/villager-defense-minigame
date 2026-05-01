package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.PagedDynamicSizeLayout;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class DataStructureDashboard<T extends Comparable<T>> extends Menu {
    private final Material buttonType;
    private final Supplier<Stream<T>> existingGetter;
    private final Function<T, Menu> menuGetter;
    private final ItemStack createButton;

    public DataStructureDashboard(String name, String dataStructure, Material buttonType, Supplier<Stream<T>> existingGetter, Function<T,Menu> menuGetter) {
        super(name, new PagedDynamicSizeLayout());
        this.buttonType = buttonType;
        this.existingGetter = existingGetter;
        this.menuGetter = menuGetter;

        this.createButton = InventoryButtons.create(dataStructure);
        ((PagedDynamicSizeLayout)layout).setNewButton(createButton);
    }

    @Override
    protected void updateInventory() {
        clear();
        addClickHandler(createButton, this::createNew);
        existingGetter.get().sorted().forEach(e ->
                addNavigation(buttonType, () -> menuGetter.apply(e), "&a&lEdit " + e.toString())
        );

        super.updateInventory();
    }

    protected abstract void createNew(Player player);
}
