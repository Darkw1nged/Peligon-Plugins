package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class bountyEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        // ---- [ Checking if the feature is enabled ] ----
        if (plugin.getConfig().getBoolean("Bounties.enabled", true)) {
            Player player = event.getEntity();
            Player killer = player.getKiller();
            UUID playerUUID = player.getUniqueId();

            // ---- [ Removing players kill streak if they have one ] ----
            Utils.KillStreak.remove(playerUUID);
            if (plugin.lives.hasData(player)) {
                plugin.bounties.setBounty(player, 0);
            }

            // ---- [ Checking if player was killed by another player ] ----
            if (killer != null) {
                UUID killerUUID = killer.getUniqueId();

                // ---- [ Adding money to killers balance if player had a bounty ] ----
                if (plugin.lives.hasData(player)) {
                    plugin.getEconomy().depositPlayer(killer, plugin.bounties.getBounty(player));
                }

                // ---- [ Managing killers kill steak ] ----
                if (Utils.KillStreak.containsKey(killerUUID)) {
                    Utils.KillStreak.put(killerUUID, Utils.KillStreak.get(killerUUID) + 1);
                } else {
                    Utils.KillStreak.put(killerUUID, 1);
                }

                // ---- [ Adding money to players bounty if they
                if (Utils.KillStreak.containsKey(killerUUID) && Utils.KillStreak.get(killerUUID) >= plugin.getConfig().getInt("Bounties.minimum-kill-streak")) {
                    if (plugin.lives.hasData(player)) {
                        plugin.bounties.addBounty(player, plugin.getConfig().getInt("Bounties.bounty-per-kill"));
                    }
                }
            }
        }
    }

}
