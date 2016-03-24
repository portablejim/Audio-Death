package portablejim.audiodeath.proxy;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import portablejim.audiodeath.AudioDeath;

import java.io.File;

@SuppressWarnings("UnusedDeclaration")
public class ServerProxy implements IProxy {
    public File getMinecraftDir() {
        return null;
    }

    @Override
    public void handleDeath(GuiOpenEvent event) {
        AudioDeath.modLogger.info("Somebody died. However as this is a client-only mod, this mod does nothing.");
    }
}
