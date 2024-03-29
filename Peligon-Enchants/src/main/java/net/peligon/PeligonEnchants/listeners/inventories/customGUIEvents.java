package net.peligon.PeligonEnchants.listeners.inventories;

import net.peligon.PeligonEnchants.Main;
import net.peligon.PeligonEnchants.libaries.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public class customGUIEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    private void onOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu)
            ((Menu) holder).onOpen(plugin, (Player) event.getPlayer());
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu)
            ((Menu) holder).onClose(plugin, (Player) event.getPlayer());
    }

}
