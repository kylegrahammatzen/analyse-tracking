package net.analyse.base.platform.bungee;

import net.analyse.base.AnalyseBase;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

public class BungeeAnalysePlatform extends Plugin implements AnalyseBase {

    @Override
    public void onEnable() {
        getLogger().info("AnalyseBase is enabled! (Bungee)");
    }

    @Override
    public void onDisable() {
        getLogger().info("AnalyseBase is disabled! (Bungee)");
    }

    @Override
    public File getDirectory() {
        return getDataFolder();
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    public <T extends Listener> T registerEvents(T l) {
        ProxyServer.getInstance().getPluginManager().registerListener(this, l);
        return l;
    }
}
