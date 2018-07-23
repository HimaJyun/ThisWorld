package jp.jyn.thisworld.command.subthisworld;

import jp.jyn.thisworld.ConfigLoader;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.command.SubExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Help implements SubExecutor {

    private final ConfigLoader.Message message;
    private final String header =
        ChatColor.GREEN + "=========" + ChatColor.WHITE + " ThisWorld " + ChatColor.GREEN + "=========";

    public Help(ThisWorld thisworld) {
        message = thisworld.getConf().message;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        sender.sendMessage(new String[]{
            header,
            message.helpShow,
            message.helpReload,
            message.helpHelp
        });
    }
}
