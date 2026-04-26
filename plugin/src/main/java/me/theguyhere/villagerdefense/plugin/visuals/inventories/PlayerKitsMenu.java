package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.common.Constants;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.data.PlayerDataManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.game.achievements.AchievementChecker;
import me.theguyhere.villagerdefense.plugin.game.kits.*;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.BiConsumer;

public class PlayerKitsMenu extends Menu {
    private final Player player;
    public PlayerKitsMenu(Player player, boolean requestedByOwner) {
        super("&9&l" + String.format(LanguageManager.messages.playerKits, player.getName()), new ManualLayout());
        this.player = player;
        UUID uuid = player.getUniqueId();
        ManualLayout l = (ManualLayout) layout;

        BiConsumer<Integer, Kit> buttonBuilder = (slot, kit) -> {
            l.setNextSlot(slot);
            addButton(kit.getButton(PlayerDataManager.getPlayerKitLevel(uuid, kit), true),
                    p -> handlePurchase(p, kit));
        };

        // Gift kits
        for (int i = 0; i < 9; i++) {
            l.setNextSlot(i);
            addButton(Material.LIME_STAINED_GLASS_PANE, NO_OP,
                    "&a&l" + LanguageManager.names.giftKits,
                    CommunicationManager.formatDescriptionArr(ChatColor.GRAY,
                            LanguageManager.messages.giftKitsDescription, Constants.LORE_CHAR_LIMIT));
        }

        buttonBuilder.accept(9, new KitOrc());
        buttonBuilder.accept(10, new KitFarmer());
        buttonBuilder.accept(11, new KitSoldier());
        buttonBuilder.accept(12, new KitAlchemist());
        buttonBuilder.accept(13, new KitTailor());
        buttonBuilder.accept(14, new KitTrader());
        buttonBuilder.accept(15, new KitSummoner());
        buttonBuilder.accept(16, new KitReaper());
        buttonBuilder.accept(17, new KitPhantom());

        // Ability kits
        for (int i = 18; i < 27; i++) {
            l.setNextSlot(i);
            addButton(Material.MAGENTA_STAINED_GLASS_PANE, NO_OP,
                    "&d&l" + LanguageManager.names.abilityKits,
                    CommunicationManager.formatDescriptionArr(ChatColor.GRAY,
                            LanguageManager.messages.abilityKitsDescription, Constants.LORE_CHAR_LIMIT));
        }

        buttonBuilder.accept(27, new KitMage());
        buttonBuilder.accept(28, new KitNinja());
        buttonBuilder.accept(29, new KitTemplar());
        buttonBuilder.accept(30, new KitWarrior());
        buttonBuilder.accept(31, new KitKnight());
        buttonBuilder.accept(32, new KitPriest());
        buttonBuilder.accept(33, new KitSiren());
        buttonBuilder.accept(34, new KitMonk());
        buttonBuilder.accept(35, new KitMessenger());

        // Effect kits
        for (int i = 36; i < 45; i++) {
            l.setNextSlot(i);
            addButton(Material.YELLOW_STAINED_GLASS_PANE, NO_OP,
                    "&e&l" + LanguageManager.names.effectKits,
                    CommunicationManager.formatDescriptionArr(ChatColor.GRAY,
                            LanguageManager.messages.effectKitsDescription, Constants.LORE_CHAR_LIMIT));
        }

        buttonBuilder.accept(45, new KitBlacksmith());
        buttonBuilder.accept(46, new KitWitch());
        buttonBuilder.accept(47, new KitMerchant());
        buttonBuilder.accept(48, new KitVampire());
        buttonBuilder.accept(49, new KitGiant());

        // Crystal balance
        if (requestedByOwner) {
            l.setNextSlot(52);
            addButton(Material.DIAMOND, NO_OP,
                    "&b&l" + LanguageManager.messages.crystalBalance + ": &b" + PlayerDataManager.getPlayerCrystals(uuid));
        }

        l.add(53, InventoryButtons.exit());
    }

    private void handlePurchase(Player player, Kit kit) {
        if (player != this.player) {
            // Ignore clicks from non-owner viewers
            return;
        }

        UUID uuid = player.getUniqueId();
        // Cache player data
        int kitLevel = PlayerDataManager.getPlayerKitLevel(uuid, kit);

        // Ignore if kit already maxed
        if (kitLevel >= kit.getMaxLevel()) {
            return;
        }

        int crystalBalance = PlayerDataManager.getPlayerCrystals(uuid);
        int price = kit.getPrice(++kitLevel);
        if (crystalBalance < price) { // not enough funds
            PlayerManager.notifyFailure(
                    player,
                    kitLevel == 1 ? LanguageManager.errors.kitBuy : LanguageManager.errors.kitUpgrade
            );
            return;
        }

        // Successful purchase
        PlayerDataManager.setPlayerCrystals(uuid, crystalBalance - price);
        PlayerDataManager.setPlayerKitLevel(uuid, kit, kitLevel);
        PlayerManager.notifySuccess(
                player,
                kitLevel == 1 ? LanguageManager.confirms.kitBuy : LanguageManager.confirms.kitUpgrade
        );
        AchievementChecker.checkDefaultKitAchievements(player);
    }
}
