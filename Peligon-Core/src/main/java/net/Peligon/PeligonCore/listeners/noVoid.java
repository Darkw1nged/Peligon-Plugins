package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class noVoid implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("no-void")) {
            if (event.getTo().getY() < -70) {
                CustomConfig spawnConfig = new CustomConfig(plugin, "spawn", "");

                if (!spawnConfig.getCustomConfigFile().exists()) {
                    event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
                    return;
                }

                Location location = new Location(
                        Bukkit.getWorld(spawnConfig.getConfig().getString("world")),
                        spawnConfig.getConfig().getDouble("x"),
                        spawnConfig.getConfig().getDouble("y"),
                        spawnConfig.getConfig().getDouble("z"),
                        (float) spawnConfig.getConfig().getDouble("yaw"),
                        (float) spawnConfig.getConfig().getDouble("pitch")
                );

                event.getPlayer().teleport(location);
            }
        }
    }

}
