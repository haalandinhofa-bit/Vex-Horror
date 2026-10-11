package com.vex.theblackfog.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "theblackfog")
public final class FogLanternLightHandler {
    private static final String POSITIONS_KEY = "theblackfog_lantern_light_positions";
    private static final int BEAM_LENGTH = 8;

    private FogLanternLightHandler() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;
        if (!(event.player.level() instanceof ServerLevel level)) return;

        Player player = event.player;
        if (!player.getPersistentData().getBoolean(FogLanternItem.ACTIVE_KEY)) {
            clearLights(level, player);
            return;
        }

        CompoundTag data = player.getPersistentData();
        removePrevious(level, data);
        List<BlockPos> placed = new ArrayList<>();
        Vec3 start = player.getEyePosition();
        Vec3 direction = player.getLookAngle().normalize();

        // Place invisible Minecraft light blocks along the view direction.
        // Existing blocks are never replaced; the beam is refreshed as the player moves.
        for (int i = 1; i <= BEAM_LENGTH; i++) {
            BlockPos pos = BlockPos.containing(start.add(direction.scale(i)));
            if (!level.isLoaded(pos) || !level.getBlockState(pos).isAir()) continue;
            BlockState lightState = Blocks.LIGHT.defaultBlockState()
                    .setValue(LightBlock.LEVEL, 15);
            if (level.setBlock(pos, lightState, 3)) placed.add(pos.immutable());
        }
        savePositions(data, placed);
    }

    public static void clearLights(ServerLevel level, Player player) {
        removePrevious(level, player.getPersistentData());
    }

    private static void removePrevious(ServerLevel level, CompoundTag data) {
        if (!data.contains(POSITIONS_KEY)) return;
        long[] packed = data.getLongArray(POSITIONS_KEY);
        for (long value : packed) {
            BlockPos pos = BlockPos.of(value);
            if (level.isLoaded(pos) && level.getBlockState(pos).is(Blocks.LIGHT)) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
        data.remove(POSITIONS_KEY);
    }

    private static void savePositions(CompoundTag data, List<BlockPos> positions) {
        long[] packed = new long[positions.size()];
        for (int i = 0; i < positions.size(); i++) packed[i] = positions.get(i).asLong();
        data.putLongArray(POSITIONS_KEY, packed);
    }
}
