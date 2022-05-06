package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class keepExperience implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.getConfig().getStringList("Events").contains("keep-experience")) {
            if (player.hasPermission("Peligon.LifeSteal.Keep.Experience") || player.hasPermission("Peligon.LifeSteal.*")) {
                event.setKeepLevel(true);
                event.setDroppedExp(0);
            }
        }
    }

}
