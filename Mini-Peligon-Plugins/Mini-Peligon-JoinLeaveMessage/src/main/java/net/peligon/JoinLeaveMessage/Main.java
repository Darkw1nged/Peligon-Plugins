package net.peligon.JoinLeaveMessage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        log("[Peligon Mini] JoinLeaveMessage has been enabled.");
    }

    public void onDisable() {
        log("[Peligon Mini] JoinLeaveMessage has been disabled.");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (getConfig().getBoolean("disable-join-message", false)) {
            event.setJoinMessage(null);
        }

        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            getConfig().set("counter", getConfig().getInt("counter") + 1);
            if (getConfig().getBoolean("requires-permission", false)) {
                if (player.hasPermission("Peligon.JoinLeave.VIP.join") || player.hasPermission("Peligon.JoinLeave.*")) {
                    event.setJoinMessage(getConfig().getString("messages.first-join-message")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%count%", getConfig().getInt("counter") + ""));
                } else {
                    event.setJoinMessage(null);
                }
            } else {
                event.setJoinMessage(getConfig().getString("messages.first-join-message")
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%count%", getConfig().getInt("counter") + ""));
            }
        } else {
            if (getConfig().getBoolean("requires-permission", false)) {
                if (player.hasPermission("Peligon.JoinLeave.VIP.join") || player.hasPermission("Peligon.JoinLeave.*")) {
                    event.setJoinMessage(getConfig().getString("messages.join-message")
                            .replaceAll("%player%", player.getName()));
                } else {
                    event.setJoinMessage(null);
                }
            } else {
                event.setJoinMessage(getConfig().getString("messages.join-message")
                        .replaceAll("%player%", player.getName()));
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (getConfig().getBoolean("disable-leave-message", false)) {
            event.setQuitMessage(null);
        }

        Player player = event.getPlayer();
        if (getConfig().getBoolean("requires-permission", false)) {
            if (player.hasPermission("Peligon.JoinLeave.VIP.leave") || player.hasPermission("Peligon.JoinLeave.*")) {
                event.setQuitMessage(getConfig().getString("messages.leave-message")
                        .replaceAll("%player%", player.getName()));
            } else {
                event.setQuitMessage(null);
            }
        } else {
            event.setQuitMessage(getConfig().getString("messages.leave-message")
                    .replaceAll("%player%", player.getName()));
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

}
