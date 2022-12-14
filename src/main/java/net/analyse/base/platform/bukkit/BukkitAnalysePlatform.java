package net.analyse.base.platform.bukkit;

import net.analyse.base.AnalyseBase;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BukkitAnalysePlatform extends JavaPlugin implements AnalyseBase {

    @Override
    public void onEnable() {
        getLogger().info("AnalyseBase is enabled! (Bukkit)");
    }

    @Override
    public void onDisable() {
        getLogger().info("AnalyseBase is disabled! (Bukkit)");
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
        getServer().getPluginManager().registerEvents(l, this);
        return l;
    }
}
