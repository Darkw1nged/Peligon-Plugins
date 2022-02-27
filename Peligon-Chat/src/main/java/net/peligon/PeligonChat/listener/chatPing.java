package net.peligon.PeligonChat.listener;

import net.peligon.PeligonChat.Main;
import net.peligon.PeligonChat.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatPing implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (!plugin.getConfig().getBoolean("chat-pings.enabled", true)) return;
        if (plugin.getConfig().getStringList("chat-pings.disabled-worlds").contains(event.getPlayer().getWorld().getName())) return;

        for (Player pinged : Bukkit.getOnlinePlayers()) {
            String ping = "@" + pinged.getName().toLowerCase();
            if (!message.contains(ping)) return;
            if (event.getPlayer() == pinged) return;

            event.setMessage(message.replaceAll(ping, Utils.chatColor(plugin.getConfig().getString("chat-pings.color") + ping + "&f")));
            pinged.playSound(pinged.getLocation(), Sound.valueOf(plugin.getConfig().getString("chat-pings.sound").toUpperCase()), 1.2f, 0.5f);
        }
    }

}
