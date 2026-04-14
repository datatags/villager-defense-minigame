package me.theguyhere.villagerdefense.plugin.game.kits;

import lombok.Getter;

public enum KitEffectType {
    BLACKSMITH(KitBlacksmith.class),
    WITCH(KitWitch.class),
    MERCHANT(KitMerchant.class),
    VAMPIRE(KitVampire.class),
    GIANT1(KitGiant.class, 1),
    GIANT2(KitGiant.class, 2),
    ;
    @Getter
    private final Class<? extends Kit> kitType;
    @Getter
    private final int level;
    private KitEffectType(Class<? extends Kit> kitType, int level) {
        this.kitType = kitType;
        this.level = level;
    }

    private KitEffectType(Class<? extends Kit> kitType) {
        this(kitType, 1);
    }
}
