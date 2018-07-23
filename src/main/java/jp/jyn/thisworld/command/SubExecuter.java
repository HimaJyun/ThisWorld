package jp.jyn.thisworld.command;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface SubExecuter {
    void onCommand(CommandSender sender, String[] args);
}
