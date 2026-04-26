package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.data.LanguageManager;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.game.challenges.Challenge;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;

import java.util.List;

public class ForcedChallengesMenu extends ArenaMenu {
    private final boolean display;
    public ForcedChallengesMenu(Arena arena, boolean display) {
        super("&9&l" + LanguageManager.messages.forcedChallenges, arena, new DynamicSizeLayout(true));
        this.display = display;

        addChallenge(Challenge.amputee());
        addChallenge(Challenge.clumsy());
        addChallenge(Challenge.featherweight());
        addChallenge(Challenge.pacifist());
        addChallenge(Challenge.dwarf());
        addChallenge(Challenge.uhc());
        addChallenge(Challenge.explosive());
        addChallenge(Challenge.naked());
        addChallenge(Challenge.blind());
    }

    private void addChallenge(Challenge challenge) {
        addButton(challenge.getButton(arena.getForcedChallenges().contains(challenge.getName())),
                p -> {
            if (display && !checkClosed(p)) {
                return;
            }
            List<String> l = arena.getForcedChallenges();
            if (!l.remove(challenge.getName())) {
                l.add(challenge.getName());
            }
            arena.setForcedChallenges(l);
        });
    }
}
