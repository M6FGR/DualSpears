package diego.efds.api.animation.types;

import java.util.Locale;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.EntityState.StateFactor;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.datastruct.TypeFlexibleHashMap;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.config.EpicFightOptions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.gamerule.EpicFightGamerules;

public class WeaponSpecialAnimation extends AttackAnimation {
    public WeaponSpecialAnimation(float convertTime, float Start, float End, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, Start, Start, End, recovery, collider, colliderJoint, path, armature);
    }

    public WeaponSpecialAnimation(float convertTime, float StopMovement, float Start, float End, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, StopMovement, Start, End, recovery, collider, colliderJoint, path, armature);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, true);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public WeaponSpecialAnimation(float convertTime, float Start, float End, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, Start, End, End, recovery, hand, collider, colliderJoint, path, armature);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, true);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }


    @Override
    public void postInit() {
        super.postInit();

        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.1f", (1.0F / this.getTotalTime())));
            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);

        boolean stiffAttack = entitypatch.getOriginal().level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS).get();

        if (!isEnd && !nextAnimation.isMainFrameAnimation() && entitypatch.isLogicalClient() && !stiffAttack) {
            float playbackSpeed = EpicFightOptions.A_TICK * this.getPlaySpeed(entitypatch, this);
            entitypatch.getClientAnimator().baseLayer.copyLayerTo(entitypatch.getClientAnimator().baseLayer.getLayer(Layer.Priority.HIGHEST), playbackSpeed);
        }
    }

    @Override
    public TypeFlexibleHashMap<StateFactor<?>> getStatesMap(LivingEntityPatch<?> entitypatch, float time) {
        TypeFlexibleHashMap<StateFactor<?>> stateMap = super.getStatesMap(entitypatch, time);

        if (!entitypatch.getOriginal().level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS).get()) {
            stateMap.put(EntityState.MOVEMENT_LOCKED, (Object)false);
            stateMap.put(EntityState.UPDATE_LIVING_MOTION, (Object)true);
        }

        return stateMap;
    }

    @Override
    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        Vec3 vec3 = super.getCoordVector(entitypatch, dynamicAnimation);

        if (entitypatch.shouldBlockMoving() && this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3 = vec3.scale(0.0F);
        }

        return vec3;
    }

    @Override
    public Optional<JointMaskEntry> getJointMaskEntry(LivingEntityPatch<?> entitypatch, boolean useCurrentMotion) {
        if (entitypatch.isLogicalClient()) {
            if (entitypatch.getClientAnimator().getPriorityFor(this) == Layer.Priority.MIDDLE) {
                return Optional.of(JointMaskEntry.BASIC_ATTACK_MASK);
            }
        }

        return super.getJointMaskEntry(entitypatch, useCurrentMotion);
    }

    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }

    @Override
    public boolean shouldPlayerMove(LocalPlayerPatch playerpatch) {
        if (playerpatch.isLogicalClient()) {
            if (!playerpatch.getOriginal().level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS).get()) {
                if (playerpatch.getOriginal().input.forwardImpulse != 0.0F || playerpatch.getOriginal().input.leftImpulse != 0.0F) {
                    return false;
                }
            }
        }

        return true;
    }
}
