package jp.jyn.thisworld;

import jp.jyn.thisworld.command.Executor;
import jp.jyn.thisworld.listeners.ChangedWorldListener;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class ThisWorld extends JavaPlugin {

    // 設定
    private ConfigLoader conf = null;

    @Override
    public void onEnable() {
        if (conf == null) { // null == init
            conf = new ConfigLoader(this);
        } else { // null != reload
            conf.loadConfig();
        }

        // コマンド登録
        Executor executer = new Executor(this);
        getCommand("tw").setExecutor(executer);
        getCommand("thisworld").setExecutor(executer);

        // イベントの登録
        new ChangedWorldListener(this);
    }

    @Override
    public void onDisable() {
        // イベントの登録解除
        HandlerList.unregisterAll(this);

        // コマンドの登録解除
        getCommand("tw").setExecutor(this);
        getCommand("thisworld").setExecutor(this);
    }

    public ConfigLoader getConf() {
        return conf;
    }
}
