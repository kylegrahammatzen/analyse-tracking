package net.analyse.base.platform.bukkit.listener;

import net.analyse.base.platform.bukkit.BukkitAnalysePlatform;
import org.bukkit.event.Listener;

public class PlayerActivityListener implements Listener {
    private final BukkitAnalysePlatform platform;

    public PlayerActivityListener(BukkitAnalysePlatform bukkitAnalysePlatform) {
        this.platform = bukkitAnalysePlatform;
    }
}
