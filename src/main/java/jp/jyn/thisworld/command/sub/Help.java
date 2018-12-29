package jp.jyn.thisworld.command.sub;

import jp.jyn.thisworld.MainConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Help implements CommandExecutor {
    private final String[] messages;

    public Help(MainConfig config) {
        messages = new String[]{
            ChatColor.GREEN + "=========" + ChatColor.WHITE + " ThisWorld " + ChatColor.GREEN + "=========",
            config.message.helpShow,
            config.message.helpReload,
            config.message.helpHelp
        };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(messages);
        return true;
    }
}
