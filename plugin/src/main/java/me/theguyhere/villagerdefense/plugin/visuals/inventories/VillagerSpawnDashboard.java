package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.structures.ArenaSpawn;
import org.bukkit.Material;

public class VillagerSpawnDashboard extends LocationDashboard {
    public VillagerSpawnDashboard(Arena arena) {
        super("&5&lVillager Spawn", "Villager Spawn", Material.POPPY,
                () -> arena.getVillagerSpawns().stream().map(ArenaSpawn::getId),
                id -> arena.getVillagerSpawn(id) == null ? null : arena.getVillagerSpawn(id).getLocation(),
                arena::setVillagerSpawn,
                arena::newVillagerSpawnID
        );
    }
}
