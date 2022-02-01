package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class mobDropEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        // ---- [ Checking if the first feature is enabled ] ----
        if (plugin.getConfig().getBoolean("Monster Drops.show-xp-amount.enabled", true)) {
            // ---- [ Getting the entity ] ----
            Entity entity = event.getEntity();

            // ---- [ Checking if the entity is a mob ] ----
            if (entity instanceof Monster || entity instanceof Animals) {
                // ---- [ Validating xp drop amount ] ----
                if (event.getDroppedExp() <= 0) return;

                // ---- [ Spawning the hologram of xp amount ] ----
                Utils.hologram(Utils.chatColor(plugin.getConfig().getString("Monster Drops.show-xp-amount.format").replaceAll("%amount%", String.valueOf(event.getDroppedExp()))),
                        new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY() + .5, entity.getLocation().getZ()), 3);
            }
        }

        // ---- [ Checking if the second feature is enabled ] ----
        if (plugin.getConfig().getBoolean("Monster Drops.money-drops.enabled", true)) {
            // ---- [ Getting the entity ] ----
            Entity entity = event.getEntity();

            // ---- [ Checking if the entity is a mob ] ----
            if (entity instanceof Monster || entity instanceof Animals) {
                // ---- [ Checking if the mob is inside the list in the config.yml ] ----
                if (plugin.getConfig().getConfigurationSection("Monster Drops.money-drops.Types").getKeys(false).contains(entity.getType().name())) {
                    // ---- [ Checking the mob drop for money is enabled. ] ----
                    if (plugin.getConfig().getBoolean("Monster Drops.money-drops.Types" + entity.getType().name() + ".enabled")) {
                        // ---- [ Spawning the hologram of money amount ] ----
                        double amount = plugin.getConfig().getDouble("Monster Drops.money-drops.Types" + entity.getType().name() + ".amount");
                        Utils.hologram(plugin.getConfig().getString("Monster Drops.money-drops.format").replaceAll("%amount%", String.valueOf(amount)),
                                new Location(entity.getWorld(), entity.getLocation().getX() + new Random().nextFloat(), entity.getLocation().getY() + new Random().nextFloat(), entity.getLocation().getZ() + new Random().nextFloat()), 3);
                    }
                }
            }
        }
    }

}
