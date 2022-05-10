package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class accountSetup implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.lives.hasData(player)) {
            plugin.lives.createData(player, plugin.getConfig().getInt("Storage.starting-lives"));
        }
    }

}
