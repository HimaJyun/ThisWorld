package jp.jyn.thisworld.command.subthisworld;

import jp.jyn.thisworld.ActionBarSender;
import jp.jyn.thisworld.ConfigLoader;
import jp.jyn.thisworld.ConfigLoader.Title;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.command.SubExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Show implements SubExecutor {

    private final ActionBarSender actionbar;

    private final Map<String, Title> titles;
    private final ConfigLoader.Message message;

    public Show(ThisWorld thisworld) {
        actionbar = new ActionBarSender();

        message = thisworld.getConf().message;
        titles = thisworld.getConf().getTitles();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) { // コンソールから実行してきた場合
            // Playerオンリーメッセージを吐いて終わり
            sender.sendMessage(ConfigLoader.Message.PLAYER_ONLY);
            return;
        }

        if (!sender.hasPermission("thisworld.show")) { // 権限を持たない時
            sender.sendMessage(message.dontHavePermission);
            return;
        }

        // Playerにキャスト
        Player player = (Player) sender;
        String world = player.getWorld().getName().toLowerCase();

        if (titles.containsKey(world)) { // 世界が登録されている
            ConfigLoader.Title title = titles.get(world);

            player.sendTitle(title.title, title.subTitle, title.fadein, title.stay, title.fadeout);
            actionbar.send(player, title.actionBar);
        } else { // 世界が登録されていない
            // 使えませんメッセージ
            sender.sendMessage(message.notAvailable);
        }
    }
}
