package portablejim.audiodeath.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.GuiOpenEvent;

import java.io.File;

@SuppressWarnings("UnusedDeclaration")
public class ClientProxy implements IProxy {
    private static int audio = 0;
    private ISound deathSound;

    public ClientProxy() {
        ResourceLocation deathSoundAudioResource = new ResourceLocation("audiodeath:audiodeath.death");
        deathSound = new PositionedSoundRecord(deathSoundAudioResource, SoundCategory.RECORDS, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0F, 0F, 0F);
    }

    @Override
    public File getMinecraftDir() {
        return Minecraft.getMinecraft().mcDataDir;
    }

    @Override
    public void handleDeath(GuiOpenEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
        if(event.getGui() instanceof GuiGameOver && Minecraft.getMinecraft().currentScreen == null && !Minecraft.getMinecraft().thePlayer.isDead) {
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
