package net.peligon.ChatColor;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        log("[Peligon Mini] Chatcolor has been enabled.");
    }

    public void onDisable() {
        log("[Peligon Mini] Chatcolor has been disabled.");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (player.hasPermission("Peligon.ChatColor.Chat.*") || player.hasPermission("Peligon.ChatColor.*")) {
            event.setMessage(ChatColor.translateAlternateColorCodes('&', message));
            return;
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.black")) {
            event.setMessage(message.replaceAll("&0", "§0"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.darkblue")) {
            event.setMessage(message.replaceAll("&1", "§1"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.darkgreen")) {
            event.setMessage(message.replaceAll("&2", "§2"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.darkaqua")) {
            event.setMessage(message.replaceAll("&3", "§3"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.darkred")) {
            event.setMessage(message.replaceAll("&4", "§4"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.darkpurple")) {
            event.setMessage(message.replaceAll("&5", "§5"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.gold")) {
            event.setMessage(message.replaceAll("&6", "§6"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.gray")) {
            event.setMessage(message.replaceAll("&7", "§7"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.darkgray")) {
            event.setMessage(message.replaceAll("&8", "§8"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.blue")) {
            event.setMessage(message.replaceAll("&9", "§9"));
        }

        if (player.hasPermission("Peligon.ChatColor.Chat.green")) {
            event.setMessage(message.replaceAll("&a", "§a"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.aqua")) {
            event.setMessage(message.replaceAll("&b", "§b"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.red")) {
            event.setMessage(message.replaceAll("&c", "§c"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.purple")) {
            event.setMessage(message.replaceAll("&d", "§d"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.yellow")) {
            event.setMessage(message.replaceAll("&e", "§e"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.white")) {
            event.setMessage(message.replaceAll("&f", "§f"));
        }

        if (player.hasPermission("Peligon.ChatColor.Chat.magic")) {
            event.setMessage(message.replaceAll("&k", "§k"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.bold")) {
            event.setMessage(message.replaceAll("&l", "§l"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.strikethrough")) {
            event.setMessage(message.replaceAll("&m", "§m"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.underline")) {
            event.setMessage(message.replaceAll("&n", "§n"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.italic")) {
            event.setMessage(message.replaceAll("&o", "§o"));
        }
        if (player.hasPermission("Peligon.ChatColor.Chat.reset")) {
            event.setMessage(message.replaceAll("&r", "§r"));
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Sign sign = (Sign) event.getBlock().getState();
        Player player = event.getPlayer();
        if (player.hasPermission("Peligon.ChatColor.Sign.*") || player.hasPermission("Peligon.ChatColor.*")) {
            event.setLine(0, ChatColor.translateAlternateColorCodes('&', event.getLine(0)));
            event.setLine(1, ChatColor.translateAlternateColorCodes('&', event.getLine(1)));
            event.setLine(2, ChatColor.translateAlternateColorCodes('&', event.getLine(2)));
            event.setLine(3, ChatColor.translateAlternateColorCodes('&', event.getLine(3)));
            sign.update(true);
            return;
        }

        if (player.hasPermission("Peligon.ChatColor.sign.black")) {
            event.setLine(0, event.getLine(0).replaceAll("&0", "§0"));
            event.setLine(1, event.getLine(1).replaceAll("&0", "§0"));
            event.setLine(2, event.getLine(2).replaceAll("&0", "§0"));
            event.setLine(3, event.getLine(3).replaceAll("&0", "§0"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.ChatColor.sign.darkblue")) {
            event.setLine(0, event.getLine(0).replaceAll("&1", "§1"));
            event.setLine(1, event.getLine(1).replaceAll("&1", "§1"));
            event.setLine(2, event.getLine(2).replaceAll("&1", "§1"));
            event.setLine(3, event.getLine(3).replaceAll("&1", "§1"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.ChatColor.sign.darkgreen")) {
            event.setLine(0, event.getLine(0).replaceAll("&2", "§2"));
            event.setLine(1, event.getLine(1).replaceAll("&2", "§2"));
            event.setLine(2, event.getLine(2).replaceAll("&2", "§2"));
            event.setLine(3, event.getLine(3).replaceAll("&2", "§2"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.ChatColor.sign.darkaqua")) {
            event.setLine(0, event.getLine(0).replaceAll("&3", "§3"));
            event.setLine(1, event.getLine(1).replaceAll("&3", "§3"));
            event.setLine(2, event.getLine(2).replaceAll("&3", "§3"));
            event.setLine(3, event.getLine(3).replaceAll("&3", "§3"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.ChatColor.sign.darkred")) {
            event.setLine(0, event.getLine(0).replaceAll("&4", "§4"));
            event.setLine(1, event.getLine(1).replaceAll("&4", "§4"));
            event.setLine(2, event.getLine(2).replaceAll("&4", "§4"));
            event.setLine(3, event.getLine(3).replaceAll("&4", "§4"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.ChatColor.sign.darkpurple")) {
            event.setLine(0, event.getLine(0).replaceAll("&5", "§5"));
            event.setLine(1, event.getLine(1).replaceAll("&5", "§5"));
            event.setLine(2, event.getLine(2).replaceAll("&5", "§5"));
            event.setLine(3, event.getLine(3).replaceAll("&5", "§5"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.ChatColor.sign.gold")) {
            event.setLine(0, event.getLine(0).replaceAll("&6", "§6"));
            event.setLine(1, event.getLine(1).replaceAll("&6", "§6"));
            event.setLine(2, event.getLine(2).replaceAll("&6", "§6"));
            event.setLine(3, event.getLine(3).replaceAll("&6", "§6"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.ChatColor.sign.gray")) {
            event.setLine(0, event.getLine(0).replaceAll("&7", "§7"));
            event.setLine(1, event.getLine(1).replaceAll("&7", "§7"));
            event.setLine(2, event.getLine(2).replaceAll("&7", "§7"));
            event.setLine(3, event.getLine(3).replaceAll("&7", "§7"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.ChatColor.sign.darkgray")) {
            event.setLine(0, event.getLine(0).replaceAll("&8", "§8"));
            event.setLine(1, event.getLine(1).replaceAll("&8", "§8"));
            event.setLine(2, event.getLine(2).replaceAll("&8", "§8"));
            event.setLine(3, event.getLine(3).replaceAll("&8", "§8"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.ChatColor.sign.blue")) {
            event.setLine(0, event.getLine(0).replaceAll("&9", "§9"));
            event.setLine(1, event.getLine(1).replaceAll("&9", "§9"));
            event.setLine(2, event.getLine(2).replaceAll("&9", "§9"));
            event.setLine(3, event.getLine(3).replaceAll("&9", "§9"));
            sign.update(true);
        }

        if (player.hasPermission("Peligon.ChatColor.sign.green")) {
            event.setLine(0, event.getLine(0).replaceAll("&a", "§a"));
            event.setLine(1, event.getLine(1).replaceAll("&a", "§a"));
            event.setLine(2, event.getLine(2).replaceAll("&a", "§a"));
            event.setLine(3, event.getLine(3).replaceAll("&a", "§a"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.aqua")) {
            event.setLine(0, event.getLine(0).replaceAll("&b", "§b"));
            event.setLine(1, event.getLine(1).replaceAll("&b", "§b"));
            event.setLine(2, event.getLine(2).replaceAll("&b", "§b"));
            event.setLine(3, event.getLine(3).replaceAll("&b", "§b"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.red")) {
            event.setLine(0, event.getLine(0).replaceAll("&c", "§c"));
            event.setLine(1, event.getLine(1).replaceAll("&c", "§c"));
            event.setLine(2, event.getLine(2).replaceAll("&c", "§c"));
            event.setLine(3, event.getLine(3).replaceAll("&c", "§c"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.purple")) {
            event.setLine(0, event.getLine(0).replaceAll("&d", "§d"));
            event.setLine(1, event.getLine(1).replaceAll("&d", "§d"));
            event.setLine(2, event.getLine(2).replaceAll("&d", "§d"));
            event.setLine(3, event.getLine(3).replaceAll("&d", "§d"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.yellow")) {
            event.setLine(0, event.getLine(0).replaceAll("&e", "§e"));
            event.setLine(1, event.getLine(1).replaceAll("&e", "§e"));
            event.setLine(2, event.getLine(2).replaceAll("&e", "§e"));
            event.setLine(3, event.getLine(3).replaceAll("&e", "§e"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.white")) {
            event.setLine(0, event.getLine(0).replaceAll("&f", "§f"));
            event.setLine(1, event.getLine(1).replaceAll("&f", "§f"));
            event.setLine(2, event.getLine(2).replaceAll("&f", "§f"));
            event.setLine(3, event.getLine(3).replaceAll("&f", "§f"));
        }

        if (player.hasPermission("Peligon.ChatColor.sign.magic")) {
            event.setLine(0, event.getLine(0).replaceAll("&k", "§k"));
            event.setLine(1, event.getLine(1).replaceAll("&k", "§k"));
            event.setLine(2, event.getLine(2).replaceAll("&k", "§k"));
            event.setLine(3, event.getLine(3).replaceAll("&k", "§k"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.bold")) {
            event.setLine(0, event.getLine(0).replaceAll("&l", "§l"));
            event.setLine(1, event.getLine(1).replaceAll("&l", "§l"));
            event.setLine(2, event.getLine(2).replaceAll("&l", "§l"));
            event.setLine(3, event.getLine(3).replaceAll("&l", "§l"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.strikethrough")) {
            event.setLine(0, event.getLine(0).replaceAll("&m", "§m"));
            event.setLine(1, event.getLine(1).replaceAll("&m", "§m"));
            event.setLine(2, event.getLine(2).replaceAll("&m", "§m"));
            event.setLine(3, event.getLine(3).replaceAll("&m", "§m"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.underline")) {
            event.setLine(0, event.getLine(0).replaceAll("&n", "§n"));
            event.setLine(1, event.getLine(1).replaceAll("&n", "§n"));
            event.setLine(2, event.getLine(2).replaceAll("&n", "§n"));
            event.setLine(3, event.getLine(3).replaceAll("&n", "§n"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.italic")) {
            event.setLine(0, event.getLine(0).replaceAll("&o", "§o"));
            event.setLine(1, event.getLine(1).replaceAll("&o", "§o"));
            event.setLine(2, event.getLine(2).replaceAll("&o", "§o"));
            event.setLine(3, event.getLine(3).replaceAll("&o", "§o"));
        }
        if (player.hasPermission("Peligon.ChatColor.sign.reset")) {
            event.setLine(0, event.getLine(0).replaceAll("&r", "§r"));
            event.setLine(1, event.getLine(1).replaceAll("&r", "§r"));
            event.setLine(2, event.getLine(2).replaceAll("&r", "§r"));
            event.setLine(3, event.getLine(3).replaceAll("&r", "§r"));
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

}
