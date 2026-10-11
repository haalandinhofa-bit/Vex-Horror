package com.vex.theblackfog.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FogLanternItem extends Item {
    public FogLanternItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.getCooldowns().addCooldown(this, 30);
        if (!level.isClientSide) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 80, 0, false, false, true));
            Vec3 start = player.getEyePosition();
            Vec3 direction = player.getLookAngle();
            if (level instanceof ServerLevel serverLevel) {
                for (int i = 1; i <= 8; i++) {
                    Vec3 point = start.add(direction.scale(i));
                    serverLevel.sendParticles(ParticleTypes.END_ROD,
                            point.x, point.y, point.z, 2, 0.06, 0.06, 0.06, 0.002);
                    serverLevel.sendParticles(ParticleTypes.SMOKE,
                            point.x, point.y, point.z, 2, 0.08, 0.08, 0.08, 0.001);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}