package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.data.PlayerDataManager;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.visuals.InventoryButtons;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CrystalConvertMenu extends Menu {
    private final VDPlayer gamer;
    public CrystalConvertMenu(VDPlayer gamer) {
        super("&9&l" + LanguageManager.names.crystalConverter, new ManualLayout());
        this.gamer = gamer;

        ManualLayout l = (ManualLayout) layout;
        // Display crystals to convert
        l.setNextSlot(3);
        addButton(Material.DIAMOND_BLOCK, NO_OP,
                () -> "&b&l" + LanguageManager.messages.crystalsToConvert + ": " + (gamer.getGemBoost() * 5));

        // Display gems to receive
        l.setNextSlot(5);
        addButton(Material.EMERALD_BLOCK, NO_OP,
                () -> "&a&l" + LanguageManager.messages.gemsToReceive + ": " + gamer.getGemBoost());

        // Crystal balance display
        l.setNextSlot(8);
        addButton(Material.DIAMOND, NO_OP,
                () -> "&b&l" + LanguageManager.messages.crystalBalance + ": &b" + PlayerDataManager.getPlayerCrystals(gamer.getID()));

        l.setNextSlot(17);
        addButton(Material.LIGHT_BLUE_CONCRETE, p -> gamer.setGemBoost(0),
                CommunicationManager.format("&3&l" + LanguageManager.messages.reset));

        addModifier(9,  +1);
        addModifier(11, +10);
        addModifier(13, +100);
        addModifier(15, +1000);
        addModifier(18, -1);
        addModifier(20, -10);
        addModifier(22, -100);
        addModifier(24, -1000);

        l.add(26, InventoryButtons.exit());
    }

    private void addModifier(int slot, int amount) {
        Material mat = amount > 0 ? Material.LIME_CONCRETE : Material.RED_CONCRETE;
        StringBuilder value = new StringBuilder(amount > 0 ? "&a&l" : "&c&l");
        if (amount >= 0) {
            value.append('+');
        }
        value.append(amount).append(' ').append(LanguageManager.messages.gems);
        ((ManualLayout)layout).setNextSlot(slot);
        addButton(mat, p -> handleClick(p, amount), value.toString());
    }

    private void handleClick(Player player, int delta) {
        int gemBoost = gamer.getGemBoost();
        int crystalBalance = PlayerDataManager.getPlayerCrystals(player.getUniqueId());

        if ((gemBoost + delta) * 5 > crystalBalance) {
            PlayerManager.notifyFailure(player, LanguageManager.errors.buyGeneral);
            return;
        }

        gamer.setGemBoost(Math.max(0, gemBoost + delta));
    }
}
