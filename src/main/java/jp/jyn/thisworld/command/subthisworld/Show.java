package jp.jyn.thisworld.command.subthisworld;

import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jp.jyn.thisworld.ConfigStruct;
import jp.jyn.thisworld.ConfigStruct.Messages;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.TitleSender;
import jp.jyn.thisworld.command.SubExecuter;

public class Show implements SubExecuter {

	private final TitleSender titleSender;
	private final Map<String, Messages> titles;
	private final ConfigStruct conf;

	public Show(ThisWorld thisworld) {
		titleSender = thisworld.getTitleSender();
		conf = thisworld.getConf();
		titles = conf.getTitles();
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) { // コンソールから実行してきた場合
			// Playerオンリーメッセージを吐いて終わり
			sender.sendMessage(ConfigStruct.PLAYER_ONLY);
			return;
		}
		if (!sender.hasPermission("thisworld.show")) { // 権限を持たない時
			// 権限ないよエラーで終わり
			sender.sendMessage(conf.getMessageDontHavePermission());
			return;
		}

		// Playerにキャスト
		Player player = (Player) sender;
		String world = player.getWorld().getName().toLowerCase();
		if (titles.containsKey(world)) { // 世界が登録されている
			Messages message = titles.get(world);
			titleSender.sendTitle(player, message.title, message.subTitle);
		} else { // 世界が登録されていない
			// 使えませんメッセージ
			sender.sendMessage(conf.getMessageNotAvailable());
		}
	}
}
