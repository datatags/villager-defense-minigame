package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.entities.VDPlayer;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.GameManager;
import me.theguyhere.villagerdefense.plugin.game.PlayerManager;
import me.theguyhere.villagerdefense.plugin.game.challenges.Challenge;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SelectChallengesMenu extends ArenaMenu {
    private final VDPlayer gamer;
    public SelectChallengesMenu(Arena arena, VDPlayer gamer) {
        super("&5&l" + arena.getName() + " " + LanguageManager.messages.challenges, arena, new DynamicSizeLayout(true));
        this.gamer = gamer;

        // Set buttons
        addChallenge(Challenge.amputee());
        addChallenge(Challenge.clumsy());
        addChallenge(Challenge.featherweight());
        addChallenge(Challenge.pacifist());
        addChallenge(Challenge.dwarf());
        addChallenge(Challenge.uhc());
        addChallenge(Challenge.explosive());
        addChallenge(Challenge.naked());
        addChallenge(Challenge.blind());
        addChallenge(Challenge.none());
    }

    private void addChallenge(Challenge challenge) {
        ItemStack item = challenge.getButton(false);
        addButton(item, p -> handleClick(p, challenge));
        buttonUpdaters.put(item, i -> swapMeta(i, challenge.getButton(gamer.getChallenges().contains(challenge)).getItemMeta()));
    }

    private void handleClick(Player player, Challenge challenge) {
        // Ignore spectators from here on out
        if (gamer.getStatus() == VDPlayer.Status.SPECTATOR) {
            return;
        }

        // Option for no challenge
        if (Challenge.none().equals(challenge)) {
            // Arena has forced challenges
            if (!arena.getForcedChallenges().isEmpty())
                PlayerManager.notifyFailure(player, LanguageManager.errors.hasForcedChallenges);
            else {
                gamer.resetChallenges();
                PlayerManager.notifySuccess(player, LanguageManager.confirms.challengeAdd);
            }
        }
        // Remove a challenge
        else if (gamer.getChallenges().contains(challenge)) {
            // Arena forced the challenge
            if (arena.getForcedChallenges().contains(challenge.getName()))
                PlayerManager.notifyFailure(player, LanguageManager.errors.forcedChallenge);
            else {
                gamer.removeChallenge(challenge);
                PlayerManager.notifySuccess(player, LanguageManager.confirms.challengeDelete);
            }
        }
        // Add a challenge
        else {
            gamer.addChallenge(challenge);
            PlayerManager.notifySuccess(player, LanguageManager.confirms.challengeAdd);
        }
        GameManager.createBoard(gamer);
    }
}
