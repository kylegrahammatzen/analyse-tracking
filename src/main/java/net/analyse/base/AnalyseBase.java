package net.analyse.base;

import java.io.File;
import java.util.logging.Logger;

public interface AnalyseBase {
    String VERSION = "1.0.0";

    File getDirectory();

    Logger getLogger();

    String getVersion();

}