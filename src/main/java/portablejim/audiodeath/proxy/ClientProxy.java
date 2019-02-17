package portablejim.audiodeath.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.GuiOpenEvent;
import portablejim.audiodeath.AudioDeath;

import java.io.File;

@SuppressWarnings("UnusedDeclaration")
public class ClientProxy implements IProxy {
    private static int audio = 0;
    private ISound deathSound;

    public ClientProxy() {
        ResourceLocation deathSoundAudioResource = new ResourceLocation("audiodeath", "audiodeath.death");
        deathSound = new SimpleSound(deathSoundAudioResource, SoundCategory.RECORDS, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0F, 0F, 0F);
    }

    @Override
    public File getMinecraftDir() {
        return Minecraft.getInstance().getFileResourcePacks().getParentFile();
    }

    @Override
    public void handleDeath(GuiOpenEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
        EntityPlayerSP entityPlayerSP = Minecraft.getInstance().player;
        if(event.getGui() instanceof GuiGameOver && Minecraft.getInstance().currentScreen == null && entityPlayerSP.isAlive()) {
            if(audio == 0) {
                AudioDeath.LOGGER.info("Playing deathsound");
                Minecraft.getInstance().getSoundHandler().play(deathSound);

                entityPlayerSP.playSound(AudioDeath.deathSoundEvent, 1f, 1f);
                audio = 1;
            }
        }
        else {
            //noinspection ConstantConditions
            if(Minecraft.getInstance().getSoundHandler() != null && deathSound != null)
            {
                if(audio > 0)
                {
                    AudioDeath.LOGGER.info("Stopping deathsound");
                }
                Minecraft.getInstance().getSoundHandler().stop(deathSound);
            }
            audio = 0;
        }
    }
}
