package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.Material;

public class MobsMenu extends Menu {
    public MobsMenu(Arena arena) {
        super("&2&lMob Settings: " + arena.getName(), new DynamicSizeLayout(true));

        // Option to edit monster spawns
        addNavigation(Material.END_PORTAL_FRAME, () -> new MonsterSpawnDashboard(arena), "&2&lMonster Spawns");

        // Option to toggle monster spawn particles
        addButton(Material.FIREWORK_ROCKET, p -> arena.setMonsterParticles(!arena.hasMonsterParticles()),
                () -> "&a&lMonster Spawn Particles: " + getToggleStatus(arena.hasMonsterParticles()),
                "&7Particles showing where the spawns are",
                "&7(Visible in-game)");

        // Option to edit villager spawns
        addNavigation(Material.END_PORTAL_FRAME, () -> new VillagerSpawnDashboard(arena), "&5&lVillager Spawns");

        // Option to toggle villager spawn particles
        addButton(Material.FIREWORK_ROCKET, p -> arena.setVillagerParticles(!arena.hasVillagerParticles()),
                () -> "&d&lVillager Spawn Particles: " + getToggleStatus(arena.hasVillagerParticles()),
                "&7Particles showing where the spawns are",
                "&7(Visible in-game)");

        // Option to edit spawn table
        addNavigation(Material.DRAGON_HEAD, () -> new SpawnTableMenu(arena), "&3&lSpawn Table");

        // Option to toggle dynamic mob count
        addButton(Material.SLIME_BALL, p -> arena.setDynamicCount(!arena.hasDynamicCount()),
                () -> "&e&lDynamic Mob Count: " + getToggleStatus(arena.hasDynamicCount()),
                "&7Mob count adjusting based on",
                "&7number of players");
    }
}
