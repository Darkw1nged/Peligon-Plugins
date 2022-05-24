package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.managers.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class sellGUIEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Menu)) return;
        ((Menu) holder).onClick(plugin, (Player) event.getWhoClicked(), event.getSlot(), event.getClick());

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();
        if (item == null || item.getType() == Material.AIR) return;

        if (inventoryName.equals(Utils.chatColor(plugin.fileSellGUI.getConfig().getString("inventory.title")))) {
            for (String key : plugin.fileSellGUI.getConfig().getConfigurationSection("inventory.contents").getKeys(false)) {
                if (item.getType().equals(Material.getMaterial(plugin.fileSellGUI.getConfig().getString("inventory.contents." + key + ".item").toUpperCase()))
                        && item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.fileSellGUI.getConfig().getString("inventory.contents." + key + ".name")))) {
                    if (plugin.fileSellGUI.getConfig().contains("inventory.contents." + key + ".event")) {
                        if ("close".equalsIgnoreCase(plugin.fileSellGUI.getConfig().getString("inventory.contents." + key + ".event"))) {
                            player.closeInventory();
                            event.setCancelled(true);
                            return;
                        }
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

}