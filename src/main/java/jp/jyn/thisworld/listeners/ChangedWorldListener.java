package jp.jyn.thisworld.listeners;

import jp.jyn.thisworld.ActionBarSender;
import jp.jyn.thisworld.ConfigLoader;
import jp.jyn.thisworld.ConfigLoader.Title;
import jp.jyn.thisworld.ThisWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Map;

public class ChangedWorldListener implements Listener {

    private final ActionBarSender actionbar;

    private final Map<String, Title> titles;

    public ChangedWorldListener(ThisWorld thisworld) {
        actionbar = new ActionBarSender();
        titles = thisworld.getConf().getTitles();

        thisworld.getServer().getPluginManager().registerEvents(this, thisworld);
    }

    @EventHandler
    public void ChangedWorldEvent(PlayerChangedWorldEvent e) {
        // Playerを取得
        Player player = e.getPlayer();
        String world = player.getWorld().getName().toLowerCase();

        if (titles.containsKey(world)) { // 世界が登録されている
            ConfigLoader.Title title = titles.get(world);

            player.sendTitle(title.title, title.subTitle, title.fadein, title.stay, title.fadeout);
            actionbar.send(player, title.actionBar);
        }
    }
}
