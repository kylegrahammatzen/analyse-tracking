package net.analyse.core;

import java.util.logging.Logger;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;

public abstract class PlatformConfiguration {
    private final Logger logger;
    private final Platform platform;
    private final File directory;

    protected PlatformConfiguration(Logger logger, Platform platform, File directory) {
        this.logger = logger;
        this.platform = platform;
        this.directory = directory;
    }

    /**
     * Get the logger for this configuration
     * @return The logger.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Get the platform this configuration is for.
     * @return The platform.
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Get the directory this configuration is stored in.
     * @return The directory.
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * Tries to save the configuration to the directory if it doesn't exist.
     * @return True if the configuration was created, false otherwise.
     */
    public boolean saveDefault(File configFile) {

        if (configFile.exists()) {
            return false;
        }

        File parentFile = configFile.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            getLogger().log(Level.SEVERE, "Failed to create directory for configuration file.");
            return false;
        }

        try (InputStream in = new BufferedInputStream(getClass().getResourceAsStream("/config/" + platform.name().toLowerCase() + ".yml"));
             OutputStream out = new BufferedOutputStream(Files.newOutputStream(configFile.toPath()))) {
            byte[] buf = new byte[2048];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not copy file", ex);
            return false;
        }

        return true;
    }

    /**
     * Load the configuration.
     */
    public abstract void load();

    /**
     * Save the configuration.
     */
    public abstract void save();

    /**
     * Reloads the configuration.
     */
    public abstract void reload();
}