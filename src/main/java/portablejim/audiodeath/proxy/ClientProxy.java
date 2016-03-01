package portablejim.audiodeath.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;

import java.io.File;

@SuppressWarnings("UnusedDeclaration")
public class ClientProxy implements IProxy {
    private static int audio = 0;

    @Override
    public File getMinecraftDir() {
        return Minecraft.getMinecraft().mcDataDir;
    }

    @Override
    public void handleDeath(GuiOpenEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
        ResourceLocation deathSoundAudioResource = new ResourceLocation("audiodeath:audiodeath.death");
        ISound deathSound = PositionedSoundRecord.create(deathSoundAudioResource, 1.0F);
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
