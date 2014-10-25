package portablejim.audiodeath.proxy;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.GuiOpenEvent;
import portablejim.audiodeath.AudioDeath;

import java.io.File;

@SuppressWarnings("UnusedDeclaration")
public class ServerProxy implements IProxy {
    public File getMinecraftDir() {
        return MinecraftServer.getServer().getFile(".");
    }

    @Override
    public void handleDeath(GuiOpenEvent event) {
        AudioDeath.modLogger.info("Somebody died. However as this is a client-only mod, this mod does nothing.");
    }
}
