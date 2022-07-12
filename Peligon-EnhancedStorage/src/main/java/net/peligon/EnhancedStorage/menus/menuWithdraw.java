package net.peligon.EnhancedStorage.menus;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.BackpackItem;
import net.peligon.EnhancedStorage.libaries.struts.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class menuWithdraw implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuWithdraw(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, plugin.fileWithdrawItem.getConfig().getInt("size"), Utils.chatColor(plugin.fileWithdrawItem.getConfig().getString("name")));

        for (String key : plugin.fileWithdrawItem.getConfig().getConfigurationSection("contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileWithdrawItem.getConfig().getString("contents." + key + ".material")));
            item.setAmount(plugin.fileWithdrawItem.getConfig().getInt("contents." + key + ".amount"));

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileWithdrawItem.getConfig().getString("contents." + key + ".name")));
            meta.setLore(Utils.getConvertedLore(plugin.fileWithdrawItem.getConfig(), "contents." + key));
            item.setItemMeta(meta);
            int slot = plugin.fileWithdrawItem.getConfig().getInt("contents." + key + ".slot");

            if (slot == -1) {
                for (int i = 0; i < inventory.getSize(); i++)
                    inventory.setItem(i, item);
            } else {
                inventory.setItem(slot, item);
            }

            if (!plugin.fileWithdrawItem.getConfig().contains("contents." + key + ".event")) continue;
            if (plugin.fileWithdrawItem.getConfig().getString("contents." + key + ".event").equalsIgnoreCase("all")) {
                if (Utils.itemSlot.containsKey(player.getUniqueId())) {
                    Map<Integer, String> slots = Utils.itemSlot.get(player.getUniqueId());
                    slots.put(slot, "all");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                } else {
                    Map<Integer, String> slots = new HashMap<>();
                    slots.put(slot, "all");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                }
            } else if (plugin.fileWithdrawItem.getConfig().getString("contents." + key + ".event").equalsIgnoreCase("64")) {
                if (Utils.itemSlot.containsKey(player.getUniqueId())) {
                    Map<Integer, String> slots = Utils.itemSlot.get(player.getUniqueId());
                    slots.put(slot, "64");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                } else {
                    Map<Integer, String> slots = new HashMap<>();
                    slots.put(slot, "64");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                }
            } else if (plugin.fileWithdrawItem.getConfig().getString("contents." + key + ".event").equalsIgnoreCase("32")) {
                if (Utils.itemSlot.containsKey(player.getUniqueId())) {
                    Map<Integer, String> slots = Utils.itemSlot.get(player.getUniqueId());
                    slots.put(slot, "32");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                } else {
                    Map<Integer, String> slots = new HashMap<>();
                    slots.put(slot, "32");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                }
            } else if (plugin.fileWithdrawItem.getConfig().getString("contents." + key + ".event").equalsIgnoreCase("1")) {
                if (Utils.itemSlot.containsKey(player.getUniqueId())) {
                    Map<Integer, String> slots = Utils.itemSlot.get(player.getUniqueId());
                    slots.put(slot, "1");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                } else {
                    Map<Integer, String> slots = new HashMap<>();
                    slots.put(slot, "1");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                }
            } else if (plugin.fileWithdrawItem.getConfig().getString("contents." + key + ".event").equalsIgnoreCase("close")) {
                if (Utils.itemSlot.containsKey(player.getUniqueId())) {
                    Map<Integer, String> slots = Utils.itemSlot.get(player.getUniqueId());
                    slots.put(slot, "close");
                    Utils.itemSlot.put(player.getUniqueId(), slots);
                } else {
                    Map<Integer, String> slots = new HashMap<>();
                    slots.put(slot, "close");
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
