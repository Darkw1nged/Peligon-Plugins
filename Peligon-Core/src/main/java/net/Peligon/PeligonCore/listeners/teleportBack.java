package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class teleportBack implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.hasPermission("Peligon.Core.TeleportBack") || player.hasPermission("Peligon.Core.*")) {
            Utils.lastLocation.put(player.getUniqueId(), player.getLocation());
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("Peligon.Core.TeleportBack") || player.hasPermission("Peligon.Core.*")) {
            Utils.lastLocation.put(player.getUniqueId(), player.getLocation());
        }
    }

}
