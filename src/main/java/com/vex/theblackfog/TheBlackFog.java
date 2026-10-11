package com.vex.theblackfog;

import com.vex.theblackfog.registry.ModEntities;
import com.vex.theblackfog.registry.ModItems;
import com.vex.theblackfog.world.PlayerJoinHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TheBlackFog.MOD_ID)
public class TheBlackFog {
    public static final String MOD_ID = "theblackfog";

    public TheBlackFog() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modBus);
        ModEntities.ENTITIES.register(modBus);
        modBus.addListener(this::registerAttributes);
        modBus.addListener(this::registerSpawnPlacements);
        MinecraftForge.EVENT_BUS.addListener(PlayerJoinHandler::onPlayerJoin);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BLACK_FOG.get(), com.vex.theblackfog.entity.BlackFogEntity.createAttributes().build());
    }

    private void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.BLACK_FOG.get(),
                net.minecraft.world.entity.SpawnPlacements.Type.ON_GROUND,
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                net.minecraft.world.entity.monster.Monster::checkMonsterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}