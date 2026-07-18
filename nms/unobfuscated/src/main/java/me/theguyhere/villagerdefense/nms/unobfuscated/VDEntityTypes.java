package me.theguyhere.villagerdefense.nms.unobfuscated;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

import java.util.Locale;

public enum VDEntityTypes {
    ARMOR_STAND,
    VILLAGER,
    ;
    private final Identifier identifier;
    private VDEntityTypes() {
        identifier = Identifier.withDefaultNamespace(name().toLowerCase(Locale.ROOT));
    }

    public EntityType<?> get() {
        return BuiltInRegistries.ENTITY_TYPE.getValue(identifier);
    }
}
