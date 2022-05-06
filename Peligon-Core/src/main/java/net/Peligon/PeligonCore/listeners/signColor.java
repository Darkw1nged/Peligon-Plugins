package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class signColor implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Sign sign = (Sign) event.getBlock().getState();
        Player player = event.getPlayer();

        if (player.hasPermission("Peligon.Chat.*") || player.hasPermission("Peligon.Chat.signColor.*")) {
            event.setLine(0, Utils.chatColor(event.getLine(0)));
            event.setLine(1, Utils.chatColor(event.getLine(1)));
            event.setLine(2, Utils.chatColor(event.getLine(2)));
            event.setLine(3, Utils.chatColor(event.getLine(3)));
            sign.update(true);
            return;
        }

        if (player.hasPermission("Peligon.Chat.signColor.black")) {
            event.setLine(0, event.getLine(0).replaceAll("&0", "§0"));
            event.setLine(1, event.getLine(1).replaceAll("&0", "§0"));
            event.setLine(2, event.getLine(2).replaceAll("&0", "§0"));
            event.setLine(3, event.getLine(3).replaceAll("&0", "§0"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.Chat.signColor.darkblue")) {
            event.setLine(0, event.getLine(0).replaceAll("&1", "§1"));
            event.setLine(1, event.getLine(1).replaceAll("&1", "§1"));
            event.setLine(2, event.getLine(2).replaceAll("&1", "§1"));
            event.setLine(3, event.getLine(3).replaceAll("&1", "§1"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.Chat.signColor.darkgreen")) {
            event.setLine(0, event.getLine(0).replaceAll("&2", "§2"));
            event.setLine(1, event.getLine(1).replaceAll("&2", "§2"));
            event.setLine(2, event.getLine(2).replaceAll("&2", "§2"));
            event.setLine(3, event.getLine(3).replaceAll("&2", "§2"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.Chat.signColor.darkaqua")) {
            event.setLine(0, event.getLine(0).replaceAll("&3", "§3"));
            event.setLine(1, event.getLine(1).replaceAll("&3", "§3"));
            event.setLine(2, event.getLine(2).replaceAll("&3", "§3"));
            event.setLine(3, event.getLine(3).replaceAll("&3", "§3"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.Chat.signColor.darkred")) {
            event.setLine(0, event.getLine(0).replaceAll("&4", "§4"));
            event.setLine(1, event.getLine(1).replaceAll("&4", "§4"));
            event.setLine(2, event.getLine(2).replaceAll("&4", "§4"));
            event.setLine(3, event.getLine(3).replaceAll("&4", "§4"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.Chat.signColor.darkpurple")) {
            event.setLine(0, event.getLine(0).replaceAll("&5", "§5"));
            event.setLine(1, event.getLine(1).replaceAll("&5", "§5"));
            event.setLine(2, event.getLine(2).replaceAll("&5", "§5"));
            event.setLine(3, event.getLine(3).replaceAll("&5", "§5"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.Chat.signColor.gold")) {
            event.setLine(0, event.getLine(0).replaceAll("&6", "§6"));
            event.setLine(1, event.getLine(1).replaceAll("&6", "§6"));
            event.setLine(2, event.getLine(2).replaceAll("&6", "§6"));
            event.setLine(3, event.getLine(3).replaceAll("&6", "§6"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.Chat.signColor.gray")) {
            event.setLine(0, event.getLine(0).replaceAll("&7", "§7"));
            event.setLine(1, event.getLine(1).replaceAll("&7", "§7"));
            event.setLine(2, event.getLine(2).replaceAll("&7", "§7"));
            event.setLine(3, event.getLine(3).replaceAll("&7", "§7"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.Chat.signColor.darkgray")) {
            event.setLine(0, event.getLine(0).replaceAll("&8", "§8"));
            event.setLine(1, event.getLine(1).replaceAll("&8", "§8"));
            event.setLine(2, event.getLine(2).replaceAll("&8", "§8"));
            event.setLine(3, event.getLine(3).replaceAll("&8", "§8"));
            sign.update(true);
        }
        if (player.hasPermission("Peligon.Chat.signColor.blue")) {
            event.setLine(0, event.getLine(0).replaceAll("&9", "§9"));
            event.setLine(1, event.getLine(1).replaceAll("&9", "§9"));
            event.setLine(2, event.getLine(2).replaceAll("&9", "§9"));
            event.setLine(3, event.getLine(3).replaceAll("&9", "§9"));
            sign.update(true);
        }

        if (player.hasPermission("Peligon.Chat.signColor.green")) {
            event.setLine(0, event.getLine(0).replaceAll("&a", "§a"));
            event.setLine(1, event.getLine(1).replaceAll("&a", "§a"));
            event.setLine(2, event.getLine(2).replaceAll("&a", "§a"));
            event.setLine(3, event.getLine(3).replaceAll("&a", "§a"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.aqua")) {
            event.setLine(0, event.getLine(0).replaceAll("&b", "§b"));
            event.setLine(1, event.getLine(1).replaceAll("&b", "§b"));
            event.setLine(2, event.getLine(2).replaceAll("&b", "§b"));
            event.setLine(3, event.getLine(3).replaceAll("&b", "§b"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.red")) {
            event.setLine(0, event.getLine(0).replaceAll("&c", "§c"));
            event.setLine(1, event.getLine(1).replaceAll("&c", "§c"));
            event.setLine(2, event.getLine(2).replaceAll("&c", "§c"));
            event.setLine(3, event.getLine(3).replaceAll("&c", "§c"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.purple")) {
            event.setLine(0, event.getLine(0).replaceAll("&d", "§d"));
            event.setLine(1, event.getLine(1).replaceAll("&d", "§d"));
            event.setLine(2, event.getLine(2).replaceAll("&d", "§d"));
            event.setLine(3, event.getLine(3).replaceAll("&d", "§d"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.yellow")) {
            event.setLine(0, event.getLine(0).replaceAll("&e", "§e"));
            event.setLine(1, event.getLine(1).replaceAll("&e", "§e"));
            event.setLine(2, event.getLine(2).replaceAll("&e", "§e"));
            event.setLine(3, event.getLine(3).replaceAll("&e", "§e"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.white")) {
            event.setLine(0, event.getLine(0).replaceAll("&f", "§f"));
            event.setLine(1, event.getLine(1).replaceAll("&f", "§f"));
            event.setLine(2, event.getLine(2).replaceAll("&f", "§f"));
            event.setLine(3, event.getLine(3).replaceAll("&f", "§f"));
        }

        if (player.hasPermission("Peligon.Chat.signColor.magic")) {
            event.setLine(0, event.getLine(0).replaceAll("&k", "§k"));
            event.setLine(1, event.getLine(1).replaceAll("&k", "§k"));
            event.setLine(2, event.getLine(2).replaceAll("&k", "§k"));
            event.setLine(3, event.getLine(3).replaceAll("&k", "§k"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.bold")) {
            event.setLine(0, event.getLine(0).replaceAll("&l", "§l"));
            event.setLine(1, event.getLine(1).replaceAll("&l", "§l"));
            event.setLine(2, event.getLine(2).replaceAll("&l", "§l"));
            event.setLine(3, event.getLine(3).replaceAll("&l", "§l"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.strikethrough")) {
            event.setLine(0, event.getLine(0).replaceAll("&m", "§m"));
            event.setLine(1, event.getLine(1).replaceAll("&m", "§m"));
            event.setLine(2, event.getLine(2).replaceAll("&m", "§m"));
            event.setLine(3, event.getLine(3).replaceAll("&m", "§m"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.underline")) {
            event.setLine(0, event.getLine(0).replaceAll("&n", "§n"));
            event.setLine(1, event.getLine(1).replaceAll("&n", "§n"));
            event.setLine(2, event.getLine(2).replaceAll("&n", "§n"));
            event.setLine(3, event.getLine(3).replaceAll("&n", "§n"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.italic")) {
            event.setLine(0, event.getLine(0).replaceAll("&o", "§o"));
            event.setLine(1, event.getLine(1).replaceAll("&o", "§o"));
            event.setLine(2, event.getLine(2).replaceAll("&o", "§o"));
            event.setLine(3, event.getLine(3).replaceAll("&o", "§o"));
        }
        if (player.hasPermission("Peligon.Chat.signColor.reset")) {
            event.setLine(0, event.getLine(0).replaceAll("&r", "§r"));
            event.setLine(1, event.getLine(1).replaceAll("&r", "§r"));
            event.setLine(2, event.getLine(2).replaceAll("&r", "§r"));
            event.setLine(3, event.getLine(3).replaceAll("&r", "§r"));
        }
    }

}
