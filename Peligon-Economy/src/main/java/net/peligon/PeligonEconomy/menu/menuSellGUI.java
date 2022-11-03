package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.managers.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class menuSellGUI implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuSellGUI(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, plugin.fileSellGUI.getConfig().getInt("inventory.size"),
                Utils.chatColor(plugin.fileSellGUI.getConfig().getString("inventory.title")));
        for (String key : plugin.fileSellGUI.getConfig().getConfigurationSection("inventory.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileSellGUI.getConfig().getString("inventory.contents." + key + ".item").toUpperCase()));
            if (plugin.fileSellGUI.getConfig().contains("inventory.contents." + key + ".amount")) {
                item.setAmount(plugin.fileSellGUI.getConfig().getInt("inventory.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileSellGUI.getConfig().getString("inventory.contents." + key + ".name")));
            if (plugin.fileSellGUI.getConfig().contains("inventory.contents." + key + ".lore")) {
                ArrayList<String> lore = new ArrayList<>();
                for (String line : plugin.fileSellGUI.getConfig().getStringList("inventory.contents." + key + ".lore")) {
                    lore.add(Utils.chatColor(line));
                }
                meta.setLore(lore);
            }

            if (plugin.fileSellGUI.getConfig().contains("inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.fileSellGUI.getConfig().getStringList("inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }
            item.setItemMeta(meta);

            if (plugin.fileSellGUI.getConfig().getInt("inventory.contents." + key + ".slot") == -1) {
                for (int i = 0; i < plugin.fileSellGUI.getConfig().getInt("inventory.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.fileSellGUI.getConfig().getInt("inventory.contents." + key + ".slot") - 1, item);
            }
        }
    }

    public void onClick(Main plugin, Player player, int slot, ClickType type) {
    }

    public void onOpen(Main plugin, Player player) {
    }

    public void onClose(Main plugin, Player player) {
        double amount = 0;
        for (int i = 0; i < plugin.fileSellGUI.getConfig().getStringList("inventory.sell-slots").size(); i++) {
            ItemStack item = this.inventory.getItem(i);
            if (item != null) {
                if (plugin.fileWorth.getConfig().contains("worth." + item.getType().name().toUpperCase())) {
                    amount += plugin.fileWorth.getConfig().getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
                } else {
                    player.getInventory().addItem(item);
                }
            }
        }
        if (amount <= 0) {
            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-items")));
            return;
        }
        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                plugin.languageFile.getConfig().getString("sold-items"), amount));
        plugin.Economy.addAccount(player, amount);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

}
