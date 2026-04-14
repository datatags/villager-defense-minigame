package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.Calculator;
import me.theguyhere.villagerdefense.plugin.Main;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.WorldManager;
import me.theguyhere.villagerdefense.plugin.game.kits.events.EndNinjaNerfEvent;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitNinja extends AbilityKit {
    public KitNinja() {
        super(LanguageManager.kits.ninja.name, Material.CHAIN, GameItems.ninja());

        setMasterDescription(LanguageManager.kits.ninja.description);

        setPrice(1, 4000);
        setPrice(2, 8000);
        setPrice(3, 14000);
    }

    @Override
    protected void activate(int level, Player player, VDPlayer gamer, Arena arena) {
        int duration = Calculator.secondsToTicks(4 + Math.pow(Math.E, (level - 1) / 8.5));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, duration, 0));
        WorldManager.getPets(player).forEach(wolf ->
                wolf.addPotionEffect((new PotionEffect(PotionEffectType.INVISIBILITY, duration, 0))));
        gamer.hideArmor();

        // Schedule un-nerf
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () ->
                Bukkit.getPluginManager().callEvent(new EndNinjaNerfEvent(gamer)), duration);

        // Fire ability sound if turned on
        if (arena.hasAbilitySound()) {
            player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
        }
    }
}
