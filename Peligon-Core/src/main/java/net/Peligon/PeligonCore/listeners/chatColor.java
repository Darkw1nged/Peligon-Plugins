package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatColor implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (plugin.getConfig().getStringList("Events").contains("chat-color")) {
            if (player.hasPermission("Peligon.Chat.*") || player.hasPermission("Peligon.Chat.chatColor.*")) {
                event.setMessage(Utils.chatColor(message));
                return;
            }
            if (player.hasPermission("Peligon.Chat.chatColor.black")) {
                event.setMessage(message.replaceAll("&0", "§0"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.darkblue")) {
                event.setMessage(message.replaceAll("&1", "§1"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.darkgreen")) {
                event.setMessage(message.replaceAll("&2", "§2"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.darkaqua")) {
                event.setMessage(message.replaceAll("&3", "§3"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.darkred")) {
                event.setMessage(message.replaceAll("&4", "§4"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.darkpurple")) {
                event.setMessage(message.replaceAll("&5", "§5"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.gold")) {
                event.setMessage(message.replaceAll("&6", "§6"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.gray")) {
                event.setMessage(message.replaceAll("&7", "§7"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.darkgray")) {
                event.setMessage(message.replaceAll("&8", "§8"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.blue")) {
                event.setMessage(message.replaceAll("&9", "§9"));
            }

            if (player.hasPermission("Peligon.Chat.chatColor.green")) {
                event.setMessage(message.replaceAll("&a", "§a"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.aqua")) {
                event.setMessage(message.replaceAll("&b", "§b"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.red")) {
                event.setMessage(message.replaceAll("&c", "§c"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.purple")) {
                event.setMessage(message.replaceAll("&d", "§d"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.yellow")) {
                event.setMessage(message.replaceAll("&e", "§e"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.white")) {
                event.setMessage(message.replaceAll("&f", "§f"));
            }

            if (player.hasPermission("Peligon.Chat.chatColor.magic")) {
                event.setMessage(message.replaceAll("&k", "§k"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.bold")) {
                event.setMessage(message.replaceAll("&l", "§l"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.strikethrough")) {
                event.setMessage(message.replaceAll("&m", "§m"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.underline")) {
                event.setMessage(message.replaceAll("&n", "§n"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.italic")) {
                event.setMessage(message.replaceAll("&o", "§o"));
            }
            if (player.hasPermission("Peligon.Chat.chatColor.reset")) {
                event.setMessage(message.replaceAll("&r", "§r"));
            }
        }
    }

}
