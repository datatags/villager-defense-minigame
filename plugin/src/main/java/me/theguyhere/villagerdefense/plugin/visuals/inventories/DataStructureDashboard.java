package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.PagedDynamicSizeLayout;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;
import java.util.stream.Stream;

public abstract class DataStructureDashboard<T extends Comparable<T>> extends Menu {
    public DataStructureDashboard(String name, String dataStructure, Material buttonType, Stream<T> existing, Function<T,Menu> menuGetter) {
        super(name, new PagedDynamicSizeLayout());
        ItemStack createButton = InventoryButtons.create(dataStructure);
        addClickHandler(createButton, this::createNew);
        ((PagedDynamicSizeLayout)layout).setNewButton(createButton);

        existing.sorted().forEach(e ->
                addNavigation(buttonType, () -> menuGetter.apply(e), "&a&lEdit " + e.toString())
        );
    }

    protected abstract void createNew(Player player);
}
