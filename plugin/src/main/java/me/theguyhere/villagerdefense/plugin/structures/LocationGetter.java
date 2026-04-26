package me.theguyhere.villagerdefense.plugin.structures;

import me.theguyhere.villagerdefense.plugin.data.exceptions.BadDataException;
import me.theguyhere.villagerdefense.plugin.data.exceptions.NoSuchPathException;
import org.bukkit.Location;

@FunctionalInterface
public interface LocationGetter {
    Location get() throws BadDataException, NoSuchPathException;
}
