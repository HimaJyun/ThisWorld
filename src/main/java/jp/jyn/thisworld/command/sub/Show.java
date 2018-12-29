package jp.jyn.thisworld.command.sub;

import jp.jyn.thisworld.ActionBarSender;
import jp.jyn.thisworld.MainConfig;
import jp.jyn.thisworld.MainConfig.TitleConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Show implements CommandExecutor {
    private final ActionBarSender actionbar;

    private final MainConfig.MessageConfig message;
    private final Map<String, TitleConfig> titles;

    public Show(MainConfig config) {
        actionbar = new ActionBarSender();

        message = config.message;
        titles = config.titles;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MainConfig.MessageConfig.PLAYER_ONLY);
            return true;
        }

        if (!sender.hasPermission("thisworld.show")) {
            sender.sendMessage(message.dontHavePermission);
            return true;
        }

        Player player = (Player) sender;
        TitleConfig title = titles.get(player.getWorld().getName());
        if (title == null) {
            sender.sendMessage(message.notAvailable);
            return true;
        }

        player.sendTitle(title.title, title.subTitle, title.fadein, title.stay, title.fadeout);
        actionbar.send(player, title.actionBar);
        return true;
    }
}
