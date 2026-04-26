package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.visuals.layout.Layout;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ArenaMenu extends Menu {
    private final String name;
    protected final Arena arena;
    public ArenaMenu(String name, Arena arena, Layout layout) {
        super(name, layout);
        this.name = name;
        this.arena = arena;
    }

    protected void addNavigation(ItemStack item, Function<Arena,Menu> other) {
        addNavigation(item, () -> other.apply(arena));
    }

    protected void addNavigation(Material mat, Function<Arena,Menu> other, String name, String... lore) {
        addNavigation(mat, () -> other.apply(arena), name, lore);
    }

    protected void addNavigation(Material mat, Function<Arena,Menu> other, Supplier<String> name, String... lore) {
        addNavigation(mat, () -> other.apply(arena), name, lore);
    }

    @Contract(pure = true)
    protected Consumer<Player> ifClosed(Consumer<Player> task) {
        return p -> {
            if (checkClosed(p)) {
                task.accept(p);
            }
        };
    }

    protected boolean checkClosed(Player player) {
        if (arena.isClosed()) {
            return true;
        }
        PlayerManager.notifyFailure(player, "Arena must be closed to modify this!");
        return false;
    }

    @Override
    public String getName() {
        return CommunicationManager.format(name.contains(arena.getName()) ? name : name + ": " + arena.getName());
    }
}
