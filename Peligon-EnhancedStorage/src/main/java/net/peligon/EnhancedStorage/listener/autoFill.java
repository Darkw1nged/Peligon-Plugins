package net.peligon.EnhancedStorage.listener;

import org.bukkit.Material;
import org.bukkit.entity.Item;
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

        // Check if nothing is in the players hand.
        if (itemInHand.getType().equals(Material.AIR)) return;

        // Call the method for checking.
        // Saving on duplicate code.
        autoItemFill(player, itemInHand, player.getInventory().getHeldItemSlot());
    }

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player consumes an item.
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack consumable = event.getItem();

        // Check if nothing is in the players hand.
        if (consumable.getType().equals(Material.AIR)) return;

        // Call the method for checking.
        // Saving on duplicate code.
        autoItemFill(player, consumable, player.getInventory().getHeldItemSlot());
    }

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player interacts with an item.
    public void potionThrow(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Check if nothing is in the players hand.
        if (itemInHand.getType().equals(Material.AIR)) return;

        // Check if the itemInHand is a potion. If not return.
        if (!isPotionThrowable(itemInHand)) return;

        // Check if the player interacts with a item.
        Action action = event.getAction();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            // Call the method for checking.
            // Saving on duplicate code.
            autoItemFill(player, itemInHand, player.getInventory().getHeldItemSlot());
        }
    }

    // Get all potions
    private boolean isPotionThrowable(ItemStack item) {
        switch (item.getType()) {
            case EXPERIENCE_BOTTLE:
            case SPLASH_POTION:
            case LINGERING_POTION:
                return true;
        }
        return false;
    }

    // Method for updating an item if they run out.
    private void autoItemFill(Player player, ItemStack itemInHand, int currentSlot) {
        // Check if the player has the correct permission to refill the item.
        if (player.hasPermission("Peligon.EnhancedStorage.AutoFill") || player.hasPermission("Peligon.EnhancedStorage.*")) {
            // If the amount of items in their hand is greater than 1.
            // If so return.
            if (itemInHand.getAmount() > 1) return;

            // Loop through all items in the players inventory.
            // Find the first item with the same material type.
            for (int i=0; i<player.getInventory().getSize(); i++) {
                // Getting the item in the slot.
                ItemStack item = player.getInventory().getItem(i);

                // If the item is null, skip.
                if (item == null) continue;
                // If i == currentSlot, skip.
                if (i == currentSlot) continue;

                // Check if the item type is equal to the current item in hand type.
                if (item.getType() == itemInHand.getType()) {
                    // Update itemInHand information.
                    itemInHand.setAmount(item.getAmount());
                    itemInHand.setItemMeta(item.getItemMeta());
                    itemInHand.setDurability(item.getDurability());

                    // Update the players inventory.
                    player.getInventory().setItem(currentSlot, itemInHand);
                    player.getInventory().setItem(i, null);
                    break;
                }
            }
        }
    }

}
