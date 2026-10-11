package com.vex.theblackfog.world;

import com.vex.theblackfog.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public final class PlayerJoinHandler {
    private static final String GIVEN_KEY = "theblackfog_lantern_given";

    private PlayerJoinHandler() {}

    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        CompoundTag data = player.getPersistentData();
        if (data.getBoolean(GIVEN_KEY)) return;

        ItemStack lantern = new ItemStack(ModItems.FOG_LANTERN.get());
        if (!player.getInventory().add(lantern)) {
            player.drop(lantern, false);
        }
        data.putBoolean(GIVEN_KEY, true);
    }
}