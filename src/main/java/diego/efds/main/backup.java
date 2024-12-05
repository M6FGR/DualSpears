package diego.efds.main;

import diego.efds.gameasset.DualSpearsAnimations;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Mod(backup.MOD_ID)
public class backup {
    public static final String MOD_ID = "efds";
    public static final String CONFIG_FILE_PATH = "efds.toml";
    public static final Logger LOGGER = LogManager.getLogger("efds");

    public backup() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(DualSpearsAnimations::registerAnimations);;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void registerGuard(Event event) {
    }

    public static void regIcon(Event event) {
    }

    public static void registerRenderer(Event event) {
    }

    public static void addPackFindersEvent(Event event) {
    }
}