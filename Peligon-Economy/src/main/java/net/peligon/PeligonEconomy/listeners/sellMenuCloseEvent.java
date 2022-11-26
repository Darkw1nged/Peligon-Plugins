package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class sellMenuCloseEvent implements Listener {

    // Get the instance of the main class
    private final Main plugin = Main.getInstance;

    // Getting when an inventory is closed
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        // Getting the player
        Player player = (Player) event.getPlayer();

        // Getting the inventory name
        String inventoryName = event.getView().getTitle();
        // Comparing the inventory name to the sellGUI title
        if (inventoryName.equals(Utils.chatColor(plugin.sellInventoryFile.getConfig().getString("sell-gui.title")))) {
            // Save all slots that are used to sell items.
            List<Integer> sellSlots = new ArrayList<>();

            // Check if sell-slots is a list.
            if (plugin.sellInventoryFile.getConfig().getList("sell-gui.sell-slots") != null) {
                // Loop through all slots.
                for (String slot : plugin.sellInventoryFile.getConfig().getStringList("sell-gui.sell-slots")) {
                    // Check if slot is a number.
                    if (Utils.isNumber(slot)) {
                        // Add slot to sellSlots list.
                        sellSlots.add(Integer.parseInt(slot));
                    }
                }
            } else {
                // Get the range of slots.
                String range = plugin.sellInventoryFile.getConfig().getString("sell-gui.sell-slots", "0-44");
                // Split the range into min and max.
                String[] split = range.split("-");

                // Check if min and max are numbers.
                if (Utils.isNumber(split[0]) && Utils.isNumber(split[1])) {
                    // Loop through all slots.
                    for (int i = Integer.parseInt(split[0]); i <= Integer.parseInt(split[1]); i++) {
                        // Add slot to sellSlots list.
                        sellSlots.add(i);
                    }
                }
            }

            // If sellSlots is empty, add all slots.
            if (sellSlots.isEmpty()) {
                for (int i = 0; i < event.getInventory().getSize(); i++) {
                    sellSlots.add(i);
                }
            }

            // Keep track of the total amount of money earned.
            double value = 0;

            // Loop through all slots.
            for (int slot : sellSlots) {
                // Get the item in the slot.
                ItemStack item = event.getInventory().getItem(slot);
                // Check if the item is valid.
                if (item == null || item.getType().equals(Material.AIR)) continue;
                // check if the item has cash-value in PersistentDataContainer
                if (item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE)) continue;

                // Check if item is inside of worth.yml
                if (plugin.itemWorthFile.getConfig().contains("worth." + item.getType().name().toUpperCase())) {
                    // Adding the value of the item to the total value.
                    value += plugin.itemWorthFile.getConfig().getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
                    // Set the item amount to 0.
                    item.setAmount(0);
                }
            }

            // Check if there is any items to sell.
            if (value <= 0) {
                // Send message to player.
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-items-to-sell")));
                return;
            }

            // Check if the player has data.
            if (playerUtils.hasData(player)) {
                // Add the amount to the players account.
                playerUtils.addCash(player, value);

                // Send the player a message.
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("sold-items"), value));
            }

        }
    }

}