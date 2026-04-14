package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.Calculator;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class KitMage extends AbilityKit {
    public KitMage() {
        super(LanguageManager.kits.mage.name, Material.FIRE_CHARGE, GameItems.mage());

        setMasterDescription(LanguageManager.kits.mage.description);

        setPrice(1, 3500);
        setPrice(2, 7500);
        setPrice(3, 13000);
    }

    @Override
    protected long calculateCooldown(int level) {
        return Calculator.secondsToMillis(13 - Math.pow(Math.E, (level - 1) / 12d));
    }

    @Override
    protected void activate(int level, Player player, VDPlayer gamer, Arena arena) {
        Fireball fireball = player.getWorld().spawn(player.getEyeLocation(), Fireball.class);
        fireball.setYield(1 + level * .05f);
        fireball.setShooter(player);
    }
}
