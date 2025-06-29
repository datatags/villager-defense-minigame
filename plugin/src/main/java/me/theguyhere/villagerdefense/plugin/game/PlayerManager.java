package me.theguyhere.villagerdefense.plugin.game;

import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.common.Calculator;
import me.theguyhere.villagerdefense.nms.common.PacketGroup;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.data.PlayerDataManager;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import me.theguyhere.villagerdefense.plugin.game.achievements.Achievement;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage player manipulations.
 */
public class PlayerManager {
    // Gives item to player if possible, otherwise drops at feet
    public static void giveItem(Player player, ItemStack item, String message) {
        // Ignore if item is null
        if (item == null)
            return;

        // Inventory is full
        if (player.getInventory().firstEmpty() == -1 && (player.getInventory().first(item.getType()) == -1 ||
                (player.getInventory().all(new ItemStack(item.getType(), item.getMaxStackSize())).size() ==
                        player.getInventory().all(item.getType()).size()) &&
                    !player.getInventory().all(item.getType()).isEmpty())) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
            notifyFailure(player, message);
        }

        // Add item to inventory
        else player.getInventory().addItem(item);
    }

    // Prepares and teleports a player into adventure mode
    public static void teleportIntoAdventure(Player player, @NotNull Location location) {
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        player.setFireTicks(0);
        AttributeInstance maxHealth = player.getAttribute(Attribute.MAX_HEALTH);
        assert maxHealth != null;

        if (!maxHealth.getModifiers().isEmpty())
            maxHealth.getModifiers().forEach(maxHealth::removeModifier);
        player.setHealth(maxHealth.getValue());
        player.setAbsorptionAmount(0);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setExp(0);
        player.setLevel(0);
        player.setFallDistance(0);
        player.setFireTicks(0);
        player.setInvulnerable(false);
        player.getInventory().clear();
        player.teleport(location);
        player.setGameMode(GameMode.ADVENTURE);
        player.setGlowing(false);
    }

    // Prepares and teleports a player into spectator mode
    public static void teleportIntoSpectator(Player player, @NotNull Location location) {
        AttributeInstance maxHealth = player.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealth == null)
            return;

        if (!maxHealth.getModifiers().isEmpty())
            maxHealth.getModifiers().forEach(maxHealth::removeModifier);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setExp(0);
        player.setLevel(0);
        player.getInventory().clear();
        player.setGameMode(GameMode.SPECTATOR);
        player.closeInventory();
        player.teleport(location);
        player.setGlowing(false);
    }

    public static void notify(Player player, ColoredMessage msg) {
        player.sendMessage(CommunicationManager.notify(msg.toString()));
    }

    public static void notify(Player player, ColoredMessage base, ColoredMessage... replacements) {
        player.sendMessage(CommunicationManager.notify(CommunicationManager.format(base, replacements)));
    }

    public static void namedNotify(Player player, ColoredMessage name, ColoredMessage msg) {
        player.sendMessage(CommunicationManager.namedNotify(name, msg.toString()));
    }

    public static void namedNotify(Player player, ColoredMessage name, ColoredMessage base,
                                   ColoredMessage... replacements) {
        player.sendMessage(CommunicationManager.namedNotify(name, CommunicationManager.format(base, replacements)));
    }

    public static void notifySuccess(Player player, String msg) {
        notify(player, new ColoredMessage(ChatColor.GREEN, msg));
    }

    public static void notifySuccess(Player player, String base, ColoredMessage... replacements) {
        notify(player, new ColoredMessage(ChatColor.GREEN, base), replacements);
    }

    public static void notifyFailure(Player player, String msg) {
        notify(player, new ColoredMessage(ChatColor.RED, msg));
    }

    public static void notifyFailure(Player player, String base, ColoredMessage... replacements) {
        notify(player, new ColoredMessage(ChatColor.RED, base), replacements);
    }

    public static void notifyAlert(Player player, String msg) {
        notify(player, new ColoredMessage(ChatColor.GOLD, msg));
    }

    public static void notifyAlert(Player player, String base, ColoredMessage... replacements) {
        notify(player, new ColoredMessage(ChatColor.GOLD, base), replacements);
    }

    public static void fakeDeath(VDPlayer vdPlayer) {
        Player player = vdPlayer.getPlayer();
        player.setGameMode(GameMode.SPECTATOR);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        player.closeInventory();
        vdPlayer.setStatus(VDPlayer.Status.GHOST);
        player.setFallDistance(0);
        player.setGlowing(false);
        player.setVelocity(new Vector());
    }

    public static void sendPacketToOnline(PacketGroup packetGroup) {
        for (Player player : Bukkit.getOnlinePlayers())
            packetGroup.sendTo(player);
    }

    public static void sendLocationPacketToOnline(PacketGroup packetGroup, World world) {
        for (Player player : Bukkit.getOnlinePlayers())
            if (player.getWorld().equals(world))
                packetGroup.sendTo(player);
    }

    // Function for giving game start choice items to player
    public static void giveChoiceItems(VDPlayer player) {
        List<String> achievements = PlayerDataManager.getPlayerAchievements(player.getID());
        List<ItemStack> choiceItems = new ArrayList<>();

        // Give kit and challenge selectors to all
        choiceItems.add(GameItems.kitSelector());
        choiceItems.add(GameItems.challengeSelector());

        // If permanent boosts are available, give ability to toggle
        if (achievements.contains(Achievement.topKills9().getID()) ||
                achievements.contains(Achievement.totalKills9().getID()) ||
                achievements.contains(Achievement.topWave9().getID()) ||
                achievements.contains(Achievement.topBalance9().getID()) ||
                achievements.contains(Achievement.allChallenges().getID()) ||
                achievements.contains(Achievement.allMaxedAbility().getID())) {
            choiceItems.add(GameItems.boostToggle(player.isBoosted()));
        }

        // If sharing effect is available, give ability to toggle
        if (achievements.contains(Achievement.allEffect().getID())) {
            choiceItems.add(GameItems.shareToggle(player.isSharing()));
        }

        // Give crystal converter if available
        if (achievements.contains(Achievement.totalGems9().getID())) {
            choiceItems.add(GameItems.crystalConverter());
        }

        // Give item to leave arena easily to all
        choiceItems.add(GameItems.leave());

        if (choiceItems.size() == 3) {
            giveItemConditional(2, choiceItems.get(0), player.getPlayer());
            giveItemConditional(4, choiceItems.get(1), player.getPlayer());
            giveItemConditional(6, choiceItems.get(2), player.getPlayer());
        } else if (choiceItems.size() == 4) {
            giveItemConditional(1, choiceItems.get(0), player.getPlayer());
            giveItemConditional(3, choiceItems.get(1), player.getPlayer());
            giveItemConditional(5, choiceItems.get(2), player.getPlayer());
            giveItemConditional(7, choiceItems.get(3), player.getPlayer());
        } else if (choiceItems.size() == 5) {
            giveItemConditional(0, choiceItems.get(0), player.getPlayer());
            giveItemConditional(2, choiceItems.get(1), player.getPlayer());
            giveItemConditional(4, choiceItems.get(2), player.getPlayer());
            giveItemConditional(6, choiceItems.get(3), player.getPlayer());
            giveItemConditional(8, choiceItems.get(4), player.getPlayer());
        } else {
            giveItemConditional(0, choiceItems.get(0), player.getPlayer());
            giveItemConditional(2, choiceItems.get(1), player.getPlayer());
            giveItemConditional(4, choiceItems.get(2), player.getPlayer());
            giveItemConditional(5, choiceItems.get(3), player.getPlayer());
            giveItemConditional(6, choiceItems.get(4), player.getPlayer());
            giveItemConditional(8, choiceItems.get(5), player.getPlayer());
        }
    }

    // Give players the effect of a totem, including setting health to 1
    public static void giveTotemEffect(Player player) {
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.setHealth(1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Calculator.secondsToTicks(45), 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Calculator.secondsToTicks(40), 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Calculator.secondsToTicks(5), 1));
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
        notifySuccess(player, LanguageManager.messages.resurrection);
    }

    // Function to give items to the proper inventory slot or change them
    private static void giveItemConditional(int slot, ItemStack item, Player player) {
        ItemStack oldItem = player.getInventory().getItem(slot);

        if (oldItem != null && oldItem.getType() == item.getType())
            oldItem.setItemMeta(item.getItemMeta());

        else player.getInventory().setItem(slot, item);
    }
}
