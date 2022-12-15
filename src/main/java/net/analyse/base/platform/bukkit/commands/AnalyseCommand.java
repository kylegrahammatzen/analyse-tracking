package net.analyse.base.platform.bukkit.commands;

import com.google.common.collect.ImmutableList;
import net.analyse.base.platform.bukkit.BukkitAnalysePlatform;
import net.analyse.base.platform.bukkit.commands.subcommands.ReloadCommand;
import net.analyse.base.platform.bukkit.commands.subcommands.SetupCommand;
import net.analyse.base.sdk.response.GetServerResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.analyse.base.platform.bukkit.util.ColorUtil.parse;

public class AnalyseCommand implements TabExecutor {
    private final BukkitAnalysePlatform platform;
    private final Map<String, SubCommand> commands = new HashMap<>();

    public AnalyseCommand(BukkitAnalysePlatform platform) {
        this.platform = platform;

        ImmutableList.of(new SetupCommand(platform), new ReloadCommand(platform)).forEach(command -> {
            commands.put(command.getName(), command);
        });
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final String[] args) {
        if (args.length == 0 || !commands.containsKey(args[0].toLowerCase())) {
            if (!sender.hasPermission("analyse.admin")) {
                sender.sendMessage(parse("&b[Analyse] &7You do not have access to that command."));
                return true;
            }

            sender.sendMessage(" ");
            sender.sendMessage(parse("&b[Analyse] &7Plugin Information:"));
            sender.sendMessage(parse(String.format(" &b- &7Version: &bv%s&7.", platform.getVersion())));

            GetServerResponse server;

            if (platform.getConfiguration().isSetup()) {
                //try {
                    server = platform.getSDK().getServer();
                    sender.sendMessage(parse(String.format(" &b- &7Linked to: &b%s&7.", server.getName())));
//                } catch (ServerNotFoundException e) {
//                    sender.sendMessage("&b[Analyse] &7The server linked no longer exists.");
//                    platform.getConfiguration().setSetup(false);
//                }
            } else {
                sender.sendMessage(parse("&b[Analyse] &7You've not yet linked a server&7."));
            }

            sender.sendMessage(" ");

            return true;
        }

        final SubCommand subCommand = commands.get(args[0].toLowerCase());
        if (!sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(parse("&b[Analyse] &7You do not have access to that command."));
            return true;
        }

        subCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return commands.keySet().stream()
                    .filter(subCommand -> subCommand.startsWith(args[0]))
                    .collect(ImmutableList.toImmutableList());
        }
        return ImmutableList.of();
    }
}
