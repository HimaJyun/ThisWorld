package jp.jyn.thisworld;

import jp.jyn.thisworld.command.SubExecutor;
import jp.jyn.thisworld.listeners.ChangedWorldListener;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class ThisWorld extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        MainConfig config = new MainConfig(getConfig());

        SubExecutor executor = new SubExecutor(this, config);
        getCommand("thisworld").setExecutor(executor);
        getCommand("thisworld").setTabCompleter(executor);

        getServer().getPluginManager().registerEvents(new ChangedWorldListener(config), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);

        getCommand("thisworld").setTabCompleter(this);
        getCommand("thisworld").setExecutor(this);
    }
}
