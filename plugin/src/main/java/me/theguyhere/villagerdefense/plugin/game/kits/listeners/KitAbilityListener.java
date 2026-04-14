package me.theguyhere.villagerdefense.plugin.game.kits.listeners;

import me.theguyhere.villagerdefense.common.Calculator;
import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.entities.PlayerNotFoundException;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.game.exceptions.ArenaNotFoundException;
import me.theguyhere.villagerdefense.plugin.game.kits.AbilityKit;
import me.theguyhere.villagerdefense.plugin.game.kits.KitEffectType;
import me.theguyhere.villagerdefense.plugin.game.kits.KitVampire;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class KitAbilityListener implements Listener {
    private final Map<VDPlayer, Long> cooldowns = new HashMap<>();

    // Most ability functionalities
    @EventHandler
    public void onAbility(PlayerInteractEvent e) {
        // Check for right click
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = e.getPlayer();
        Arena arena;
        VDPlayer gamer;

        // Attempt to get arena and player
        try {
            arena = GameManager.getArena(player);
            gamer = arena.getPlayer(player);
        } catch (ArenaNotFoundException | PlayerNotFoundException err) {
            return;
        }

        ItemStack item = e.getItem();
        ItemStack main = player.getInventory().getItemInMainHand();

        // Avoid accidental usage when holding food, shop, ranged weapons, potions, or care packages
        if (GameItems.shop().equals(main) ||
                Arrays.stream(GameItems.FOOD_MATERIALS).anyMatch(m -> m == main.getType()) ||
                Arrays.stream(GameItems.ARMOR_MATERIALS).anyMatch(m -> m == main.getType()) ||
                Arrays.stream(GameItems.CARE_MATERIALS).anyMatch(m -> m == main.getType()) ||
                Arrays.stream(GameItems.CLICKABLE_WEAPON_MATERIALS).anyMatch(m -> m == main.getType()) ||
                Arrays.stream(GameItems.CLICKABLE_CONSUME_MATERIALS).anyMatch(m -> m == main.getType()))
            return;

        if (gamer.getKit() instanceof AbilityKit) {
            ((AbilityKit)gamer.getKit()).handleInteract(e, gamer, arena);
        }
        if (gamer.getKit2() instanceof AbilityKit) {
            ((AbilityKit)gamer.getKit2()).handleInteract(e, gamer, arena);
        }
    }

    // Vampire healing
    @EventHandler
    public void onVampire(EntityDamageByEntityEvent e) {
        // Ignore cancelled events
        if (e.isCancelled())
            return;

        Entity ent = e.getEntity();
        Entity damager = e.getDamager();

        // Check if damage was done by player to valid monsters
        if (!(ent instanceof Monster || ent instanceof Slime || ent instanceof Hoglin) ||
                !(damager instanceof Player))
            return;

        Player player = (Player) damager;
        Arena arena;
        VDPlayer gamer;

        // Attempt to get arena and player
        try {
            arena = GameManager.getArena(player);
            gamer = arena.getPlayer(player);
        } catch (ArenaNotFoundException | PlayerNotFoundException err) {
            return;
        }

        Random r = new Random();
        double damage = e.getFinalDamage();

        // Check for vampire kit
        if (gamer.using(KitVampire.class) && !gamer.isSharing()) {
            // Heal if probability is right
            if (r.nextInt(50) < damage)
                player.setHealth(Math.min(player.getHealth() + 1,
                        Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue()));
        }
        // Check for shared vampire effect
        else if (arena.rollEffectShare(KitEffectType.VAMPIRE)) {
            // Heal if probability is right
            if (r.nextInt(50) < damage) {
                player.setHealth(Math.min(player.getHealth() + 1,
                        Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue()));
                PlayerManager.notifySuccess(player, LanguageManager.messages.effectShare);
            }
        }
    }

    // Ninja stealth
    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        Entity ent = e.getEntity();
        Entity target = e.getTarget();

        // Check for arena mobs
        if (!ent.hasMetadata("VD"))
            return;

        // Cancel for invisible players
        if (target instanceof Player) {
            Player player = (Player) target;

            if (player.getActivePotionEffects().stream()
                    .anyMatch(potion -> potion.getType().equals(PotionEffectType.INVISIBILITY)))
                e.setCancelled(true);
        }

        // Cancel for invisible wolves
        if (target instanceof Wolf) {
            Wolf wolf = (Wolf) target;

            if (wolf.getActivePotionEffects().stream()
                    .anyMatch(potion -> potion.getType().equals(PotionEffectType.INVISIBILITY)))
                e.setCancelled(true);
        }
    }

    // Ninja stun
    @EventHandler
    public void onStun(EntityDamageByEntityEvent e) {
        Entity ent = e.getEntity();
        Entity damager = e.getDamager();

        // Check for arena enemies
        if (!ent.hasMetadata("VD"))
            return;

        // Check for player or wolf dealing damage
        if (!(damager instanceof Player || damager instanceof Wolf))
            return;

        // Check for mob taking damage
        if (!(ent instanceof Mob))
            return;

        Mob mob = (Mob) ent;
        LivingEntity stealthy = (LivingEntity) damager;

        // Check for invisibility
        if (stealthy.getActivePotionEffects().stream()
                .noneMatch(potion -> potion.getType().equals(PotionEffectType.INVISIBILITY)))
            return;

        // Set target to null if not already
        if (mob.getTarget() != null)
            mob.setTarget(null);
    }

    // Ninja nerf
    @EventHandler
    public void onInvisibleEquip(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        // Check if player is in a game
        if (!GameManager.checkPlayer(player))
            return;

        // Ignore creative and spectator mode players
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)
            return;

        // Ignore if not invisible
        if (player.getActivePotionEffects().stream()
                .noneMatch(potion -> potion.getType().equals(PotionEffectType.INVISIBILITY)))
            return;

        // Get armor
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        // Unequip armor
        if (!(helmet == null || helmet.getType() == Material.AIR)) {
            PlayerManager.giveItem(player, helmet);
            player.getInventory().setHelmet(null);
            PlayerManager.notifyFailure(player, LanguageManager.errors.ninja);
        }
        if (!(chestplate == null || chestplate.getType() == Material.AIR)) {
            PlayerManager.giveItem(player, chestplate);
            player.getInventory().setChestplate(null);
            PlayerManager.notifyFailure(player, LanguageManager.errors.ninja);
        }
        if (!(leggings == null || leggings.getType() == Material.AIR)) {
            PlayerManager.giveItem(player, leggings);
            player.getInventory().setLeggings(null);
            PlayerManager.notifyFailure(player, LanguageManager.errors.ninja);
        }
        if (!(boots == null || boots.getType() == Material.AIR)) {
            PlayerManager.giveItem(player, boots);
            player.getInventory().setBoots(null);
            PlayerManager.notifyFailure(player, LanguageManager.errors.ninja);
        }
    }

    private boolean checkLevel(int level, Player player) {
        if (level == 0) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    new ColoredMessage(ChatColor.RED, LanguageManager.errors.level).toString()));
            return true;
        }
        return false;
    }

    private boolean checkCooldown(long dif, Player player) {
        if (dif > 0) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    CommunicationManager.format(new ColoredMessage(ChatColor.RED, LanguageManager.errors.cooldown),
                            String.valueOf(Math.round(Calculator.millisToSeconds(dif) * 10) / 10d))));
            return true;
        }
        return false;
    }

    private static int normalCooldown(int level) {
        return Calculator.secondsToMillis(46 - Math.pow(Math.E, (level - 1) / 12d));
    }

    private static int normalDuration(int level) {
        return Calculator.secondsToTicks(4 + Math.pow(Math.E, (level - 1) / 4d));
    }

    private static int normalDuration10Plus(int level) {
        return Calculator.secondsToTicks(12 + Math.pow(Math.E, (level - 11) / 4d));
    }

    private static int normalDuration20Plus(int level) {
        return Calculator.secondsToTicks(20.5 + Math.pow(Math.E, (level - 21) / 4d));
    }
}
