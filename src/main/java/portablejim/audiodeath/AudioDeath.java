package portablejim.audiodeath;

import com.google.common.io.Files;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import portablejim.audiodeath.proxy.IProxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

@Mod(modid = AudioDeath.MODID, acceptedMinecraftVersions = "[1.9,1.13)")
public class AudioDeath
{
    public static final String MODID = "audiodeath";

    @SidedProxy(clientSide = "portablejim.audiodeath.proxy.ClientProxy", serverSide = "portablejim.audiodeath.proxy.ServerProxy")
    public static IProxy proxy;
    public static Logger modLogger;

    
    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        if(event.getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(this);
        }

        modLogger = event.getModLog();

        File additionalResourcesFolder = new File(proxy.getMinecraftDir(), "mods-resourcepacks");
        File audioDeathFolder = new File(additionalResourcesFolder, MODID);
        File soundsFolder = new File(audioDeathFolder, "sounds");
        try {
            if(!soundsFolder.exists()) {
                //noinspection ResultOfMethodCallIgnored
                soundsFolder.mkdirs();
            }
            if(event.getSide() == Side.CLIENT) {
                File soundsFile = new File(audioDeathFolder, "sounds.json");
                if(!soundsFile.exists()) {
                    AudioDeath.copyFileFromLocation(new File(audioDeathFolder, "sounds.json"), "/initial/sounds.json");
                    AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-gameover.ogg"), "/initial/sounds/death-gameover.ogg");
                    AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-haha.ogg"), "/initial/sounds/death-haha.ogg");
                    AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-no.ogg"), "/initial/sounds/death-no.ogg");
                    AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-seethatcoming.ogg"), "/initial/sounds/death-seethatcoming.ogg");
                    AudioDeath.copyFileFromLocation(new File(soundsFolder, "death-youmessedup.ogg"), "/initial/sounds/death-youmessedup.ogg");
                }
            }
        }
        catch (Exception e) {
            event.getModLog().error("Error creating required files and directories!", e);
        }
    }

    public static void copyFileFromLocation(File output, String inputLocation) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(output);
        InputStream input = AudioDeath.class.getResourceAsStream(inputLocation);

        IOUtils.copy(input, outputStream);

        input.close();
        outputStream.close();
    }

    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void deathScreen(GuiOpenEvent event) {
        proxy.handleDeath(event);
    }
}
