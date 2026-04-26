package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.structures.NumberedLocationGetter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class LocationDashboard extends DataStructureDashboard<LocationDashboard.LocationStructure> {
    private final NumberedLocationGetter getter;
    private final BiConsumer<Integer, Location> setter;
    private final Supplier<Integer> newId;

    public LocationDashboard(String name, String dataStructure, Material buttonType, Collection<Integer> ids,
                             NumberedLocationGetter getter, BiConsumer<Integer, Location> setter, Supplier<Integer> newId) {
        super(name, dataStructure, buttonType, ids.stream().map(id -> new LocationStructure(id, name, getter, setter)), LocationStructure::makeMenu);

        this.getter = getter;
        this.setter = setter;
        this.newId = newId;
    }

    @Override
    protected void createNew(Player player) {
        open(player, new LocationStructure(newId.get(), getName(), getter, setter).makeMenu());
    }

    public static class LocationStructure implements Comparable<LocationStructure> {
        private final int id;
        private final String name;
        private final NumberedLocationGetter getter;
        private final BiConsumer<Integer, Location> setter;

        public LocationStructure(int id, String name, NumberedLocationGetter getter, BiConsumer<Integer, Location> setter) {
            this.id = id;
            this.name = name;
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        public int compareTo(@NotNull LocationDashboard.LocationStructure o) {
            return Integer.compare(id, o.id);
        }

        public Menu makeMenu() {
            return new LocationMenu(name, () -> getter.get(id), loc -> setter.accept(id, loc));
        }

        @Override
        public String toString() {
            return name + " " + id;
        }
    }
}
