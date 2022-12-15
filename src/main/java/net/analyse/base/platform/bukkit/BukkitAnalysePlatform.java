package net.analyse.base.platform.bukkit;

import net.analyse.base.AnalyseBase;

import net.analyse.base.platform.bukkit.commands.AnalyseCommand;
import net.analyse.base.platform.bukkit.configuration.Configuration;
import net.analyse.base.platform.bukkit.event.ServerHeartbeatEvent;
import net.analyse.base.platform.bukkit.listener.PlayerActivityListener;
import net.analyse.base.sdk.AnalyseSDK;

import net.analyse.base.sdk.response.GetPluginResponse;
import net.analyse.base.utils.player.AnalysePlayer;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static net.analyse.base.utils.encryption.EncryptUtil.generateEncryptionKey;
import static net.analyse.base.utils.resources.ResourceUtil.getBundledFile;

public class BukkitAnalysePlatform extends JavaPlugin implements AnalyseBase {

    private AnalyseSDK sdk;
    private Configuration platformConfiguration;

    /**
     * Runs when the plugin is enabled
     */
    @Override
    public void onEnable() {
        getLogger().info(String.format("Enabling Analyse v%s (Bukkit)", getVersion()));

        // Initialise the configuration
        this.platformConfiguration = new Configuration(this);

        // Load the configuration
        try {
            loadConfig();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }

        // Load commands/events
        AnalyseCommand baseCommand = new AnalyseCommand(this);
        PluginCommand basePluginCommand = getCommand("analyse");
        if (basePluginCommand != null) {
            basePluginCommand.setExecutor(baseCommand);
            basePluginCommand.setTabCompleter(baseCommand);
        }

        // Register events
        registerEvents(new PlayerActivityListener(this));

        // Register the heartbeat event
        ServerHeartbeatEvent serverHeartBeatEvent = new ServerHeartbeatEvent(this);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, serverHeartBeatEvent, 0, 20 * 10);

        // Test the encryption key
        if (platformConfiguration.getEncryptionKey() == null || platformConfiguration.getEncryptionKey().isEmpty()) {
            platformConfiguration.setEncryptionKey(generateEncryptionKey(64));
            try {
                platformConfiguration.save();
            } catch (IOException e) {
                throw new RuntimeException("Failed to save new encryption key", e);
            }
        }

        // Register the SDK
        if (!platformConfiguration.isSetup()) {
            getLogger().info("Hey! I'm not yet set-up, please run the following command:");
            getLogger().info("/analyse setup <server-token>");
        } else {
            getLogger().info("I'm all set-up! I'll start sending data to the Analyse server now.");

//            try {
//                getLogger().info(String.format("Linked to server %s", sdk.getServer().getName()));
//            } catch (ServerNotFoundException ex) {
//                getLogger().warning("The server linked no longer exists.");
//            }
        }

        if (sdk != null) {
            GetPluginResponse corePluginVersion = sdk.getPluginVersion();
            if(corePluginVersion.getVersionNumber() > platformConfiguration.getIncrementalVersion()) {
                getLogger().warning(String.format("This server is running v%s, an outdated version of Analyse.", getDescription().getVersion()));
                getLogger().warning(String.format("Download v%s at: %s", corePluginVersion.getVersionName(), corePluginVersion.getBukkitDownload()));
            }
        }
    }

    /**
     * Get the SDK
     * @return The SDK
     */
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
        platformConfiguration.load(configFile);
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
     * @param l the listener to register
     */
    public <T extends Listener> void registerEvents(T l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

    public void setSDK(AnalyseSDK analyseSDK) {
        this.sdk = analyseSDK;
    }

    /**
     * Set up the SDK
     * @param token The server token
     */
    public AnalyseSDK setup(String token) {
        setSDK(new AnalyseSDK(token, getConfiguration().getEncryptionKey()));
        return getSDK();
    }

    @Override
    public List<UUID> getOnlinePlayers() {
        return null;
    }

    @Override
    public List<AnalysePlayer> getOnlineAnalysePlayers() {
        return null;
    }

    @Override
    public AnalysePlayer getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public AnalysePlayer getPlayer(String name) {
        return null;
    }

    @Override
    public List<AnalysePlayer> getPlayersByCountry(String country) {
        return null;
    }

    @Override
    public List<AnalysePlayer> getPlayersByCountry(String country, int limit) {
        return null;
    }

    @Override
    public List<AnalysePlayer> getPlayersByDomain(String domain) {
        return null;
    }

    @Override
    public List<AnalysePlayer> getPlayersByDomain(String domain, int limit) {
        return null;
    }
}