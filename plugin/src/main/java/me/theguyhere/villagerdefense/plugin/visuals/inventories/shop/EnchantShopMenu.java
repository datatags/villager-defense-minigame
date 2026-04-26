package me.theguyhere.villagerdefense.plugin.visuals.inventories.shop;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.items.EnchantingBook;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class EnchantShopMenu extends ShopMenu {
    public EnchantShopMenu(Arena arena) {
        super("&a&l" + LanguageManager.names.enchantShop, arena);

        // Melee enchants
        addEnchant(0, Material.PISTON, "Increase Knockback", 4);
        addEnchant(1, Material.GOLDEN_HOE, "Increase Sweeping Edge", 6);
        addEnchant(2, Material.DIAMOND_SWORD, "Increase Smite", 7);
        addEnchant(3, Material.NETHERITE_AXE, "Increase Sharpness", 8);
        addEnchant(4, Material.FIRE_CHARGE, "Increase Fire Aspect", 10);

        // Ranged enchants
        addEnchant(18, Material.STICKY_PISTON, "Increase Punch", 4);
        addEnchant(19, Material.SPECTRAL_ARROW, "Increase Piercing", 5);
        addEnchant(20, Material.REDSTONE_TORCH, "Increase Quick Charge", 6);
        addEnchant(21, Material.BOW, "Increase Power", 8);
        addEnchant(22, Material.TRIDENT, "Increase Loyalty", 10);
        addEnchant(23, Material.MAGMA_BLOCK, "Add Flame", 10);
        addEnchant(24, Material.CROSSBOW, "Add Multishot", 10);
        addEnchant(25, Material.BEACON, "Add Infinity", 15);

        // Armor enchants
        addEnchant(36, Material.TNT, "Increase Blast Protection", 4);
        addEnchant(37, Material.VINE, "Increase Thorns", 5);
        addEnchant(38, Material.ARROW, "Increase Projectile Protection", 6);
        addEnchant(39, Material.SHIELD, "Increase Protection", 8);

        // General enchants
        addEnchant(43, Material.BEDROCK, "Increase Unbreaking", 3);
        addEnchant(44, Material.ANVIL, "Add Mending", 20);

        // Return option
        ((ManualLayout)layout).add(53, InventoryButtons.exit());
    }

    private void addEnchant(int slot, Material mat, String displayName, int cost) {
        ItemStack item = ItemManager.createItem(mat,
                CommunicationManager.format("&a&lIncrease " + displayName),
                CommunicationManager.format("&2Costs " + cost + " XP Levels"));
        addItem(slot, item);
    }

    @Override
    protected void handleBuy(Player player, ItemStack displayItem) {
        // Get necessary info
        ItemStack buy = displayItem.clone();
        List<String> lore = Objects.requireNonNull(buy.getItemMeta()).getLore();
        if (lore == null || lore.isEmpty()) {
            return;
        }
        int cost = Integer.parseInt(lore.get(0).split(" ")[1]);

        // Check if they can afford the item, then deduct
        if (player.getLevel() < cost) {
            PlayerManager.notifyFailure(player, LanguageManager.errors.buy);
            return;
        }
        player.setLevel(player.getLevel() - cost);

        // Give book
        String enchant;
        ItemStack give;

        // Gather enchant from name
        try {
            enchant = buy.getItemMeta().getDisplayName().split(" ")[1];
        } catch (Exception err) {
            return;
        }

        // Assign to known enchanting books
        if (enchant.equals(LanguageManager.enchants.knockback.split(" ")[0]))
            give = EnchantingBook.knockback();
        else if (enchant.equals(LanguageManager.enchants.sweepingEdge.split(" ")[0]))
            give = EnchantingBook.sweepingEdge();
        else if (enchant.equals(LanguageManager.enchants.smite.split(" ")[0]))
            give = EnchantingBook.smite();
        else if (enchant.equals(LanguageManager.enchants.sharpness.split(" ")[0]))
            give = EnchantingBook.sharpness();
        else if (enchant.equals(LanguageManager.enchants.fireAspect.split(" ")[0]))
            give = EnchantingBook.fireAspect();
        else if (enchant.equals(LanguageManager.enchants.punch.split(" ")[0]))
            give = EnchantingBook.punch();
        else if (enchant.equals(LanguageManager.enchants.piercing.split(" ")[0]))
            give = EnchantingBook.piercing();
        else if (enchant.equals(LanguageManager.enchants.quickCharge.split(" ")[0]))
            give = EnchantingBook.quickCharge();
        else if (enchant.equals(LanguageManager.enchants.power.split(" ")[0]))
            give = EnchantingBook.power();
        else if (enchant.equals(LanguageManager.enchants.loyalty.split(" ")[0]))
            give = EnchantingBook.loyalty();
        else if (enchant.equals(LanguageManager.enchants.flame.split(" ")[0]))
            give = EnchantingBook.flame();
        else if (enchant.equals(LanguageManager.enchants.multishot.split(" ")[0]))
            give = EnchantingBook.multishot();
        else if (enchant.equals(LanguageManager.enchants.infinity.split(" ")[0]))
            give = EnchantingBook.infinity();
        else if (enchant.equals(LanguageManager.enchants.blastProtection.split(" ")[0]))
            give = EnchantingBook.blastProtection();
        else if (enchant.equals(LanguageManager.enchants.thorns.split(" ")[0]))
            give = EnchantingBook.thorns();
        else if (enchant.equals(LanguageManager.enchants.projectileProtection.split(" ")[0]))
            give = EnchantingBook.projectileProtection();
        else if (enchant.equals(LanguageManager.enchants.protection.split(" ")[0]))
            give = EnchantingBook.protection();
        else if (enchant.equals(LanguageManager.enchants.unbreaking.split(" ")[0]))
            give = EnchantingBook.unbreaking();
        else if (enchant.equals(LanguageManager.enchants.mending.split(" ")[0]))
            give = EnchantingBook.mending();
        else give = null;

        PlayerManager.giveItem(player, give);
        PlayerManager.notifySuccess(player, LanguageManager.confirms.buy);
    }
}
