package net.Peligon.PeligonCore.listeners;

import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class godmode implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (Utils.godmode.contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

}
