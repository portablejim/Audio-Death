package portablejim.audiodeath;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.ForgeClientHandler;
import net.minecraftforge.client.event.sound.SoundSetupEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import portablejim.audiodeath.proxy.ClientProxy;
import portablejim.audiodeath.proxy.IProxy;
import portablejim.audiodeath.proxy.ServerProxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

        LOGGER.warn("Loading sounds");
        File additionalResourcesFolder = new File(proxy.getMinecraftDir(), "mods-resourcepacks");
        File audioDeathFolder = new File(additionalResourcesFolder, MODID);
        File soundsFolder = new File(audioDeathFolder, "sounds");
        try {
            if(!soundsFolder.exists()) {
                //noinspection ResultOfMethodCallIgnored
                soundsFolder.mkdirs();
            }

            InitialFiles initialFiles = new InitialFiles();

            File soundsFile = new File(audioDeathFolder, "sounds.json");
            if(!soundsFile.exists()) {
                AudioDeath.copyFileFromLocation(new File(audioDeathFolder, "sounds.json"), initialFiles.sounds_json);
                AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-gameover.ogg"), initialFiles.death_gameover);
                AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-haha.ogg"), initialFiles.death_haha);
                AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-no.ogg"), initialFiles.death_no);
                AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-seethatcoming.ogg"), initialFiles.death_seethatcoming);
                AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-youmessedup.ogg"), initialFiles.death_youmessedup);
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

    public static void copyFileFromLocation(File output, String base64In) throws IOException {
        byte[] decodedBytes = Base64.decodeBase64(base64In);
        FileOutputStream outputStream = new FileOutputStream(output);
        outputStream.write(decodedBytes);
        outputStream.close();
    }

}
