package net.peligon.AutoPickup.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {

    /**
     * This will send a message to the console.
     * !! Does not support color codes !!
     *
     * @param message The message to send to the console.
     */
    public void systemLog(String message) {
        System.out.println(message);
    }

    /**
     * This will send a message to the console.
     * !! Supports color codes !!
     *
     * @param message The message to send to the console.
     */
    public void log(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * This will check if a string is only letters.
     *
     * @param string The string to check.
     * @return True if the string is only letters, false if not.
     */
    public boolean isOnlyLetters(String string) {
        return !string.matches("[a-zA-Z]+");
    }

    /**
     * This will replace all color codes in a string.
     *
     * @param string The string to replace color codes in.
     * @return The string with replaced color codes.
     */
    public String chatColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * This will replace all color codes in a list of strings.
     *
     * @param list The list of strings to replace color codes in.
     * @return The list of strings with replaced color codes.
     */
    public List<String> chatColor(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (String word : list) {
            newList.add(ChatColor.translateAlternateColorCodes('&', word));
        }
        return newList;
    }

    /**
     * This will add commas to a number (Integer).
     * This is useful if you need something like "1,000,000" instead of "1000000".
     *
     * @param number The number to add commas to.
     * @return The number with commas.
     */
    public String formatAmount(int number) {
        return NumberFormat.getInstance(new Locale("en", "US")).format(number);
    }

    /**
     * This will add commas to a number (Double).
     * This is useful if you need something like "1,000,000" instead of "1000000".
     *
     * @param number The number to add commas to.
     * @return The number with commas.
     */
    public String formatAmount(double number) {
        return NumberFormat.getInstance(new Locale("en", "US")).format(number);
    }

    /**
     * Formatting material names.
     * This will remove underscores and capitalize the first letter of each word.
     *
     * @param name The name to format.
     * @return The formatted name.
     */
    public String formatMaterialName(String name) {
        String[] words = name.toLowerCase().split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            stringBuilder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        return stringBuilder.toString().trim();
    }

}
