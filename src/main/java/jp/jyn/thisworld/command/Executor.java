package jp.jyn.thisworld.command;

import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.command.subthisworld.Help;
import jp.jyn.thisworld.command.subthisworld.Reload;
import jp.jyn.thisworld.command.subthisworld.Show;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Executor implements CommandExecutor {

    private final Map<String, SubExecutor> subCommands;

    public Executor(ThisWorld thisworld) {
        Map<String, SubExecutor> commands = new HashMap<>();

        commands.put("show", new Show(thisworld));
        commands.put("reload", new Reload(thisworld));
        commands.put("help", new Help(thisworld));

        subCommands = Collections.unmodifiableMap(commands);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String arg0 = "help";
        if (args.length != 0) {
            arg0 = args[0].toLowerCase(Locale.ENGLISH);
        }

        // 実行
        subCommands.getOrDefault(arg0, subCommands.get("help")).onCommand(sender, args);
        // Always true(Bukkit shut up)
        return true;
    }
}
