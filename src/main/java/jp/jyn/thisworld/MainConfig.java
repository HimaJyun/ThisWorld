package jp.jyn.thisworld;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainConfig {
    public final MessageConfig message;
    public final Map<String, TitleConfig> titles;

    public MainConfig(ConfigurationSection config) {
        message = new MessageConfig(config.getConfigurationSection("Messages"));

        Map<String, TitleConfig> tmp = new HashMap<>();
        for (String world : config.getConfigurationSection("Worlds").getKeys(false)) {
            tmp.put(world, new TitleConfig(
                config.getConfigurationSection("Worlds." + world),
                config.getConfigurationSection("DefaultTime")
            ));
        }
        titles = Collections.unmodifiableMap(tmp);
    }

    public final static class MessageConfig {
        private final static String PREFIX = "[ThisWorld] ";
        public final static String PLAYER_ONLY = PREFIX + ChatColor.RED + "This command can only be run by a player.";

        public final String dontHavePermission;
        public final String notAvailable;
        public final String configReloaded;

        public final String helpHelp;
        public final String helpShow;
        public final String helpReload;

        private MessageConfig(ConfigurationSection config) {
            dontHavePermission = PREFIX + replaceColor(config.getString("DontHavePermission"));
            notAvailable = PREFIX + replaceColor(config.getString("NotAvailable"));
            configReloaded = PREFIX + replaceColor(config.getString("ConfigReloaded"));

            helpHelp = "/thisworld help - " + replaceColor(config.getString("Help.Help"));
            helpShow = "/thisworld show - " + replaceColor(config.getString("Help.Show"));
            helpReload = "/thisworld reload - " + replaceColor(config.getString("Help.Reload"));
        }
    }

    public final static class TitleConfig {
        public final String title;
        public final String subTitle;
        public final String actionBar;
        public final int fadein;
        public final int stay;
        public final int fadeout;

        private TitleConfig(ConfigurationSection config, ConfigurationSection defaultConfig) {
            title = replaceColor(config.getString("title", defaultConfig.getString("title", "")));
            subTitle = replaceColor(config.getString("subtitle", defaultConfig.getString("subtitle", "")));
            actionBar = replaceColor(config.getString("actionbar", defaultConfig.getString("actionbar", "")));

            fadein = secondToTick(config.getDouble("fadein", defaultConfig.getDouble("fadein", 0)));
            stay = secondToTick(config.getDouble("stay", defaultConfig.getDouble("stay", 0)));
            fadeout = secondToTick(config.getDouble("fadeout", defaultConfig.getDouble("fadeout", 0)));
        }

        private static int secondToTick(double seconds) {
            return (int) (seconds * 20);
        }
    }

    private static String replaceColor(String str) {
        if (str == null) {
            return null;
        }

        return ChatColor.translateAlternateColorCodes('&', str).replace(ChatColor.COLOR_CHAR + "&", "&");
    }
}
