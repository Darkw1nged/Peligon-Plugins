package net.peligon.InstantRespawn;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        log("[Peligon Mini] InstantRespawn has been enabled.");
    }

    public void onDisable() {
        log("[Peligon Mini] InstantRespawn has been disabled.");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.hasPermission("Peligon.InstantRespawn.respawn")) {
            player.spigot().respawn();
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
