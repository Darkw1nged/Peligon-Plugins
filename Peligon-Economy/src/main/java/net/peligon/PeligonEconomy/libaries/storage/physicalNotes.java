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

    public static ItemStack Cash1() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-1.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-1.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-1"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-1.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash2() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-2.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-2.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-2"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-2.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash3() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-3.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-3.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-3"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-3.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash4() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-4.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-4.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-4"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-4.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash5() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-5.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-5.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-5"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-5.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash6() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-6.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-6.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-6"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-6.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash7() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-7.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-7.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-7"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-7.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash8() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-8.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-8.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-8"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-8.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash9() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-9.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-9.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-9"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-9.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash10() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-10.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-10.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-10"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-10.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

    public static ItemStack Cash11() {
        // Getting the material from the config.
        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("cash-11.item").toUpperCase()));
        // Creating the item meta.
        ItemMeta meta = item.getItemMeta();

        // Setting the display name.
        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("cash-11.name")));
        // Setting the lore.
        meta.setLore(Utils.getConvertedLore(plugin.custonItemsFile.getConfig(), "cash-11"));

        // Adding the value to the item.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE, plugin.custonItemsFile.getConfig().getDouble("cash-11.worth"));
        // Setting the item meta.
        item.setItemMeta(meta);

        // Returning the item.
        return item;
    }

}
