package net.peligon.EnhancedStorage.libaries;

import net.peligon.EnhancedStorage.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class CustomItems {

    // Getting the instance of the main class.
    private final static Main plugin = Main.getInstance;

    // regular small backpack
    public static ItemStack regularSmallBackpack() {
        // Creating the item.
        ItemStack item = new ItemStack(Material.valueOf(plugin.customItemsFile.getConfig().getString("backpacks.normal-small.material").toUpperCase()));
        // Getting the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.customItemsFile.getConfig().getString("backpacks.normal-small.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.customItemsFile.getConfig(), "backpacks.normal-small"));

        // Giving the backpack a unique random id.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING, generateRandomID());
        // Setting the backpack type.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING, "normal-small");
        // Setting the backpack size. Must be multiple of 9. Max 54.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER, plugin.getConfig().getInt("Backpacks.normal-settings.small.size"));

        // Setting the item meta.
        item.setItemMeta(meta);
        // Returning the item.
        return item;
    }

    // regular medium backpack
    public static ItemStack regularMediumBackpack() {
        // Creating the item.
        ItemStack item = new ItemStack(Material.valueOf(plugin.customItemsFile.getConfig().getString("backpacks.normal-medium.material").toUpperCase()));
        // Getting the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.customItemsFile.getConfig().getString("backpacks.normal-medium.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.customItemsFile.getConfig(), "backpacks.normal-medium"));

        // Giving the backpack a unique random id.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING, generateRandomID());
        // Setting the backpack type.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING, "normal-medium");
        // Setting the backpack size. Must be multiple of 9. Max 54.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER, plugin.getConfig().getInt("Backpacks.normal-settings.medium.size"));

        // Setting the item meta.
        item.setItemMeta(meta);
        // Returning the item.
        return item;
    }

    // regular large backpack
    public static ItemStack regularLargeBackpack() {
        // Creating the item.
        ItemStack item = new ItemStack(Material.valueOf(plugin.customItemsFile.getConfig().getString("backpacks.normal-large.material").toUpperCase()));
        // Getting the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.customItemsFile.getConfig().getString("backpacks.normal-large.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.customItemsFile.getConfig(), "backpacks.normal-large"));

        // Giving the backpack a unique random id.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING, generateRandomID());
        // Setting the backpack type.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING, "normal-large");
        // Setting the backpack size. Must be multiple of 9. Max 54.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER, plugin.getConfig().getInt("Backpacks.normal-settings.large.size"));

        // Setting the item meta.
        item.setItemMeta(meta);
        // Returning the item.
        return item;
    }

    // regular massive backpack
    public static ItemStack regularMassiveBackpack() {
        // Creating the item.
        ItemStack item = new ItemStack(Material.valueOf(plugin.customItemsFile.getConfig().getString("backpacks.normal-massive.material").toUpperCase()));
        // Getting the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.customItemsFile.getConfig().getString("backpacks.normal-massive.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.customItemsFile.getConfig(), "backpacks.normal-massive"));

        // Giving the backpack a unique random id.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING, generateRandomID());
        // Setting the backpack type.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING, "normal-massive");
        // Setting the backpack size. Must be multiple of 9. Max 54.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER, plugin.getConfig().getInt("Backpacks.normal-settings.massive.size"));

        // Setting the item meta.
        item.setItemMeta(meta);
        // Returning the item.
        return item;
    }

    // adventure beginner backpack
    public static ItemStack adventureBeginnerBackpack() {
        // Creating the item.
        ItemStack item = new ItemStack(Material.valueOf(plugin.customItemsFile.getConfig().getString("backpacks.adventure-beginner.material").toUpperCase()));
        // Getting the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.customItemsFile.getConfig().getString("backpacks.adventure-beginner.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.customItemsFile.getConfig(), "backpacks.adventure-beginner"));

        // Giving the backpack a unique random id.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING, generateRandomID());
        // Setting the backpack type.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING, "adventure-beginner");
        // Setting the backpack size. Must be multiple of 9. Max 54.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER, plugin.getConfig().getInt("Backpacks.adventure-settings.beginner.inventory.size"));
        // Get if filtering is enabled.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "hasFiltering"), PersistentDataType.STRING, plugin.getConfig().getBoolean("Backpacks.adventure-settings.beginner.filtering", true) ? "true" : "false");
        // Get if automatic-pickup is enabled.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "hasAutoPickup"), PersistentDataType.STRING, plugin.getConfig().getBoolean("Backpacks.adventure-settings.beginner.automatic-pickup", true) ? "true" : "false");
        // Get if keep-food-restocked is enabled.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "hasKeepFoodRestocked"), PersistentDataType.STRING, plugin.getConfig().getBoolean("Backpacks.adventure-settings.beginner.keep-food-restocked", false) ? "true" : "false");

        // Setting the item meta.
        item.setItemMeta(meta);
        // Returning the item.
        return item;
    }

    // adventure experienced backpack
    public static ItemStack adventureExperiencedBackpack() {
        // Creating the item.
        ItemStack item = new ItemStack(Material.valueOf(plugin.customItemsFile.getConfig().getString("backpacks.adventure-experienced.material").toUpperCase()));
        // Getting the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.customItemsFile.getConfig().getString("backpacks.adventure-experienced.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.customItemsFile.getConfig(), "backpacks.adventure-experienced"));

        // Giving the backpack a unique random id.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING, generateRandomID());
        // Setting the backpack type.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING, "adventure-experienced");
        // Setting the backpack size. Must be multiple of 9. Max 54.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER, plugin.getConfig().getInt("Backpacks.adventure-settings.experienced.inventory.size"));
        // Get if filtering is enabled.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "hasFiltering"), PersistentDataType.STRING, plugin.getConfig().getBoolean("Backpacks.adventure-settings.experienced.filtering", true) ? "true" : "false");
        // Get if automatic-pickup is enabled.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "hasAutoPickup"), PersistentDataType.STRING, plugin.getConfig().getBoolean("Backpacks.adventure-settings.experienced.automatic-pickup", true) ? "true" : "false");
        // Get if keep-food-restocked is enabled.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "hasKeepFoodRestocked"), PersistentDataType.STRING, plugin.getConfig().getBoolean("Backpacks.adventure-settings.experienced.keep-food-restocked", true) ? "true" : "false");

        // Setting the item meta.
        item.setItemMeta(meta);
        // Returning the item.
        return item;
    }

    // miners backpack
    public static ItemStack minersBackpack() {
        // Creating the item.
        ItemStack item = new ItemStack(Material.valueOf(plugin.customItemsFile.getConfig().getString("backpacks.miners-backpack.material").toUpperCase()));
        // Getting the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.customItemsFile.getConfig().getString("backpacks.miners-backpack.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.customItemsFile.getConfig(), "backpacks.miners-backpack"));

        // Giving the backpack a unique random id.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackID"), PersistentDataType.STRING, generateRandomID());
        // Setting the backpack type.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackType"), PersistentDataType.STRING, "miners-backpack");
        // Setting the backpack size. Must be multiple of 9. Max 54.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackSize"), PersistentDataType.INTEGER, plugin.getConfig().getInt("Backpacks.miners-settings.size"));

        // Setting the item meta.
        item.setItemMeta(meta);
        // Returning the item.
        return item;
    }

    private static String generateRandomID() {
        // Creating a random id.
        return UUID.randomUUID().toString();
    }

}
