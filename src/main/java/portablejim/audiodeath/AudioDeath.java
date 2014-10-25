package portablejim.audiodeath;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.resources.ResourcePack;
import net.minecraft.client.resources.ResourcePackRepositoryEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

import java.util.List;

@Mod(modid = AudioDeath.MODID)
public class AudioDeath
{
    public static final String MODID = "audiodeath";

    private boolean audio = true;
    
    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        if(event.getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @ForgeSubscribe
    public void onSoundsLoaded(SoundLoadEvent event) {
        List repositoryEntries = Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries();
        for(Object entry : repositoryEntries.toArray()) {
            if(entry instanceof ResourcePackRepositoryEntry) {
                ResourcePackRepositoryEntry repositoryEntry = (ResourcePackRepositoryEntry) entry;
                ResourcePack r = repositoryEntry.getResourcePack();
                if(r.resourceExists(new ResourceLocation(MODID, "sound/deathsound.ogg"))) {
                    event.manager.addSound(MODID + ":deathsound.ogg");
                }
                for(int i = 1; i < 10; i++) {
                    if(r.resourceExists(new ResourceLocation(MODID, "sound/deathsound" + i + ".ogg"))) {
                        event.manager.addSound(MODID + ":deathsound" + i + ".ogg");
                    }
                }
            }
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    @ForgeSubscribe
    public void deathScreen(GuiOpenEvent event) {
        if(event.gui instanceof GuiGameOver) {
            if(audio) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                //player.playSound(String.format("%s:deathsound", MODID), 1.0F, 1.0F);
                Minecraft.getMinecraft().sndManager.playSoundFX(String.format("%s:deathsound", MODID), 1.0F, 1.0F);
                audio = false;
            }
        }
        else {
            audio = true;
        }
    }
}
