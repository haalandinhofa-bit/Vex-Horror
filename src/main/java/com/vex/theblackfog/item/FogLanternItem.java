package com.vex.theblackfog.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FogLanternItem extends Item {
    public static final String ACTIVE_KEY = "theblackfog_lantern_active";

    public FogLanternItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            CompoundTag data = player.getPersistentData();
            boolean active = !data.getBoolean(ACTIVE_KEY);
            data.putBoolean(ACTIVE_KEY, active);
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal(active
                            ? "Lanterna da Névoa: ligada"
                            : "Lanterna da Névoa: desligada"), true);
            if (!active) {
                FogLanternLightHandler.clearLights((ServerLevel) level, player);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
