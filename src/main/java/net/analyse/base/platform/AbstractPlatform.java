package net.analyse.base.platform;

import net.analyse.base.AnalyseBase;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class AbstractPlatform implements AnalyseBase {

    protected final Logger logger;
    protected final File directory;

    protected final String platformName;

    protected AbstractPlatform(Logger logger, File directory, String platformName) {
        this.logger = logger;
        this.directory = directory;
        this.platformName = platformName;
    }

    @Override
    public File getDirectory() {
        return directory;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getPlatformName() {
        return platformName;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public void loadConfig() {

    }

    @Override
    public void reloadConfig() {

    }
}