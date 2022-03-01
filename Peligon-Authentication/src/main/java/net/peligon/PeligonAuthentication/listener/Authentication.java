package net.peligon.PeligonAuthentication.listener;

import net.peligon.PeligonAuthentication.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

public class Authentication implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!Utils.neededAuthentication.contains(player.getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBeak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!Utils.neededAuthentication.contains(player.getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!Utils.neededAuthentication.contains(player.getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!Utils.neededAuthentication.contains(player.getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (!Utils.neededAuthentication.contains(player.getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!Utils.neededAuthentication.contains(player.getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!Utils.neededAuthentication.contains(player.getUniqueId())) return;
        Utils.neededAuthentication.remove(player.getUniqueId());
    }

}
