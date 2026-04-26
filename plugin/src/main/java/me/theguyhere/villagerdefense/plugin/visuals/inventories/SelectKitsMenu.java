package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.common.Constants;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.data.PlayerDataManager;
import me.theguyhere.villagerdefense.plugin.entities.PlayerNotFoundException;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.game.kits.*;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class SelectKitsMenu extends ArenaMenu {
    private final Player player;
    public SelectKitsMenu(Arena arena, Player player) {
        super("&9&l" + arena.getName() + " " + LanguageManager.messages.kits, arena, new ManualLayout());
        this.player = player;
        ManualLayout l = (ManualLayout) layout;
        UUID uuid = player.getUniqueId();

        final List<String> banned = arena.getBannedKits();
        Consumer<Kit> buttonBuilder = kit -> {
            if (!banned.contains(kit.getName())) {
                addButton(kit.getButton(PlayerDataManager.getPlayerKitLevel(uuid, kit), false),
                        p -> handleClick(p, kit));
            }
        };

        // Gift kits
        l.setNextSlot(0);
        for (int i = 0; i < 9; i++) {
            addButton(Material.LIME_STAINED_GLASS_PANE, NO_OP, "&a&l" + LanguageManager.names.giftKits,
                    CommunicationManager.formatDescriptionArr(ChatColor.GRAY,
                            LanguageManager.messages.giftKitsDescription, Constants.LORE_CHAR_LIMIT));
        }

        l.setNextSlot(9);
        buttonBuilder.accept(new KitOrc());
        buttonBuilder.accept(new KitFarmer());
        buttonBuilder.accept(new KitSoldier());
        buttonBuilder.accept(new KitAlchemist());
        buttonBuilder.accept(new KitTailor());
        buttonBuilder.accept(new KitTrader());
        buttonBuilder.accept(new KitSummoner());
        buttonBuilder.accept(new KitReaper());
        buttonBuilder.accept(new KitPhantom());

        // Ability kits
        l.setNextSlot(18);
        for (int i = 0; i < 9; i++) {
            addButton(Material.MAGENTA_STAINED_GLASS_PANE, NO_OP, "&d&l" + LanguageManager.names.abilityKits,
                    CommunicationManager.formatDescriptionArr(ChatColor.GRAY,
                            LanguageManager.messages.abilityKitsDescription, Constants.LORE_CHAR_LIMIT));
        }

        l.setNextSlot(27);
        buttonBuilder.accept(new KitMage());
        buttonBuilder.accept(new KitNinja());
        buttonBuilder.accept(new KitTemplar());
        buttonBuilder.accept(new KitWarrior());
        buttonBuilder.accept(new KitKnight());
        buttonBuilder.accept(new KitPriest());
        buttonBuilder.accept(new KitSiren());
        buttonBuilder.accept(new KitMonk());
        buttonBuilder.accept(new KitMessenger());

        // Effect kits
        l.setNextSlot(36);
        for (int i = 0; i < 9; i++) {
            addButton(Material.YELLOW_STAINED_GLASS_PANE, NO_OP, "&e&l" + LanguageManager.names.effectKits,
                    CommunicationManager.formatDescriptionArr(ChatColor.GRAY,
                            LanguageManager.messages.effectKitsDescription, Constants.LORE_CHAR_LIMIT));
        }

        buttonBuilder.accept(new KitBlacksmith());
        buttonBuilder.accept(new KitWitch());
        buttonBuilder.accept(new KitMerchant());
        buttonBuilder.accept(new KitVampire());
        buttonBuilder.accept(new KitGiant());

        // Option for no kit
        l.setNextSlot(52);
        buttonBuilder.accept(new KitNone());

        // Option to exit
        l.add(53, InventoryButtons.exit());
    }

    private void handleClick(Player player, Kit kit) {
        VDPlayer gamer;
        try {
            gamer = arena.getPlayer(player);
        } catch (PlayerNotFoundException err) {
            return;
        }

        UUID uuid = player.getUniqueId();

        // Ignore spectators from here on out
        if (gamer.getStatus() == VDPlayer.Status.SPECTATOR) {
            return;
        }

        // Set proper kit for gamer
        if (PlayerDataManager.playerOwnsKit(uuid, kit)) {
            gamer.setKit(kit);
            PlayerManager.notifySuccess(player, LanguageManager.confirms.kitSelect);
        } else {
            PlayerManager.notifyFailure(player, LanguageManager.errors.kitSelect);
            return;
        }

        closeInv(player);
        GameManager.createBoard(gamer);
    }
}
