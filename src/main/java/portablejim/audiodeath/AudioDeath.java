package portablejim.audiodeath;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.ForgeClientHandler;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import portablejim.audiodeath.proxy.ClientProxy;
import portablejim.audiodeath.proxy.IProxy;
import portablejim.audiodeath.proxy.ServerProxy;

import java.io.File;
import java.io.FileOutputStream;

@Mod(AudioDeath.MODID)
public class AudioDeath
{
    public static final String MODID = "audiodeath";
    public static final Logger LOGGER = LogManager.getLogger();
    public static SoundEvent deathSoundEvent;

    public AudioDeath()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientInit);
        MinecraftForge.EVENT_BUS.addListener(this::deathScreen);
    }

    IProxy proxy;

    public void clientInit(FMLClientSetupEvent event)
    {
        proxy = new ClientProxy();

        File additionalResourcesFolder = new File(proxy.getMinecraftDir(), "mods-resourcepacks");
        File audioDeathFolder = new File(additionalResourcesFolder, MODID);
        File soundsFolder = new File(audioDeathFolder, "sounds");
        try {
            if(!soundsFolder.exists()) {
                //noinspection ResultOfMethodCallIgnored
                soundsFolder.mkdirs();
            }

            String soundsJson = "" +
                    "{\n" +
                    "  \"audiodeath.death\": {\n" +
                    "    \"category\": \"record\",\n" +
                    "    \"sounds\": [ \"audiodeath:deathSound\" ]\n" +
                    "  }\n" +
                    "}";
            File soundsFile = new File(audioDeathFolder, "sounds.json");
            if(!soundsFile.exists()) {
                FileOutputStream soundsFileStream = new FileOutputStream(soundsFile);
                soundsFileStream.write(soundsJson.getBytes("utf-8"));
                soundsFileStream.close();
            }
        }
        catch (Exception e) {
            LOGGER.error("Error creating required files and directories!", e);
        }
    }

    public void serverInit(FMLDedicatedServerSetupEvent event)
    {
        proxy = new ServerProxy();
    }

    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void deathScreen(GuiOpenEvent event) {
        if(proxy != null)
        {
            proxy.handleDeath(event);
        }
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD event bus
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<SoundEvent> soundEventRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");

            ResourceLocation deathSoundAudioResource = new ResourceLocation("audiodeath", "audiodeath.death");
            deathSoundEvent = new SoundEvent(deathSoundAudioResource);
            deathSoundEvent.setRegistryName(deathSoundAudioResource);
            soundEventRegistryEvent.getRegistry().register(deathSoundEvent);
        }
    }
}
