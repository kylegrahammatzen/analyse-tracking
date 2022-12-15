package net.analyse.base.platform.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.analyse.base.AnalyseBase;
import net.analyse.base.platform.AbstractPlatform;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
public class VelocityAnalysePlatform extends AbstractPlatform {

    private final ProxyServer server;

    @Inject
    public VelocityAnalysePlatform(ProxyServer server, @DataDirectory Path dataDirectory) {
        super(Logger.getLogger("Analyse"), dataDirectory.toFile(), "config.yml");
        this.server = server;
    }

    @Subscribe
    public void onInit(ProxyInitializeEvent event) {
        logger.info(String.format("Enabling Analyse v%s (Velocity)", getVersion()));
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion().orElse("Unknown");
    }

    PluginDescription getDescription() {
        return server.getPluginManager().getPlugin("analyse").map(PluginContainer::getDescription).orElse(null);
    }
}