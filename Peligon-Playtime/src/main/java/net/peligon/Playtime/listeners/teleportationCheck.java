package net.peligon.Playtime.listeners;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.Utils;
import net.peligon.Playtime.libaries.playerUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class teleportationCheck implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        World world = event.getTo().getWorld();
        Player player = event.getPlayer();

        if (plugin.getConfig().getStringList("disabled-words").contains(world.getName())) {
            if (Utils.activeTimes.contains(player.getUniqueId())) {
                playerUtils.addPlaytime(player);
            }
            Utils.activeTimes.remove(event.getPlayer().getUniqueId());
            return;
        }

        if (!Utils.activeTimes.contains(event.getPlayer().getUniqueId())) {
            Utils.activeTimes.add(event.getPlayer().getUniqueId());
        }
    }

}
