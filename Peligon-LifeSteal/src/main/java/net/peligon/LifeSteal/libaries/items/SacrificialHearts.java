package net.peligon.LifeSteal.libaries.items;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SacrificialHearts {

    private final Main plugin = Main.getInstance;

    public ItemStack getScarceHeart() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileItems.getConfig().getString("Scarce_Heart.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileItems.getConfig(), "Scarce_Heart"));

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER,
                plugin.fileItems.getConfig().getInt("Scarce_Heart.consume") * 2);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getShockHeart() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileItems.getConfig().getString("Shock_Heart.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileItems.getConfig(), "Shock_Heart"));

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER,
                plugin.fileItems.getConfig().getInt("Shock_Heart.consume") * 2);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getRageHeart() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileItems.getConfig().getString("Rage_Heart.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileItems.getConfig(), "Rage_Heart"));

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER,
                plugin.fileItems.getConfig().getInt("Rage_Heart.consume") * 2);

        item.setItemMeta(meta);
        return item;
    }

}
