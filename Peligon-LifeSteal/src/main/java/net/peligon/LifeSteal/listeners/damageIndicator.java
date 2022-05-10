package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class damageIndicator implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("damage-indicator")) {
            Entity entity = event.getEntity();
            Location entityLocation = entity.getLocation();
            double height = (entityLocation.getY() - 1) + entity.getHeight();
            if (entity.getHeight() > 1.8) {
                height -= .25;
            }
            Location loc = new Location(entity.getWorld(), entityLocation.getX(), height, entityLocation.getZ());
            String formatDamage = String.format("%.0f", event.getDamage());

            if (event.getDamager() instanceof Player) {
                if (plugin.getConfig().getBoolean("damage-indicator.only-players", false)) {
                    if (entity instanceof Player) {
                        Utils.moveUpHologram(plugin.getConfig().getString("damage-indicator.text", "&c+%damage%").replaceAll("%damage%", formatDamage),
                                loc, plugin.getConfig().getInt("damage-indicator.length", 5));
                    }
                    return;
                }
                Utils.moveUpHologram(plugin.getConfig().getString("damage-indicator.text", "&c+%damage%").replaceAll("%damage%", formatDamage),
                        loc, plugin.getConfig().getInt("damage-indicator.length", 5));
            }
        }

    }

}
