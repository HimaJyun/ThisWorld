package jp.jyn.thisworld.command;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface SubExecutor {
    void onCommand(CommandSender sender, String[] args);
}
