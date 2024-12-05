package diego.efds.gameasset;

import diego.efds.main.backup;
import diego.efds.skill.passive.DualGreatSwordSkill;
import net.minecraftforge.eventbus.api.Event;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent.ModRegistryWorker;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import diego.efds.skill.passive.DualSpearSkill;
import diego.efds.skill.passive.DualAxeSkill;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;


@EventBusSubscriber
      (modid = backup.MOD_ID, bus= EventBusSubscriber.Bus.MOD)



public class DualSpearsSkills {
    public static Skill CRIMSON_SLAUGHTER;
    public static Skill DEATH_TORNADO;
    public static Skill CHAOS_WAVE;
    public static Skill DUALSPEAR;
    public static Skill DUALAXE;
    public static Skill DUALGREATSWORD;




    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build) {
        ModRegistryWorker modRegistry = build.createRegistryWorker(backup.MOD_ID);
        WeaponInnateSkill slash = modRegistry.build("slash", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) DualSpearsAnimations.SPEAR_DUAL_SLASH));
        slash.newProperty()
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        CRIMSON_SLAUGHTER = slash;

                WeaponInnateSkill tornado = modRegistry.build("tornado", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) DualSpearsAnimations.SPEAR_DUAL_SLASH));
        tornado.newProperty()
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        DEATH_TORNADO = tornado;


        DUALSPEAR = modRegistry.build("dualspear", DualSpearSkill::new, PassiveSkill.createPassiveBuilder());
        DUALAXE = modRegistry.build("dualaxe", DualAxeSkill::new, PassiveSkill.createPassiveBuilder());
        DUALGREATSWORD = modRegistry.build("dualgreatsword", DualGreatSwordSkill::new, PassiveSkill.createPassiveBuilder());
    }

    public static void registerGuard(Event event) {
    }
}












