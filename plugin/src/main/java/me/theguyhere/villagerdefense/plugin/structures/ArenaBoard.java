package me.theguyhere.villagerdefense.plugin.structures;

import lombok.Getter;
import me.theguyhere.villagerdefense.common.CommunicationManager;
import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.data.exceptions.InvalidLocationException;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * The scoreboard of an Arena.
 */
@Getter
public class ArenaBoard {
	/** The information for the ArenaBoard.*/
	private final Hologram hologram;
	/** The location of the ArenaBoard.*/
	private final Location location;

	public ArenaBoard(Location location, Arena arena) throws InvalidLocationException {
		// Check for null world
		if (location == null || location.getWorld() == null) {
            throw new InvalidLocationException("Location world cannot be null!");
        }

		// Gather relevant stats
		List<String> info = new ArrayList<>();
		info.add(CommunicationManager.format( "&6&l" + arena.getName() + " " + LanguageManager.messages.records));
        arena.getSortedDescendingRecords().forEach(record -> {
            StringBuilder line = new StringBuilder(LanguageManager.messages.wave)
                    .append(" &b").append(record.getWave()).append(" &f- ");
            StringJoiner players = new StringJoiner(", ");
            for (int i = 0; i < record.getPlayers().size(); i++) {
                players.add(record.getPlayers().get(i));
                // No more than 4 players per line
                if (i % 4 == 3) {
                    line.append(players);
                    if (i < record.getPlayers().size() - 1) {
                        line.append(',');
                    }
                    info.add(CommunicationManager.format(line.toString()));
                    line = new StringBuilder();
                    players = new StringJoiner(", ");
                }
            }
            if (players.length() != 0) {
                line.append(players);
                info.add(CommunicationManager.format(line.toString()));
            }
        });

		// Set location and hologram
		this.location = location;
		this.hologram = new Hologram(location.clone().add(0, 2.5, 0), false,
				info.toArray(new String[]{}));
	}

    /**
	 * Spawn in the ArenaBoard for every online player.
	 */
	public void displayForOnline() {
		hologram.displayForOnline();
	}

	/**
	 * Spawn in the ArenaBoard for a specific player.
	 * @param player - The player to display the ArenaBoard for.
	 */
	public void displayForPlayer(Player player) {
		hologram.displayForPlayer(player);
	}

	/**
	 * Stop displaying the ArenaBoard for every online player.
	 */
	public void remove() {
		hologram.remove();
	}
}
