package com.vex.theblackfog.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlackFogEntity extends Monster {
    private int watchedTicks;

    public BlackFogEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 0;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    public boolean isHunting() {
        return !level().isDay();
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15D, true) {
            @Override public boolean canUse() {
                return BlackFogEntity.this.isHunting() && super.canUse();
            }
            @Override public boolean canContinueToUse() {
                return BlackFogEntity.this.isHunting() && super.canContinueToUse();
            }
        });
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D) {
            @Override public boolean canUse() {
                return !BlackFogEntity.this.isHunting() && super.canUse();
            }
        });
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 20.0F));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, true) {
            @Override public boolean canUse() {
                return BlackFogEntity.this.isHunting() && super.canUse();
            }
            @Override public boolean canContinueToUse() {
                return BlackFogEntity.this.isHunting() && super.canContinueToUse();
            }
        });
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) return;

        if (!isHunting()) {
            if (getTarget() != null) setTarget(null);

            Player nearest = level().getNearestPlayer(this, 32.0D);
            if (nearest != null) {
                double distance = distanceTo(nearest);
                boolean staring = false;
                if (distance > 0.001D && nearest.hasLineOfSight(this)) {
                    net.minecraft.world.phys.Vec3 toCreature =
                            position().add(0.0D, getBbHeight() * 0.65D, 0.0D)
                                    .subtract(nearest.getEyePosition()).normalize();
                    staring = nearest.getLookAngle().normalize().dot(toCreature) > 0.94D;
                }

                if (distance < 3.0D) {
                    discard();
                    return;
                }
                if (staring) {
                    watchedTicks++;
                    if (watchedTicks >= 60) {
                        discard();
                        return;
                    }
                } else {
                    watchedTicks = 0;
                }
            } else {
                watchedTicks = 0;
            }
        } else {
            watchedTicks = 0;
            if (tickCount % 80 == 0) {
                // Sparse smoke particles give the night form a subtle supernatural presence.
                level().broadcastEntityEvent(this, (byte) 61);
            }
        }
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        return isHunting() && super.doHurtTarget(target);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 61) {
            for (int i = 0; i < 5; i++) {
                level().addParticle(net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE,
                        getX() + (random.nextDouble() - 0.5D) * 0.8D,
                        getY() + random.nextDouble() * 1.8D,
                        getZ() + (random.nextDouble() - 0.5D) * 0.8D,
                        0.0D, 0.015D, 0.0D);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }
}