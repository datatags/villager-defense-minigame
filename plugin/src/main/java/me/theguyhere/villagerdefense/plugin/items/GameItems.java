package me.theguyhere.villagerdefense.plugin.items;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.LevelledWeightedRandom;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("SpellCheckingInspection")
public class GameItems {
	private static final boolean[] FLAGS = {false, false};

	// Categories of items
	public static ItemStack[] ABILITY_ITEMS;
	public static Material[] FOOD_MATERIALS;
	public static Material[] HELMET_MATERIALS;
	public static Material[] CHESTPLATE_MATERIALS;
	public static Material[] LEGGING_MATERIALS;
	public static Material[] BOOTS_MATERIALS;
	public static Material[] ARMOR_MATERIALS;
	public static Material[] CARE_MATERIALS;
	public static Material[] CLICKABLE_WEAPON_MATERIALS;
	public static Material[] CLICKABLE_CONSUME_MATERIALS;

	public static void init() {
		// Initialize constant arrays
		ABILITY_ITEMS = new ItemStack[]{mage(), ninja(), templar(), warrior(), knight(),
				priest(), siren(), monk(), messenger()};
		FOOD_MATERIALS = new Material[]{Material.BEETROOT, Material.CARROT, Material.BREAD,
				Material.MUTTON, Material.COOKED_BEEF, Material.GOLDEN_CARROT, Material.GOLDEN_APPLE,
				Material.ENCHANTED_GOLDEN_APPLE};
		HELMET_MATERIALS = new Material[]{Material.LEATHER_HELMET, Material.GOLDEN_HELMET,
				Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.DIAMOND_HELMET, Material.NETHERITE_HELMET,
				Material.TURTLE_HELMET};
		CHESTPLATE_MATERIALS = new Material[]{Material.LEATHER_CHESTPLATE, Material.GOLDEN_CHESTPLATE,
				Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE,
				Material.NETHERITE_HELMET};
		LEGGING_MATERIALS = new Material[]{Material.LEATHER_LEGGINGS, Material.GOLDEN_LEGGINGS,
				Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS,
				Material.NETHERITE_LEGGINGS};
		BOOTS_MATERIALS = new Material[]{Material.LEATHER_BOOTS, Material.GOLDEN_BOOTS,
				Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS};
		ARMOR_MATERIALS = new Material[]{Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE,
				Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
				Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE,
				Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
				Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE,
				Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS};
		CARE_MATERIALS = new Material[]{Material.COAL_BLOCK, Material.IRON_BLOCK,
				Material.DIAMOND_BLOCK, Material.BEACON};
		CLICKABLE_WEAPON_MATERIALS = new Material[]{Material.BOW, Material.CROSSBOW,
				Material.TRIDENT};
		CLICKABLE_CONSUME_MATERIALS = new Material[]{Material.GLASS_BOTTLE,
				Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.EXPERIENCE_BOTTLE,
				Material.MILK_BUCKET, Material.GHAST_SPAWN_EGG, Material.WOLF_SPAWN_EGG};
	}

	// Standard game items
	public static @NotNull ItemStack shop() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(Material.EMERALD,
				CommunicationManager.format("&2&l" + LanguageManager.names.itemShop),
				ItemManager.HIDE_ENCHANT_FLAGS, enchants,
				CommunicationManager.format("&7&o" + String.format(LanguageManager.messages.itemShopDesc, "10")));

		return GameItemType.SHOP.apply(item);
	}
	public static @NotNull ItemStack kitSelector() {
		ItemStack item = ItemManager.createItem(Material.CHEST,
				CommunicationManager.format("&9&l" + LanguageManager.names.kitSelection));
		return GameItemType.KIT_SELECTOR.apply(item);
	}
	public static @NotNull ItemStack challengeSelector() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(Material.NETHER_STAR,
				CommunicationManager.format("&9&l" + LanguageManager.names.challengeSelection),
				ItemManager.HIDE_ENCHANT_FLAGS, enchants);

		return GameItemType.CHALLENGE_SELECTOR.apply(item);
	}
	public static @NotNull ItemStack boostToggle(boolean boosted) {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(Material.FIREWORK_ROCKET,
				CommunicationManager.format("&b&l" + LanguageManager.names.boosts + ": " +
						getToggleStatus(boosted)),
				ItemManager.HIDE_ENCHANT_FLAGS, enchants);

		return GameItemType.BOOST_TOGGLE.apply(item);
	}
	public static @NotNull ItemStack shareToggle(boolean sharing) {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(Material.DISPENSER,
				CommunicationManager.format("&b&l" + LanguageManager.names.effectShare + ": " +
						getToggleStatus(sharing)),
				ItemManager.HIDE_ENCHANT_FLAGS, enchants);

		return GameItemType.SHARE_TOGGLE.apply(item);
	}
	public static @NotNull ItemStack crystalConverter() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(Material.DIAMOND,
				CommunicationManager.format("&b&l" + LanguageManager.names.crystalConverter),
				ItemManager.HIDE_ENCHANT_FLAGS, enchants);

		return GameItemType.CRYSTAL_CONVERTER.apply(item);
	}
	public static @NotNull ItemStack leave() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(Material.BARRIER,
				CommunicationManager.format("&c&l" + LanguageManager.messages.leave),
				ItemManager.HIDE_ENCHANT_FLAGS, enchants);

		return GameItemType.LEAVE.apply(item);
	}

    private static ItemStack generateItem(LevelledWeightedRandom<Material> random, int level) {
        return new ItemStack(random.getRandom(level));
    }

    private static void addProtection(Map<Enchantment,Integer> enchants, int level) {
        final Enchantment[] enchantments = {
                Enchantment.PROTECTION,
                Enchantment.PROJECTILE_PROTECTION,
                Enchantment.BLAST_PROTECTION,
                Enchantment.FIRE_PROTECTION,
        };
        Enchantment chosen = enchantments[ThreadLocalRandom.current().nextInt(4)];
        addEnchant(enchants, chosen, Randomizers.PROTECTION, level);
    }

    private static void addEnchant(Map<Enchantment,Integer> enchants, Enchantment ench, LevelledWeightedRandom<Integer> random, int level) {
        int enchantLevel = random.getRandom(level);
        if (enchantLevel > 0) {
            enchants.put(ench, enchantLevel);
        }
    }

	// Weapons
	public static @NotNull ItemStack sword(int level) {
		Material mat = Randomizers.SWORD.getRandom(level);
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.MELEE_UNBREAKING, level);
        addEnchant(enchantments, Enchantment.KNOCKBACK, Randomizers.KNOCKBACK, level);
        addEnchant(enchantments, Enchantment.SWEEPING_EDGE, Randomizers.SWEEPING_EDGE, level);
        addEnchant(enchantments, Enchantment.SMITE, Randomizers.SMITE, level);
        addEnchant(enchantments, Enchantment.SHARPNESS, Randomizers.SHARPNESS, level);
        addEnchant(enchantments, Enchantment.FIRE_ASPECT, Randomizers.FIRE_ASPECT, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack axe(int level) {
		Material mat = Randomizers.AXE.getRandom(level);
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.MELEE_UNBREAKING, level);
        addEnchant(enchantments, Enchantment.SMITE, Randomizers.AXE_SMITE, level);
        addEnchant(enchantments, Enchantment.SHARPNESS, Randomizers.POWER, level);
        addEnchant(enchantments, Enchantment.FIRE_ASPECT, Randomizers.FIRE_ASPECT, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack bow(int level) {
        Material mat = Material.BOW;
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.UNBREAKING, level);
        addEnchant(enchantments, Enchantment.PUNCH, Randomizers.PUNCH, level);
        addEnchant(enchantments, Enchantment.POWER, Randomizers.POWER, level);
        addEnchant(enchantments, Enchantment.FLAME, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
        addEnchant(enchantments, Enchantment.INFINITY, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.BOW_MENDING, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack crossbow(int level) {
		Material mat = Material.CROSSBOW;
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.UNBREAKING, level);
        addEnchant(enchantments, Enchantment.QUICK_CHARGE, Randomizers.KNOCKBACK, level);
        addEnchant(enchantments, Enchantment.PIERCING, Randomizers.POWER, level);
        addEnchant(enchantments, Enchantment.MULTISHOT, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.BOW_MENDING, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack trident(int level) {
		Material mat = Material.TRIDENT;
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.UNBREAKING, level);
        addEnchant(enchantments, Enchantment.LOYALTY, Randomizers.LOYALTY, level);
        addEnchant(enchantments, Enchantment.KNOCKBACK, Randomizers.KNOCKBACK, level);
        addEnchant(enchantments, Enchantment.SMITE, Randomizers.SMITE, level);
        addEnchant(enchantments, Enchantment.SHARPNESS, Randomizers.SHARPNESS, level);
        addEnchant(enchantments, Enchantment.FIRE_ASPECT, Randomizers.FIRE_ASPECT, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack shield(int level) {
		Material mat = Material.SHIELD;
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.SHIELD_UNBREAKING, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}

	// Ammo
	public static @NotNull ItemStack arrows() {
		ItemStack item = ItemManager.createItems(Material.ARROW, 16, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a45"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack arrowsS() {
		ItemStack item = ItemManager.createPotionItems(Material.TIPPED_ARROW, PotionType.SLOWNESS,
				8, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a50"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack arrowsD() {
		ItemStack item = ItemManager.createPotionItems(Material.TIPPED_ARROW, PotionType.HARMING,
				8, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a70"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack arrowsW() {
		ItemStack item = ItemManager.createPotionItems(Material.TIPPED_ARROW, PotionType.WEAKNESS,
				8, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a50"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack arrowsP() {
		ItemStack item = ItemManager.createPotionItems(Material.TIPPED_ARROW, PotionType.POISON,
				16, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a60"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack arrowsSPlus() {
		ItemStack item = ItemManager.createPotionItems(Material.TIPPED_ARROW,
				PotionType.LONG_SLOWNESS, 8, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a125"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack arrowsDPlus() {
		ItemStack item = ItemManager.createPotionItems(Material.TIPPED_ARROW,
				PotionType.STRONG_HARMING, 8, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a175"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack arrowsWPlus() {
		ItemStack item = ItemManager.createPotionItems(Material.TIPPED_ARROW,
				PotionType.LONG_WEAKNESS, 8, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a125"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack arrowsPPlus() {
		ItemStack item = ItemManager.createPotionItems(Material.TIPPED_ARROW,
				PotionType.LONG_POISON, 16, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a130"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack rockets() {
		ItemStack item = ItemManager.createItems(Material.FIREWORK_ROCKET, 4, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a50"));
		assert item != null;
		ItemMeta meta = item.getItemMeta();
		FireworkMeta fireworkMeta = (FireworkMeta) meta;
		assert fireworkMeta != null;

		for (int i = 0; i < 3; i++) {
			fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).with(FireworkEffect.Type.BALL_LARGE)
					.build());
		}
		fireworkMeta.setPower(3);

		item.setItemMeta(meta);

		return item;
	}
	public static @NotNull ItemStack rocketsPlus() {
		ItemStack item = new ItemStack(Material.FIREWORK_ROCKET, 4);
		ItemMeta meta = item.getItemMeta();
		FireworkMeta fireworkMeta = (FireworkMeta) meta;

		List<String> lore = new ArrayList<>();
		lore.add(CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a100"));
		assert meta != null;
		meta.setLore(lore);
		for (int i = 0; i < 9; i++) {
			fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.BALL_LARGE)
					.build());
		}
		fireworkMeta.setPower(9);

		item.setItemMeta(meta);

		return item;
	}

	// Armor
	public static @NotNull ItemStack helmet(int level) {
		Material mat = Randomizers.HELMET.getRandom(level);
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.UNBREAKING, level);
        addEnchant(enchantments, Enchantment.THORNS, Randomizers.THORNS, level);
        addProtection(enchantments, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack chestplate(int level) {
		Material mat = Randomizers.CHESTPLATE.getRandom(level);
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.UNBREAKING, level);
        addEnchant(enchantments, Enchantment.THORNS, Randomizers.THORNS, level);
        addProtection(enchantments, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack leggings(int level) {
		Material mat = Randomizers.LEGGINGS.getRandom(level);
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.UNBREAKING, level);
        addEnchant(enchantments, Enchantment.THORNS, Randomizers.THORNS, level);
        addProtection(enchantments, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack boots(int level) {
		Material mat = Randomizers.BOOTS.getRandom(level);
		Map<Enchantment, Integer> enchantments = new HashMap<>();
        addEnchant(enchantments, Enchantment.UNBREAKING, Randomizers.UNBREAKING, level);
        addEnchant(enchantments, Enchantment.THORNS, Randomizers.THORNS, level);
        addProtection(enchantments, level);
        addEnchant(enchantments, Enchantment.MENDING, Randomizers.ENCHANTMENT_MAX_LEVEL_1, level);
		int price = Costs.getTotalCost(mat, enchantments);

		ItemStack item = ItemManager.createItem(mat, null, FLAGS, enchantments,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a" +
						price));

		return item == null ? new ItemStack(Material.AIR) : item;
	}

	// Consumables
	public static @NotNull ItemStack totem() {
		ItemStack item = ItemManager.createItem(Material.TOTEM_OF_UNDYING, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a1000"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack gapple() {
		ItemStack item = ItemManager.createItem(Material.GOLDEN_APPLE, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a120"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack egapple() {
		ItemStack item = ItemManager.createItem(Material.ENCHANTED_GOLDEN_APPLE, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a300"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack gcarrot() {
		ItemStack item = ItemManager.createItem(Material.GOLDEN_CARROT, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a80"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack steak() {
		ItemStack item = ItemManager.createItems(Material.COOKED_BEEF, 2, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a60"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack mutton() {
		ItemStack item = ItemManager.createItems(Material.COOKED_MUTTON, 2, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a40"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack bread() {
		ItemStack item = ItemManager.createItems(Material.BREAD, 3, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a40"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack carrot() {
		ItemStack item = ItemManager.createItems(Material.CARROT, 5, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a30"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack beetroot() {
		ItemStack item = ItemManager.createItems(Material.BEETROOT, 8, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a25"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack health() {
		ItemStack item = ItemManager.createPotionItem(Material.POTION, PotionType.HEALING,
				null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a50"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack health2() {
		ItemStack item = ItemManager.createPotionItem(Material.POTION,
				PotionType.STRONG_HEALING, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a120"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack health3() {
		ItemStack item = ItemManager.createPotionItem(Material.LINGERING_POTION,
				PotionType.STRONG_HEALING, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a200"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack strength() {
		ItemStack item = ItemManager.createPotionItem(Material.POTION, PotionType.STRENGTH,
				null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a150"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack strength2() {
		ItemStack item = ItemManager.createPotionItem(Material.POTION,
				PotionType.STRONG_STRENGTH, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a400"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack regen() {
		ItemStack item = ItemManager.createPotionItem(Material.POTION, PotionType.REGENERATION, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a175"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack regen2() {
		ItemStack item = ItemManager.createPotionItem(Material.POTION,
				PotionType.STRONG_REGENERATION, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a450"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack speed() {
		ItemStack item = ItemManager.createPotionItem(Material.POTION, PotionType.SWIFTNESS, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a125"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack speed2() {
		ItemStack item = ItemManager.createPotionItem(Material.POTION, PotionType.STRONG_SWIFTNESS, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a350"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack milk() {
		ItemStack item = ItemManager.createItem(Material.MILK_BUCKET, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a75"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack golem() {
		ItemStack item = ItemManager.createItem(Material.GHAST_SPAWN_EGG,
				new ColoredMessage(ChatColor.WHITE, LanguageManager.names.golemEgg).toString(),
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a500"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack wolf() {
		ItemStack item = ItemManager.createItem(Material.WOLF_SPAWN_EGG, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a250"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack smallCare() {
		ItemStack item = ItemManager.createItem(Material.COAL_BLOCK,
				new ColoredMessage(ChatColor.DARK_GREEN, LanguageManager.names.carePackageSmall).toString(),
				new ColoredMessage(LanguageManager.names.contents + ":").toString(),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.weapon), "1", "1"),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.armor), "1", "1"),
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a200"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack mediumCare() {
		ItemStack item = ItemManager.createItem(Material.IRON_BLOCK,
				new ColoredMessage(ChatColor.DARK_AQUA, LanguageManager.names.carePackageMedium).toString(),
				new ColoredMessage(LanguageManager.names.contents + ":").toString(),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.weapon), "1", "2"),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.armor), "1", "2"),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.consumable), "1",
						"2"),
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a500"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack largeCare() {
		ItemStack item = ItemManager.createItem(Material.DIAMOND_BLOCK,
				new ColoredMessage(ChatColor.BLUE, LanguageManager.names.carePackageLarge).toString(),
				new ColoredMessage(LanguageManager.names.contents + ":").toString(),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.weapon), "1", "4"),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.armor), "2", "3"),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.consumable), "1",
						"3"),
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a1200"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack extraCare() {
		ItemStack item = ItemManager.createItem(Material.BEACON,
				new ColoredMessage(ChatColor.AQUA, LanguageManager.names.carePackageExtra).toString(),
				new ColoredMessage(LanguageManager.names.contents + ":").toString(),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.weapon), "1", "5"),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.weapon), "1", "4"),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.armor), "1", "5"),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.armor), "1", "4"),
				CommunicationManager.format(new ColoredMessage(LanguageManager.messages.consumable), "2",
						"4"),
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a3000"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack experience() {
		ItemStack item = ItemManager.createItem(Material.EXPERIENCE_BOTTLE, null,
				CommunicationManager.format("&2" + LanguageManager.messages.gems + ": &a75"));

		return item == null ? new ItemStack(Material.AIR) : item;
	}

	// Kit abilities
	public static @NotNull ItemStack mage() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(
				Material.PURPLE_DYE,
				new ColoredMessage(
						ChatColor.LIGHT_PURPLE,
						LanguageManager.kits.mage.name + " " + LanguageManager.names.essence
				).toString(),
				ItemManager.HIDE_ENCHANT_FLAGS,
				enchants,
				new ColoredMessage(LanguageManager.messages.rightClick).toString());

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack ninja() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(
				Material.BLACK_DYE,
				new ColoredMessage(
						ChatColor.LIGHT_PURPLE,
						LanguageManager.kits.ninja.name + " " + LanguageManager.names.essence
				).toString(),
				ItemManager.HIDE_ENCHANT_FLAGS,
				enchants,
				new ColoredMessage(LanguageManager.messages.rightClick).toString()
		);

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack templar() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(
				Material.YELLOW_DYE,
				new ColoredMessage(
						ChatColor.LIGHT_PURPLE,
						LanguageManager.kits.templar.name + " " + LanguageManager.names.essence
				).toString(),
				ItemManager.HIDE_ENCHANT_FLAGS,
				enchants,
				new ColoredMessage(LanguageManager.messages.rightClick).toString()
		);

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack warrior() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(
				Material.RED_DYE,
				new ColoredMessage(
						ChatColor.LIGHT_PURPLE,
						LanguageManager.kits.warrior.name + " " + LanguageManager.names.essence
				).toString(),
				ItemManager.HIDE_ENCHANT_FLAGS,
				enchants,
				new ColoredMessage(LanguageManager.messages.rightClick).toString()
		);

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack knight() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(
				Material.BROWN_DYE,
				new ColoredMessage(
						ChatColor.LIGHT_PURPLE,
						LanguageManager.kits.knight.name + " " + LanguageManager.names.essence
				).toString(),
				ItemManager.HIDE_ENCHANT_FLAGS,
				enchants,
				new ColoredMessage(LanguageManager.messages.rightClick).toString()
		);

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack priest() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(
				Material.WHITE_DYE,
				new ColoredMessage(
						ChatColor.LIGHT_PURPLE,
						LanguageManager.kits.priest.name + " " + LanguageManager.names.essence
				).toString(),
				ItemManager.HIDE_ENCHANT_FLAGS,
				enchants,
				new ColoredMessage(LanguageManager.messages.rightClick).toString()
		);

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack siren() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(
				Material.PINK_DYE,
				new ColoredMessage(
						ChatColor.LIGHT_PURPLE,
						LanguageManager.kits.siren.name + " " + LanguageManager.names.essence
				).toString(),
				ItemManager.HIDE_ENCHANT_FLAGS,
				enchants,
				new ColoredMessage(LanguageManager.messages.rightClick).toString()
		);

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack monk() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(
				Material.GREEN_DYE,
				new ColoredMessage(
						ChatColor.LIGHT_PURPLE,
						LanguageManager.kits.monk.name + " " + LanguageManager.names.essence
				).toString(),
				ItemManager.HIDE_ENCHANT_FLAGS,
				enchants,
				new ColoredMessage(LanguageManager.messages.rightClick).toString()
		);

		return item == null ? new ItemStack(Material.AIR) : item;
	}
	public static @NotNull ItemStack messenger() {
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.UNBREAKING, 1);

		ItemStack item = ItemManager.createItem(
				Material.BLUE_DYE,
				new ColoredMessage(
						ChatColor.LIGHT_PURPLE,
						LanguageManager.kits.messenger.name + " " + LanguageManager.names.essence
				).toString(),
				ItemManager.HIDE_ENCHANT_FLAGS,
				enchants,
				new ColoredMessage(LanguageManager.messages.rightClick).toString()
		);

		return item == null ? new ItemStack(Material.AIR) : item;
	}

    public static final LevelledWeightedRandom<ItemStack> WEAPON = new LevelledWeightedRandom.Builder<ItemStack>()
            .add(GameItems::sword, 40).add(GameItems::axe, 35).add(GameItems::bow, 15).add(GameItems::arrows, 10).nextLevel()
            .add(GameItems::sword, 35).add(GameItems::axe, 35).add(GameItems::shield, 10)
                .add(GameItems::bow, 10).add(GameItems::arrows, 6).add(GameItems::arrowsP, 4).nextLevel()
            .add(GameItems::sword, 30).add(GameItems::axe, 30).add(GameItems::bow, 10).add(GameItems::shield, 10).add(GameItems::crossbow, 10)
                .add(GameItems::arrows, 3).add(GameItems::arrowsP, 3).add(GameItems::arrowsS, 2).add(GameItems::arrowsW, 1).add(GameItems::arrowsD, 1).nextLevel()
            .add(GameItems::sword, 30).add(GameItems::axe, 25).add(GameItems::bow, 10).add(GameItems::shield, 10)
                .add(GameItems::crossbow, 10).add(GameItems::arrows, 5).add(GameItems::arrowsPPlus, 3).add(GameItems::arrowsS, 2)
                .add(GameItems::arrowsW, 2).add(GameItems::arrowsD, 2).add(GameItems::rockets, 1).nextLevel()
            .add(GameItems::sword, 20).add(GameItems::axe, 20).add(GameItems::shield, 10).add(GameItems::bow, 10)
                .add(GameItems::crossbow, 10).add(GameItems::trident, 10).add(GameItems::arrows, 5).add(GameItems::arrowsPPlus, 4)
                .add(GameItems::arrowsSPlus, 3).add(GameItems::arrowsWPlus, 2).add(GameItems::arrowsD, 2).add(GameItems::rockets, 2)
                .add(GameItems::rocketsPlus, 2).nextLevel()
            .add(GameItems::sword, 20).add(GameItems::axe, 20).add(GameItems::shield, 10).add(GameItems::bow, 10)
                .add(GameItems::crossbow, 10).add(GameItems::trident, 10).add(GameItems::arrows, 5).add(GameItems::arrowsPPlus, 4)
                .add(GameItems::arrowsSPlus, 3).add(GameItems::arrowsWPlus, 3).add(GameItems::arrowsDPlus, 3).add(GameItems::rocketsPlus, 2)
            .build();

    public static final LevelledWeightedRandom<ItemStack> RANGED = new LevelledWeightedRandom.Builder<ItemStack>()
            .add(GameItems::bow, 100).nextLevel()
            .add(GameItems::bow, 50).add(GameItems::crossbow, 25).add(GameItems::shield, 25).nextLevel()
            .add(GameItems::bow, 33).add(GameItems::crossbow, 33).add(GameItems::shield, 34).nextLevel()
            .add(GameItems::bow, 30).add(GameItems::crossbow, 30).add(GameItems::shield, 30).add(GameItems::trident, 10).nextLevel()
            .add(GameItems::bow, 25).add(GameItems::crossbow, 25).add(GameItems::shield, 25).add(GameItems::trident, 25).build();

    public static final LevelledWeightedRandom<ItemStack> AMMO = new LevelledWeightedRandom.Builder<ItemStack>()
            .add(GameItems::arrows, 100).nextLevel()
            .add(GameItems::arrows, 60).add(GameItems::arrowsP, 40).nextLevel()
            .add(GameItems::arrows, 30).add(GameItems::arrowsP, 30).add(GameItems::arrowsS, 20).add(GameItems::arrowsW, 10)
                .add(GameItems::arrowsD, 10).nextLevel()
            .add(GameItems::arrows, 35).add(GameItems::arrowsPPlus, 20).add(GameItems::arrowsSPlus, 15).add(GameItems::arrowsW, 10)
                .add(GameItems::arrowsD, 10).add(GameItems::rockets, 10).nextLevel()
            .add(GameItems::arrows, 25).add(GameItems::arrowsPPlus, 25).add(GameItems::arrowsSPlus, 20).add(GameItems::arrowsWPlus, 10)
                .add(GameItems::arrowsDPlus, 10).add(GameItems::rocketsPlus, 10).build();

    public static final LevelledWeightedRandom<ItemStack> ARMOR = new LevelledWeightedRandom.Builder<ItemStack>()
            .add(GameItems::helmet, 1).add(GameItems::chestplate, 1).add(GameItems::leggings, 1).add(GameItems::boots, 1).build();

    public static final LevelledWeightedRandom<ItemStack> CONSUMABLE = new LevelledWeightedRandom.Builder<ItemStack>(1000)
            .add(GameItems::beetroot, 250).add(GameItems::carrot, 250).add(GameItems::bread, 150).add(GameItems::health, 75)
                .add(GameItems::speed, 50).add(GameItems::wolf, 75).add(GameItems::experience, 50).add(GameItems::smallCare, 100).nextLevel()
            .add(GameItems::carrot, 150).add(GameItems::bread, 150).add(GameItems::mutton, 100).add(GameItems::steak, 50)
                .add(GameItems::milk, 25).add(GameItems::health, 75).add(GameItems::speed, 50).add(GameItems::strength, 50)
                .add(GameItems::regen, 50).add(GameItems::wolf, 75).add(GameItems::experience, 50).add(GameItems::smallCare, 75)
                .add(GameItems::mediumCare, 100).nextLevel()
            .add(GameItems::bread, 100).add(GameItems::mutton, 100).add(GameItems::steak, 100).add(GameItems::gcarrot, 100)
                .add(GameItems::milk, 25).add(GameItems::health2, 50).add(GameItems::speed2, 50).add(GameItems::strength, 50)
                .add(GameItems::regen, 50).add(GameItems::wolf, 100).add(GameItems::golem, 50).add(GameItems::experience, 50)
                .add(GameItems::smallCare, 50).add(GameItems::mediumCare, 50).add(GameItems::largeCare, 75).nextLevel()
            .add(GameItems::mutton, 100).add(GameItems::steak, 100).add(GameItems::gcarrot, 100).add(GameItems::gapple, 100)
                .add(GameItems::milk, 20).add(GameItems::health2, 30).add(GameItems::health3, 25).add(GameItems::speed2, 50)
                .add(GameItems::strength2, 50).add(GameItems::regen2, 50).add(GameItems::wolf, 75).add(GameItems::golem, 75)
                .add(GameItems::experience, 50).add(GameItems::mediumCare, 25).add(GameItems::largeCare, 75).add(GameItems::extraCare, 75).nextLevel()
            .add(GameItems::mutton, 50).add(GameItems::steak, 100).add(GameItems::gcarrot, 100).add(GameItems::gapple, 100)
                .add(GameItems::egapple, 50).add(GameItems::totem, 50).add(GameItems::milk, 20).add(GameItems::health2, 30)
                .add(GameItems::health3, 25).add(GameItems::speed2, 50).add(GameItems::strength2, 50).add(GameItems::regen2, 50)
                .add(GameItems::wolf, 75).add(GameItems::golem, 75).add(GameItems::experience, 50).add(GameItems::largeCare, 50)
                .add(GameItems::extraCare, 75).nextLevel()
            .add(GameItems::steak, 50).add(GameItems::gcarrot, 150).add(GameItems::gapple, 100).add(GameItems::egapple, 100)
                .add(GameItems::totem, 50).add(GameItems::milk, 20).add(GameItems::health2, 30).add(GameItems::health3, 25)
                .add(GameItems::speed2, 50).add(GameItems::strength2, 50).add(GameItems::regen2, 50).add(GameItems::wolf, 75)
                .add(GameItems::golem, 75).add(GameItems::experience, 50).add(GameItems::largeCare, 50).add(GameItems::extraCare, 75)
            .build();

    public static final LevelledWeightedRandom<ItemStack> FOOD = new LevelledWeightedRandom.Builder<ItemStack>(1000)
            .add(GameItems::beetroot, 400).add(GameItems::carrot, 400).add(GameItems::bread, 200).nextLevel()
            .add(GameItems::carrot, 325).add(GameItems::bread, 325).add(GameItems::mutton, 200).add(GameItems::steak, 150).nextLevel()
            .add(GameItems::bread, 250).add(GameItems::mutton, 250).add(GameItems::steak, 250).add(GameItems::gcarrot, 250).nextLevel()
            .add(GameItems::mutton, 250).add(GameItems::steak, 250).add(GameItems::gcarrot, 250).add(GameItems::gapple, 250).nextLevel()
            .add(GameItems::mutton, 150).add(GameItems::steak, 250).add(GameItems::gcarrot, 250).add(GameItems::gapple, 250)
                .add(GameItems::egapple, 100).build();

    public static final LevelledWeightedRandom<ItemStack> OTHER = new LevelledWeightedRandom.Builder<ItemStack>(100)
            .add(GameItems::smallCare, 35).add(GameItems::wolf, 30).add(GameItems::experience, 10).add(GameItems::health, 15)
                .add(GameItems::speed, 10).nextLevel()
            .add(GameItems::smallCare, 15).add(GameItems::mediumCare, 30).add(GameItems::wolf, 15).add(GameItems::experience, 10)
                .add(GameItems::milk, 5).add(GameItems::health, 10).add(GameItems::speed, 5).add(GameItems::strength, 5)
                .add(GameItems::regen, 5).nextLevel()
            .add(GameItems::mediumCare, 15).add(GameItems::largeCare, 25).add(GameItems::wolf, 10).add(GameItems::golem, 10)
                .add(GameItems::experience, 10).add(GameItems::milk, 5).add(GameItems::health2, 10).add(GameItems::speed2, 5)
                .add(GameItems::strength, 5).add(GameItems::regen, 5).nextLevel()
            .add(GameItems::largeCare, 15).add(GameItems::extraCare, 25).add(GameItems::wolf, 10).add(GameItems::golem, 10)
                .add(GameItems::experience, 10).add(GameItems::milk, 5).add(GameItems::health2, 10).add(GameItems::speed2, 5)
                .add(GameItems::strength2, 5).add(GameItems::regen2, 5).nextLevel()
            .add(GameItems::extraCare, 30).add(GameItems::wolf, 15).add(GameItems::golem, 15).add(GameItems::experience, 10)
                .add(GameItems::milk, 5).add(GameItems::health2, 10).add(GameItems::speed2, 5).add(GameItems::strength2, 5)
                .add(GameItems::regen2, 5).build();

    public static final LevelledWeightedRandom<ItemStack> CARE = new LevelledWeightedRandom.Builder<ItemStack>(100)
            .add(GameItems::smallCare, 100).nextLevel()
            .add(GameItems::smallCare, 35).add(GameItems::mediumCare, 65).nextLevel()
            .add(GameItems::mediumCare, 35).add(GameItems::largeCare, 65).nextLevel()
            .add(GameItems::largeCare, 35).add(GameItems::extraCare, 65).nextLevel()
            .add(GameItems::extraCare, 100).build();

    public static final LevelledWeightedRandom<ItemStack> NOT_CARE = new LevelledWeightedRandom.Builder<ItemStack>(1000)
            .add(GameItems::beetroot, 250).add(GameItems::carrot, 250).add(GameItems::bread, 200).add(GameItems::health, 100)
                .add(GameItems::speed, 50).add(GameItems::wolf, 100).add(GameItems::experience, 50).nextLevel()
            .add(GameItems::carrot, 200).add(GameItems::bread, 150).add(GameItems::mutton, 125).add(GameItems::steak, 75)
                .add(GameItems::milk, 25).add(GameItems::health, 50).add(GameItems::speed, 50).add(GameItems::strength, 50)
                .add(GameItems::regen, 50).add(GameItems::wolf, 125).add(GameItems::experience, 100).nextLevel()
            .add(GameItems::bread, 125).add(GameItems::mutton, 125).add(GameItems::steak, 100).add(GameItems::gcarrot, 100)
                .add(GameItems::milk, 25).add(GameItems::health2, 50).add(GameItems::speed2, 50).add(GameItems::strength, 75)
                .add(GameItems::regen, 50).add(GameItems::wolf, 100).add(GameItems::golem, 100).add(GameItems::experience, 100).nextLevel()
            .add(GameItems::mutton, 100).add(GameItems::steak, 125).add(GameItems::gcarrot, 125).add(GameItems::gapple, 100)
                .add(GameItems::milk, 20).add(GameItems::health2, 50).add(GameItems::health3, 30).add(GameItems::speed2, 50)
                .add(GameItems::strength2, 50).add(GameItems::regen2, 50).add(GameItems::wolf, 100).add(GameItems::golem, 100)
                .add(GameItems::experience, 100).nextLevel()
            .add(GameItems::mutton, 50).add(GameItems::steak, 100).add(GameItems::gcarrot, 100).add(GameItems::gapple, 100)
                .add(GameItems::egapple, 50).add(GameItems::totem, 50).add(GameItems::milk, 25).add(GameItems::health2, 75)
                .add(GameItems::health3, 50).add(GameItems::speed2, 50).add(GameItems::strength2, 50).add(GameItems::regen2, 50)
                .add(GameItems::wolf, 100).add(GameItems::golem, 75).add(GameItems::experience, 75).nextLevel()
            .add(GameItems::steak, 50).add(GameItems::gcarrot, 150).add(GameItems::gapple, 100).add(GameItems::egapple, 100)
                .add(GameItems::totem, 50).add(GameItems::milk, 20).add(GameItems::health2, 50).add(GameItems::health3, 30)
                .add(GameItems::speed2, 50).add(GameItems::strength2, 50).add(GameItems::regen2, 50).add(GameItems::wolf, 100)
                .add(GameItems::golem, 100).add(GameItems::experience, 100).build();

	// Easy way to get a string for a toggle status
	private static String getToggleStatus(boolean status) {
		String toggle;
		if (status)
			toggle = "&a&l" + LanguageManager.messages.onToggle;
		else toggle = "&c&l" + LanguageManager.messages.offToggle;
		return toggle;
	}
}
