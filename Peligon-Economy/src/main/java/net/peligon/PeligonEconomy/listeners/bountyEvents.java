package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
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
            Utils.bounties.remove(playerUUID);

            // ---- [ Checking if player was killed by another player ] ----
            if (killer != null) {
                UUID killerUUID = killer.getUniqueId();

                // ---- [ Adding money to killers balance if player had a bounty ] ----
                if (Utils.bounties.containsKey(playerUUID)) {
                    plugin.Economy.AddAccount(killer, Utils.bounties.get(playerUUID));
                }

                // ---- [ Managing killers kill steak ] ----
                if (Utils.KillStreak.containsKey(killerUUID)) {
                    Utils.KillStreak.put(killerUUID, Utils.KillStreak.get(killerUUID) + 1);
                } else {
                    Utils.KillStreak.put(killerUUID, 1);
                }

                // ---- [ Adding money to players bounty if they
                if (Utils.KillStreak.containsKey(killerUUID) && Utils.KillStreak.get(killerUUID) >= plugin.getConfig().getInt("Bounties.minimum-kill-streak")) {
                    if (Utils.bounties != null && Utils.bounties.containsKey(killerUUID)) {
                        Utils.bounties.put(killerUUID, Utils.bounties.get(killerUUID) + plugin.getConfig().getDouble("Bounties.amount"));
                    } else {
                        Utils.bounties.put(killerUUID, plugin.getConfig().getDouble("Bounties.amount"));
                    }

                }
            }
        }
    }

}
