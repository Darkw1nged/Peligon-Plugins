package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class accountSetup implements Listener {

    @EventHandler
    public void Setup(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if the player has data.
        if (!playerUtils.hasData(player)) {
            playerUtils.createPlayerData(player);
        }
    }


}
