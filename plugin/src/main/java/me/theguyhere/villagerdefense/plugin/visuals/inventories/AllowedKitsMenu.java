package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.common.Constants;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.kits.*;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

public class AllowedKitsMenu extends ArenaMenu {
    public AllowedKitsMenu(Arena arena, boolean display) {
        super("&9&l" + LanguageManager.messages.allowedKits, arena, new ManualLayout());

        ManualLayout layout = (ManualLayout) this.layout;
        final List<String> banned = arena.getBannedKits();
        BiConsumer<Integer, Kit> buttonBuilder = (slot, kit) -> {
            String name = kit.getName();
            ItemStack item = kit.getDisplayItem(!banned.contains(name));
            layout.add(slot, item);
            addClickHandler(item, p -> {
                if (display || !checkClosed(p)) {
                    return;
                }
                List<String> b = arena.getBannedKits();
                if (!b.remove(name)) {
                    b.add(name);
                }
                arena.setBannedKits(b);
            });
            buttonUpdaters.put(item, i -> {
                swapMeta(i, kit.getDisplayItem(arena.getBannedKits().contains(name)).getItemMeta());
            });
        };

        // Gift kits
        for (int i = 0; i < 9; i++) {
            layout.add(i, ItemManager.createItem(Material.LIME_STAINED_GLASS_PANE,
                    CommunicationManager.format("&a&l" + LanguageManager.names.giftKits),
                    CommunicationManager.formatDescriptionArr(ChatColor.GRAY,
                            LanguageManager.messages.giftKitsDescription, Constants.LORE_CHAR_LIMIT)));
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
        for (int i = 18; i < 27; i++)
            layout.add(i, ItemManager.createItem(Material.MAGENTA_STAINED_GLASS_PANE,
                    CommunicationManager.format("&d&l" + LanguageManager.names.abilityKits),
                    CommunicationManager.formatDescriptionArr(ChatColor.GRAY,
                            LanguageManager.messages.abilityKitsDescription, Constants.LORE_CHAR_LIMIT)));

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
        for (int i = 36; i < 45; i++)
            layout.add(i, ItemManager.createItem(Material.YELLOW_STAINED_GLASS_PANE,
                    CommunicationManager.format("&e&l" + LanguageManager.names.effectKits),
                    CommunicationManager.formatDescriptionArr(ChatColor.GRAY,
                            LanguageManager.messages.effectKitsDescription, Constants.LORE_CHAR_LIMIT)));

        buttonBuilder.accept(45, new KitBlacksmith());
        buttonBuilder.accept(46, new KitWitch());
        buttonBuilder.accept(47, new KitMerchant());
        buttonBuilder.accept(48, new KitVampire());
        buttonBuilder.accept(49, new KitGiant());

        // Option to exit
        layout.add(53, InventoryButtons.exit());
    }
}
