package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.Plugins.libaries.struts.Menu;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class menuSellGUI extends Menu {

    // Save all slots that are used to sell items.
    private final List<Integer> sellSlots = new ArrayList<>();

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
        }
    }

    @Override
    public void setMenuItems() {
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
            for (int i = 0; i < getSlots(); i++) {
                sellSlots.add(i);
            }
        }

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
            if (plugin.bankAccountInventoryFile.getConfig().contains("sell-gui.contents." + key + ".event")) {
                meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "event"), PersistentDataType.STRING, plugin.bankAccountInventoryFile.getConfig().getString("sell-gui.contents." + key + ".event"));
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
