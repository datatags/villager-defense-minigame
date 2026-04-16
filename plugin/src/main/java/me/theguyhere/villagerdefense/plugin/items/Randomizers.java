package me.theguyhere.villagerdefense.plugin.items;

import me.theguyhere.villagerdefense.plugin.game.LevelledWeightedRandom;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Randomizers {
    private static final String[] toolTypes = new String[] {"WOODEN", "STONE", "IRON", "DIAMOND", "NETHERITE"};
    private static final String[] armorTypes = new String[] {"LEATHER", "CHAINMAIL", "IRON", "DIAMOND", "NETHERITE"};

    // sword mending
    // bow flame
    // bow infinity
    // crossbow multishot
    // trident mending
    // shield mending
    // helmet mending
    public static final LevelledWeightedRandom<Integer> ENCHANTMENT_MAX_LEVEL_1 = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .nextLevel()
            .nextLevel()
            .add(1,  5).nextLevel()
            .add(1, 10).nextLevel()
            .add(1, 15).nextLevel()
            .add(1, 20).nextLevel()
            .add(1, 25).build();

    // bow mending
    // crossbow mending
    public static final LevelledWeightedRandom<Integer> BOW_MENDING = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .nextLevel()
            .nextLevel()
            .nextLevel()
            .add(1,  5).nextLevel()
            .add(1, 10).nextLevel()
            .add(1, 15).nextLevel()
            .add(1, 20).build();

    // sword fire aspect
    // axe fire aspect
    // trident fire aspect
    public static final LevelledWeightedRandom<Integer> FIRE_ASPECT = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 10).nextLevel()
            .add(1, 15).add(2,  5).nextLevel()
            .add(1, 15).add(2, 10).nextLevel()
            .add(1, 10).add(2, 15).add(3, 5).nextLevel()
            .add(1,  5).add(2, 20).add(3, 10).nextLevel()
            .add(2, 20).add(3, 15).nextLevel()
            .add(2, 10).add(3, 20).build();

    // bow punch
    public static final LevelledWeightedRandom<Integer> PUNCH = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 30).nextLevel()
            .add(1, 40).add(2,  5).nextLevel()
            .add(1, 30).add(2, 20).nextLevel()
            .add(1, 25).add(2, 25).add(3, 5).nextLevel()
            .add(1, 10).add(2, 35).add(3, 15).nextLevel()
            .add(2, 40).add(3, 15).nextLevel()
            .add(2, 40).add(3, 20).build();

    // sword unbreaking
    // axe unbreaking
    public static final LevelledWeightedRandom<Integer> MELEE_UNBREAKING = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 40).add(2, 20).nextLevel()
            .add(1, 25).add(2, 25).add(3, 10).nextLevel()
            .add(1, 10).add(2, 40).add(3, 20).nextLevel()
            .add(1,  5).add(2, 55).add(3, 30).nextLevel()
            .add(2, 40).add(3, 40).nextLevel()
            .add(3, 60).build();

    // bow unbreaking
    // crossbow unbreaking
    // trident unbreaking
    // helmet unbreaking
    // chestplate unbreaking
    public static final LevelledWeightedRandom<Integer> UNBREAKING = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 40).add(2, 20).nextLevel()
            .add(1, 25).add(2, 25).add(3, 10).nextLevel()
            .add(1, 10).add(2, 40).add(3, 20).nextLevel()
            .add(1,  5).add(2, 55).add(3, 30).nextLevel()
            .add(2, 40).add(3, 40).nextLevel()
            .add(2, 25).add(3, 65).nextLevel()
            .add(3, 60).nextLevel()
            .add(3, 75).add(4,  5).nextLevel()
            .add(3, 80).add(4, 20).build();

    // shield unbreaking
    public static final LevelledWeightedRandom<Integer> SHIELD_UNBREAKING = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .nextLevel()
            .add(1, 20).nextLevel()
            .add(1, 25).add(2, 15).nextLevel()
            .add(1, 15).add(2, 20).add(3, 10).nextLevel()
            .add(1, 10).add(2, 15).add(3, 20).nextLevel()
            .add(1, 10).add(2, 20).add(3, 20).build();

    // sword knockback
    // crossbow quick charge
    // trident knockback
    public static final LevelledWeightedRandom<Integer> KNOCKBACK = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 30).nextLevel()
            .add(1, 40).add(2,  5).nextLevel()
            .add(1, 30).add(2, 20).nextLevel()
            .add(1, 25).add(2, 25).add(3,  5).nextLevel()
            .add(1, 10).add(2, 35).add(3, 15).nextLevel()
            .add(2, 40).add(3, 15).nextLevel()
            .add(2, 40).add(3, 20).build();

    // sword sweeping edge
    public static final LevelledWeightedRandom<Integer> SWEEPING_EDGE = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1,  5).nextLevel()
            .add(1, 15).add(2,  5).nextLevel()
            .add(1, 20).add(2, 10).nextLevel()
            .add(1, 15).add(2, 15).add(3, 10).nextLevel()
            .add(1,  5).add(2, 20).add(3, 20).nextLevel()
            .add(2, 10).add(3, 25).add(4, 10).nextLevel()
            .add(2,  5).add(3, 30).add(4, 15).build();

    // helmet thorns
    // chestplate thorns
    public static final LevelledWeightedRandom<Integer> THORNS = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1,  5).nextLevel()
            .add(1, 15).add(2,  5).nextLevel()
            .add(1, 20).add(2, 10).nextLevel()
            .add(1, 15).add(2, 15).add(3, 10).nextLevel()
            .add(1,  5).add(2, 20).add(3, 20).nextLevel()
            .add(2, 10).add(3, 25).add(4, 10).nextLevel()
            .add(2,  5).add(3, 30).add(4, 15).nextLevel()
            .add(3, 40).add(4, 20).build();

    // sword smite
    // trident smite
    public static final LevelledWeightedRandom<Integer> SMITE = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 15).add(2, 10).nextLevel()
            .add(1, 15).add(2, 15).add(3,  5).nextLevel()
            .add(1,  5).add(2, 15).add(3, 15).nextLevel()
            .add(2, 10).add(3, 20).add(4, 10).nextLevel()
            .add(3, 20).add(4, 15).add(5, 10).nextLevel()
            .add(3, 10).add(4, 20).add(5, 20).nextLevel()
            .add(4, 25).add(5, 25).build();

    // axe sharpness
    // bow power
    // crossbow piercing
    public static final LevelledWeightedRandom<Integer> POWER = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 15).add(2, 10).nextLevel()
            .add(1, 20).add(2, 15).add(3,  5).nextLevel()
            .add(1,  5).add(2, 20).add(3, 20).nextLevel()
            .add(2, 10).add(3, 25).add(4, 10).nextLevel()
            .add(3, 20).add(4, 15).add(5, 10).nextLevel()
            .add(3, 10).add(4, 25).add(5, 20).nextLevel()
            .add(4, 15).add(5, 25).add(6, 10).nextLevel()
            .add(5, 30).add(6, 20).build();

    // axe smite
    public static final LevelledWeightedRandom<Integer> AXE_SMITE = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 20).add(2, 10).nextLevel()
            .add(1, 15).add(2, 15).add(3,  10).nextLevel()
            .add(1,  5).add(2, 20).add(3, 20).nextLevel()
            .add(2, 10).add(3, 20).add(4, 15).nextLevel()
            .add(3, 25).add(4, 20).add(5, 10).nextLevel()
            .add(3, 10).add(4, 25).add(5, 15).add(6, 10).nextLevel()
            .add(4, 20).add(5, 20).add(6, 20).nextLevel()
            .add(5, 25).add(6, 25).build();

    // sword sharpness
    // trident sharpness
    public static final LevelledWeightedRandom<Integer> SHARPNESS = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 10).add(2, 10).nextLevel()
            .add(1, 15).add(2, 10).add(3,  5).nextLevel()
            .add(1,  5).add(2, 15).add(3, 15).nextLevel()
            .add(2, 10).add(3, 20).add(4, 10).nextLevel()
            .add(3, 20).add(4, 15).add(5, 15).nextLevel()
            .add(3, 10).add(4, 20).add(5, 20).nextLevel()
            .add(4, 20).add(5, 30).build();

    // trident loyalty
    public static final LevelledWeightedRandom<Integer> LOYALTY = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .nextLevel()
            .nextLevel()
            .add(1, 100).nextLevel()
            .add(1, 75).add(2, 25).nextLevel()
            .add(1, 50).add(2, 30).add(3, 20).nextLevel()
            .add(1, 30).add(2, 40).add(3, 30).nextLevel()
            .add(1, 20).add(2, 30).add(3, 50).build();

    // helmet protection
    public static final LevelledWeightedRandom<Integer> PROTECTION = new LevelledWeightedRandom.Builder<Integer>(0, 100)
            .add(1, 10).add(2, 10).nextLevel()
            .add(1, 15).add(2, 10).add(3,  5).nextLevel()
            .add(1,  5).add(2, 15).add(3, 15).nextLevel()
            .add(2, 10).add(3, 20).add(4, 10).nextLevel()
            .add(3, 20).add(4, 15).add(5, 10).nextLevel()
            .add(3, 10).add(4, 20).add(5, 20).nextLevel()
            .add(4, 20).add(5, 30).build();

    public static final LevelledWeightedRandom<Material> SWORD = generatePairs(toolTypes, "SWORD");
    public static final LevelledWeightedRandom<Material> AXE = generatePairs(toolTypes, "AXE");
    public static final LevelledWeightedRandom<Material> HELMET = generatePairs(armorTypes, "HELMET");
    public static final LevelledWeightedRandom<Material> CHESTPLATE = generatePairs(armorTypes, "CHESTPLATE");
    public static final LevelledWeightedRandom<Material> LEGGINGS = generatePairs(armorTypes, "LEGGINGS");
    public static final LevelledWeightedRandom<Material> BOOTS = generatePairs(armorTypes, "BOOTS");

    private static LevelledWeightedRandom<Material> generatePairs(String[] prefixes, String suffix) {
        List<Material> materials = new ArrayList<>();
        for (String prefix : prefixes) {
            Material mat = Material.getMaterial(prefix + "_" + suffix);
            if (mat == null) {
                throw new IllegalArgumentException("Unable to find material: " + prefix + "_" + suffix);
            }
            materials.add(mat);
        }
        return materialGenerator(materials);
    }

    private static LevelledWeightedRandom<Material> materialGenerator(List<Material> materials) {
        if (materials.size() != 5) {
            throw new IllegalArgumentException("Materials list must be exactly 5 in length");
        }
        Material a = materials.get(0);
        Material b = materials.get(1);
        Material c = materials.get(2);
        Material d = materials.get(3);
        Material e = materials.get(4);
        return new LevelledWeightedRandom.Builder<Material>()
                .add(a, 40).add(b, 40).add(c, 20).nextLevel()
                .add(b, 40).add(c, 50).add(d, 10).nextLevel()
                .add(c, 50).add(d, 35).add(e, 15).nextLevel()
                .add(c, 10).add(d, 65).add(e, 25).nextLevel()
                .add(d, 30).add(e, 70).nextLevel()
                .add(e, 1).build();
    }
}
