package net.peligon.EnhancedStorage.listener;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.CustomConfig;
import net.peligon.EnhancedStorage.libaries.CustomItems;
import net.peligon.EnhancedStorage.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class minerBackpackHandle implements Listener {

    // Getting instance of the main class.
    private final Main plugin = Main.getInstance;

    @EventHandler
    // Getting the correct event.
    // In this case we are checking if a player picks up an item.
    public void onPickup(EntityPickupItemEvent event) {
        Player player = (Player) event.getEntity();
        ItemStack itemPickedUp = event.getItem().getItemStack();

        // Check if item is ore. If not then return.
        if (!isOre(itemPickedUp)) return;

        // Declare backpack variable.
        ItemStack backpack = null;

        // Loop through all the items in the players inventory.
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING)) {
                backpack = item;
                break;
            }
        }

        // Check if backpack is null. If so then return.
        if (backpack == null) return;

        // Checking if automatic pickup is enabled on the backpack.
        if (backpack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "autoPickup"), PersistentDataType.STRING)) {
            // Getting boolean value of autoPickup.
            boolean autoPickup = Boolean.parseBoolean(backpack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "autoPickup"), PersistentDataType.STRING));
            // If autoPickup is false then return.
            if (!autoPickup) return;

            // Getting the backpackID from the backpack.
            String backpackID = backpack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING);

            // Getting datafile for the backpack.
            CustomConfig record = new CustomConfig(Main.getInstance, "backpacks/" + backpackID, false);

            // Check if item contains "backpackSize" inside getPersistentDataContainer
            if (backpack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER)) {
                // Get the backpackSize
                int backpackSize = backpack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER);

                Inventory inventory = Bukkit.createInventory(null, backpackSize, "");

                for (int i = 0; i < backpackSize; i++) {
                    // Get the item from the backpack.
                    ItemStack item = record.getConfig().getItemStack("contents." + i);
                    // If item is null, continue.
                    if (item == null) continue;
                    // Set the item in the inventory.
                    inventory.setItem(i, item);
                }

                // Check if item can be placed into backpack.
                if (Utils.hasSpace(inventory, itemPickedUp, itemPickedUp.getAmount())) {
                    // Update the item on the ground.
                    itemPickedUp.setAmount(0);
                    // Don't let the item be picked up.
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player puts an item into a backpack.
    public void inventoryUpdate(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        ItemStack item = event.getCurrentItem();

        // Check if inventory is a backpack. If not then return.
        if (!isBackpackTitle(event.getView().getTitle())) return;

        // Check if item is null. If so then return.
        if (item == null) return;

        // Check if item is ore. If not then return.
        if (!isOre(item)) {
            event.setCancelled(true);
        }
    }

    // Method to check if item is ore.
    private boolean isOre(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
            case COAL:
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
            case RAW_COPPER:
            case RAW_COPPER_BLOCK:
            case COPPER_INGOT:
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
            case RAW_IRON:
            case RAW_IRON_BLOCK:
            case IRON_INGOT:
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
            case REDSTONE:
            case REDSTONE_BLOCK:
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
            case LAPIS_LAZULI:
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
            case RAW_GOLD:
            case RAW_GOLD_BLOCK:
            case GOLD_INGOT:
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
            case DIAMOND:
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
            case EMERALD:
            case NETHER_QUARTZ_ORE:
            case QUARTZ:
            case NETHER_GOLD_ORE:
            case ANCIENT_DEBRIS:
            case NETHERITE_SCRAP:
                return true;
            default:
                return false;
        }
    }

    // Method to check if string is a backpack title.
    private boolean isBackpackTitle(String title) {
        return Utils.chatColor(title).equals(Utils.chatColor(plugin.getConfig().getString("Backpacks.normal-settings.small.title"))) ||
        Utils.chatColor(title).equals(Utils.chatColor(plugin.getConfig().getString("Backpacks.normal-settings.medium.title"))) ||
        Utils.chatColor(title).equals(Utils.chatColor(plugin.getConfig().getString("Backpacks.normal-settings.large.title"))) ||
        Utils.chatColor(title).equals(Utils.chatColor(plugin.getConfig().getString("Backpacks.normal-settings.massive.title"))) ||
        Utils.chatColor(title).equals(Utils.chatColor(plugin.getConfig().getString("Backpacks.adventure-settings.beginner.inventory.title"))) ||
        Utils.chatColor(title).equals(Utils.chatColor(plugin.getConfig().getString("Backpacks.adventure-settings.experienced.inventory.title"))) ||
        Utils.chatColor(title).equals(Utils.chatColor(plugin.getConfig().getString("Backpacks.miners-settings.inventory.title")));
    }

}
