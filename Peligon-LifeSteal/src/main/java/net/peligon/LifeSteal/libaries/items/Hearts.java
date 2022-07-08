package net.peligon.LifeSteal.libaries.items;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Hearts {

    private final Main plugin = Main.getInstance;

    public ItemStack getHeart() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileItems.getConfig().getString("Heart.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileItems.getConfig(), "Heart"));

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin,"amount"), PersistentDataType.INTEGER,
                plugin.fileItems.getConfig().getInt("Heart.add") * 2);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getGoldenHeart() {
           ItemStack item = new ItemStack(Material.DIAMOND);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileItems.getConfig().getString("Golden_Heart.name")));
            meta.setLore(Utils.getConvertedLore(plugin.fileItems.getConfig(), "Golden_Heart"));

            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER,
                    plugin.fileItems.getConfig().getInt("Golden_Heart.add") * 2);

            item.setItemMeta(meta);
            return item;
    }

    public ItemStack getExoticHeart() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileItems.getConfig().getString("Exotic_Heart.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileItems.getConfig(), "Exotic_Heart"));

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER,
                plugin.fileItems.getConfig().getInt("Exotic_Heart.add") * 2);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getMythicHeart() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileItems.getConfig().getString("Mythical_Heart.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileItems.getConfig(), "Mythical_Heart"));

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER,
                plugin.fileItems.getConfig().getInt("Mythical_Heart.add") * 2);

        item.setItemMeta(meta);
        return item;
    }

}
