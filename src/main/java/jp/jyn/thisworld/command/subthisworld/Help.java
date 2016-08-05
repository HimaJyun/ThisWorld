package jp.jyn.thisworld.command.subthisworld;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import jp.jyn.thisworld.ConfigStruct;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.command.SubExecuter;

public class Help implements SubExecuter {

	private final ConfigStruct conf;
	private final String header = ChatColor.GREEN + "=========" + ChatColor.WHITE + " ThisWorld " + ChatColor.GREEN
			+ "=========";

	public Help(ThisWorld thisworld) {
		conf = thisworld.getConf();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		sender.sendMessage(new String[] { header, conf.getHelpShow(), conf.getHelpFix(), conf.getHelpReload(),
				conf.getHelpHelp(), });
	}
}
