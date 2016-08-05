package jp.jyn.thisworld.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.command.subthisworld.Fix;
import jp.jyn.thisworld.command.subthisworld.Help;
import jp.jyn.thisworld.command.subthisworld.Reload;
import jp.jyn.thisworld.command.subthisworld.Show;

public class Executer implements CommandExecutor {

	private final Map<String, SubExecuter> subCommands;

	public Executer(ThisWorld thisworld) {
		Map<String, SubExecuter> commands = new HashMap<>();

		commands.put("show", new Show(thisworld));
		commands.put("fix", new Fix(thisworld));
		commands.put("reload", new Reload(thisworld));
		commands.put("help", new Help(thisworld));

		subCommands = Collections.unmodifiableMap(commands);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String arg0;
		if (args.length != 0) { // 引数がある
			arg0 = args[0].toLowerCase(Locale.ENGLISH);
			// 引数のコマンドがなければhelpを
			if (!subCommands.containsKey(arg0)) {
				arg0 = "help";
			}
		} else { // 引数がない
			if (sender instanceof Player) { // プレイヤーの場合はshowを
				arg0 = "show";
			} else { // コンソールの場合はhelpを
				arg0 = "help";
			}
		}

		// 実行
		subCommands.get(arg0).onCommand(sender, args);
		// 常時true(エラーはこっちで出すからBukkitは黙れ)
		return true;
	}

}
