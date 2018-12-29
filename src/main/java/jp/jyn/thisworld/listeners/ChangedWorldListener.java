package jp.jyn.thisworld.listeners;

import jp.jyn.thisworld.ActionBarSender;
import jp.jyn.thisworld.MainConfig;
import jp.jyn.thisworld.MainConfig.TitleConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Map;

@SuppressWarnings("unused")
public class ChangedWorldListener implements Listener {
    private final ActionBarSender actionbar;
    private final Map<String, TitleConfig> titles;

    public ChangedWorldListener(MainConfig config) {
        actionbar = new ActionBarSender();
        titles = config.titles;
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();

        TitleConfig title = titles.get(player.getWorld().getName());
        if (title == null) {
            return;
        }

        player.sendTitle(title.title, title.subTitle, title.fadein, title.stay, title.fadeout);
        actionbar.send(player, title.actionBar);
    }
}
