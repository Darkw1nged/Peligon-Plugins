package net.peligon.EnhancedStorage.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class autoFillLapisLazuli implements Listener {

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player opens an enchanting table.
    public void onOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        // Check if the inventory is an enchanting table.
        if (!inventory.getType().equals(InventoryType.ENCHANTING)) return;

        // Check if the player has the correct permission to fill the enchantment table.
        if (player.hasPermission("Peligon.EnhancedStorage.AutoLapis") || player.hasPermission("Peligon.EnhancedStorage.*")) {
            // Autofill the enchantment table with lapis lazuli.
            inventory.setItem(1, new ItemStack(Material.LAPIS_LAZULI, 64));
        }
    }

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player clicks in an inventory.
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        // Check if no inventory is clicked.
        if (event.getClickedInventory() == null) return;
        // Check if player inventory is clicked.
        if (event.getClickedInventory().equals(player.getInventory())) return;

        // Check if the inventory is an enchanting table.
        if (!inventory.getType().equals(InventoryType.ENCHANTING)) return;

        // Check if the player has the correct permission to fill the enchantment table.
        if (player.hasPermission("Peligon.EnhancedStorage.AutoLapis") || player.hasPermission("Peligon.EnhancedStorage.*")) {
            // Check if slot 1 was clicked.
            // If so then cancel the click.
            if (event.getSlot() == 1) event.setCancelled(true);
        }
    }

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when an item is enchanted.
    public void onEnchant(EnchantItemEvent event) {
        Player player = (Player) event.getEnchanter();
        Inventory inventory = event.getInventory();

        // Check if the player has the correct permission to fill the enchantment table.
        if (player.hasPermission("Peligon.EnhancedStorage.AutoLapis") || player.hasPermission("Peligon.EnhancedStorage.*")) {
            // Autofill the enchantment table with lapis lazuli.
            inventory.setItem(1, new ItemStack(Material.LAPIS_LAZULI, 64));
        }
    }

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when the enchantment table is closed.
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        // Check if the inventory is an enchanting table.
        if (!inventory.getType().equals(InventoryType.ENCHANTING)) return;

        // Check if the player has the correct permission to fill the enchantment table.
        if (player.hasPermission("Peligon.EnhancedStorage.AutoLapis") || player.hasPermission("Peligon.EnhancedStorage.*")) {
            // Remove the lapis lazuli from the enchantment table.
            inventory.setItem(1, new ItemStack(Material.AIR));
        }
    }

}
