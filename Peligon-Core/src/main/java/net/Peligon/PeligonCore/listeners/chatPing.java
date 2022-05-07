package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
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
        if (!plugin.getConfig().getStringList("Events").contains("chat-ping")) return;
        if (plugin.fileChatSettings.getConfig().getStringList("Chat-Pings.disabled-worlds").contains(event.getPlayer().getWorld().getName())) return;

        for (Player pinged : Bukkit.getOnlinePlayers()) {
            String ping = "@" + pinged.getName().toLowerCase();
            if (!message.toLowerCase().contains(ping)) continue;
            if (event.getPlayer() == pinged) continue;

            event.setMessage(Utils.chatColor(message.replaceAll(ping, plugin.fileChatSettings.getConfig().getString("Chat-Pings.color") + ping)));
            pinged.playSound(pinged.getLocation(), Sound.valueOf(plugin.fileChatSettings.getConfig().getString("Chat-Pings.sound").toUpperCase()),
                    Float.parseFloat(plugin.fileChatSettings.getConfig().getString("Chat-Pings.sound-volume", "1.2")),
                    Float.parseFloat(plugin.fileChatSettings.getConfig().getString("Chat-Pings.sound-pitch", "0.5")));
        }
    }

}
