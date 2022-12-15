package net.analyse.base.platform.bukkit.configuration;

import net.analyse.base.platform.bukkit.BukkitAnalysePlatform;
import net.analyse.base.sdk.AnalyseSDK;
import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.Bukkit;

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

    public boolean isSetup() {
        return setup;
    }

    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public String getAPI_HEADER() {
        return API_HEADER;
    }

    public int getIncrementalVersion() {
        return incrementalVersion;
    }
}
