package diego.efds.world.capabilites.item;

import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;
import diego.efds.gameasset.DualSpearsAnimations;
import diego.efds.gameasset.DualSpearsCollider;
import diego.efds.gameasset.DualSpearsSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;



@EventBusSubscriber(
        modid = "efds",
        bus = Bus.MOD
)
@SuppressWarnings("")//
public class WeaponCapabilityPresets {
    public static final Function<Item, CapabilityItem.Builder> SPEAR = (item) -> {
        CapabilityItem.Builder builder = WeaponCapability.builder()
                .category(WeaponCategories.SPEAR)
                .styleProvider((playerpatch) -> {
                    return  playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND)
                            .getWeaponCategory() == WeaponCategories.SPEAR && ((PlayerPatch) playerpatch)
                            .getSkill(DualSpearsSkills.DUALSPEAR) != null && ((PlayerPatch) playerpatch)
                            .getSkill(DualSpearsSkills.DUALSPEAR)
                            .getSkill().getRegistryName()
                            .getPath() == "dualspear" ? Styles.OCHS : Styles.TWO_HAND;
                })

                .collider(DualSpearsCollider.SPEAR)

                .newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.SPEAR_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH})
                .innateSkill(Styles.TWO_HAND, (itemstack) -> EpicFightSkills.GRASPING_SPIRE)

                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_SPEAR)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_SPEAR)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_WALK_SPEAR)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD)

                .newStyleCombo(Styles.OCHS, new StaticAnimation[]{DualSpearsAnimations.SPEAR_DUAL_AUTO_1, DualSpearsAnimations.SPEAR_DUAL_AUTO_2, DualSpearsAnimations.SPEAR_DUAL_AUTO_3, DualSpearsAnimations.SPEAR_DUAL_AUTO_4, DualSpearsAnimations.SPEAR_DUAL_DASH, DualSpearsAnimations.SPEAR_DUAL_AIRSLASH})
                .innateSkill(Styles.OCHS, (itemstack) -> DualSpearsSkills.CRIMSON_SLAUGHTER)

                .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, DualSpearsAnimations.SPEAR_DUAL_IDLE)
                .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, DualSpearsAnimations.SPEAR_DUAL_WALK)
                .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, DualSpearsAnimations.SPEAR_DUAL_IDLE)
                .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, DualSpearsAnimations.SPEAR_DUAL_RUN)
                .livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, DualSpearsAnimations.SPEAR_DUAL_RUN)
                .livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD_HIT)

                .weaponCombinationPredicator((entitypatch) -> {
                    return entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND)
                            .getWeaponCategory() == WeaponCategories.SPEAR && ((PlayerPatch) entitypatch)
                            .getSkill(DualSpearsSkills.DUALSPEAR) != null && ((PlayerPatch) entitypatch)
                            .getSkill(DualSpearsSkills.DUALSPEAR).getSkill().getRegistryName().getPath() == "dualspear" ? true : true;
                });

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> AXE = (item) -> {
        CapabilityItem.Builder builder = WeaponCapability.builder().category(WeaponCategories.AXE).styleProvider((playerpatch) -> {
                    return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND)
                            .getWeaponCategory() == WeaponCategories.AXE ? Styles.TWO_HAND : Styles.ONE_HAND;
                })
                .collider(ColliderPreset.TOOLS)
                .newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH})
                .innateSkill(Styles.ONE_HAND, (itemstack) -> {
                    return EpicFightSkills.GUILLOTINE_AXE;
                })
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_IDLE)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_RUN)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, Animations.BIPED_SNEAK)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_SWIM)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)

                .collider(ColliderPreset.TOOLS)
                .newStyleCombo(Styles.TWO_HAND, DualSpearsAnimations.AXE_DUAL_AUTO_1, DualSpearsAnimations.AXE_DUAL_AUTO_2, DualSpearsAnimations.AXE_DUAL_AUTO_3, DualSpearsAnimations.AXE_DUAL_DASH, DualSpearsAnimations.AXE_DUAL_AIRSLASH)
                .innateSkill(Styles.TWO_HAND, (itemstack) -> EpicFightSkills.RELENTLESS_COMBO)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, DualSpearsAnimations.AXE_DUAL_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, DualSpearsAnimations.AXE_DUAL_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, DualSpearsAnimations.AXE_DUAL_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, DualSpearsAnimations.AXE_DUAL_RUN)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, DualSpearsAnimations.AXE_DUAL_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, DualSpearsAnimations.AXE_DUAL_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, DualSpearsAnimations.AXE_DUAL_GUARD)
                .weaponCombinationPredicator((entitypatch) -> {
                    return entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND)
                            .getWeaponCategory() == WeaponCategories.AXE && ((PlayerPatch) entitypatch)
                            .getSkill(DualSpearsSkills.DUALAXE) != null && ((PlayerPatch) entitypatch)
                            .getSkill(DualSpearsSkills.DUALAXE).getSkill().getRegistryName().getPath() == "dualaxe" ? true : false;
                });
        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> GREATSWORD = (item) -> {
        CapabilityItem.Builder builder = WeaponCapability.builder().category(WeaponCategories.GREATSWORD).styleProvider((playerpatch) -> {
                    return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND)
                            .getWeaponCategory() == WeaponCategories.GREATSWORD && ((PlayerPatch)playerpatch)
                            .getSkill(DualSpearsSkills.DUALGREATSWORD) != null && ((PlayerPatch)playerpatch)
                            .getSkill(DualSpearsSkills.DUALGREATSWORD).getSkill().getRegistryName().getPath() == "dualgreatsword" ? Styles.OCHS : Styles.TWO_HAND;
                    })

                    .collider(ColliderPreset.GREATSWORD)

                    .newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH})
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> {
                        return EpicFightSkills.STEEL_WHIRLWIND;
                    })
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)

                    .newStyleCombo(Styles.OCHS, new StaticAnimation[]{DualSpearsAnimations.GREATSWORD_DUAL_AUTO_1, DualSpearsAnimations.GREATSWORD_DUAL_AUTO_2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH})
                    .innateSkill(Styles.OCHS, (itemstack) -> {
                        return EpicFightSkills.STEEL_WHIRLWIND;
                    })
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, DualSpearsAnimations.GREATSWORD_DUAL_IDLE)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, DualSpearsAnimations.GREATSWORD_DUAL_WALK)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, DualSpearsAnimations.GREATSWORD_DUAL_IDLE)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, DualSpearsAnimations.SPEAR_DUAL_RUN)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, DualSpearsAnimations.SPEAR_DUAL_RUN)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_SPEAR)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_SPEAR)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, DualSpearsAnimations.GREATSWORD_DUAL_GUARD)
                    .weaponCombinationPredicator((entitypatch) -> {
                        return entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND)
                                .getWeaponCategory() == WeaponCategories.GREATSWORD && ((PlayerPatch) entitypatch)
                                .getSkill(DualSpearsSkills.DUALGREATSWORD) != null && ((PlayerPatch) entitypatch)
                                .getSkill(DualSpearsSkills.DUALGREATSWORD).getSkill().getRegistryName().getPath() == "dualgreatsword" ? true : true;
                    });
            return builder;
    };
    public WeaponCapabilityPresets() {
    }
    private static boolean CheckPlayer(LivingEntityPatch<?> playerPatch) {
        return playerPatch.getOriginal().getType() != EntityType.PLAYER;
    }

    private static final Map<String, Function<Item, CapabilityItem.Builder>> PRESETS = Maps.newHashMap();
    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation("epicfight", "spear"), SPEAR);
        event.getTypeEntry().put(new ResourceLocation("epicfight", "axe"), AXE);
        event.getTypeEntry().put(new ResourceLocation("epicfight", "greatsword"), GREATSWORD);

    }
}