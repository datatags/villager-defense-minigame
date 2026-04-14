package me.theguyhere.villagerdefense.plugin.game.kits;

import lombok.Getter;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.common.Constants;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class representing kits in Villager Defense. Comes with static methods for the following kits:<br/>
 * <br/><b>Gift Kits:</b><ul>
 *     <li>Orc</li>
 *     <li>Farmer</li>
 *     <li>Soldier</li>
 *     <li>Tailor</li>
 *     <li>Alchemist</li>
 *     <li>Trader</li>
 *     <li>Summoner</li>
 *     <li>Reaper</li>
 *     <li>Phantom</li>
 * </ul>
 * <b>Ability Kits:</b><ul>
 *     <li>Mage</li>
 *     <li>Ninja</li>
 *     <li>Templar</li>
 *     <li>Warrior</li>
 *     <li>Knight</li>
 *     <li>Priest</li>
 *     <li>Siren</li>
 *     <li>Monk</li>
 *     <li>Messenger</li>
 * </ul>
 * <b>Effect Kits:</b><ul>
 *     <li>Blacksmith</li>
 *     <li>Witch</li>
 *     <li>Merchant</li>
 *     <li>Vampire</li>
 *     <li>Giant</li>
 * </ul>
 */
public abstract class Kit {
    /** The name of the kit.*/
    @Getter
    private final String name;
    /** The category of kit.*/
    @Getter
    private final KitCategory kitCategory;
    /** The main description for the kit.*/
    private final List<String> masterDescription = new ArrayList<>();
    /** A mapping between kit level and kit description.*/
    private final Map<Integer, List<String>> descriptionsMap = new LinkedHashMap<>();
    /** The material used for GUI buttons relating to this kit.*/
    private final Material buttonMaterial;
    /** A mapping between kit level and purchase price.*/
    private final Map<Integer, Integer> pricesMap = new HashMap<>();
    /** A mapping between kit level and an array of {@link ItemStack} the player would receive.*/
    private final Map<Integer, List<ItemStack>> itemsMap = new HashMap<>();
    /** The level of this instance of the kit.*/

    public Kit(String name, KitCategory category, Material buttonMaterial) {
        this.name = name;
        this.kitCategory = category;
        this.buttonMaterial = buttonMaterial;

        List<ItemStack> first = new ArrayList<>();
        first.add(new ItemStack(Material.WOODEN_SWORD));
        itemsMap.put(1, first);
    }

    public Kit(String name, Material buttonMaterial) {
        // most kits are gift kits
        this(name, KitCategory.GIFT, buttonMaterial);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kit)) return false;
        Kit kit = (Kit) o;
        return Objects.equals(name, kit.name) && kitCategory == kit.kitCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, kitCategory, masterDescription, descriptionsMap, buttonMaterial, pricesMap, itemsMap);
    }

    /**
     * Returns the highest level this kit goes to.
     * @return Highest level.
     */
    public int getMaxLevel() {
        return pricesMap.size();
    }

    /**
     * Checks if this kit has multiple levels.
     * @return Whether the kit has multiple levels.
     */
    public boolean isMultiLevel() {
        return pricesMap.size() > 1;
    }

    /**
     * Adds a line into the master description for the kit.
     * @param line Line to add to the description.
     */
    protected void setMasterDescription(String line) {
        masterDescription.clear();
        masterDescription.addAll(CommunicationManager.formatDescriptionList(ChatColor.GRAY, line, Constants.LORE_CHAR_LIMIT));
    }

    /**
     * Adds a kit level-description pair into the descriptions map.
     * @param level Kit level.
     * @param description Kit description.
     */
    protected void addLevelDescriptions(int level, String description) {
        descriptionsMap.put(level, CommunicationManager.formatDescriptionList(ChatColor.GRAY, description,
                Constants.LORE_CHAR_LIMIT));
    }

    /**
     * Returns the description of the kit at the specified level.
     * @param level Kit level.
     * @return Kit description.
     */
    public List<String> getLevelDescription(int level) {
        return descriptionsMap.get(level);
    }

    /**
     * Adds a kit level-kit price pair into the prices map.
     * @param level Kit level.
     * @param price Kit price.
     */
    protected void setPrice(int level, int price) {
        pricesMap.put(level, price);
    }

    /**
     * Set the price for level 1 of this kit.
     * @param price Kit price for level 1.
     */
    protected void setPrice(int price) {
        setPrice(1, price);
    }

    /**
     * Returns the price of the kit at the specified level.
     * @param level Kit level.
     * @return Kit price.
     */
    public int getPrice(int level) {
        return pricesMap.get(level);
    }

    /**
     * Remove all items from the kit, including the default wooden sword
     */
    protected void clearItems() {
        itemsMap.clear();
    }

    /**
     * Adds a kit level-items pair into the items map.
     * @param level Kit level.
     * @param items Items to be received by the player.
     */
    protected void addItems(int level, ItemStack... items) {
        itemsMap.computeIfAbsent(level, l -> new ArrayList<>()).addAll(Arrays.asList(items));
    }

    /**
     * Adds a kit level-items pair into the items map for level 1.
     * @param items Items to be received by the player.
     */
    protected void addItems(ItemStack... items) {
        addItems(1, items);
    }

    /**
     * Returns the items a player would receive.
     * @return Items to be received by the player.
     */
    public List<ItemStack> getItems(int level) {
        if (itemsMap.containsKey(level)) {
            return Collections.unmodifiableList(itemsMap.get(level));
        } else {
            return Collections.unmodifiableList(itemsMap.get(1));
        }
    }

    /**
     * Returns an {@link ItemStack} for a GUI button.<br/>
     * <br/>Use -1 as purchasedLevel if the button is for display only.
     * @param purchasedLevel The level at which the player has purchased this kit.
     * @param purchaseMode Whether the button is in purchase mode or not. When used for display only, represents
     *                     whether the kit is active or not.
     * @return GUI button.
     */
    public ItemStack getButton(int purchasedLevel, boolean purchaseMode) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.UNBREAKING, 1);

        if (kitCategory == KitCategory.NONE) {
            return ItemManager.createItem(buttonMaterial,
                    CommunicationManager.format(kitCategory.getPrefix() + name), ItemManager.BUTTON_FLAGS,
                    null);
        }
        else if (isMultiLevel()) {
            List<String> lores = new ArrayList<>();
            if (purchasedLevel == 0) {
                lores.addAll(masterDescription);
                lores.add(CommunicationManager.format("&c" + LanguageManager.messages.level + " 1"));
                lores.addAll(getLevelDescription(1));
                lores.add(purchaseMode ? CommunicationManager.format("&c" +
                        LanguageManager.messages.purchase + ": &b" + getPrice(1) + " " +
                        LanguageManager.names.crystals) : CommunicationManager.format(ChatColor.RED +
                        LanguageManager.messages.unavailable));
            }
            else if (purchasedLevel == pricesMap.size()) {
                lores.addAll(masterDescription);
                lores.add(CommunicationManager.format("&a" + LanguageManager.messages.level + " " +
                        pricesMap.size()));
                lores.addAll(getLevelDescription(pricesMap.size()));
                lores.add(purchaseMode ? CommunicationManager.format(ChatColor.GREEN +
                        LanguageManager.messages.purchased) : CommunicationManager.format(ChatColor.GREEN +
                        LanguageManager.messages.available));
            }
            else if (purchasedLevel == -1) {
                lores.addAll(masterDescription);
                descriptionsMap.forEach((level, description) -> {
                    lores.add(CommunicationManager.format("&f" + LanguageManager.messages.level + " " + level));
                    lores.addAll(description);
                });
                return ItemManager.createItem(buttonMaterial,
                        CommunicationManager.format((purchaseMode ? kitCategory.getPrefix() : "&4&l") + name),
                        ItemManager.BUTTON_FLAGS, purchaseMode ? enchants : null, lores);
            }
            else {
                lores.addAll(masterDescription);
                lores.add(CommunicationManager.format("&a" + LanguageManager.messages.level + " " +
                        purchasedLevel));
                lores.addAll(getLevelDescription(purchasedLevel));
                if (purchaseMode) {
                    lores.add(CommunicationManager.format("&c" + LanguageManager.messages.level +
                            " " + ++purchasedLevel));
                    lores.addAll(getLevelDescription(purchasedLevel));
                    lores.add(CommunicationManager.format("&c" +
                            LanguageManager.messages.purchase + ": &b" + getPrice(purchasedLevel) +
                            " " + LanguageManager.names.crystals));
                } else lores.add(CommunicationManager.format(ChatColor.GREEN +
                        LanguageManager.messages.available));
            }

            return ItemManager.createItem(buttonMaterial,
                    CommunicationManager.format(kitCategory.getPrefix() + name), ItemManager.BUTTON_FLAGS,
                    null, lores);
        } else {
            if (purchasedLevel == -1)
                return ItemManager.createItem(buttonMaterial,
                        CommunicationManager.format((purchaseMode ? kitCategory.getPrefix(): "&4&l") + name),
                        ItemManager.BUTTON_FLAGS, purchaseMode ? enchants : null, masterDescription);
            else if (pricesMap.get(1) == 0)
                return ItemManager.createItem(buttonMaterial,
                        CommunicationManager.format(kitCategory.getPrefix() + name), ItemManager.BUTTON_FLAGS,
                        null, masterDescription, purchaseMode ?
                                CommunicationManager.format(ChatColor.GREEN + LanguageManager.messages.free) :
                                CommunicationManager.format(ChatColor.GREEN + LanguageManager.messages.available));
            else return ItemManager.createItem(buttonMaterial,
                    CommunicationManager.format(kitCategory.getPrefix() + name), ItemManager.BUTTON_FLAGS,
                        null, masterDescription, purchasedLevel == 1 ?
                                (purchaseMode ?
                                        CommunicationManager.format(ChatColor.GREEN +
                                                LanguageManager.messages.purchased) :
                                        CommunicationManager.format(ChatColor.GREEN +
                                                LanguageManager.messages.available)) :
                                (purchaseMode ? CommunicationManager.format("&c" +
                                        LanguageManager.messages.purchase + ": &b" +
                                        getPrice(1) + " " + LanguageManager.names.crystals) :
                                        CommunicationManager.format(ChatColor.RED +
                                                LanguageManager.messages.unavailable)));
        }
    }
}
