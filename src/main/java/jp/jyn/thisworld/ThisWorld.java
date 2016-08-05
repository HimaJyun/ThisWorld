package jp.jyn.thisworld;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import jp.jyn.thisworld.command.Executer;
import jp.jyn.thisworld.listeners.FixTitleTime;
import jp.jyn.thisworld.listeners.SendingListener;

public class ThisWorld extends JavaPlugin implements Listener {

	// 設定
	private ConfigStruct conf = null;
	// TitleSenderクラスの準備
	private TitleSender titleSender;

	@Override
	public void onEnable() {
		if (conf == null) { // confがnull->初期起動
			conf = new ConfigStruct(this);
		} else { // confが非null->リロード
			conf.loadConfig();
		}

		// TitleSenderの初期化
		titleSender = new TitleSender();

		// コマンド登録
		Executer executer = new Executer(this);
		getCommand("tw").setExecutor(executer);
		getCommand("thisworld").setExecutor(executer);

		// イベントの登録
		if (conf.isFixtimeEnable()) {
			new FixTitleTime(this);
		}
		new SendingListener(this);
	}

	@Override
	public void onDisable() {
		// イベントの登録解除
		HandlerList.unregisterAll((Plugin) this);

		// コマンドの登録解除
		getCommand("tw").setExecutor(this);
		getCommand("thisworld").setExecutor(this);
	}

	public ConfigStruct getConf() {
		return conf;
	}

	public TitleSender getTitleSender() {
		return titleSender;
	}
}
