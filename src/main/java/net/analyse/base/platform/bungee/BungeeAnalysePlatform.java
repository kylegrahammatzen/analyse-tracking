package net.analyse.base.platform.bungee;

import net.analyse.base.AnalyseBase;
import net.analyse.base.database.AnalyseDatabase;
import net.analyse.base.sdk.AnalyseSDK;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.io.File;
import java.io.IOException;

import static net.analyse.base.utils.ResourceUtils.getBundledFile;

public class BungeeAnalysePlatform extends Plugin implements AnalyseBase {

    private AnalyseSDK sdk;
    private AnalyseDatabase database;
    private AnalyseDatabase backupDatabase;

    /**
     * Runs when the plugin is enabled
     */
    @Override
    public void onEnable() {
        getLogger().info(String.format("Enabling Analyse v%s (Bungee)", getVersion()));

        try {
            loadConfig();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    @Override
    public AnalyseSDK getSDK() {
        return sdk;
    }

    /**
     * Get the plugin directory
     * @return The plugin directory
     */
    @Override
    public File getDirectory() {
        return getDataFolder();
    }

    /**
     * Get the plugin platform
     * @return The platform
     */
    @Override
    public String getPlatformName() {
        return "bungee";
    }

    /**
     * Get the version of the plugin
     * @return The version of the plugin
     */
    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    /**
     * Loads the config file
     * @throws IOException If the config file could not be loaded
     */
    @Override
    public void loadConfig() throws IOException {
        // Retrieve the config file
        TypeSerializerCollection serializerCollection = TypeSerializerCollection.create();

        ConfigurationOptions options = ConfigurationOptions.defaults()
                .withSerializers(serializerCollection);

        ConfigurationNode configFile = YAMLConfigurationLoader.builder()
                .setDefaultOptions(options)
                .setFile(getBundledFile(getLogger(), getDirectory(), getPlatformName(), "config.yml"))
                .build()
                .load();

        // Load the config
    }

    /**
     * Reloads the config
     */
    @Override
    public void reloadConfig() {
        try {
            loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers the specified listener with the plugin manager.
     *
     * @param l the listener to register
     * @return the registered listener
     */
    public <T extends Listener> T registerEvents(T l) {
        ProxyServer.getInstance().getPluginManager().registerListener(this, l);
        return l;
    }
}
