package net.Peligon.PeligonCore.listeners;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class teleportCancel implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Utils.isTeleporting != null && Utils.isTeleporting.contains(player.getUniqueId())) {
            Utils.isTeleporting.remove(player.getUniqueId());
            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("teleport-canceled")));
        }
    }

}
