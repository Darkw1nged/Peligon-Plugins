package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.managers.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class menuDaily implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuDaily(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, plugin.fileDailyReward.getConfig().getInt("inventory.size"),
                Utils.chatColor(plugin.fileDailyReward.getConfig().getString("inventory.title")));
        for (String key : plugin.fileDailyReward.getConfig().getConfigurationSection("inventory.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileDailyReward.getConfig().getString("inventory.contents." + key + ".item").toUpperCase()));
            if (plugin.fileDailyReward.getConfig().contains("inventory.contents." + key + ".amount")) {
                item.setAmount(plugin.fileDailyReward.getConfig().getInt("inventory.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileDailyReward.getConfig().getString("inventory.contents." + key + ".name")));
            if (plugin.fileDailyReward.getConfig().contains("inventory.contents." + key + ".lore")) {
                ArrayList<String> lore = new ArrayList<>();
                for (String line : plugin.fileDailyReward.getConfig().getStringList("inventory.contents." + key + ".lore")) {
                    lore.add(Utils.chatColor(line));
                }
                meta.setLore(lore);
            }

            if (plugin.fileDailyReward.getConfig().contains("inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.fileDailyReward.getConfig().getStringList("inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }
            item.setItemMeta(meta);

            if (plugin.fileDailyReward.getConfig().getInt("inventory.contents." + key + ".slot") == -1) {
                for (int i=0; i<plugin.fileDailyReward.getConfig().getInt("inventory.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.fileDailyReward.getConfig().getInt("inventory.contents." + key + ".slot") - 1, item);
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