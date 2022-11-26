package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.struts.Menu;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class menuSellGUI extends Menu {

    private final Main plugin = Main.getInstance;

    public menuSellGUI(Player player) {
        super(player);
    }

    @Override
    public String getMenuName() {
        return plugin.sellInventoryFile.getConfig().getString("sell-gui.title", "&eSell Items");
    }

    @Override
    public int getSlots() {
        return Math.min(plugin.sellInventoryFile.getConfig().getInt("sell-gui.size", 54), 54);
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        // Check if item is null.
        if (item == null) return;

        // Check if item has meta.
        if (!item.hasItemMeta()) return;
        // Check if item has PersistentDataContainer has event key.
        if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "event"), PersistentDataType.STRING)) return;

        // Get event key.
        String eventKey = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "event"), PersistentDataType.STRING);
        // Check if event key is null or is empty.
        if (eventKey == null || eventKey.equals("")) return;

        // Check if event key is valid.
        if (eventKey.equalsIgnoreCase("close")) {
            player.closeInventory();
            event.setCancelled(true);
        } else if (eventKey.equalsIgnoreCase("background")) {
            event.setCancelled(true);
        }
    }

    @Override
    public void setMenuItems() {
        // Loop through all items in the config.
        for (String key : plugin.sellInventoryFile.getConfig().getConfigurationSection("sell-gui.contents").getKeys(false)) {
            // Get item from config.
            ItemStack item = new ItemStack(Material.getMaterial(plugin.sellInventoryFile.getConfig().getString("sell-gui.contents." + key + ".item").toUpperCase()));
            // Get item meta.
            ItemMeta meta = item.getItemMeta();

            // Check if meta is null.
            if (meta == null) {
                // Add item to inventory.
                addItem(key, item);
                // Continue to next item.
                continue;
            }

            // Set item name.
            meta.setDisplayName(Utils.chatColor(plugin.sellInventoryFile.getConfig().getString("sell-gui.contents." + key + ".name", "&eItem")));

            // Set item lore.
            meta.setLore(Utils.getConvertedLore(plugin.sellInventoryFile.getConfig(), "sell-gui.contents." + key));

            // Set item event.
            if (plugin.sellInventoryFile.getConfig().contains("sell-gui.contents." + key + ".event")) {
                meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "event"), PersistentDataType.STRING, plugin.sellInventoryFile.getConfig().getString("sell-gui.contents." + key + ".event"));
            }

            // Set item meta.
            item.setItemMeta(meta);

            // Add item to inventory.
            addItem(key, item);
        }
    }

    private void addItem(String key, ItemStack item) {
        if (plugin.sellInventoryFile.getConfig().getInt("sell-gui.contents." + key + ".slot") == -1) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, item);
            }
        } else {
            inventory.setItem(plugin.sellInventoryFile.getConfig().getInt("sell-gui.contents." + key + ".slot"), item);
        }
    }

}
