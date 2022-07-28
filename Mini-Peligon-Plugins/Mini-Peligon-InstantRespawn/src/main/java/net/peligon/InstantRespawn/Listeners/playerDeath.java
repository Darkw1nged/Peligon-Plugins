package net.peligon.InstantRespawn.Listeners;

import net.peligon.InstantRespawn.Utilities.Lists.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class playerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.hasPermission(Permissions.respawn_permission.getPermission())) {
            player.spigot().respawn();
        }
    }

}
