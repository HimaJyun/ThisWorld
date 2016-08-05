package jp.jyn.thisworld.command.subthisworld;

import org.bukkit.command.CommandSender;

import jp.jyn.thisworld.ConfigStruct;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.command.SubExecuter;

public class Reload implements SubExecuter {

	private final ThisWorld thisworld;
	private final ConfigStruct conf;

	public Reload(ThisWorld thisworld) {
		this.thisworld = thisworld;
		conf = thisworld.getConf();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (!sender.hasPermission("thisworld.reload")) { // 権限を持たない場合
			sender.sendMessage(conf.getMessageDontHavePermission());
			return;
		}
		// リロード(単にDisableしてEnableするだけ)
		thisworld.onDisable();
		thisworld.onEnable();
		// メッセージを送信
		sender.sendMessage(conf.getMessageConfigReloaded());
	}

}
