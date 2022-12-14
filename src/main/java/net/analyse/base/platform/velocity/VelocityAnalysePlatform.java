package net.analyse.base.platform.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.analyse.base.platform.AbstractPlatform;

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
        getLogger().info("AnalyseBase is enabled! (Velocity)");
    }

    @Subscribe
    public void onShutdown(ProxyShutdownEvent event) {
        getLogger().info("AnalyseBase is disabled! (Velocity)");
    }

    public <T> T registerEvents(T l) {
        server.getEventManager().register(this, l);
        return l;
    }

    public ProxyServer getServer() {
        return server;
    }
}