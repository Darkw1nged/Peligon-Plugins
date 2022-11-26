package net.peligon.PeligonEconomy.libaries.storage;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class physicalNotes {

    // Getting the instance of the main class.
    private static final Main plugin = Main.getInstance;

    public static ItemStack Dime() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Dime.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Dime.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Dime"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Dime.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Nickel() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Nickel.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Nickel.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Nickel"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Nickel.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Quarter() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Quarter.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Quarter.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Quarter"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Quarter.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Penny() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Penny.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Penny.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Penny"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Penny.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Dollar() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Dollar.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Dollar.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Dollar"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Dollar.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack TwoDollar() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Two-Dollar.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Two-Dollar.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Two-Dollar"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Two-Dollar.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack FiveDollar() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Five-Dollar.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Five-Dollar.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Five-Dollar"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Five-Dollar.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack TenDollar() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Ten-Dollar.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Ten-Dollar.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Ten-Dollar"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Ten-Dollar.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack TwentyDollar() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Twenty-Dollar.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Twenty-Dollar.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Twenty-Dollar"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Twenty-Dollar.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack FiftyDollar() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Fifty-Dollar.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Fifty-Dollar.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Fifty-Dollar"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Fifty-Dollar.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack HundredDollar() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("Hundred-Dollar.item")));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("Hundred-Dollar.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "Hundred-Dollar"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("Hundred-Dollar.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

}
