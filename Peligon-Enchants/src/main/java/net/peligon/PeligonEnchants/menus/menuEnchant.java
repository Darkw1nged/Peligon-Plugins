package net.peligon.PeligonEnchants.menus;

import net.peligon.PeligonEnchants.Main;
import net.peligon.PeligonEnchants.libaries.Menu;
import net.peligon.PeligonEnchants.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class menuEnchant implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuEnchant(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, plugin.fileUI.getConfig().getInt("main.size"),
                Utils.chatColor(plugin.fileUI.getConfig().getString("main.title")));

        for (String key : plugin.fileUI.getConfig().getConfigurationSection("main.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileUI.getConfig().getString("main.contents." + key + ".item").toUpperCase()));
            if (plugin.fileUI.getConfig().contains("main.contents." + key + ".amount")) {
                item.setAmount(plugin.fileUI.getConfig().getInt("main.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileUI.getConfig().getString("main.contents." + key + ".name")));
            if (plugin.fileUI.getConfig().contains("main.contents." + key + ".lore")) {
                meta.setLore(Utils.getConvertedLore(plugin.fileUI.getConfig(), "main.contents." + key));
            }

            if (plugin.fileUI.getConfig().contains("main.contents." + key + ".item-flags")) {
                for (String flag : plugin.fileUI.getConfig().getStringList("main.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }

            if (plugin.fileUI.getConfig().contains("main.contents." + key + ".enchantments")) {
                for (String enchant : plugin.fileUI.getConfig().getConfigurationSection("main.contents." + key + ".enchantments").getKeys(false)) {
                    meta.addEnchant(Enchantment.getByName(plugin.fileUI.getConfig().getString("main.contents." + key + ".enchantments." + enchant + ".type").toUpperCase()),
                            plugin.fileUI.getConfig().getInt("main.contents." + key + ".enchantments." + enchant + ".type"),
                            false);
                }
            }
            item.setItemMeta(meta);

            if (plugin.fileUI.getConfig().getInt("main.contents." + key + ".slot") == -1) {
                for (int i=0; i<plugin.fileUI.getConfig().getInt("main.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.fileUI.getConfig().getInt("main.contents." + key + ".slot") - 1, item);
            }
        }
    }

    public void onClick(Main plugin, Player player, int slot, ClickType type) { }

    public void onOpen(Main plugin, Player player) { }

    public void onClose(Main plugin, Player player) { }

    public Inventory getInventory() {
        return this.inventory;
    }
}
