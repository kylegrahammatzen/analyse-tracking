package net.analyse.platform.bukkit;

import net.analyse.core.AnalyseCore;
import net.analyse.core.Platform;
import net.analyse.platform.bukkit.configuration.BukkitConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BukkitPlatform extends JavaPlugin  {
    private final Platform PLATFORM = Platform.BUKKIT;
    private AnalyseCore analyseCore;
    private BukkitConfiguration bukkitConfiguration;

    /**
     * Called when the plugin is enabled
     */
    @Override
    public void onEnable() {

        getLogger().info("    _   ");
        getLogger().info("   / \\  ");
        getLogger().info(String.format("  / _ \\     Analyse v%s", getDescription().getVersion()));
        getLogger().info(String.format(" / ___ \\    Running on platform %s", PLATFORM.name().toLowerCase().replaceFirst(".", (PLATFORM.name().substring(0, 1)).toUpperCase())));
        getLogger().info("/_/   \\_\\");
        getLogger().info(" ");

        // Initialise the configuration
        this.bukkitConfiguration = new BukkitConfiguration(getLogger(), PLATFORM, getDataFolder());

        // Load the configuration
        try {
            this.bukkitConfiguration.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }

        // Load commands

        // Load events

        // Register heartbeat task.

        // Test the encryption key.
    }

    /**
     * Called when the plugin is disabled
     */
    @Override
    public void onDisable() {
    }

    /**
     * Get the platform this plugin is running on.
     * @return The platform.
     */
    public Platform getPlatform() {
        return PLATFORM;
    }

    /**
     * Get the configuration for this plugin.
     * @return The configuration.
     */
    public BukkitConfiguration getConfiguration() {
        return bukkitConfiguration;
    }

    /**
     * Get the core of the plugin.
     * @return The core.
     */
    public AnalyseCore getAnalyseCore() {
        return analyseCore;
    }
}