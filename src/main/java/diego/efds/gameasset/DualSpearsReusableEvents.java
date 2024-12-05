package diego.efds.gameasset;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public  class DualSpearsReusableEvents {


    public static final AnimationEvent.AnimationEventConsumer GROUNDSLAM = (livingEntityPatch, staticAnimation, objects) -> {
        LivingEntity entity = livingEntityPatch.getOriginal();
        Vec3 viewVec = entity.getViewVector(1.0F);
        Vec3 hVec = viewVec.add(0, -viewVec.y, 0);
        Vec3 target = entity.position().add(hVec.normalize().scale(3.0)).add(0, -1, 0);
        LevelUtil.circleSlamFracture(entity, entity.level(), target, 3.0, false, false);
    };
    public static final AnimationEvent.AnimationEventConsumer GROUNDSLAM_MEDIUM = (livingEntityPatch, staticAnimation, objects) -> {
        LivingEntity entity = livingEntityPatch.getOriginal();
        Vec3 viewVec = entity.getViewVector(1.0F);
        Vec3 hVec = viewVec.add(0, -viewVec.y, 0);
        Vec3 target = entity.position().add(hVec.normalize().scale(3.0)).add(0, -1, 0);

        LevelUtil.circleSlamFracture(entity, entity.level(), target, 1.5, false, false);

    };
    public static final AnimationEvent.AnimationEventConsumer GROUNDSLAM_HUGE = (livingEntityPatch, staticAnimation, objects) -> {
        LivingEntity entity = livingEntityPatch.getOriginal();
        Vec3 viewVec = entity.getViewVector(1.0F);
        Vec3 hVec = viewVec.add(0, -viewVec.y, 0);
        Vec3 target = entity.position().add(hVec.normalize().scale(3.0)).add(0, -1, 0);
        LevelUtil.circleSlamFracture(entity, entity.level(), target, 7.0, false, false);


    };
    public static final AnimationEvent.AnimationEventConsumer GROUNDSLAM_SMALL = (entitypatch, self, params) -> {
        Vec3 position = ((LivingEntity)entitypatch.getOriginal()).position();
        OpenMatrix4f modelTransform = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(0.0F), Armatures.BIPED.toolR).mulFront(OpenMatrix4f.createTranslation((float)position.x, (float)position.y, (float)position.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(entitypatch.getModelMatrix(1.0F))));
        Vec3 weaponEdge = OpenMatrix4f.transform(modelTransform, (new Vec3f(0.0F, 0.0F, -1.4F)).toDoubleVector());
        Level level = ((LivingEntity)entitypatch.getOriginal()).level();
        Vec3 floorPos = getfloor(entitypatch, self, new Vec3f(0.0F, 0.0F, -1.4F), Armatures.BIPED.toolR);
        BlockState blockState = ((LivingEntity)entitypatch.getOriginal()).level().getBlockState(new BlockPos.MutableBlockPos(floorPos.x, floorPos.y, floorPos.z));
        if (entitypatch instanceof PlayerPatch) {
            ((LivingEntity)entitypatch.getOriginal()).level().playSound((Player)entitypatch.getOriginal(), entitypatch.getOriginal(), blockState.is(Blocks.WATER) ? SoundEvents.GENERIC_SPLASH : (SoundEvent)EpicFightSounds.GROUND_SLAM.get(), SoundSource.PLAYERS, 1.5F, 1.5F - ((new Random()).nextFloat() - 0.5F) * 0.2F);
        }
        weaponEdge = new Vec3(weaponEdge.x, floorPos.y, weaponEdge.z);
        LevelUtil.circleSlamFracture((LivingEntity)entitypatch.getOriginal(), level, weaponEdge, 1.8, true, false);
    };



    public static final AnimationEvent.AnimationEventConsumer WEAPON_SPIN = (entitypatch, self, params) -> entitypatch.getOriginal().level().playSound((Player) entitypatch.getOriginal(), entitypatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.MASTER, 0.5F, 1.1F - ((new Random().nextFloat() - 0.5f) * 0.2F));

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int PARTICLE_COUNT = 69;

    private static final AnimationEvent.AnimationEventConsumer TELEPORT = (entitypatch, self, params) -> {
        Entity entity = entitypatch.getOriginal();
        RandomSource random = entitypatch.getOriginal().getRandom();
        entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1F, 1.2F); //First sound
        spawnParticles(entity, random);
        scheduleParticleSpawn(entity, random, 215);

        scheduler.schedule(() -> {
            entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1F, 1.2F); //Second Sound
            spawnParticles(entity, random);
            scheduleParticleSpawn(entity, random, 215);
        }, 396, TimeUnit.MILLISECONDS); //Second Sound Delay, if you want to add the second sound in the same event (If not delete all this from scheuler.schedule)
    };

    private static void spawnParticles(Entity entity, RandomSource random) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            //Particle Radius
            double horizontalRadius = 1.2;
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                double x = (random.nextDouble() - 0.5) * horizontalRadius;
                double y = entity.getY() + (random.nextDouble() - random.nextDouble()) * 1.5D;
                double z = (random.nextDouble() - 0.5) * horizontalRadius;
                //Particle speed, position, and type
                clientLevel.addParticle(ParticleTypes.PORTAL,
                        entity.getX() + x,
                        y,
                        entity.getZ() + z,
                        0,
                        0.3,
                        0
                );
            }
        }
    }


    private static void scheduleParticleSpawn(Entity entity, RandomSource random, long delay) {
        scheduler.schedule(() -> DualSpearsReusableEvents.spawnParticles(entity, random), delay, TimeUnit.MILLISECONDS);
    }

    public static Vec3 getfloor(LivingEntityPatch<?> entitypatch, StaticAnimation self, Vec3f WeaponOffset, Joint joint) {
        float dpx = WeaponOffset.x + (float)((LivingEntity)entitypatch.getOriginal()).getX();
        float dpy = WeaponOffset.y + (float)((LivingEntity)entitypatch.getOriginal()).getY();
        float dpz = WeaponOffset.z + (float)((LivingEntity)entitypatch.getOriginal()).getZ();
        if (joint != null) {
            OpenMatrix4f transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(1.0F), joint);
            transformMatrix.translate(WeaponOffset);
            OpenMatrix4f CORRECTION = (new OpenMatrix4f()).rotate(-((float)Math.toRadians((double)(((LivingEntity)entitypatch.getOriginal()).yRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F));
            OpenMatrix4f.mul(CORRECTION, transformMatrix, transformMatrix);
            dpx = transformMatrix.m30 + (float)((LivingEntity)entitypatch.getOriginal()).getX();
            dpy = transformMatrix.m31 + (float)((LivingEntity)entitypatch.getOriginal()).getY();
            dpz = transformMatrix.m32 + (float)((LivingEntity)entitypatch.getOriginal()).getZ();
        }

        for(BlockState block = ((LivingEntity)entitypatch.getOriginal()).level().getBlockState(new BlockPos.MutableBlockPos((double)dpx, (double)dpy, (double)dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR) && dpy > -64.0F; block = ((LivingEntity)entitypatch.getOriginal()).level().getBlockState(new BlockPos.MutableBlockPos((double)dpx, (double)dpy, (double)dpz))) {
            --dpy;
        }

        return new Vec3((double)dpx, (double)dpy, (double)dpz);
    }
}



