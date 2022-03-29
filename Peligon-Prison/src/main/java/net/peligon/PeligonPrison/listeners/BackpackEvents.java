package net.peligon.PeligonPrison.listeners;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.menu.menuBackpack;
import net.peligon.PeligonPrison.struts.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class BackpackEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().equals(plugin.backpackManager.backpack)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                menuBackpack menu = new menuBackpack(player);
                player.openInventory(menu.getInventory());
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("Backpack.Settings.allow-drop", false)) {
            if (player.getInventory().getItemInMainHand().equals(plugin.backpackManager.backpack)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("Backpack.Settings.allow-place", false)) {
            if (player.getInventory().getItemInMainHand().equals(plugin.backpackManager.backpack)) {
                event.setCancelled(true);
            }
        }
    }

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
