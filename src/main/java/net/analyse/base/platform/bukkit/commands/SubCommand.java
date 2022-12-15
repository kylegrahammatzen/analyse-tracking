package net.analyse.base.platform.bukkit.commands;

import net.analyse.base.platform.bukkit.BukkitAnalysePlatform;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class SubCommand {

    protected final BukkitAnalysePlatform platform;
    private final String name;
    private final String permission;

    protected SubCommand(final @NotNull BukkitAnalysePlatform platform, final @NotNull String name, final @NotNull String permission) {
        this.platform = platform;
        this.name = name;
        this.permission = permission;
    }

    public abstract void execute(final CommandSender player, final String[] args);

    public BukkitAnalysePlatform getPlatform() {
        return platform;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }
}