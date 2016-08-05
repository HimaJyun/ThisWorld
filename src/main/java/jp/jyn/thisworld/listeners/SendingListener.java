package jp.jyn.thisworld.listeners;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import jp.jyn.thisworld.ConfigStruct.Messages;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.TitleSender;

public class SendingListener implements Listener {

	private final TitleSender titleSender;
	private final Map<String, Messages> titles;

	/**
	 * SendingListenerを初期化します。
	 *
	 * @param ThisWorld
	 *            メインクラス
	 */
	public SendingListener(ThisWorld thisworld) {
		// イベント登録
		thisworld.getServer().getPluginManager().registerEvents(this, thisworld);

		// TitleSenderを取得
		titleSender = thisworld.getTitleSender();
		// 世界を取得
		titles = thisworld.getConf().getTitles();
	}

	/**
	 * 世界を切り替えた際にタイトルを送信します。
	 *
	 * @param e
	 *            PlayerChangedWorldEvent
	 */
	@EventHandler
	public void ChangedWorldEvent(PlayerChangedWorldEvent e) {
		// Playerを取得
		Player player = e.getPlayer();
		String world = player.getWorld().getName().toLowerCase();
		if (titles.containsKey(world)) { // 世界が登録されている
			Messages message = titles.get(world);
			// 送信
			titleSender.sendTitle(player, message.title, message.subTitle);
		}
	}
}
