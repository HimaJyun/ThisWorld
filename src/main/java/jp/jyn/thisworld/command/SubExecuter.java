package jp.jyn.thisworld.command;

import org.bukkit.command.CommandSender;

public interface SubExecuter {
	abstract void onCommand(CommandSender sender, String[] args);
}
