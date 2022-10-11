package net.peligon.Homes;

import net.peligon.Homes.commands.cmdDeleteHome;
import net.peligon.Homes.commands.cmdHome;
import net.peligon.Homes.commands.cmdHomes;
import net.peligon.Homes.commands.cmdSetHome;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    public static Main getInstance;
    public List<UUID> isTeleporting = new ArrayList<>();

    public void onEnable() {
        getInstance = this;
        saveDefaultConfig();

        getCommand("sethome").setExecutor(new cmdSetHome());
        getCommand("deletehome").setExecutor(new cmdDeleteHome());
        getCommand("home").setExecutor(new cmdHome());
        getCommand("homes").setExecutor(new cmdHomes());
        getServer().getPluginManager().registerEvents(this, this);

        log("[Peligon Mini] Homes has been enabled.");
    }

    public void onDisable() {
        log("[Peligon Mini] Homes has been disabled.");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (isTeleporting != null && isTeleporting.contains(player.getUniqueId())) {
            isTeleporting.remove(player.getUniqueId());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.teleport-cancelled")));
        }
    }

    private static void log(String message) { System.out.println(message); }
}
