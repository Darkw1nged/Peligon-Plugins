package net.peligon.PeligonTNTTag.libariesV2;

import net.peligon.PeligonTNTTag.libariesV2.enums.MessageCenter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatUtil {

    public static ChatUtil getInstance;
    public ChatUtil() {
        getInstance = this;
    }

    public String chatColor(String toTranslate) { return ChatColor.translateAlternateColorCodes('&', toTranslate); }
    public String formatNumber(double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }
    public List<String> getConvertedLore(FileConfiguration configuration, String path) {
        if (configuration == null) return null;
        List<String> oldList = configuration.getStringList(path);
        List<String> newList = new ArrayList<>();
        for (String a : oldList)
            newList.add(ChatColor.translateAlternateColorCodes('&', a));
        return newList;
    }
    public String replaceAllColorCodes(String toEdit) {
        return toEdit.replaceAll("&a", "")
                .replaceAll("&b", "")
                .replaceAll("&c", "")
                .replaceAll("&d", "")
                .replaceAll("&e", "")
                .replaceAll("&f", "")
                .replaceAll("&1", "")
                .replaceAll("&2", "")
                .replaceAll("&3", "")
                .replaceAll("&4", "")
                .replaceAll("&5", "")
                .replaceAll("&6", "")
                .replaceAll("&7", "")
                .replaceAll("&8", "")
                .replaceAll("&9", "")
                .replaceAll("&k", "")
                .replaceAll("&l", "")
                .replaceAll("&m", "")
                .replaceAll("&n", "")
                .replaceAll("&o", "")
                .replaceAll("&r", "");

    }
    public void log(String message) { System.out.println(message); }

    public void broadcastMessage(String message) { Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message)); }
    public void sendPlayerMessage(Player player, String message) { player.sendMessage(ChatColor.translateAlternateColorCodes('&', message)); }
    public void sendAllPlayersMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
    public String centerMessage(String message) {
        String[] lines = ChatColor.translateAlternateColorCodes('&', message).split("\n", 40);
        StringBuilder returnMessage = new StringBuilder();

        for (String line : lines) {
            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;

            for (char c : line.toCharArray()) {
                if (c == '§') {
                    previousCode = true;
                } else if (previousCode) {
                    previousCode = false;
                    isBold = c == 'l';
                } else {
                    MessageCenter dFI = MessageCenter.getDefaultFontInfo(c);
                    messagePxSize = isBold ? messagePxSize + dFI.getBoldLength() : messagePxSize + dFI.getLength();
                    messagePxSize++;
                }
            }
            int toCompensate = 154 - messagePxSize / 2;
            int spaceLength = MessageCenter.SPACE.getLength() + 1;
            int compensated = 0;
            StringBuilder sb = new StringBuilder();
            while (compensated < toCompensate) {
                sb.append(" ");
                compensated += spaceLength;
            }
            returnMessage.append(sb).append(line).append("\n");
        }

        return returnMessage.toString();
    }

}
