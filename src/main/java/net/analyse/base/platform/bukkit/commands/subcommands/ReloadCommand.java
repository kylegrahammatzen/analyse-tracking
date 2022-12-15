package net.analyse.base.platform.bukkit.commands.subcommands;

import net.analyse.base.platform.bukkit.BukkitAnalysePlatform;
import net.analyse.base.platform.bukkit.commands.SubCommand;
import net.analyse.base.platform.bukkit.util.ColorUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends SubCommand {
    public ReloadCommand(@NotNull BukkitAnalysePlatform platform) {
        super(platform, "reload", "analyse.reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
       platform.reloadConfig();

       sender.sendMessage(ColorUtil.parse("&b[Analyse] &7Reloaded configuration file."));
    }
}