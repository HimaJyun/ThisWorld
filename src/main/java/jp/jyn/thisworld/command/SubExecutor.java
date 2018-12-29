package jp.jyn.thisworld.command;

import jp.jyn.thisworld.MainConfig;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.command.sub.Help;
import jp.jyn.thisworld.command.sub.Reload;
import jp.jyn.thisworld.command.sub.Show;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class SubExecutor implements CommandExecutor, TabCompleter {
    private final Map<String, CommandExecutor> subCommands = new HashMap<>();

    public SubExecutor(ThisWorld thisworld, MainConfig config) {
        subCommands.put("show", new Show(config));
        subCommands.put("reload", new Reload(thisworld, config));
        subCommands.put("help", new Help(config));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String arg0 = "help";
        if (args.length != 0) {
            arg0 = args[0].toLowerCase(Locale.ENGLISH);
        }

        subCommands.getOrDefault(arg0, subCommands.get("help")).onCommand(sender, command, label, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            return new ArrayList<>(subCommands.keySet());
        }

        if (args.length == 1) {
            return subCommands.keySet().stream()
                .filter(str -> str.startsWith(args[0]))
                .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
