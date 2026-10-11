package com.vex.theblackfog.registry;

import com.vex.theblackfog.TheBlackFog;
import com.vex.theblackfog.entity.BlackFogEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheBlackFog.MOD_ID);

    public static final RegistryObject<EntityType<BlackFogEntity>> BLACK_FOG = ENTITIES.register("black_fog",
            () -> EntityType.Builder.of(BlackFogEntity::new, MobCategory.MONSTER)
                    .sized(0.7F, 2.35F)
                    .clientTrackingRange(10)
                    .build(TheBlackFog.MOD_ID + ":black_fog"));

    private ModEntities() {}
}