package jp.jyn.thisworld.command.subthisworld;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jp.jyn.thisworld.ConfigStruct;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.TitleSender;
import jp.jyn.thisworld.command.SubExecuter;

public class Fix implements SubExecuter {

	private final TitleSender titleSender;
	private final ConfigStruct conf;

	public Fix(ThisWorld thisworld) {
		titleSender = thisworld.getTitleSender();
		conf = thisworld.getConf();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) { // コンソールから実行してきた場合
			// Playerオンリーメッセージを吐いて終わり
			sender.sendMessage(ConfigStruct.PLAYER_ONLY);
			return;
		}
		if (!sender.hasPermission("thisworld.fix")) { // 権限を持たない時
			// 権限ないよエラーで終わり
			sender.sendMessage(conf.getMessageDontHavePermission());
			return;
		}

		// Playerにキャスト
		Player player = (Player) sender;
		// 表示時間を修正
		titleSender.resetTitle(player);
		titleSender.setTime(player, conf.getFeedin(), conf.getShow(), conf.getFeedout());
		sender.sendMessage(conf.getMessageFixed());
	}
}
