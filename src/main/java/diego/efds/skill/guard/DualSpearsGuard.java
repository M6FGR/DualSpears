package diego.efds.skill.guard;
import com.mojang.logging.LogUtils;
import diego.efds.gameasset.DualSpearsAnimations;
import diego.efds.gameasset.DualSpearsSkills;
import diego.efds.main.backup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.property.JointMaskReloadListener;
import yesman.epicfight.api.client.forgeevent.WeaponCategoryIconRegisterEvent;
import yesman.epicfight.api.client.model.ItemSkins;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.config.ConfigManager;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.item.EpicFightItems;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets.*;


@Mod(backup.MOD_ID)
public class DualSpearsGuard
{
    public static final String MOD_ID = "efds";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DualSpearsGuard() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        WeaponCategory.ENUM_MANAGER.registerEnumCls(MOD_ID, WeaponCategory.class);
        EpicFightItems.ITEMS.register(bus);
        bus.addListener(DualSpearsSkills::registerGuard);
        bus.addListener(DualSpearsAnimations::registerAnimations);
        bus.addListener(DualSpearsSkills::buildSkillEvent);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigManager.CLIENT_CONFIG);
//        bus.addListener(this::doCommonStuff);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {bus.addListener(backup::regIcon);});
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {bus.addListener(backup::registerRenderer);});
        bus.addListener(backup::addPackFindersEvent);

    }

    @OnlyIn(Dist.CLIENT)
    // just make an WeaponCategories guard so you just need register an icon for an WeaponCategories ,that meke it show in skill book
    public static void regIcon(WeaponCategoryIconRegisterEvent event){
        event.registerCategory(CapabilityItem.WeaponCategories.SPEAR,new ItemStack(EpicFightItems.IRON_SPEAR.get()));
//        event.registerCategory(CapabilityItem.WeaponCategories.SPEAR,new ItemStack(EpicFightItems.STONE_SPEAR.get()));
//        event.registerCategory(CapabilityItem.WeaponCategories.SPEAR,new ItemStack(EpicFightItems.GOLDEN_SPEAR.get()));
//        event.registerCategory(CapabilityItem.WeaponCategories.SPEAR,new ItemStack(EpicFightItems.DIAMOND_SPEAR.get()));
//        event.registerCategory(CapabilityItem.WeaponCategories.SPEAR,new ItemStack(EpicFightItems.NETHERITE_SPEAR.get()));
        event.registerCategory(CapabilityItem.WeaponCategories.GREATSWORD,new ItemStack(EpicFightItems.IRON_GREATSWORD.get()));
//        event.registerCategory(CapabilityItem.WeaponCategories.GREATSWORD,new ItemStack(EpicFightItems.GOLDEN_GREATSWORD.get()));
//        event.registerCategory(CapabilityItem.WeaponCategories.GREATSWORD,new ItemStack(EpicFightItems.DIAMOND_GREATSWORD.get()));
//        event.registerCategory(CapabilityItem.WeaponCategories.GREATSWORD,new ItemStack(EpicFightItems.NETHERITE_GREATSWORD.get()));
//        event.registerCategory(CapabilityItem.WeaponCategories.GREATSWORD,new ItemStack(EpicFightItems.STONE_GREATSWORD.get()));
    }



    // this is my NetworkManager register
//    private void doCommonStuff(final FMLCommonSetupEvent event) {
//        event.enqueueWork(LOGGER::registerPackets);
//    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(Event event) {
    }

    //here is meke an resourcepacks to show in game resourcepacks       just like epicfight, if you need it
    public static void addPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(backup.MOD_ID).getFile().findResource("packs/corrupt_animation");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(backup.MOD_ID).getFile().getFileName() + ":" + resourcePath, resourcePath, false);
            Pack.ResourcesSupplier resourcesSupplier = (string) -> pack;
            Pack.Info info = Pack.readPackInfo("corrupt_animation", resourcesSupplier);

            if (info != null) {
                event.addRepositorySource((source) ->
                        source.accept(Pack.create("corrupt_animation", Component.translatable("pack.corrupt_animation.title"), false, resourcesSupplier, info, PackType.CLIENT_RESOURCES, Pack.Position.TOP, false, PackSource.BUILT_IN)));
            }
        }
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(backup.MOD_ID).getFile().findResource("packs/power");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(backup.MOD_ID).getFile().getFileName() + ":" + resourcePath, resourcePath, false);
            Pack.ResourcesSupplier resourcesSupplier = (string) -> pack;
            Pack.Info info = Pack.readPackInfo("More_Power By zhong004", resourcesSupplier);

            if (info != null) {
                event.addRepositorySource((source) ->
                        source.accept(Pack.create("More_Power", Component.translatable("pack.More_Power.title"), false, resourcesSupplier, info, PackType.CLIENT_RESOURCES, Pack.Position.TOP, false, PackSource.BUILT_IN)));
            }
        }
    }

    //here is meke an resourcepacks to show in game resourcepacks       just like epicfight, if you need it
    @SubscribeEvent
    public static void registerResourcepackReloadListnerEvent(final RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new JointMaskReloadListener());
        event.registerReloadListener(Meshes.INSTANCE);
        event.registerReloadListener(AnimationManager.getInstance());
        event.registerReloadListener(ItemSkins.INSTANCE);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
    //emmmmm           this my problem here is register weaponCapabilityPreset that make datapack work,
    public static void registerGuard(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(EpicFightMod.MODID,"greatsword"), GREATSWORD);
        event.getTypeEntry().put(new ResourceLocation(EpicFightMod.MODID,"axe"), AXE);
        event.getTypeEntry().put(new ResourceLocation(EpicFightMod.MODID,"spear"), SPEAR);
    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
    public static boolean regGuarded=false;
    public static void buildSkillEvent(RegisterEvent event){
        //if(!event.getRegistryKey().equals(SkillManager.SKILL_REGISTRY_KEY)){return;}
        if (EpicFightSkills.GUARD==null){return;}
        if (regGuarded){return;}
        try {
            regGuard();
        }catch (Exception e){
            e.printStackTrace();
        }
        regGuarded=true;
    }
    public static void regGuard() throws NoSuchFieldException, IllegalAccessException {
        LOGGER.info("buildSkillEvent");
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardMotions=new HashMap<>();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardBreakMotions=new HashMap<>();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> advancedGuardMotions=new HashMap<>();

        guardMotions.put(CapabilityItem.WeaponCategories.AXE,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? Animations.SWORD_GUARD_HIT : Animations.SWORD_GUARD);
        guardBreakMotions.put(CapabilityItem.WeaponCategories.AXE,
                (item, player) ->  Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(CapabilityItem.WeaponCategories.AXE, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == CapabilityItem.Styles.TWO_HAND ?
                new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 } :
                new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 });

        guardMotions.put(CapabilityItem.WeaponCategories.SPEAR,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.TWO_HAND ? DualSpearsAnimations.SPEAR_DUAL_GUARD_HIT : DualSpearsAnimations.SPEAR_DUAL_GUARD);
        guardBreakMotions.put(CapabilityItem.WeaponCategories.SPEAR,
                (item, player) -> DualSpearsAnimations.SPEAR_DUAL_RUN);
        advancedGuardMotions.put(CapabilityItem.WeaponCategories.AXE, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == CapabilityItem.Styles.OCHS ?
                new StaticAnimation[] { DualSpearsAnimations.AXE_DUAL_PARRY_1, DualSpearsAnimations.AXE_DUAL_PARRY_2 } :
                new StaticAnimation[] { DualSpearsAnimations.AXE_DUAL_PARRY_2, Animations.SWORD_GUARD_ACTIVE_HIT3 });

        guardMotions.put(CapabilityItem.WeaponCategories.GREATSWORD,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.TWO_HAND ? Animations.GREATSWORD_GUARD_HIT : Animations.SWORD_DUAL_GUARD_HIT);
        guardBreakMotions.put(CapabilityItem.WeaponCategories.GREATSWORD,
                (item, player) ->  Animations.GREATSWORD_GUARD_BREAK);

        guardMotions.put(CapabilityItem.WeaponCategories.SPEAR,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.TWO_HAND ? Animations.SPEAR_GUARD_HIT : Animations.SPEAR_GUARD);
        guardBreakMotions.put(CapabilityItem.WeaponCategories.SPEAR,
                (item, player) ->  Animations.BIPED_COMMON_NEUTRALIZED);

        guardMotions.put(CapabilityItem.WeaponCategories.SPEAR,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.OCHS ? DualSpearsAnimations.SPEAR_DUAL_GUARD_HIT : DualSpearsAnimations.SPEAR_DUAL_GUARD);
        guardBreakMotions.put(CapabilityItem.WeaponCategories.SPEAR,
                (item, player) ->  Animations.BIPED_COMMON_NEUTRALIZED);


        Field temp;
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> target;
        temp=GuardSkill.class.getDeclaredField("guardMotions");
        temp.setAccessible(true);
        target= (Map) temp.get(EpicFightSkills.GUARD);
        for (WeaponCategory weaponCapability:guardMotions.keySet()){
            target.put(weaponCapability,guardMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.PARRYING);
        for (WeaponCategory weaponCapability:guardMotions.keySet()){
            target.put(weaponCapability,guardMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.IMPACT_GUARD);
        for (WeaponCategory weaponCapability:guardMotions.keySet()){
            target.put(weaponCapability,guardMotions.get(weaponCapability));
        }


        temp=GuardSkill.class.getDeclaredField("guardBreakMotions");
        temp.setAccessible(true);
        target= (Map) temp.get(EpicFightSkills.GUARD);
        for (WeaponCategory weaponCapability:guardBreakMotions.keySet()){
            target.put(weaponCapability,guardBreakMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.PARRYING);
        for (WeaponCategory weaponCapability:guardBreakMotions.keySet()){
            target.put(weaponCapability,guardBreakMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.IMPACT_GUARD);
        for (WeaponCategory weaponCapability:guardBreakMotions.keySet()){
            target.put(weaponCapability,guardBreakMotions.get(weaponCapability));
        }


        temp=GuardSkill.class.getDeclaredField("advancedGuardMotions");
        temp.setAccessible(true);
        target=(Map) temp.get(EpicFightSkills.PARRYING);
        for (WeaponCategory weaponCapability:advancedGuardMotions.keySet()){
            target.put(weaponCapability,advancedGuardMotions.get(weaponCapability));
        }
    }
}