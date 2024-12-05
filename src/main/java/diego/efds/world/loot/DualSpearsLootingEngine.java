package diego.efds.world.loot;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.api.forgeevent.SkillLootTableRegistryEvent;
import yesman.epicfight.config.ConfigManager;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.world.item.EpicFightItems;

@EventBusSubscriber(
        modid = "efds"
)
public class DualSpearsLootingEngine {
    public DualSpearsLootingEngine() {
    }

    @SubscribeEvent
    public static void SkillLootDrop(SkillLootTableRegistryEvent event) {
        int modifier = ConfigManager.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
        int dropChance = 100 + modifier;
        int antiDropChance = 100 - modifier;
        float dropChanceModifier = (antiDropChance == 0) ? Float.MAX_VALUE : dropChance / (float)antiDropChance;
        event.add(EntityType.ZOMBIE, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(
                        LootItemRandomChanceCondition.randomChance(0.040F * dropChanceModifier))
                .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(
                                1.0F, "efds:dualaxe",
                                1.0F, "efds:dualspear",
                                1.0F, "efds:dualgreatsword"


                        ))
                )).add(EntityType.HUSK,
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.025F * dropChanceModifier))
                        .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(
                                1.0F, "efds:dualaxe",
                                1.0F, "efds:dualspear",
                                1.0F, "efds:dualgreatsword"
                                ))
                        )).add(EntityType.SKELETON,
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.025F * dropChanceModifier))
                        .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(
                                1.0F, "efds:dualaxe",
                                1.0F, "efds:dualspear",
                                1.0F, "efds:dualgreatsword"
                                ))
                        )).add(EntityType.SPIDER,
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.025F * dropChanceModifier))
                        .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(
                                1.0F, "efds:dualaxe",
                                1.0F, "efds:dualspear",
                                1.0F, "efds:dualgreatsword"
                                ))
                        )).add(EntityType.CREEPER,
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.025F * dropChanceModifier))
                        .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(
                                1.0F, "efds:dualaxe",
                                1.0F, "efds:dualspear",
                                1.0F, "efds:dualgreatsword"
                                ))
                        )).add(EntityType.ENDERMAN,
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.025F * dropChanceModifier))
                        .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(
                                        1.0F, "efds:dualaxe",
                                        1.0F, "efds:dualspear",
                                        1.0F, "efds:dualgreatsword"
                        ))
                        ));
    }
}
