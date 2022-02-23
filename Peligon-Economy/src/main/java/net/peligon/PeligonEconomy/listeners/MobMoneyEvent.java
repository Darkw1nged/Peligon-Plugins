package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobMoneyEvent implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Location entityLocation = entity.getLocation();
        double height = (entityLocation.getY() - 1) + entity.getHeight();
        if (entity.getHeight() > 1.8) {
            height -= .25;
        }
        if (event.getEntity().getKiller() instanceof Player) {
            if (entity instanceof Animals || entity instanceof Monster) {
                Location loc = new Location(entity.getWorld(), entityLocation.getX(), height, entityLocation.getZ());
                if (plugin.getConfig().getBoolean("Mob-Rewards.money-drop-types." + entity.getName().toUpperCase() + ".enable", true)) {
                    Utils.moveUpHologram("&2+$" + plugin.getConfig().getInt("Mob-Rewards.money-drop-types." + entity.getName().toUpperCase() + ".reward"), loc, 2);
                    plugin.Economy.AddAccount(event.getEntity().getKiller(), plugin.getConfig().getInt("Mob-Rewards.money-drop-types." + entity.getName().toUpperCase() + ".reward"));
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ArmorStand) {
            if (!entity.hasMetadata("hologram")) return;
            event.setCancelled(true);
        }
    }

}
