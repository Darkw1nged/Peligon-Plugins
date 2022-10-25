package net.peligon.Playtime.listeners;

import net.peligon.Playtime.libaries.Utils;
import net.peligon.Playtime.libaries.playerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class updatePlayerPlaytime implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!playerUtils.hasData(player)) {
            playerUtils.createPlayerData(player);
            Utils.activeTimes.add(player.getUniqueId());
            return;
        }
        playerUtils.setLastUpdated(player);
        Utils.activeTimes.add(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (Utils.activeTimes.contains(player.getUniqueId())) {
            playerUtils.addPlaytime(player);
        }
        Utils.activeTimes.remove(player.getUniqueId());
    }

}
