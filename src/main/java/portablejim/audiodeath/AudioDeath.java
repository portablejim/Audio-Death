package portablejim.audiodeath;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = AudioDeath.MODID)
public class AudioDeath
{
    public static final String MODID = "audiodeath";

    private int audio = 0;
    
    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        if(event.getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void deathScreen(GuiOpenEvent event) {
        ResourceLocation deathSoundAudioResource = new ResourceLocation("audiodeath:audiodeath.death");
        ISound deathSound = PositionedSoundRecord.func_147674_a(deathSoundAudioResource, 1.0F);
        if(event.gui instanceof GuiGameOver) {
            if(audio == 0) {
                Minecraft.getMinecraft().getSoundHandler().playSound(deathSound);
                audio = 1;
            }
        }
        else {
            Minecraft.getMinecraft().getSoundHandler().stopSound(deathSound);
            audio = 0;
        }
    }
}
