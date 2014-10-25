package portablejim.audiodeath.proxy;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.GuiOpenEvent;

import java.io.File;

@SuppressWarnings("UnusedDeclaration")
public class ServerProxy implements IProxy {
    public File getMinecraftDir() {
        return MinecraftServer.getServer().getFile(".");
    }

    @Override
    public void handleDeath(GuiOpenEvent event) { }
}
