package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.structures.ArenaSpawn;
import org.bukkit.Material;

import java.util.stream.Collectors;

public class VillagerSpawnDashboard extends LocationDashboard {
    public VillagerSpawnDashboard(Arena arena) {
        super("&5&lVillager Spawn", "Villager Spawn", Material.POPPY,
                arena.getVillagerSpawns().stream().map(ArenaSpawn::getId).collect(Collectors.toList()),
                id -> arena.getVillagerSpawn(id).getLocation(),
                arena::setVillagerSpawn,
                arena::newVillagerSpawnID
        );
    }
}
