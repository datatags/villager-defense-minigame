package me.theguyhere.villagerdefense.plugin.items;

import me.theguyhere.villagerdefense.plugin.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public enum GameItemType {
    SHOP,
    KIT_SELECTOR,
    CHALLENGE_SELECTOR,
    BOOST_TOGGLE,
    SHARE_TOGGLE,
    CRYSTAL_CONVERTER,
    LEAVE,
    ;
    private final NamespacedKey tag;
    private GameItemType() {
        tag = new NamespacedKey(Main.plugin, name());
    }

    public NamespacedKey getTag() {
        return tag;
    }

    public ItemStack apply(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return new ItemStack(Material.AIR);
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(tag, PersistentDataType.BYTE, (byte)1);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public boolean is(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return false;
        }
        return itemStack.getItemMeta().getPersistentDataContainer().has(tag, PersistentDataType.BYTE);
    }
}
