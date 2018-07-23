package jp.jyn.thisworld.command.subthisworld;

import jp.jyn.thisworld.ActionBarSender;
import jp.jyn.thisworld.ConfigLoader;
import jp.jyn.thisworld.ConfigLoader.Title;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.command.SubExecuter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Show implements SubExecuter {

    private final ActionBarSender actionbar;

    private final Map<String, Title> title;
    private final ConfigLoader.Message message;

    public Show(ThisWorld thisworld) {
        actionbar = new ActionBarSender();

        message = thisworld.getConf().message;
        title = thisworld.getConf().getTitle();
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

        if (title.containsKey(world)) { // 世界が登録されている
            ConfigLoader.Title message = title.get(world);

            player.sendTitle(message.title, message.subTitle, message.fadein, message.stay, message.fadeout);
            actionbar.send(player, message.actionBar);
        } else { // 世界が登録されていない
            // 使えませんメッセージ
            sender.sendMessage(message.notAvailable);
        }
    }
}
