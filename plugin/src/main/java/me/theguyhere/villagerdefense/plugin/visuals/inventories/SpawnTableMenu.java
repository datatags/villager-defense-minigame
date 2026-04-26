package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import me.theguyhere.villagerdefense.plugin.visuals.layout.SingleRowLayout;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Predicate;

public class SpawnTableMenu extends ArenaMenu {
    public SpawnTableMenu(Arena arena) {
        super(getMenuName(arena), arena, new SingleRowLayout(true));

        // Option to set spawn table to default
        addOption("default", "&4&lDefault", Material.OAK_WOOD);

        // Option to set spawn table to global option 1
        addOption("option1", "&6&lOption 1", Material.RED_CONCRETE);

        // Option to set spawn table to global option 2
        addOption("option2", "&6&lOption 2", Material.ORANGE_CONCRETE);

        // Option to set spawn table to global option 3
        addOption("option3", "&6&lOption 3", Material.YELLOW_CONCRETE);

        // Option to set spawn table to global option 4
        addOption("option4", "&6&lOption 4", Material.BROWN_CONCRETE);

        // Option to set spawn table to global option 5
        addOption("option5", "&6&lOption 5", Material.LIGHT_GRAY_CONCRETE);

        // Option to set spawn table to global option 6
        addOption("option6", "&6&lOption 6", Material.WHITE_CONCRETE);

        // Option to set spawn table to custom option
        addOption("custom", "&e&lCustom", Material.BIRCH_WOOD, table -> table.length() < 5,
                "&7Sets spawn table to arena.[arena number].yml", "&7(Check the arena number in arenaData.yml)");
    }

    private static String getMenuName(Arena arena) {
        return arena.getSpawnTableName().equals("custom") ?
                CommunicationManager.format("&3&lSpawn Table: arena." + arena.getId() + ".yml") :
                CommunicationManager.format("&3&lSpawn Table: " + arena.getSpawnTableName() + ".yml");
    }

    @Override
    public String getName() {
        return getMenuName(arena);
    }

    private void addOption(String name, String displayName, Material mat, Predicate<String> matches, String... rawLore) {
        ItemStack item = addButton(mat, ifClosed(pl -> {
            if (!arena.setSpawnTableFile(name)) {
                PlayerManager.notifyFailure(pl, "File doesn't exist!");
            }}), displayName, rawLore);
        buttonUpdaters.put(item, i -> {
            ItemMeta meta = i.getItemMeta();
            if (matches.test(arena.getSpawnTableName())) {
                ItemManager.glow().forEach((ench, lvl) -> meta.addEnchant(ench, lvl, true));
            } else {
                meta.removeEnchantments();
            }
            i.setItemMeta(meta);
        });
    }

    private void addOption(String name, String displayName, Material mat) {
        addOption(name, displayName, mat, s -> s.equals(name), "&4Sets spawn table to " + name + ".yml");
    }
}
