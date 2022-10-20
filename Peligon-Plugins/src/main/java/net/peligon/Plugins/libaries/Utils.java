package net.peligon.Plugins.libaries;

import org.bukkit.ChatColor;

public class Utils {

    public static String chatColor(String s) {
        return s == null ? null : ChatColor.translateAlternateColorCodes('&', s);
    }

}
