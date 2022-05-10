package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class autoRespawn implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.getConfig().getStringList("Events").contains("auto-respawn")) {
            player.spigot().respawn();
        }
    }

}
