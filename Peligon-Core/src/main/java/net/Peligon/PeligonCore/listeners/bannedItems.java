package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class bannedItems implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onPickup(InventoryPickupItemEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("banned-items")) {
            if (plugin.getConfig().getStringList("banned-items.types").contains(event.getItem().getItemStack().getType().toString())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(EntityDropItemEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("banned-items")) {
            if (plugin.getConfig().getStringList("banned-items.types").contains(event.getItemDrop().getItemStack().getType().toString())) {
                event.getItemDrop().getItemStack().setType(Material.AIR);
            }
        }
    }

}
