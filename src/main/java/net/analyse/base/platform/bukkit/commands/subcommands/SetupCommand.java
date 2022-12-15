package net.analyse.base.platform.bukkit.commands.subcommands;

import net.analyse.base.platform.bukkit.BukkitAnalysePlatform;
import net.analyse.base.platform.bukkit.commands.SubCommand;
import net.analyse.base.platform.bukkit.util.ColorUtil;
import net.analyse.base.sdk.AnalyseSDK;
import net.analyse.base.sdk.response.GetServerResponse;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetupCommand extends SubCommand {
    public SetupCommand(@NotNull BukkitAnalysePlatform platform) {
        super(platform, "setup", "analyse.setup");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (platform.getConfiguration().isSetup()) {
            sender.sendMessage(ColorUtil.parse("&b[Analyse] &7This server has already been setup."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorUtil.parse("&b[Analyse] &7You must specify a server token."));
            return;
        }

        final String serverToken = args[0];

        platform.getServer().getScheduler().runTaskAsynchronously(platform, () -> {
            AnalyseSDK analyseSDK = platform.setup(serverToken);

//            try {
                GetServerResponse server = analyseSDK.getServer();

                sender.sendMessage(ColorUtil.parse("&b[Analyse] &7Successfully setup server with token &b" + server.getName() + "&7."));
//            } catch (ServerNotFoundException e) {
//                sender.sendMessage(ColorUtil.parse("&b[Analyse] &7Sorry, but that server token isn't valid."));
//            }
        });
    }
}
