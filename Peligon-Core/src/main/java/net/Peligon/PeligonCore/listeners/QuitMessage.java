package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitMessage implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("player-leave-message")) {
            event.setQuitMessage(plugin.fileMessage.getConfig().getString("player-leave-message").replaceAll("%player%", event.getPlayer().getName()));
        } else {
            event.setQuitMessage(null);
        }
    }

}
