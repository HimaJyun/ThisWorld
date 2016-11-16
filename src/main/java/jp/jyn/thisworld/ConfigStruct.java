package jp.jyn.thisworld;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigStruct {

	private FileConfiguration conf = null;

	/**
	 * 使用されるプラグイン
	 */
	private final Plugin plg;

	// 接頭辞
	private final static String PREFIX_SUCCESS = "[" + ChatColor.GREEN + "ThisWorld" + ChatColor.RESET + "] ";
	private final static String PREFIX_FAIL = "[" + ChatColor.RED + "ThisWorld" + ChatColor.RESET + "] ";

	// プレイヤー専用
	public final static String PLAYER_ONLY = PREFIX_FAIL + ChatColor.RED + "This command can only be run by a player.";

	// 各種メッセージ
	private String messageDontHavePermission;
	private String messageNotAvailable;
	private String messageConfigReloaded;
	private String messageFixed;

	// ヘルプメッセージ
	private String helpHelp;
	private String helpShow;
	private String helpFix;
	private String helpReload;

	// 表示時間
	private int feedin, show, feedout;
	// ログイン時修正が有効か
	private boolean fixtimeEnable = false;

	// タイトルとサブタイトルを取得
	Map<String, Messages> titles = new HashMap<>();

	public ConfigStruct(Plugin plg) {
		this.plg = plg;
		loadConfig();
	}

	public void loadConfig() {
		// もし、configが存在しない場合は作成
		plg.saveDefaultConfig();

		if (conf != null) { // confが非null == リロード
			// リロード
			plg.reloadConfig();
			// メッセージをクリア
			titles.clear();
		}

		// configの取得
		conf = plg.getConfig();

		// 表示時間の読み込み
		feedin = (int) (conf.getDouble("FixTitleTime.Fadein") * 20);
		show = (int) (conf.getDouble("FixTitleTime.Show") * 20);
		feedout = (int) (conf.getDouble("FixTitleTime.Fadeout") * 20);

		for (String world : conf.getConfigurationSection("Worlds").getKeys(false)) {
			titles.put(world.toLowerCase(), new Messages(
					replaceColor(conf.getString("Worlds." + world + ".title", "")),
					replaceColor(conf.getString("Worlds." + world + ".subtitle", "")),
					replaceColor(conf.getString("Worlds." + world + ".actionbar", ""))));
		}

		messageDontHavePermission = PREFIX_FAIL + replaceColor(conf.getString("Messages.Dont_Have_Permission"));
		messageNotAvailable = PREFIX_FAIL + replaceColor(conf.getString("Messages.Not_Available"));
		messageConfigReloaded = PREFIX_SUCCESS + replaceColor(conf.getString("Messages.Config_Reloaded"));
		messageFixed = PREFIX_SUCCESS + replaceColor(conf.getString("Messages.Fixed"));

		helpHelp = "/thisworld help - " + replaceColor(conf.getString("Messages.Help.Help"));
		helpShow = "/thisworld show - " + replaceColor(conf.getString("Messages.Help.Show"));
		helpFix = "/thisworld fix - " + replaceColor(conf.getString("Messages.Help.Fix"));
		helpReload = "/thisworld reload - " + replaceColor(conf.getString("Messages.Help.Reload"));

		fixtimeEnable = conf.getBoolean("FixTitleTime.Enable");
	}

	/**
	 * 色コードを置換します、置換される文字列は以下の通りです。<br>
	 * &0-><span style="color:#000;">Black</span><br>
	 * &1-><span style="color:#00A;">Dark Blue</span><br>
	 * &2-><span style="color:#0A0;">Dark Green</span><br>
	 * &3-><span style="color:#0AA;">Dark Aqua</span><br>
	 * &4-><span style="color:#A00;">Dark Red</span><br>
	 * &5-><span style="color:#A0A;">Purple</span><br>
	 * &6-><span style="color:#FA0;">Gold</span><br>
	 * &7-><span style="color:#AAA;">Gray</span><br>
	 * &8-><span style="color:#555;">Dark Gray</span><br>
	 * &9-><span style="color:#55F;">Blue</span><br>
	 * &a-><span style="color:#5F5;">Green</span><br>
	 * &b-><span style="color:#5FF;">Aqua</span><br>
	 * &c-><span style="color:#F55;">Red</span><br>
	 * &d-><span style="color:#F5F;">Light Purple</span><br>
	 * &e-><span style="color:#FF5;background-color:#000;">Yellow</span><br>
	 * &f-><span style="color:#FFF;background-color:#000;">White</span><br>
	 * &k->Obfuscated<br>
	 * &l-><b>Bold</b><br>
	 * &m-><del>Strikethrough</del><br>
	 * &n-><u>Underline</u><br>
	 * &o-><i>Italic</i><br>
	 * &r->Reset<br>
	 * &&で上記の変換を無効化出来ます。
	 *
	 * @param str
	 *            置換対象の色コード
	 * @return 置換後の色コード、入力がnullならnull
	 */
	public String replaceColor(String str) {
		return str == null ? null
				: ChatColor.translateAlternateColorCodes('&', str).replace(ChatColor.COLOR_CHAR + "&", "&");
	}

	/**
	 * タイトルメッセージ用クラス(構造体)
	 *
	 * @author HimaJyun
	 */
	public final class Messages {
		public final String title;
		public final String subTitle;
		public final String actionBar;

		public Messages(String title, String subTitle, String actionBar) {
			this.title = title;
			this.subTitle = subTitle;
			this.actionBar = actionBar;
		}
	}

	public String getMessageDontHavePermission() {
		return messageDontHavePermission;
	}

	public String getMessageNotAvailable() {
		return messageNotAvailable;
	}

	public String getMessageConfigReloaded() {
		return messageConfigReloaded;
	}

	public String getMessageFixed() {
		return messageFixed;
	}

	public String getHelpHelp() {
		return helpHelp;
	}

	public String getHelpShow() {
		return helpShow;
	}

	public String getHelpFix() {
		return helpFix;
	}

	public String getHelpReload() {
		return helpReload;
	}

	public int getFeedin() {
		return feedin;
	}

	public int getShow() {
		return show;
	}

	public int getFeedout() {
		return feedout;
	}

	public boolean isFixtimeEnable() {
		return fixtimeEnable;
	}

	public Map<String, Messages> getTitles() {
		return titles;
	}
}
