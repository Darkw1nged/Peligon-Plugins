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

import java.util.ArrayList;
import java.util.List;

public class menuEnchant implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuEnchant(Player player) {
        this.player = player;
        int size = plugin.fileUI.getConfig().getInt("main.size");
        if (plugin.fileUI.getConfig().getInt("main.size") > 54) size = 54;
        this.inventory = Bukkit.createInventory(this, size,
                Utils.chatColor(plugin.fileUI.getConfig().getString("main.title")));

        for (String key : plugin.fileUI.getConfig().getConfigurationSection("main.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileUI.getConfig().getString("main.contents." + key + ".item").toUpperCase()));
            if (plugin.fileUI.getConfig().contains("main.contents." + key + ".amount")) {
                item.setAmount(plugin.fileUI.getConfig().getInt("main.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileUI.getConfig().getString("main.contents." + key + ".name").replaceAll("%player%", player.getName())));
            if (plugin.fileUI.getConfig().contains("main.contents." + key + ".lore")) {
                List<String> lore = new ArrayList<>();
                for (String line : plugin.fileUI.getConfig().getStringList("main.contents." + key + ".lore")) {
                    lore.add(Utils.chatColor(line)
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%level%", Utils.format(player.getLevel()))
                            .replaceAll("%exp%", Utils.format(Utils.getPlayerExp(player)))
                            .replaceAll("%free-enchants%", Utils.format(0))
                            .replaceAll("%booster%", "x1.0"));
                }
                meta.setLore(lore);
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
                for (int i = 0; i < plugin.fileUI.getConfig().getInt("main.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.fileUI.getConfig().getInt("main.contents." + key + ".slot") - 1, item);
            }
        }

        int pos = 0;
        for (String s : plugin.fileUI.getConfig().getStringList("main.enchantment-slots")) {
            if (player.getInventory().getItem(pos) == null) {
                ItemStack item = new ItemStack(Material.getMaterial(plugin.fileUI.getConfig().getString("main.no-item.item")));
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Utils.chatColor(plugin.fileUI.getConfig().getString("main.no-item.name")));
                item.setItemMeta(meta);

                inventory.setItem(Integer.parseInt(s), item);
                pos++;
                continue;
            }
            if (player.getInventory().getItem(pos).getType().name().endsWith("_PICKAXE") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("_AXE") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("_SHOVEL") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("_HOE") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("_SWORD") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("BOW") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("_HELMET") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("_CHESTPLATE") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("_LEGGINGS") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("_BOOTS") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("SHIELD") ||
                    player.getInventory().getItem(pos).getType().name().endsWith("ELYTRA")) {
                inventory.setItem(Integer.parseInt(s), player.getInventory().getItem(pos));
                pos++;
                continue;
            }
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileUI.getConfig().getString("main.no-item.item")));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileUI.getConfig().getString("main.no-item.name")));
            item.setItemMeta(meta);

            inventory.setItem(Integer.parseInt(s), item);
            pos++;
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
