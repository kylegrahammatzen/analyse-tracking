package net.analyse.base.platform;

import net.analyse.base.AnalyseBase;

import java.io.File;
import java.util.logging.Logger;

public abstract class AbstractPlatform implements AnalyseBase {

    protected final Logger logger;
    protected final File directory;

    protected AbstractPlatform(Logger logger, File directory, String configFileName) {
        this.logger = logger;
        this.directory = directory;

        logger.info("AnalyseBase is being loaded! (Abstract)");
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
    public String getVersion() {
        return VERSION;
    }
}