package jp.jyn.thisworld;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleToIntFunction;

public class ConfigLoader {

    /**
     * 使用されるプラグイン
     */
    private final Plugin plg;

    public Message message;
    private Map<String, Title> title = new HashMap<>();

    public ConfigLoader(Plugin plg) {
        this.plg = plg;
    }

    public ConfigLoader loadConfig() {
        plg.saveDefaultConfig();
        // for reload
        plg.reloadConfig();
        FileConfiguration conf = plg.getConfig();

        message = new Message(conf);

        Map<String, Title> tmp = new HashMap<>();
        for (String world : conf.getConfigurationSection("Worlds").getKeys(false)) {
            tmp.put(world.toLowerCase(), new Title(conf, world));
        }
        title = Collections.unmodifiableMap(tmp);

        return this;
    }

    private static String replaceColor(String str) {
        if (str == null) {
            return null;
        }

        return ChatColor.translateAlternateColorCodes('&', str).replace(ChatColor.COLOR_CHAR + "&", "&");
    }

    public final static class Message {
        // 接頭辞
        private final static String PREFIX = "[ThisWorld] ";

        public final static String PLAYER_ONLY = PREFIX + ChatColor.RED + "This command can only be run by a player.";

        public final String dontHavePermission;
        public final String notAvailable;
        public final String configReloaded;

        public final String helpHelp;
        public final String helpShow;
        public final String helpReload;

        private Message(FileConfiguration conf) {
            dontHavePermission = PREFIX + replaceColor(conf.getString("Messages.DontHavePermission"));
            notAvailable = PREFIX + replaceColor(conf.getString("Messages.NotAvailable"));
            configReloaded = PREFIX + replaceColor(conf.getString("Messages.ConfigReloaded"));

            helpHelp = "/thisworld help - " + replaceColor(conf.getString("Messages.Help.Help"));
            helpShow = "/thisworld show - " + replaceColor(conf.getString("Messages.Help.Show"));
            helpReload = "/thisworld reload - " + replaceColor(conf.getString("Messages.Help.Reload"));
        }
    }

    public final static class Title {
        public final String title;
        public final String subTitle;
        public final String actionBar;
        public final int fadein;
        public final int stay;
        public final int fadeout;

        private Title(FileConfiguration conf, String world) {
            final DoubleToIntFunction convertTick = d -> (int) d * 20;
            final double defaultFadein = conf.getDouble("DefaultTime.fadein");
            final double defaultStay = conf.getDouble("DefaultTime.stay");
            final double defaultFadeout = conf.getDouble("DefaultTime.fadeout");

            title = replaceColor(conf.getString("Worlds." + world + ".title", ""));
            subTitle = replaceColor(conf.getString("Worlds." + world + ".subtitle", ""));
            actionBar = replaceColor(conf.getString("Worlds." + world + ".actionbar", ""));

            this.fadein = convertTick.applyAsInt(conf.getDouble("Worlds." + world + ".fadein", defaultFadein));
            this.stay = convertTick.applyAsInt(conf.getDouble("Worlds." + world + ".stay", defaultStay));
            this.fadeout = convertTick.applyAsInt(conf.getDouble("Worlds." + world + ".fadeout", defaultFadeout));
        }
    }

    public Map<String, Title> getTitle() {
        return title;
    }
}
