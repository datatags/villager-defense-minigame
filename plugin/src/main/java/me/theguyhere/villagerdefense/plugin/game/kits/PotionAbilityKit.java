package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.Calculator;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.WorldManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionAbilityKit extends AbilityKit {
    private final PotionEffectType effectType;
    public PotionAbilityKit(String name, Material buttonMaterial, ItemStack abilityItem, PotionEffectType effectType) {
        super(name, buttonMaterial, abilityItem);
        this.effectType = effectType;
    }

    @Override
    protected void activate(int level, Player player, VDPlayer gamer, Arena arena) {
        // Calculate stats
        int duration, amplifier;
        double range = 2 + level * .1d;
        if (level > 20) {
            duration = normalDuration20Plus(level);
            amplifier = 2;
        } else if (level > 10) {
            duration = normalDuration10Plus(level);
            amplifier = 1;
        } else {
            duration = normalDuration(level);
            amplifier = 0;
        }
        int altDuration = (int) (.6 * duration);

        // Activate ability
        WorldManager.getNearbyPlayers(player, range).forEach(p -> p.addPotionEffect(
                new PotionEffect(effectType, altDuration, amplifier)));
        WorldManager.getNearbyAllies(player, range).forEach(ally -> ally.addPotionEffect(
                new PotionEffect(effectType, altDuration, amplifier)));
        player.addPotionEffect(new PotionEffect(effectType, duration, amplifier));

        // Fire ability sound if turned on
        if (arena.hasAbilitySound())
            arena.getActives().forEach(vdPlayer -> vdPlayer.getPlayer()
                    .playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 1));
    }

    protected static int normalDuration(int level) {
        return Calculator.secondsToTicks(4 + Math.pow(Math.E, (level - 1) / 4d));
    }

    protected static int normalDuration10Plus(int level) {
        return Calculator.secondsToTicks(12 + Math.pow(Math.E, (level - 11) / 4d));
    }

    protected static int normalDuration20Plus(int level) {
        return Calculator.secondsToTicks(20.5 + Math.pow(Math.E, (level - 21) / 4d));
    }
}
