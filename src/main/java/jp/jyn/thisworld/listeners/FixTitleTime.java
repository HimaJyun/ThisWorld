package jp.jyn.thisworld.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import jp.jyn.thisworld.ConfigStruct;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.TitleSender;

public class FixTitleTime implements Listener {

	private final TitleSender titleSender;
	private final int feedin, show, feedout;

	/**
	 * FixTitleTimeを初期化します。
	 *
	 * @param ThisWorld
	 *            メインクラス
	 */
	public FixTitleTime(ThisWorld thisworld) {
		// イベント登録
		thisworld.getServer().getPluginManager().registerEvents(this, thisworld);

		// TitleSenderを取得
		titleSender = thisworld.getTitleSender();

		// 設定取得
		ConfigStruct conf = thisworld.getConf();
		feedin = conf.getFeedin();
		show = conf.getShow();
		feedout = conf.getFeedout();
	}

	/**
	 * ログイン時に表示時間をリセットします。
	 *
	 * @param e
	 *            PlayerJoinEvent
	 */
	@EventHandler
	public void resetDisplaytime(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		titleSender.resetTitle(player);
		titleSender.setTime_tick(player, feedin, show, feedout);
	}
}
