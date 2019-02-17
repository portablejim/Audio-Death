package portablejim.audiodeath.proxy;

import net.minecraftforge.client.event.GuiOpenEvent;

import java.io.File;

@SuppressWarnings("UnusedDeclaration")
public class ServerProxy implements IProxy {
    public File getMinecraftDir() {
        return null;
    }

    @Override
    public void handleDeath(GuiOpenEvent event) {
    }
}
