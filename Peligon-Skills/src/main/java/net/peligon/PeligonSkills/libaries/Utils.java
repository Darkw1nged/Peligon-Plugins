package net.peligon.PeligonSkills.libaries;

import org.bukkit.ChatColor;

public class Utils {

    // ---- [ Managing Color chat within the plugin ] ----
    public static String chatColor(String s) { return ChatColor.translateAlternateColorCodes('&', s); }

    // ---- [ Cached Items ] ----

    // ---- [ XP Equation ] ----
    public static Integer neededExperience(int level) {
        return (int) (5 * (Math.pow(level, 2)) + (50 * level) + 100);
    }

}
