package net.peligon.JoinLeaveMessage.Listeners;

import net.peligon.JoinLeaveMessage.Main;
import net.peligon.JoinLeaveMessage.Utilities.Lists.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class coreEvent implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getConfig().getBoolean("disable-join-message", false)) {
            event.setJoinMessage(null);
        }

        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            plugin.getConfig().set("counter", plugin.getConfig().getInt("counter") + 1);
            if (plugin.getConfig().getBoolean("requires-permission", false)) {
                if (player.hasPermission(Permissions.VIP_join_permission.getPermission()) || player.hasPermission(Permissions.global_permission.getPermission())) {
                    event.setJoinMessage(plugin.fileMessage.getConfig().getString("first-join-message")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%count%", plugin.getConfig().getInt("counter") + ""));
                } else {
                    event.setJoinMessage(null);
                }
            } else {
                event.setJoinMessage(plugin.fileMessage.getConfig().getString("first-join-message")
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%count%", plugin.getConfig().getInt("counter") + ""));
            }
        } else {
            if (plugin.getConfig().getBoolean("requires-permission", false)) {
                if (player.hasPermission(Permissions.VIP_join_permission.getPermission()) || player.hasPermission(Permissions.global_permission.getPermission())) {
                    event.setJoinMessage(plugin.fileMessage.getConfig().getString("join-message")
                            .replaceAll("%player%", player.getName()));
                } else {
                    event.setJoinMessage(null);
                }
            } else {
                event.setJoinMessage(plugin.fileMessage.getConfig().getString("join-message")
                        .replaceAll("%player%", player.getName()));
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("disable-leave-message", false)) {
            event.setQuitMessage(null);
        }

        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("requires-permission", false)) {
            if (player.hasPermission(Permissions.VIP_leave_permission.getPermission()) || player.hasPermission(Permissions.global_permission.getPermission())) {
                event.setQuitMessage(plugin.fileMessage.getConfig().getString("leave-message")
                        .replaceAll("%player%", player.getName()));
            } else {
                event.setQuitMessage(null);
            }
        } else {
            event.setQuitMessage(plugin.fileMessage.getConfig().getString("leave-message")
                    .replaceAll("%player%", player.getName()));
        }
    }

}
