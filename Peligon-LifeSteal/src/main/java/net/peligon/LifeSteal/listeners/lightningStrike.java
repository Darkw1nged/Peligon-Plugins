package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class lightningStrike implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (plugin.getConfig().getStringList("Events").contains("lightning-strike-on-death")) {
            if (plugin.getConfig().getBoolean("Lightning-on-death.only-players", false)) {
                if (player.getKiller() != null) {
                    if (plugin.getConfig().getInt("Lightning-on-death.execute-at-lives", -1) == -1) {
                        player.getWorld().strikeLightning(player.getLocation());
                    } else {
                        if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() == plugin.getConfig().getInt("Lightning-on-death.execute-at-lives", -1)) {
                            player.getWorld().strikeLightning(player.getLocation());
                        }
                    }
                }
            } else {
                if (plugin.getConfig().getInt("Lightning-on-death.execute-at-lives", -1) == -1) {
                    player.getWorld().strikeLightning(player.getLocation());
                } else {
                    if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() == plugin.getConfig().getInt("Lightning-on-death.execute-at-lives", -1)) {
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }
            }
        }
    }

}
