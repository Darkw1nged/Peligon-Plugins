package net.peligon.EnhancedStorage.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class autoFill implements Listener {

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a block is placed.
    public void onBuild(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Call the method for checking.
        // Saving on duplicate code.
        autoItemFill(player, itemInHand);
    }

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player consumes an item.
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack consumable = event.getItem();

        // Call the method for checking.
        // Saving on duplicate code.
        autoItemFill(player, consumable);
    }

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player interacts with an item.
    public void onConsume(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Check if the itemInHand is a potion. If not return.
        if (!isPotion(itemInHand)) return;

        // Check if the player interacts with a item.
        Action action = event.getAction();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            // Call the method for checking.
            // Saving on duplicate code.
            autoItemFill(player, itemInHand);
        }
    }

    // Get all potion
    private boolean isPotion(ItemStack item) {
        switch (item.getType()) {
            case GLASS_BOTTLE:
            case EXPERIENCE_BOTTLE:
            case HONEY_BOTTLE:
            case POTION:
            case SPLASH_POTION:
            case LINGERING_POTION:
                return true;
        }
        return false;
    }

    // Method for updating an item if they run out.
    private void autoItemFill(Player player, ItemStack itemInHand) {
        // Check if the player has the correct permission to refill the item.
        if (player.hasPermission("Peligon.EnhancedStorage.AutoFill") || player.hasPermission("Peligon.EnhancedStorage.*")) {
            // If the amount of blocks in their hand is greater than 1.
            // If so return.
            if (itemInHand.getAmount() > 1) return;

            // Loop through all items in the players inventory.
            // Find the first item with the same material type.
            for (ItemStack item : player.getInventory()) {
                // If the item found is the same as item in the main hand, skip item.
                if (item == itemInHand) continue;

                // Check if the item type is equal to the current item in hand type.
                if (item.getType() == itemInHand.getType()) {
                    // Update itemInHand information.
                    itemInHand.setItemMeta(item.getItemMeta());
                    itemInHand.setAmount(item.getAmount());
                    item.setAmount(0);
                }
            }
        }
    }

}
