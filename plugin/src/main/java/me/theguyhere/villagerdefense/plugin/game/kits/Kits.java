package me.theguyhere.villagerdefense.plugin.game.kits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Kits {
    /**
     * List of all kits, not including the "none" kit
     */
    public static final List<Kit> ALL_KITS;
    /**
     * Lists of all kits, organized by the kit category, not including the "none" kit.
     */
    public static final Map<KitCategory,List<Kit>> KITS_BY_CATEGORY;

    static {
        List<Kit> all = new ArrayList<>();
        Map<KitCategory,List<Kit>> byCategory = new HashMap<>();
        Consumer<Kit> adder = k -> {
            all.add(k);
            byCategory.computeIfAbsent(k.getKitCategory(), l -> new ArrayList<>()).add(k);
        };

        adder.accept(new KitAlchemist());
        adder.accept(new KitBlacksmith());
        adder.accept(new KitFarmer());
        adder.accept(new KitGiant());
        adder.accept(new KitKnight());
        adder.accept(new KitMage());
        adder.accept(new KitMerchant());
        adder.accept(new KitMessenger());
        adder.accept(new KitMonk());
        adder.accept(new KitNinja());
        adder.accept(new KitOrc());
        adder.accept(new KitPhantom());
        adder.accept(new KitPriest());
        adder.accept(new KitReaper());
        adder.accept(new KitSiren());
        adder.accept(new KitSoldier());
        adder.accept(new KitSummoner());
        adder.accept(new KitTailor());
        adder.accept(new KitTemplar());
        adder.accept(new KitTrader());
        adder.accept(new KitVampire());
        adder.accept(new KitWarrior());
        adder.accept(new KitWitch());

        ALL_KITS = Collections.unmodifiableList(all);
        Map<KitCategory,List<Kit>> unmodifiableByCategory = new HashMap<>();
        byCategory.forEach((cat, list) -> unmodifiableByCategory.put(cat, Collections.unmodifiableList(list)));
        KITS_BY_CATEGORY = Collections.unmodifiableMap(unmodifiableByCategory);
    }

    public static Kit getByName(String name) {
        if (name == null) {
            return null;
        }
        // "none" kit is not in ALL_KITS so we check for it separately
        Kit none = new KitNone();
        if (name.equalsIgnoreCase(none.getName())) {
            return none;
        }
        for (Kit kit : ALL_KITS) {
            if (kit.getName().equalsIgnoreCase(name)) {
                return kit;
            }
        }
        return null;
    }

    public static Kit randomKitOtherThan(Kit other) {
        int targetIndex = ThreadLocalRandom.current().nextInt(ALL_KITS.size() - 1);
        int skipIndex = ALL_KITS.indexOf(other);
        if (targetIndex >= skipIndex) {
            targetIndex++;
        }
        return ALL_KITS.get(targetIndex);
    }

    private Kits() {
        throw new UnsupportedOperationException("Utility class");
    }
}
