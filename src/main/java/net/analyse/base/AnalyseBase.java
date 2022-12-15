package net.analyse.base;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public interface AnalyseBase {

    /**
     * The version of the plugin.
     */
    String VERSION = "${projectVersion}";

    /**
     * Get the directory where the plugin is running from.
     * @return The directory.
     */
    File getDirectory();

    /**
     * Get the logger for this platform
     * @return The logger
     */
    Logger getLogger();

    /**
     * Get the name of the config file
     * @return The name of the config file
     */
    String getPlatformName();

    /**
     * Get the version of the platform
     * @return The version of the platform
     */
    String getVersion();


    /**
     * Load configuration from file
     */
    void loadConfig() throws IOException, URISyntaxException;

    /**
     * Reload configuration from file
     */
    void reloadConfig();
}