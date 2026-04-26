package me.theguyhere.villagerdefense.plugin.visuals.inventories;

import me.theguyhere.villagerdefense.plugin.data.NMSVersion;
import me.theguyhere.villagerdefense.plugin.game.Arena;
import me.theguyhere.villagerdefense.plugin.visuals.layout.DynamicSizeLayout;

public class WaitSoundMenu extends ArenaMenu {
    public WaitSoundMenu(Arena arena) {
        super("&6&lWaiting Sound", arena, new DynamicSizeLayout(true));

        // Sound options
        addSound("blocks");
        addSound("cat");
        addSound("chirp");
        addSound("far");
        addSound("mall");
        addSound("mellohi");
        if (NMSVersion.isGreaterEqualThan(NMSVersion.v1_18_R1)) {
            addSound("otherside");
        }
        addSound("pigstep");
        addSound("stal");
        addSound("strad");
        addSound("wait");
        addSound("ward");
        addSound("none");
    }

    private void addSound(String sound) {
        addButton(arena.getWaitingSoundButton(sound), ifClosed(p -> arena.setWaitingSound(sound)));
    }

    @Override
    public String getName() {
        return super.getName() + ": " + arena.getWaitingSoundName();
    }
}
