package net.peligon.Playtime.libaries;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {

    // Manage all ChatColor within the plugin
    public static String chatColor(String s) {
        return s == null ? null : ChatColor.translateAlternateColorCodes('&', s);
    }

    // Storing all active playtimes in a list.
    // Note this is all the playtimes that will be counted.
    public static List<UUID> activeTimes = new ArrayList<>();

}
