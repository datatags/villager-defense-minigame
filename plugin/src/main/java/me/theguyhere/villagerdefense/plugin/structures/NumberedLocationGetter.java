package me.theguyhere.villagerdefense.plugin.structures;

import me.theguyhere.villagerdefense.plugin.data.exceptions.BadDataException;
import me.theguyhere.villagerdefense.plugin.data.exceptions.NoSuchPathException;
import org.bukkit.Location;

@FunctionalInterface
public interface NumberedLocationGetter {
    Location get(int id) throws BadDataException, NoSuchPathException;
}
