package net.peligon.BlockedCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();

        log("[Peligon Mini] BlockedCommands has been enabled.");
    }

    public void onDisable() {
        log("[Peligon Mini] BlockedCommands has been disabled.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("Peligon.BlockedCommands.bypass")) return;
        for (String s : getConfig().getStringList("blocked-commands")) {
            if (event.getMessage().toLowerCase().startsWith(s.toLowerCase())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.blocked-command")));
            }
        }
    }

    private static void log(String message) { System.out.println(message); }
}
