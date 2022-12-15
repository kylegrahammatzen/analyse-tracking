package net.analyse.base.platform.bukkit.event;

import net.analyse.base.platform.bukkit.BukkitAnalysePlatform;
import net.analyse.base.sdk.exception.ServerNotFoundException;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class ServerHeartbeatEvent implements Runnable {

    private final BukkitAnalysePlatform platform;

    public ServerHeartbeatEvent(final @NotNull BukkitAnalysePlatform platform) {
        this.platform = platform;
    }

    @Override
    public void run() {
        if (!platform.getConfiguration().isSetup()) return;

        try {
            platform.getSDK().sendHeartbeat(Bukkit.getOnlinePlayers().size());
        } catch (ServerNotFoundException e) {
            platform.getConfiguration().setSetup(false);
            platform.getLogger().warning("The server specified no longer exists.");
        }
    }
}