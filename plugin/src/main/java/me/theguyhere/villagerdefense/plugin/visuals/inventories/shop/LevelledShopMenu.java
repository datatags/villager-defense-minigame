package me.theguyhere.villagerdefense.plugin.visuals.inventories.shop;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LevelledShopMenu extends ShopMenu {
    protected final int level;
    protected final double priceModifier;
    private int counter = 0;
    public LevelledShopMenu(String color, String name, Arena arena, int level) {
        super(color + LanguageManager.messages.level + " &9&l" + level + " " + color + name, arena);
        this.level = level;
        this.priceModifier = arena.hasDynamicPrices() ? Math.pow(arena.getActiveCount() - 5, 2) / 200 + 1 : 1;
    }

    protected void addExit() {
        ((ManualLayout)layout).add(nextSlotOffset() + 4, InventoryButtons.exit());
    }

    protected int nextSlotOffset() {
        return (int)Math.ceil(counter++ * 4.5); // 0, 5, 9, 14, 18, ...
    }

    protected void generate(Function<Integer, ItemStack> generator) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            items.add(generator.apply(level));
        }
        sort(items);
        int slotOffset = nextSlotOffset();
        for (int i = 0; i < 4; i++) {
            addItem(i + slotOffset, modifyPrice(items.get(i), priceModifier));
        }
    }
}
