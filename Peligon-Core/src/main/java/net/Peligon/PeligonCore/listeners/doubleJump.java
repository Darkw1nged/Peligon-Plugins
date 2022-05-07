package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class doubleJump implements Listener {

    private final Main plugin = Main.getInstance;
    private final HashMap<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getStringList("Events").contains("double-jump")) {
            if (player.hasPermission("Peligon.Core.Double-Jump") || player.hasPermission("Peligon.Core.*")) {
                player.setAllowFlight(true);
            }
        }
    }

    @EventHandler
    public void onJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getStringList("Events").contains("double-jump")) {
            if (player.hasPermission("Peligon.Core.Double-Jump") || player.hasPermission("Peligon.Core.*")) {
                switch (player.getGameMode()) {
                    case CREATIVE:
                    case SPECTATOR:
                        player.setFlying(true);
                        player.setAllowFlight(true);
                        break;
                    case SURVIVAL:
                    case ADVENTURE:
                        if (player.hasPermission("Peligon.Core.Double-Jump.Bypass")) {
                            LaunchPlayer(event, player);
                        }
                        long timeLeft = ((cooldown.get(player.getUniqueId()) / 1000) + plugin.getConfig().getInt("Double-Jump.delay")) - (System.currentTimeMillis() / 1000);
                        if (timeLeft > 0) {
                            LaunchPlayer(event, player);
                            return;
                        }
                        cooldown.remove(player.getUniqueId());
                }
            }
        }
    }

    public void LaunchPlayer(PlayerToggleFlightEvent event, Player player) {
        event.setCancelled(true);
        Block block = player.getWorld().getBlockAt(player.getLocation().subtract(0.0D, 2.0D, 0.0D));
        if (!block.getType().equals(Material.AIR)) {
            Vector vector = player.getLocation().getDirection().multiply(1).setY(1);
            player.setVelocity(vector);
        }
    }

}
