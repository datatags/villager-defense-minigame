package me.theguyhere.villagerdefense.plugin.entities;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.Main;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.LerpChance;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Mobs {
    private enum ItemType {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        SWORD,
        AXE,
    }

    private enum ItemMaterial {
        NONE,
        WOODEN,
        STONE,
        LEATHER,
        CHAINMAIL,
        IRON,
        GOLD,
        DIAMOND,
        NETHERITE,
    }

    private static final NamespacedKey HEALTH_BOOST = new NamespacedKey(Main.plugin, "hpBoost");
    private static final NamespacedKey ATTACK_BOOST = new NamespacedKey(Main.plugin, "atkBoost");
    private static final NamespacedKey SPEED_BOOST = new NamespacedKey(Main.plugin, "spdBoost");
    private static void setMinion(Arena arena, LivingEntity livingEntity) {
        livingEntity.setCustomName(healthBar(1, 1, 5));
        livingEntity.setCustomNameVisible(true);
        livingEntity.setMetadata("game", new FixedMetadataValue(Main.plugin, arena.getGameID()));
        livingEntity.setMetadata("wave", new FixedMetadataValue(Main.plugin, arena.getCurrentWave()));
        if (livingEntity.isInsideVehicle())
            Objects.requireNonNull(livingEntity.getVehicle()).remove();
        for (Entity passenger : livingEntity.getPassengers())
            passenger.remove();

        commonMobSetup(arena, livingEntity);
    }

    private static void setBoss(Arena arena, LivingEntity livingEntity) {
        commonMobSetup(arena, livingEntity);
    }

    private static void setLargeMinion(Arena arena, LivingEntity livingEntity) {
        livingEntity.setCustomName(healthBar(1, 1, 10));
        livingEntity.setCustomNameVisible(true);
        commonMobSetup(arena, livingEntity);
    }

    private static void commonMobSetup(Arena arena, LivingEntity livingEntity) {
        Team monsters = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard()
                .getTeam("monsters");
        assert monsters != null;

        monsters.addEntry(livingEntity.getUniqueId().toString());
        livingEntity.setMetadata("VD", new FixedMetadataValue(Main.plugin, arena.getId()));
        livingEntity.setMetadata("VD_Monster", new FixedMetadataValue(Main.plugin, true));
        livingEntity.setRemoveWhenFarAway(false);
        livingEntity.setCanPickupItems(false);

        EntityEquipment equipment = livingEntity.getEquipment();
        if (equipment != null && (equipment.getHelmet() == null || equipment.getHelmet().getType() == Material.AIR)) {
            // Prevent mobs burning in daylight without giving complete fire immunity
            equipment.setHelmet(new ItemStack(Material.OAK_BUTTON, 1));
        }
        setAttributeModifiers(arena, livingEntity);
    }

    @SuppressWarnings("UnstableApiUsage")
    private static void setAttributeModifiers(Arena arena, LivingEntity entity) {
        // Set attribute modifiers
        double difficulty = arena.getCurrentDifficulty();
        for (int i = 0; i < 3; i++) {
            double boost;
            if (difficulty < 5)
                boost = 0;
            else boost = difficulty - 5;
            switch (i) {
                case 0:
                    Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH))
                            .addModifier(new AttributeModifier(
                                    HEALTH_BOOST, boost / 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY
                            ));
                    break;
                case 1:
                    Objects.requireNonNull(entity.getAttribute(Attribute.ATTACK_DAMAGE))
                            .addModifier(new AttributeModifier(
                                    ATTACK_BOOST, boost / 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY
                            ));
                    break;
                case 2:
                    Objects.requireNonNull(entity.getAttribute(Attribute.MOVEMENT_SPEED))
                            .addModifier(new AttributeModifier(
                                    SPEED_BOOST, boost / 120, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY
                            ));
            }
        }
    }

    private static void setBaby(Arena arena, Ageable ageable) {
        Random r = new Random();
        double difficulty = arena.getCurrentDifficulty();

        if (r.nextDouble() < .25 / (1 + Math.pow(Math.E, - (difficulty - 8) / 2)))
            ageable.setBaby();
        else ageable.setAdult();
    }

    private static void setSize(Arena arena, Slime slime) {
        int size = new LerpChance<Integer>()
                .add(3, 1).add(6, 2).add(9, 3).add(12, 4)
                .choose(arena.getCurrentDifficulty());

        slime.setSize(size);
    }

    private static void setSword(Arena arena, Monster monster) {
        EntityEquipment equipment = monster.getEquipment();
        assert equipment != null;
        equipment.setItemInMainHand(getSword(arena), true);
        equipment.setItemInMainHandDropChance(0);
        equipment.setItemInOffHand(null);
    }

    private static void setAxe(Arena arena, Monster monster) {
        EntityEquipment equipment = monster.getEquipment();
        assert equipment != null;
        equipment.setItemInMainHand(getAxe(arena), true);
        equipment.setItemInMainHandDropChance(0);
        equipment.setItemInOffHand(null);
    }

    private static void setBow(Arena arena, Monster monster) {
        EntityEquipment equipment = monster.getEquipment();
        assert equipment != null;
        equipment.setItemInMainHand(getBow(arena), true);
        equipment.setItemInMainHandDropChance(0);
        equipment.setItemInOffHand(null);
    }

    private static void setCrossbow(Arena arena, Monster monster) {
        EntityEquipment equipment = monster.getEquipment();
        assert equipment != null;
        equipment.setItemInMainHand(getCrossbow(arena), true);
        equipment.setItemInMainHandDropChance(0);
        equipment.setItemInOffHand(null);
    }

    private static void setTrident(Arena arena, Monster monster) {
        EntityEquipment equipment = monster.getEquipment();
        assert equipment != null;
        equipment.setItemInMainHand(getTrident(arena), true);
        equipment.setItemInMainHandDropChance(0);
        equipment.setItemInOffHand(null);
    }

    private static void setArmor(Arena arena, Monster monster) {
        EntityEquipment equipment = monster.getEquipment();
        assert equipment != null;
        equipment.setHelmet(getHelmet(arena), true);
        equipment.setHelmetDropChance(0);
        equipment.setChestplate(getChestplate(arena), true);
        equipment.setChestplateDropChance(0);
        equipment.setLeggings(getLeggings(arena), true);
        equipment.setLeggingsDropChance(0);
        equipment.setBoots(getBoots(arena), true);
        equipment.setBootsDropChance(0);
    }

    private static ItemStack getSword(Arena arena) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        double difficulty = arena.getCurrentDifficulty();

        ItemStack item = getItem(ItemType.AXE, getToolTypeSelector().choose(difficulty));
        if (item == null) {
            return null;
        }
        Material mat = item.getType();

        enchants.put(Enchantment.SHARPNESS, new LerpChance<Integer>()
                .add(3, 0)
                .add(5, 1)
                .add(7, 2)
                .add(9, 3)
                .add(11, 4)
                .add(13, 5)
                .choose(difficulty));

        enchants.put(Enchantment.KNOCKBACK, new LerpChance<Integer>()
                .add(5, 0)
                .add(9, 1)
                .add(13, 2)
                .add(18, 3)
                .choose(difficulty));

        enchants.put(Enchantment.FIRE_ASPECT, new LerpChance<Integer>()
                .add(6, 0)
                .add(10, 1)
                .add(15, 2)
                .add(20, 3)
                .choose(difficulty));

        return ItemManager.createItem(mat, null, null, enchants);
    }

    private static ItemStack getAxe(Arena arena) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        double difficulty = arena.getCurrentDifficulty();

        ItemStack item = getItem(ItemType.AXE, getToolTypeSelector().choose(difficulty));
        if (item == null) {
            return null;
        }
        Material mat = item.getType();

        enchants.put(Enchantment.SHARPNESS, new LerpChance<Integer>()
                .add(3, 0)
                .add(5, 1)
                .add(7, 2)
                .add(9, 3)
                .add(11, 4)
                .add(13, 5)
                .choose(difficulty));

        enchants.put(Enchantment.FIRE_ASPECT, new LerpChance<Integer>()
                .add(6, 0)
                .add(10, 1)
                .add(15, 2)
                .add(20, 3)
                .choose(difficulty));

        return ItemManager.createItem(mat, null, null, enchants);
    }

    private static ItemStack getBow(Arena arena) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        double difficulty = arena.getCurrentDifficulty();

        enchants.put(Enchantment.POWER, new LerpChance<Integer>()
                .add(3, 0)
                .add(5, 1)
                .add(7, 2)
                .add(9, 3)
                .add(11, 4)
                .add(13, 5)
                .choose(difficulty));

        enchants.put(Enchantment.PUNCH, new LerpChance<Integer>()
                .add(5, 0)
                .add(9, 1)
                .add(13, 2)
                .add(18, 3)
                .choose(difficulty));

        enchants.put(Enchantment.FLAME, new LerpChance<Integer>()
                .add(6, 0)
                .add(11, 1)
                .choose(difficulty));

        return ItemManager.createItem(Material.BOW, null, null, enchants);
    }

    private static ItemStack getCrossbow(Arena arena) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        double difficulty = arena.getCurrentDifficulty();

        enchants.put(Enchantment.PIERCING, new LerpChance<Integer>()
                .add(3, 0)
                .add(5, 1)
                .add(7, 2)
                .add(9, 3)
                .add(11, 4)
                .add(13, 5)
                .choose(difficulty));

        enchants.put(Enchantment.QUICK_CHARGE, new LerpChance<Integer>()
                .add(5, 0)
                .add(9, 1)
                .add(13, 2)
                .add(18, 3)
                .choose(difficulty));

        enchants.put(Enchantment.MULTISHOT, new LerpChance<Integer>()
                .add(6, 0)
                .add(11, 1)
                .choose(difficulty));

        return ItemManager.createItem(Material.CROSSBOW, null, null, enchants);
    }

    private static ItemStack getTrident(Arena arena) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        double difficulty = arena.getCurrentDifficulty();

        enchants.put(Enchantment.SHARPNESS, new LerpChance<Integer>()
                .add(3, 0)
                .add(5, 1)
                .add(7, 2)
                .add(9, 3)
                .add(11, 4)
                .add(13, 5)
                .choose(difficulty));

        enchants.put(Enchantment.KNOCKBACK, new LerpChance<Integer>()
                .add(5, 0)
                .add(9, 1)
                .add(13, 2)
                .add(18, 3)
                .choose(difficulty));

        enchants.put(Enchantment.FIRE_ASPECT, new LerpChance<Integer>()
                .add(6, 0)
                .add(10, 1)
                .add(15, 2)
                .add(20, 3)
                .choose(difficulty));

        return ItemManager.createItem(Material.TRIDENT, null, null, enchants);
    }

    private static ItemStack getItem(ItemType type, ItemMaterial mat) {
        if (mat == ItemMaterial.NONE) {
            if (type == ItemType.HELMET) {
                return new ItemStack(Material.OAK_BUTTON);
            }
            return null;
        }
        if (type != ItemType.HELMET && type != ItemType.CHESTPLATE && type != ItemType.LEGGINGS && type != ItemType.BOOTS) {
            return null;
        }
        return new ItemStack(Material.valueOf(mat.name() + "_" + type.name()));
    }

    private static LerpChance<ItemMaterial> getArmorTypeSelector() {
        return new LerpChance<ItemMaterial>()
                .add(1, ItemMaterial.NONE)
                .add(3, ItemMaterial.LEATHER)
                .add(5, ItemMaterial.CHAINMAIL)
                .add(7, ItemMaterial.IRON)
                .add(10, ItemMaterial.DIAMOND)
                .add(15, ItemMaterial.NETHERITE);
    }

    private static LerpChance<ItemMaterial> getToolTypeSelector() {
        return new LerpChance<ItemMaterial>()
                .add(1, ItemMaterial.NONE)
                .add(3, ItemMaterial.WOODEN)
                .add(5, ItemMaterial.STONE)
                .add(7, ItemMaterial.IRON)
                .add(10, ItemMaterial.DIAMOND)
                .add(15, ItemMaterial.NETHERITE);
    }

    private static ItemStack getHelmet(Arena arena) {
        ItemMaterial type = getArmorTypeSelector().choose(arena.getCurrentDifficulty());
        return getItem(ItemType.HELMET, type);
    }

    private static ItemStack getChestplate(Arena arena) {
        ItemMaterial type = getArmorTypeSelector().choose(arena.getCurrentDifficulty());
        return getItem(ItemType.CHESTPLATE, type);
    }

    private static ItemStack getLeggings(Arena arena) {
        ItemMaterial type = getArmorTypeSelector().choose(arena.getCurrentDifficulty());
        return getItem(ItemType.CHESTPLATE, type);
    }

    private static ItemStack getBoots(Arena arena) {
        ItemMaterial type = getArmorTypeSelector().choose(arena.getCurrentDifficulty());
        return getItem(ItemType.BOOTS, type);
    }

    public static void setVillager(Arena arena, Villager villager) {
        Team villagers = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard()
                .getTeam("villagers");
        assert villagers != null;

        villagers.addEntry(villager.getUniqueId().toString());
        villager.setCustomName(healthBar(1, 1, 5));
        villager.setCustomNameVisible(true);
        villager.setMetadata("VD", new FixedMetadataValue(Main.plugin, arena.getId()));
    }

    public static void setZombie(Arena arena, Zombie zombie) {
        setMinion(arena, zombie);
        setSword(arena, zombie);
        setArmor(arena, zombie);
        setBaby(arena, zombie);
    }

    public static void setHusk(Arena arena, Husk husk) {
        setMinion(arena, husk);
        setSword(arena, husk);
        setArmor(arena, husk);
        setBaby(arena, husk);
    }

    public static void setWitherSkeleton(Arena arena, WitherSkeleton witherSkeleton) {
        setMinion(arena, witherSkeleton);
        setSword(arena, witherSkeleton);
        setArmor(arena, witherSkeleton);
    }

    public static void setBrute(Arena arena, PiglinBrute brute) {
        setMinion(arena, brute);
        setAxe(arena, brute);
        setArmor(arena, brute);
        setBaby(arena, brute);
        brute.setImmuneToZombification(true);
    }

    public static void setVindicator(Arena arena, Vindicator vindicator) {
        setMinion(arena, vindicator);
        setAxe(arena, vindicator);
        vindicator.setPatrolLeader(false);
        vindicator.setCanJoinRaid(false);
    }

    public static void setSpider(Arena arena, Spider spider) {
        setMinion(arena, spider);
    }

    public static void setCaveSpider(Arena arena, CaveSpider caveSpider) {
        setMinion(arena, caveSpider);
    }

    public static void setWitch(Arena arena, Witch witch) {
        setMinion(arena, witch);
    }

    public static void setSkeleton(Arena arena, Skeleton skeleton) {
        setMinion(arena, skeleton);
        setBow(arena, skeleton);
        setArmor(arena, skeleton);
    }

    public static void setStray(Arena arena, Stray stray) {
        setMinion(arena, stray);
        setBow(arena, stray);
        setArmor(arena, stray);
    }

    public static void setDrowned(Arena arena, Drowned drowned) {
        setMinion(arena, drowned);
        setTrident(arena, drowned);
        setArmor(arena, drowned);
        setBaby(arena, drowned);
    }

    public static void setBlaze(Arena arena, Blaze blaze) {
        setMinion(arena, blaze);
    }

    public static void setGhast(Arena arena, Ghast ghast) {
        setMinion(arena, ghast);
    }

    public static void setPillager(Arena arena, Pillager pillager) {
        setMinion(arena, pillager);
        setCrossbow(arena, pillager);
        pillager.setPatrolLeader(false);
        pillager.setCanJoinRaid(false);
    }

    public static void setSlime(Arena arena, Slime slime) {
        setMinion(arena, slime);
        setSize(arena, slime);
    }

    public static void setMagmaCube(Arena arena, MagmaCube magmaCube) {
        setMinion(arena, magmaCube);
        setSize(arena, magmaCube);
    }

    public static void setCreeper(Arena arena, Creeper creeper) {
        setMinion(arena, creeper);
        double difficulty = arena.getCurrentDifficulty();
        if (new LerpChance<Boolean>().add(5, false).add(9, true).choose(difficulty)) {
            creeper.setPowered(true);
        }
    }

    public static void setPhantom(Arena arena, Phantom phantom) {
        setMinion(arena, phantom);
    }

    public static void setEvoker(Arena arena, Evoker evoker) {
        setMinion(arena, evoker);
        evoker.setCanJoinRaid(false);
        evoker.setPatrolLeader(false);
    }

    public static void setZoglin(Arena arena, Zoglin zoglin) {
        setMinion(arena, zoglin);
    }

    public static void setRavager(Arena arena, Ravager ravager) {
        setLargeMinion(arena, ravager);
    }

    public static void setWither(Arena arena, Wither wither) {
        setBoss(arena, wither);
    }

    public static void setWolf(Main plugin, Arena arena, VDPlayer vdPlayer, Wolf wolf) {
        wolf.setAdult();
        wolf.setOwner(vdPlayer.getPlayer());
        wolf.setBreed(false);
        wolf.setMetadata("VD", new FixedMetadataValue(plugin, arena.getId()));
        wolf.setCustomName(vdPlayer.getPlayer().getName() + "'s Wolf");
        wolf.setCustomNameVisible(true);
        vdPlayer.incrementWolves();

        setAttributeModifiers(arena, wolf);
    }

    public static void setGolem(Main plugin, Arena arena, IronGolem ironGolem) {
        ironGolem.setMetadata("VD", new FixedMetadataValue(plugin, arena.getId()));
        ironGolem.setCustomName(healthBar(1, 1, 10));
        ironGolem.setCustomNameVisible(true);
        arena.incrementGolems();

        setAttributeModifiers(arena, ironGolem);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void setBreeze(Arena arena, Breeze breeze) {
        setMinion(arena, breeze);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void setBogged(Arena arena, Bogged bogged) {
        setMinion(arena, bogged);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void setCreaking(Arena arena, Creaking creaking) {
        setMinion(arena, creaking);
    }

    public static void setGuardian(Arena arena, Guardian guardian) {
        setMinion(arena, guardian);
    }

    public static void setElderGuardian(Arena arena, ElderGuardian elderGuardian) {
        setMinion(arena, elderGuardian);
    }

    public static void setIllusioner(Arena arena, Illusioner illusioner) {
        setMinion(arena, illusioner);
    }

    public static void setKillerBunny(Arena arena, Rabbit killerBunny) {
        killerBunny.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
        setMinion(arena, killerBunny);
    }

    public static void setWarden(Arena arena, Warden warden) {
        setBoss(arena, warden);
    }

    // Returns a formatted health bar
    public static String healthBar(double max, double remaining, int size) {
        String toFormat;
        double healthLeft = remaining / max;
        int healthBars = (int) (healthLeft * size + .99);
        if (healthBars < 0) healthBars = 0;

        if (healthLeft > .5)
            toFormat = "&a";
        else if (healthLeft > .25)
            toFormat = "&e";
        else toFormat = "&c";

        return CommunicationManager.format(toFormat +
                new String(new char[healthBars]).replace("\0", "▒") +
                new String(new char[size - healthBars]).replace("\0", "  "));
    }
}
