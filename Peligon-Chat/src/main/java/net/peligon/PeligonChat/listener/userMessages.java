package net.peligon.PeligonChat.listener;

import net.peligon.PeligonChat.Main;
import net.peligon.PeligonChat.libaries.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class userMessages implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            if (!plugin.serverTotal.hasTotal()) {
                plugin.serverTotal.createTotal();
            }
            plugin.serverTotal.addTotal(1);
            event.setJoinMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("total-join-message")
                    .replaceAll("%player%", event.getPlayer().getName())
                    .replaceAll("%total%", "" + plugin.serverTotal.getTotal())
            ));
            return;
        }

        if (plugin.getConfig().getBoolean("enable-join-message", true)) {
            event.setJoinMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("join-message")
                    .replaceAll("%player%", event.getPlayer().getName())
            ));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("enable-quit-message", true)) {
            event.setQuitMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("quit-message")
                    .replaceAll("%player%", event.getPlayer().getName())
            ));
        }
    }

}
