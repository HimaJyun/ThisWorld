package jp.jyn.thisworld.command.subthisworld;

import org.bukkit.command.CommandSender;

import jp.jyn.thisworld.ConfigLoader;
import jp.jyn.thisworld.ThisWorld;
import jp.jyn.thisworld.command.SubExecutor;

public class Reload implements SubExecutor {

    private final ThisWorld thisworld;
    private final ConfigLoader.Message message;

    public Reload(ThisWorld thisworld) {
        this.thisworld = thisworld;
        message = thisworld.getConf().message;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("thisworld.reload")) { // 権限を持たない場合
            sender.sendMessage(message.dontHavePermission);
            return;
        }

        // リロード(単にDisableしてEnableするだけ)
        thisworld.onDisable();
        thisworld.onEnable();

        // メッセージを送信
        sender.sendMessage(message.configReloaded);
    }

}
