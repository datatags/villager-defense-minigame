package me.theguyhere.villagerdefense.plugin.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;

public class Costs {
    private static final Map<Material,Integer> MATERIAL_COSTS = new HashMap<>();
    private static final Map<Enchantment,Integer> ENCHANTMENT_COSTS = new HashMap<>();
    private static final Map<Enchantment,Integer> SHIELD_ENCHANTMENT_COSTS = new HashMap<>();

    static {
        MATERIAL_COSTS.put(Material.WOODEN_SWORD, 50);
        MATERIAL_COSTS.put(Material.STONE_SWORD, 120);
        MATERIAL_COSTS.put(Material.IRON_SWORD, 250);
        MATERIAL_COSTS.put(Material.DIAMOND_SWORD, 500);
        MATERIAL_COSTS.put(Material.NETHERITE_SWORD, 700);

        MATERIAL_COSTS.put(Material.WOODEN_AXE, 40);
        MATERIAL_COSTS.put(Material.STONE_AXE, 100);
        MATERIAL_COSTS.put(Material.IRON_AXE, 220);
        MATERIAL_COSTS.put(Material.DIAMOND_AXE, 480);
        MATERIAL_COSTS.put(Material.NETHERITE_AXE, 700);

        MATERIAL_COSTS.put(Material.LEATHER_HELMET, 55);
        MATERIAL_COSTS.put(Material.CHAINMAIL_HELMET, 140);
        MATERIAL_COSTS.put(Material.IRON_HELMET, 160);
        MATERIAL_COSTS.put(Material.DIAMOND_HELMET, 300);
        MATERIAL_COSTS.put(Material.NETHERITE_HELMET, 375);

        MATERIAL_COSTS.put(Material.LEATHER_CHESTPLATE, 200);
        MATERIAL_COSTS.put(Material.CHAINMAIL_CHESTPLATE, 320);
        MATERIAL_COSTS.put(Material.IRON_CHESTPLATE, 420);
        MATERIAL_COSTS.put(Material.DIAMOND_CHESTPLATE, 550);
        MATERIAL_COSTS.put(Material.NETHERITE_CHESTPLATE, 650);

        MATERIAL_COSTS.put(Material.LEATHER_LEGGINGS, 120);
        MATERIAL_COSTS.put(Material.CHAINMAIL_LEGGINGS, 250);
        MATERIAL_COSTS.put(Material.IRON_LEGGINGS, 350);
        MATERIAL_COSTS.put(Material.DIAMOND_LEGGINGS, 400);
        MATERIAL_COSTS.put(Material.NETHERITE_LEGGINGS, 475);

        MATERIAL_COSTS.put(Material.LEATHER_BOOTS, 45);
        MATERIAL_COSTS.put(Material.CHAINMAIL_BOOTS, 60);
        MATERIAL_COSTS.put(Material.IRON_BOOTS, 120);
        MATERIAL_COSTS.put(Material.DIAMOND_BOOTS, 275);
        MATERIAL_COSTS.put(Material.NETHERITE_BOOTS, 325);

        MATERIAL_COSTS.put(Material.BOW, 150);
        MATERIAL_COSTS.put(Material.CROSSBOW, 300);
        MATERIAL_COSTS.put(Material.TRIDENT, 700);
        MATERIAL_COSTS.put(Material.SHIELD, 500);
        MATERIAL_COSTS.put(Material.ARROW, 45); // for x16

        MATERIAL_COSTS.put(Material.TOTEM_OF_UNDYING, 1000);
        MATERIAL_COSTS.put(Material.GOLDEN_APPLE, 120);
        MATERIAL_COSTS.put(Material.ENCHANTED_GOLDEN_APPLE, 300);
        MATERIAL_COSTS.put(Material.GOLDEN_CARROT, 80);
        MATERIAL_COSTS.put(Material.COOKED_BEEF, 60); // for x2
        MATERIAL_COSTS.put(Material.COOKED_MUTTON, 40); // for x2
        MATERIAL_COSTS.put(Material.BREAD, 40); // for x3
        MATERIAL_COSTS.put(Material.CARROT, 30); // for x5
        MATERIAL_COSTS.put(Material.BEETROOT, 25); // for x8

        MATERIAL_COSTS.put(Material.MILK_BUCKET, 75);
        MATERIAL_COSTS.put(Material.GHAST_SPAWN_EGG, 500); // golem spawn egg
        MATERIAL_COSTS.put(Material.WOLF_SPAWN_EGG, 250);
        MATERIAL_COSTS.put(Material.EXPERIENCE_BOTTLE, 75);

        // Enchantment costs are per-level. Note that shield enchantment costs are different
        ENCHANTMENT_COSTS.put(Enchantment.UNBREAKING, 25);
        ENCHANTMENT_COSTS.put(Enchantment.FIRE_PROTECTION, 25);
        ENCHANTMENT_COSTS.put(Enchantment.KNOCKBACK, 30);
        ENCHANTMENT_COSTS.put(Enchantment.PUNCH, 30);
        ENCHANTMENT_COSTS.put(Enchantment.BLAST_PROTECTION, 30);
        ENCHANTMENT_COSTS.put(Enchantment.THORNS, 45);
        ENCHANTMENT_COSTS.put(Enchantment.SWEEPING_EDGE, 50);
        ENCHANTMENT_COSTS.put(Enchantment.PIERCING, 50);
        ENCHANTMENT_COSTS.put(Enchantment.PROJECTILE_PROTECTION, 50);
        ENCHANTMENT_COSTS.put(Enchantment.SMITE, 60);
        ENCHANTMENT_COSTS.put(Enchantment.QUICK_CHARGE, 60);
        ENCHANTMENT_COSTS.put(Enchantment.SHARPNESS, 75);
        ENCHANTMENT_COSTS.put(Enchantment.POWER, 75);
        ENCHANTMENT_COSTS.put(Enchantment.PROTECTION, 75);
        ENCHANTMENT_COSTS.put(Enchantment.FIRE_ASPECT, 100);
        ENCHANTMENT_COSTS.put(Enchantment.FLAME, 100);
        ENCHANTMENT_COSTS.put(Enchantment.LOYALTY, 100);
        ENCHANTMENT_COSTS.put(Enchantment.MULTISHOT, 120);
        ENCHANTMENT_COSTS.put(Enchantment.MENDING, 250);
        ENCHANTMENT_COSTS.put(Enchantment.INFINITY, 400);

        SHIELD_ENCHANTMENT_COSTS.put(Enchantment.UNBREAKING, 100);
        SHIELD_ENCHANTMENT_COSTS.put(Enchantment.MENDING, 400);
    }

    public static int getMaterialCost(Material mat) {
        if (MATERIAL_COSTS.containsKey(mat)) {
            return MATERIAL_COSTS.get(mat);
        }
        throw new RuntimeException("Cannot calculate cost for item " + mat);
    }

    public static int getEnchantCost(Material mat, Enchantment ench) {
        if (mat == Material.SHIELD && SHIELD_ENCHANTMENT_COSTS.containsKey(ench)) {
            return SHIELD_ENCHANTMENT_COSTS.get(ench);
        }
        if (ENCHANTMENT_COSTS.containsKey(ench)) {
            return ENCHANTMENT_COSTS.get(ench);
        }
        throw new RuntimeException("Cannot calculate cost for enchantment " + ench);
    }

    public static int getTotalCost(Material mat, Map<Enchantment, Integer> enchants) {
        int cost = getMaterialCost(mat);
        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            cost += getEnchantCost(mat, entry.getKey()) * entry.getValue();
        }
        return cost;
    }
}
