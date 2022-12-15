package net.analyse.base.platform.bukkit.configuration;

import net.analyse.base.platform.bukkit.BukkitAnalysePlatform;
import net.analyse.base.sdk.AnalyseSDK;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.HeaderMode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;

public class Configuration {

    private final BukkitAnalysePlatform platform;

    private boolean setup;

    private String encryptionKey;

    private final String API_HEADER;
    private final int incrementalVersion;

    public Configuration(BukkitAnalysePlatform platform) {
        this.platform = platform;
        this.API_HEADER = "Analyse v" + platform.getVersion() + " / " + Bukkit.getServer().getName() + " " + platform.getServer().getVersion();
        this.incrementalVersion = Integer.parseInt(platform.getDescription().getVersion().replace(".", ""));
    }

    public void load(ConfigurationNode node) {
        setup = node.getNode("setup").getBoolean();
        encryptionKey = node.getNode("encryption-key").getString();

        platform.setSDK(new AnalyseSDK(
                node.getNode("token").getString(),
                encryptionKey
        ));
    }

    /**
     * Gets whether the plugin has been set up
     * @return Whether the plugin has been set up
     */
    public boolean isSetup() {
        return setup;
    }

    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    /**
     * Gets the encryption key
     * @return The encryption key
     */
    public String getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Sets a new encryption key
     * @param encryptionKey The new encryption key
     */
    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    /**
     * Gets the API header
     * @return The API header
     */
    public String getAPI_HEADER() {
        return API_HEADER;
    }

    /**
     * Gets the incremental version
     * @return The incremental version
     */
    public int getIncrementalVersion() {
        return incrementalVersion;
    }

    /**
     * Saves the configuration
     */
    public void save() throws IOException {
        File configFile = new File(platform.getDataFolder(), "config.yml");

        YAMLConfigurationLoader loader = YAMLConfigurationLoader.builder()
                .setFile(configFile)
                .setHeaderMode(HeaderMode.PRESET)
                .build();

        ConfigurationNode configNode = loader.load();

        platform.getLogger().info("Current encryption key: " + encryptionKey);

        // Config storage
        String encryptionKey = configNode.getNode("encryption-key").getString();

        if (encryptionKey == null || encryptionKey.isEmpty()) {
            configNode.getNode("encryption-key").setValue(getEncryptionKey());

            platform.getLogger().info("Generated new encryption key: " + getEncryptionKey());

            loader.save(configNode);
        }

        platform.getLogger().info("Config node: `" + configNode.getNode("encryption-key").getString() + "`");
    }
}
