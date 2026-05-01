package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.structures.ArenaSpawn;
import org.bukkit.Material;

public class MonsterSpawnDashboard extends LocationDashboard {
    public MonsterSpawnDashboard(Arena arena) {
        super("&2&lMonster Spawns", "Mob Spawn", Material.ZOMBIE_HEAD,
                () -> arena.getMonsterSpawns().stream().map(ArenaSpawn::getId),
                id -> arena.getMonsterSpawn(id) == null ? null : arena.getMonsterSpawn(id).getLocation(),
                arena::setMonsterSpawn,
                arena::newMonsterSpawnID);
    }
}
