package me.theguyhere.villagerdefense.plugin.game.kits;

import me.theguyhere.villagerdefense.common.Calculator;
import me.theguyhere.villagerdefense.common.ColoredMessage;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.data.PlayerDataManager;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.achievements.Achievement;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AbilityKit extends Kit {
    private final ItemStack abilityItem;
    private long cooldownExpiry = 0;
    public AbilityKit(String name, Material buttonMaterial, ItemStack abilityItem) {
        super(name, KitCategory.ABILITY, buttonMaterial);
        this.abilityItem = abilityItem;
        addItems(abilityItem);

        ColoredMessage coloredAbilityText = new ColoredMessage(ChatColor.GRAY, LanguageManager.messages.upToAbilityLevel);
        addLevelDescriptions(1, CommunicationManager.format(coloredAbilityText, "10"));
        addLevelDescriptions(2, CommunicationManager.format(coloredAbilityText, "20"));
        addLevelDescriptions(3, CommunicationManager.format(coloredAbilityText, "30"));
    }

    protected long calculateCooldown(int level) {
        return Calculator.secondsToMillis(46 - Math.pow(Math.E, (level - 1) / 12d));
    }

    public final void handleInteract(PlayerInteractEvent event, VDPlayer gamer, Arena arena) {
        if (!abilityItem.isSimilar(event.getItem())) {
            return;
        }
        // Check if player has cooldown decrease achievement and is boosted
        double coolDownMult = 1;
        if (gamer.isBoosted() && PlayerDataManager.getPlayerAchievements(gamer.getID()).contains(Achievement.allMaxedAbility().getID())) {
            coolDownMult = 0.9;
        }

        Player player = event.getPlayer();
        int level = Math.min(PlayerDataManager.getPlayerKitLevel(gamer.getID(), this) * 10, player.getLevel());
        if (level == 0) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    new ColoredMessage(ChatColor.RED, LanguageManager.errors.level).toString()));
            return;
        }
        long now = System.currentTimeMillis();
        if (cooldownExpiry > now) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    CommunicationManager.format(new ColoredMessage(ChatColor.RED, LanguageManager.errors.cooldown),
                            String.valueOf(Math.round(Calculator.millisToSeconds(cooldownExpiry - now) * 10) / 10d))));
            return;
        }
        cooldownExpiry = now + (long)(calculateCooldown(level) * coolDownMult);
        activate(level, player, gamer, arena);
    }

    protected abstract void activate(int level, Player player, VDPlayer gamer, Arena arena);
}
