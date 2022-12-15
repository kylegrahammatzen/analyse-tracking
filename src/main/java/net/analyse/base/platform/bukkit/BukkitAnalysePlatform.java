package net.analyse.base.platform.bukkit;

import net.analyse.base.AnalyseBase;
import net.analyse.base.database.AnalyseDatabase;
import net.analyse.base.platform.bukkit.configuration.Configuration;
import net.analyse.base.sdk.AnalyseSDK;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static net.analyse.base.utils.ResourceUtils.getBundledFile;

public class BukkitAnalysePlatform extends JavaPlugin implements AnalyseBase {

    private AnalyseSDK sdk;
    private AnalyseDatabase database;
    private AnalyseDatabase backupDatabase;
    private Configuration platformConfiguration;

    /**
     * Runs when the plugin is enabled
     */
    @Override
    public void onEnable() {
        getLogger().info(String.format("Enabling Analyse v%s (Bukkit)", getVersion()));

        this.platformConfiguration = new Configuration(this);

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
        return "bukkit";
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
     * Get the config
     */
    public Configuration getConfiguration() {
        return platformConfiguration;
    }

    /**
     * Registers the specified listener with the plugin manager.
     *
     * @param l the listener to register
     * @return the registered listener
     */
    public <T extends Listener> T registerEvents(T l) {
        getServer().getPluginManager().registerEvents(l, this);
        return l;
    }

    public void setSDK(AnalyseSDK analyseSDK) {
        this.sdk = analyseSDK;
    }

    /**
     * Set up the SDK
     */
    public AnalyseSDK setup(String token) {
        setSDK(new AnalyseSDK(token, getConfiguration().getEncryptionKey()));
        return getSDK();
    }
}