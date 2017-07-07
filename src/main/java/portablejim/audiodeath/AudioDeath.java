package portablejim.audiodeath;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;
import portablejim.audiodeath.proxy.IProxy;

import java.io.File;
import java.io.FileOutputStream;

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
        }
        catch (Exception e) {
            event.getModLog().error("Error creating required files and directories!", e);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void deathScreen(GuiOpenEvent event) {
        proxy.handleDeath(event);
    }
}
