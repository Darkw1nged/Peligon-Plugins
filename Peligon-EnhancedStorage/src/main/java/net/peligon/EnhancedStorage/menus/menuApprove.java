package net.peligon.EnhancedStorage.menus;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class menuApprove implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuApprove(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, plugin.fileApproveItem.getConfig().getInt("size"), Utils.chatColor(plugin.fileApproveItem.getConfig().getString("name")));

        for (String key : plugin.fileApproveItem.getConfig().getConfigurationSection("contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileApproveItem.getConfig().getString("contents." + key + ".material")));
            item.setAmount(plugin.fileApproveItem.getConfig().getInt("contents." + key + ".amount"));

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileApproveItem.getConfig().getString("contents." + key + ".name")));
            meta.setLore(Utils.getConvertedLore(plugin.fileApproveItem.getConfig(), "contents." + key));
            item.setItemMeta(meta);
            int slot = plugin.fileApproveItem.getConfig().getInt("contents." + key + ".slot");

            if (slot == -1) {
                for (int i = 0; i < inventory.getSize(); i++)
                    inventory.setItem(i, item);
            } else {
                inventory.setItem(slot, item);
            }

            if (!plugin.fileApproveItem.getConfig().contains("contents." + key + ".event")) continue;
            if (plugin.fileApproveItem.getConfig().getString("contents." + key + ".event").equalsIgnoreCase("yes")) {
                if (Utils.itemSlot.containsKey(player.getUniqueId())) {
                    Map<Integer, String> slots = Utils.itemSlot.get(player.getUniqueId());
                    slots.put(slot, "yes");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                } else {
                    Map<Integer, String> slots = new HashMap<>();
                    slots.put(slot, "yes");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                }
            } else if (plugin.fileApproveItem.getConfig().getString("contents." + key + ".event").equalsIgnoreCase("no")) {
                if (Utils.itemSlot.containsKey(player.getUniqueId())) {
                    Map<Integer, String> slots = Utils.itemSlot.get(player.getUniqueId());
                    slots.put(slot, "no");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                } else {
                    Map<Integer, String> slots = new HashMap<>();
                    slots.put(slot, "no");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                }
            }
        }
    }

    public void onClick(Main plugin, Player player, int slot, ClickType type) {
    }

    public void onOpen(Main plugin, Player player) {
    }

    public void onClose(Main plugin, Player player) {
    }

    public Inventory getInventory() {
        return this.inventory;
    }

}