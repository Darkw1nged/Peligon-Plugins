package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinMessage implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("player-join-message")) {
            event.setJoinMessage(plugin.fileMessage.getConfig().getString("player-join-message").replaceAll("%player%", event.getPlayer().getName()));
        } else {
            event.setJoinMessage(null);
        }
    }

}
