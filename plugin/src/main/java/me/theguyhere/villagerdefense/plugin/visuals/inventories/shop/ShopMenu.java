package me.theguyhere.villagerdefense.plugin.visuals.inventories.shop;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.entities.PlayerNotFoundException;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.game.kits.KitBlacksmith;
import me.theguyhere.villagerdefense.plugin.game.kits.KitEffectType;
import me.theguyhere.villagerdefense.plugin.game.kits.KitMerchant;
import me.theguyhere.villagerdefense.plugin.game.kits.KitWitch;
import me.theguyhere.villagerdefense.plugin.items.GameItems;
import me.theguyhere.villagerdefense.plugin.items.ItemManager;
import me.theguyhere.villagerdefense.plugin.visuals.inventories.ArenaMenu;
import me.theguyhere.villagerdefense.plugin.visuals.layout.ManualLayout;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class ShopMenu extends ArenaMenu {
    public ShopMenu(String name, Arena arena) {
        super(name, arena, new ManualLayout());
    }

    protected void addItem(int slot, ItemStack item) {
        ((ManualLayout)layout).add(slot, item);
        addClickHandler(item, p -> handleBuy(p, item));
    }

    protected void handleBuy(Player player, ItemStack displayItem) {
        VDPlayer gamer;
        try {
            gamer = arena.getPlayer(player);
        } catch (PlayerNotFoundException err) {
            return;
        }

        ItemStack buy = displayItem.clone();
        Material buyType = buy.getType();
        List<String> lore = buy.getItemMeta().getLore();

        // Ignore un-purchasable items
        if (lore == null || lore.isEmpty()) {
            return;
        }

        int cost = Integer.parseInt(lore.get(lore.size() - 1)
                .substring(6 + LanguageManager.messages.gems.length()));

        // Check if they can afford the item
        if (!gamer.canAfford(cost)) {
            PlayerManager.notifyFailure(player, LanguageManager.errors.buy);
            return;
        }

        // Remove cost meta
        buy = ItemManager.removeLastLore(buy);

        // Make unbreakable for blacksmith (not sharing)
        if (gamer.using(KitBlacksmith.class) && !gamer.isSharing()) {
            buy = ItemManager.makeUnbreakable(buy);
        }

        // Make unbreakable for successful blacksmith sharing
        if (arena.rollEffectShare(KitEffectType.BLACKSMITH)) {
            buy = ItemManager.makeUnbreakable(buy);
            PlayerManager.notifySuccess(player, LanguageManager.messages.effectShare);
        }

        // Make splash potion for witch (not sharing)
        if (gamer.using(KitWitch.class) && !gamer.isSharing())
            buy = ItemManager.makeSplash(buy);

        // Make splash potion for successful witch sharing
        if (arena.rollEffectShare(KitEffectType.WITCH)) {
            buy = ItemManager.makeSplash(buy);
            PlayerManager.notifySuccess(player, LanguageManager.messages.effectShare);
        }

        // Subtract from balance, apply rebate, and update scoreboard
        gamer.addGems(-cost);
        if (gamer.using(KitMerchant.class) && !gamer.isSharing())
            gamer.addGems(cost / 10);
        if (arena.rollEffectShare(KitEffectType.MERCHANT)) {
            gamer.addGems(cost / 10);
            PlayerManager.notifySuccess(player, LanguageManager.messages.effectShare);
        }
        GameManager.createBoard(gamer);

        EntityEquipment equipment = Objects.requireNonNull(player.getPlayer()).getEquipment();
        Objects.requireNonNull(equipment);

        // Equip armor if possible, otherwise put in inventory, otherwise drop at feet
        if (GameItems.HELMET_MATERIALS.contains(buyType) &&
                (equipment.getHelmet() == null || equipment.getHelmet().getType() == Material.AIR)) {
            equipment.setHelmet(buy);
            PlayerManager.notifySuccess(player, LanguageManager.confirms.helmet);
        } else if (GameItems.CHESTPLATE_MATERIALS.contains(buyType) &&
                (equipment.getChestplate() == null || equipment.getChestplate().getType() == Material.AIR)) {
            equipment.setChestplate(buy);
            PlayerManager.notifySuccess(player, LanguageManager.confirms.chestplate);
        } else if (GameItems.LEGGING_MATERIALS.contains(buyType) &&
                (equipment.getLeggings() == null || equipment.getLeggings().getType() == Material.AIR)) {
            equipment.setLeggings(buy);
            PlayerManager.notifySuccess(player, LanguageManager.confirms.leggings);
        } else if (GameItems.BOOTS_MATERIALS.contains(buyType) &&
                (equipment.getBoots() == null || equipment.getBoots().getType() == Material.AIR)) {
            equipment.setBoots(buy);
            PlayerManager.notifySuccess(player, LanguageManager.confirms.boots);
        } else {
            PlayerManager.giveItem(player, buy);
            PlayerManager.notifySuccess(player, LanguageManager.confirms.buy);
        }
    }
}
