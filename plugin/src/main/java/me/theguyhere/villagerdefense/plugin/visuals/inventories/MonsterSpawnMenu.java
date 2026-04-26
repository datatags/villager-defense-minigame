package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import com.sun.tools.javac.util.List;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MonsterSpawnMenu extends LocationMenu {
    private final Arena arena;
    private final int monsterSpawnID;
    private final ItemStack typeButton;
    public MonsterSpawnMenu(Arena arena, int monsterSpawnID) {
        super("&2&lMonster Spawn " + monsterSpawnID + ": " + arena.getName(),
                () -> arena.getMonsterSpawn(monsterSpawnID).getLocation(),
                loc -> arena.setMonsterSpawn(monsterSpawnID, loc),
                p -> {
                    if (!arena.isClosed()) {
                        PlayerManager.notifyFailure(p, "Arena must be closed to modify this!");
                        return false;
                    }
                    return true;
                });
        this.arena = arena;
        this.monsterSpawnID = monsterSpawnID;
        this.typeButton = new ItemStack(Material.GUNPOWDER);

        addButton(this.typeButton, p -> arena.setMonsterSpawnType(monsterSpawnID, (arena.getMonsterSpawnType(monsterSpawnID) + 1) % 3));
        buttonUpdaters.put(this.typeButton, i -> {
            // Toggle to set monster spawn type
            switch (arena.getMonsterSpawnType(monsterSpawnID)) {
                case 1:
                    updateItem(Material.GUNPOWDER, "Ground");
                    break;
                case 2:
                    updateItem(Material.FEATHER, "Flying");
                    break;
                default:
                    updateItem(Material.BONE, "All");
                    break;
            }
        });

        // Reorder the buttons such that the type button is just before the remove button.
        // There's probably a better way to do this, but we basically make a comparator that says the type button is
        // less than (left of) the remove button, and greater than (right of) all the other buttons. All other buttons
        // are equal, which is fine since this is a stable sort and thus they will stay in the same order.
        layout.sort((a, b) -> {
            if (a != typeButton && b != typeButton) {
                return 0; // don't reorder other buttons
            }
            ItemStack other = (a == typeButton) ? b : a;
            if (InventoryButtons.Type.REMOVE.matches(other)) {
                return (a == typeButton) ? -1 : 1;
            } else {
                return (a == typeButton) ? 1 : -1;
            }
        });
    }

    private void updateItem(Material mat, String lore) {
        typeButton.setType(mat);
        ItemMeta meta = typeButton.getItemMeta();
        meta.setLore(List.of(CommunicationManager.format("&5&lType: " + lore)));
        typeButton.setItemMeta(meta);
    }
}
