package net.analyse.base.platform.bukkit;

import net.analyse.base.AnalyseBase;
import net.analyse.base.database.AnalyseDatabase;
import net.analyse.base.database.types.MySQLDatabase;
import net.analyse.base.database.types.SQLiteDatabase;
import net.analyse.base.utils.ResourceUtils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BukkitAnalysePlatform extends JavaPlugin implements AnalyseBase {

    private AnalyseDatabase database;
    private AnalyseDatabase backupDatabase;

    /**
     * Runs when the plugin is enabled
     */
    @Override
    public void onEnable() {
        getLogger().info(String.format("Enabling Analyse v%s (Bukkit)", getVersion()));

        try {
            loadConfig();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
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
        // Load the config
        TypeSerializerCollection serializerCollection = TypeSerializerCollection.create();

        ConfigurationOptions options = ConfigurationOptions.defaults()
                .withSerializers(serializerCollection);

        ConfigurationNode configFile = YAMLConfigurationLoader.builder()
                .setDefaultOptions(options)
                .setFile(getBundledFile("config.yml"))
                .build()
                .load();

        ConfigurationNode databaseConfig = configFile.getNode("database");

        // Load the databases
        //database = new MySQLDatabase(super.getLogger(), "", 0, "", "", "", true);
        //backupDatabase = new SQLiteDatabase(super.getLogger(), getDirectory().getAbsolutePath());

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
        getServer().getPluginManager().registerEvents(l, this);
        return l;
    }

    /**
     * Gets a file from the resources folder
     * @param fileName The name of the file
     * @return The file
     */
    private File getBundledFile(String fileName) {
        File file = new File(getDirectory(), fileName);

        if (!file.exists()) {
            if (!getDirectory().exists()) {
                if (!getDirectory().mkdirs()) {
                    throw new RuntimeException("Failed to create plugin folder");
                }
            }

            try {
                // Copies the file from the resources' folder to the plugin folder
                Files.copy(ResourceUtils.getFile(getPlatformName(), fileName).toPath(), file.toPath());
            } catch (IOException e) {
                getLogger().severe(String.format("Failed to copy %s to plugin folder", fileName));
            }
        }

        return file;
    }
}