package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitBlacksmith extends Kit {
    public KitBlacksmith() {
        super(LanguageManager.kits.blacksmith.name, KitCategory.EFFECT, Material.ANVIL);

        setMasterDescription(LanguageManager.kits.blacksmith.description);
        setPrice(7500);
        clearItems();
        addItems(ItemManager.makeUnbreakable(new ItemStack(Material.WOODEN_SWORD)));
    }
}
