package net.analyse.base.platform.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.analyse.base.database.AnalyseDatabase;
import net.analyse.base.platform.AbstractPlatform;
import net.analyse.base.sdk.AnalyseSDK;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import static net.analyse.base.utils.ResourceUtils.getBundledFile;

public class VelocityAnalysePlatform extends AbstractPlatform {

    private final ProxyServer server;
    private AnalyseSDK sdk;
    private AnalyseDatabase database;
    private AnalyseDatabase backupDatabase;

    /**
     * Create a new VelocityAnalysePlatform
     * @param server The Velocity server
     * @param dataDirectory The data directory
     */
    @Inject
    public VelocityAnalysePlatform(ProxyServer server, @DataDirectory Path dataDirectory) {
        super(Logger.getLogger("Analyse"), dataDirectory.toFile(), "velocity");
        this.server = server;
    }

    /**
     * Runs when the plugin is enabled
     * @param event Fired when the proxy is loaded but before any connections are accepted
     */
    @Subscribe
    public void onInit(ProxyInitializeEvent event) {
        logger.info(String.format("Enabling Analyse v%s (Velocity)", getVersion()));
    }

    @Override
    public AnalyseSDK getSDK() {
        return sdk;
    }

    /**
     * Get the version of the plugin
     * @return The version of the plugin
     */
    @Override
    public String getVersion() {
        return getDescription().getVersion().orElse("Unknown");
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
     * Get the plugin description
     * @return The plugin description
     */
    PluginDescription getDescription() {
        return server.getPluginManager().getPlugin("analyse").map(PluginContainer::getDescription).orElse(null);
    }
}