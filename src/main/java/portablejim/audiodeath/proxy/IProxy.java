package portablejim.audiodeath.proxy;

import net.minecraftforge.client.event.GuiOpenEvent;

import java.io.File;

public interface IProxy {
    public File getMinecraftDir();
    public void handleDeath(GuiOpenEvent event);
}
