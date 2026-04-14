package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.WorldManager;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitSiren extends AbilityKit {
    public KitSiren() {
        super(LanguageManager.kits.siren.name, Material.COBWEB, GameItems.siren());

        setMasterDescription(LanguageManager.kits.siren.description);

        setPrice(1, 4000);
        setPrice(2, 8000);
        setPrice(3, 13500);
    }

    @Override
    protected void activate(int level, Player player, VDPlayer gamer, Arena arena) {
        // Calculate stats
        int duration;
        int amp1;
        int amp2;
        double range = 3 + level * .1d;
        if (level > 20) {
            duration = PotionAbilityKit.normalDuration20Plus(level);
            amp1 = 1;
            amp2 = 0;
        }
        else if (level > 10) {
            duration = PotionAbilityKit.normalDuration10Plus(level);
            amp1 = 1;
            amp2 = -1;
        }
        else {
            duration = PotionAbilityKit.normalDuration(level);
            amp1 = 0;
            amp2 = -1;
        }
        int altDuration = (int) (.4 * duration);

        // Activate ability
        WorldManager.getNearbyMonsters(player, range).forEach(ent -> ent.addPotionEffect(
                new PotionEffect(PotionEffectType.SLOWNESS, duration, amp1)));
        if (amp2 > -1) {
            WorldManager.getNearbyMonsters(player, range).forEach(ent -> ent.addPotionEffect(
                    new PotionEffect(PotionEffectType.WEAKNESS, altDuration, amp2)));
        }

        // Fire ability sound if turned on
        if (arena.hasAbilitySound())
            arena.getActives().forEach(vdPlayer -> vdPlayer.getPlayer()
                    .playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1.25f));
    }
}
