package net.peligon.PeligonLifeSteal.listeners;

import net.peligon.PeligonLifeSteal.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AccountSetup implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.lives.hasData(player)) {
            plugin.lives.createData(player, plugin.getConfig().getInt("Basic-Settings.starting-lives"));
        }
    }

}
