package jp.jyn.thisworld.command.sub;

import jp.jyn.thisworld.MainConfig;
import jp.jyn.thisworld.ThisWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {
    private final ThisWorld thisworld;
    private final MainConfig.MessageConfig message;

    public Reload(ThisWorld thisworld, MainConfig config) {
        this.thisworld = thisworld;
        message = config.message;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("thisworld.reload")) { // 権限を持たない場合
            sender.sendMessage(message.dontHavePermission);
            return true;
        }

        thisworld.onDisable();
        thisworld.onEnable();

        sender.sendMessage(message.configReloaded);
        return true;
    }
}
