package net.peligon.Playtime.listeners;

import net.peligon.Playtime.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class playTimeManage implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.playerTime.hasData(event.getPlayer())) {
            plugin.playerTime.createData(event.getPlayer());
        } else {
            plugin.playerTime.setLastUpdated(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.playerTime.hasData(event.getPlayer())) {
            plugin.playerTime.addTime(event.getPlayer());
        }
    }

}
