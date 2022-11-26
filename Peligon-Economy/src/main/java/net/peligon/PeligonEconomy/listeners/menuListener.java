package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.struts.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class menuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu) {
            // Get the inventory title
            String menuTitle = event.getView().getTitle();
            // Check if the title is the same as the sell-gui title, if not, event is cancelled
            if (!menuTitle.equals(Utils.chatColor(Main.getInstance.sellInventoryFile.getConfig().getString("sell-gui.title")))) event.setCancelled(true);
            // Check if the current item is null
            if (event.getCurrentItem() == null) return;

            Menu menu = (Menu) holder;
            menu.handleMenu(event);
        }
    }

}
