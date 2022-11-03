package net.peligon.EnhancedStorage.listener;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.CustomConfig;
import net.peligon.EnhancedStorage.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.*;

public class backpackHandles implements Listener {

    // Getting the main class.
    private final Main plugin = Main.getInstance;
    // List of all players that have a backpack open.
    private final Map<UUID, String> openBackpacks = new HashMap<>();

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player opens a backpack.
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInHand();

        // Check if backpacks are enabled.
        if (!plugin.getConfig().getBoolean("Backpacks.enabled")) return;

        // If item is null, return
        if (itemInHand.getType().equals(Material.AIR)) return;

        // Check if item has itemMeta
        if (!itemInHand.hasItemMeta()) return;

        // Check if item contains "backpackID" inside getPersistentDataContainer
        if (itemInHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING)) {
            // Get the backpackID
            String backpackID = itemInHand.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING);

            // Check if data file exists inside folder backpacks
            File file = new File(plugin.getDataFolder() + "/backpacks/" + backpackID + ".yml");

            if (!file.exists()) {
                // Check if item contains "backpackSize" inside getPersistentDataContainer
                if (itemInHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER)) {
                    // Get the backpackSize
                    int backpackSize = itemInHand.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER);

                    // Check if item contains "backpackType" inside getPersistentDataContainer
                    if (itemInHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING)) {
                        // Get the backpackType
                        String backpackType = itemInHand.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING);
                        // If backpackType is null, return.
                        if (backpackType == null) return;

                        // Create a new inventory.
                        Inventory inventory = Bukkit.createInventory(null, backpackSize, backpackGUITitle(backpackType));

                        // Open inventory for player.
                        player.openInventory(inventory);
                        // Add player to openBackpacks list.
                        openBackpacks.put(player.getUniqueId(), backpackID);
                    }
                }
                return;
            }
            // Get the backpack data file.
            CustomConfig record = new CustomConfig(Main.getInstance, "backpacks/" + backpackID, false);

            // Check if item contains "backpackSize" inside getPersistentDataContainer
            if (itemInHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER)) {
                // Get the backpackSize
                int backpackSize = itemInHand.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER);

                // Check if item contains "backpackType" inside getPersistentDataContainer
                if (itemInHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING)) {
                    // Get the backpackType
                    String backpackType = itemInHand.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING);
                    // If backpackType is null, return.
                    if (backpackType == null) return;

                    // Create a new inventory.
                    Inventory inventory = Bukkit.createInventory(null, backpackSize, backpackGUITitle(backpackType));

                    // Loop through all items in the backpack.
                    for (int i = 0; i < backpackSize; i++) {
                        // Get the item from the backpack.
                        ItemStack item = record.getConfig().getItemStack("contents." + i);
                        // If item is null, continue.
                        if (item == null) continue;
                        // Set the item in the inventory.
                        inventory.setItem(i, item);
                    }

                    // Open inventory for player.
                    player.openInventory(inventory);
                    // Add player to openBackpacks list.
                    openBackpacks.put(player.getUniqueId(), backpackID);
                }
            }
        }
    }

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player closes an inventory.
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        // Check if backpacks are enabled.
        if (!plugin.getConfig().getBoolean("Backpacks.enabled")) return;

        // If player is not in openBackpacks list, return.
        if (!openBackpacks.containsKey(player.getUniqueId())) return;

        // Get the backpackID.
        String backpackID = openBackpacks.get(player.getUniqueId());
        // Get the backpack data file.
        CustomConfig record = new CustomConfig(Main.getInstance, "backpacks/" + backpackID, false);

        // If inventory is empty, return and delete the backpack data file.
        if (inventory.isEmpty()) {
            // Remove file from backpacks folder.
            File file = new File(plugin.getDataFolder() + "/backpacks/" + backpackID + ".yml");
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Remove player from openBackpacks list.
            openBackpacks.remove(player.getUniqueId());
            return;
        }

        // Loop through all items in the inventory.
        for (int i = 0; i < inventory.getSize(); i++) {
            // Get the item from the inventory.
            ItemStack item = inventory.getItem(i);
            // Set the item in the backpack.
            record.getConfig().set("contents." + i, item);
        }

        // Save the backpack data file.
        record.saveConfig();

        // Remove player from openBackpacks list.
        openBackpacks.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

    }

    private String backpackGUITitle(String name) {
        if (name.equals("normal-small")) return Utils.chatColor(plugin.getConfig().getString("Backpacks.normal-settings.small.title"));
        if (name.equals("normal-medium")) return Utils.chatColor(plugin.getConfig().getString("Backpacks.normal-settings.medium.title"));
        if (name.equals("normal-large")) return Utils.chatColor(plugin.getConfig().getString("Backpacks.normal-settings.large.title"));
        if (name.equals("normal-massive")) return Utils.chatColor(plugin.getConfig().getString("Backpacks.normal-settings.massive.title"));
        if (name.equals("adventure-beginner")) return Utils.chatColor(plugin.getConfig().getString("Backpacks.adventure-settings.beginner.inventory.title"));
        if (name.equals("adventure-experienced")) return Utils.chatColor(plugin.getConfig().getString("Backpacks.adventure-settings.experienced.inventory.title"));
        if (name.equals("miners-backpack")) return Utils.chatColor(plugin.getConfig().getString("Backpacks.miners-settings.inventory.title"));
        return Utils.chatColor("&eBackpack");
    }

}
