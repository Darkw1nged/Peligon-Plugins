package net.peligon.EnhancedStorage.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class fillBrewingStand implements Listener {

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player interacts with a brewing stand.
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInHand();
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        // Check if block is null.
        if (block == null) return;

        // Check if nothing is in the players hand.
        if (itemInHand.getType().equals(Material.AIR)) return;

        // Check if the player has the correct permission.
        if (player.hasPermission("Peligon.EnhancedStorage.AutoBottle") || player.hasPermission("Peligon.EnhancedStorage.*")) {

            // Check if the player is sneaking.
            if (!player.isSneaking()) return;

            // Check if the player interacts with a brewing stand.
            if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                // Check if the block is a brewing stand.
                if (block.getType().equals(Material.BREWING_STAND)) {
                    // Check if the itemInHand is a valid potion.
                    if (!isPotion(itemInHand)) return;

                    // Cancel the event.
                    event.setCancelled(true);

                    // Get the inventory of the brewing stand.
                    Inventory inventory = ((BrewingStand) block.getState()).getInventory();

                    // Since potions are not stackable we need to check if that's the only one in the players inventory.
                    checkInventory(player, itemInHand, inventory);
                }
            }
        }
    }

    // Get all potion
    private boolean isPotion(ItemStack item) {
        switch (item.getType()) {
            case POTION:
            case SPLASH_POTION:
            case LINGERING_POTION:
                return true;
        }
        return false;
    }

    // Placing all potions in the brewing stand.
    private void checkInventory(Player player, ItemStack itemInHand, Inventory inventory) {
        // Checking if any slots are empty in the brewing stand.
        if (inventory.firstEmpty() == -1) return;

        // Looping through all items in the players inventory.
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            // Get the item in the slot.
            ItemStack item = player.getInventory().getItem(i);

            // Check if the item is null.
            if (item == null) continue;

            // Check if the item is a potion.
            if (!isPotion(item)) continue;

            // Check if the item is the same as the item in the players hand.
            if (!item.equals(itemInHand)) continue;

            // Getting items in the brewing stand.
            ItemStack itemInSlotOne = inventory.getItem(0);
            ItemStack itemInSlotTwo = inventory.getItem(1);
            ItemStack itemInSlotThree = inventory.getItem(2);

            // Setting the item in the brewing stand.
            if (itemInSlotOne == null) {
                inventory.setItem(0, item);
                player.getInventory().setItem(i, null);
            } else if (itemInSlotTwo  == null) {
                inventory.setItem(1, item);
                player.getInventory().setItem(i, null);
            } else if (itemInSlotThree  == null) {
                inventory.setItem(2, item);
                player.getInventory().setItem(i, null);
            } else return;
        }
    }

}
