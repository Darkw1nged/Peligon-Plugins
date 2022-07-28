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

public class menuEnchantEquip implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuEnchantEquip(Player player, ItemStack activeItem) {
        this.player = player;
        int size = plugin.fileUI.getConfig().getInt("enchantment.size");
        if (plugin.fileUI.getConfig().getInt("enchantment.size") > 54) size = 54;

        String ItemName = activeItem.hasItemMeta() && activeItem.getItemMeta().hasDisplayName() ? activeItem.getItemMeta().getDisplayName() : Utils.formatWord(activeItem.getType().name());
        this.inventory = Bukkit.createInventory(this, size,
                Utils.chatColor(plugin.fileUI.getConfig().getString("enchantment.title").replaceAll("%item%", ItemName)));

        for (String key : plugin.fileUI.getConfig().getConfigurationSection("enchantment.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileUI.getConfig().getString("enchantment.contents." + key + ".item").toUpperCase()));
            if (plugin.fileUI.getConfig().contains("enchantment.contents." + key + ".amount")) {
                item.setAmount(plugin.fileUI.getConfig().getInt("enchantment.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileUI.getConfig().getString("enchantment.contents." + key + ".name").replaceAll("%player%", player.getName())));
            if (plugin.fileUI.getConfig().contains("enchantment.contents." + key + ".lore")) {
                List<String> lore = new ArrayList<>();
                for (String line : plugin.fileUI.getConfig().getStringList("enchantment.contents." + key + ".lore")) {
                    lore.add(Utils.chatColor(line)
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%level%", Utils.format(player.getLevel()))
                            .replaceAll("%exp%", Utils.format(Utils.getPlayerExp(player)))
                            .replaceAll("%free-enchants%", Utils.format(0))
                            .replaceAll("%booster%", "x1.0"));
                }
                meta.setLore(lore);
            }

            if (plugin.fileUI.getConfig().contains("enchantment.contents." + key + ".item-flags")) {
                for (String flag : plugin.fileUI.getConfig().getStringList("enchantment.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }

            if (plugin.fileUI.getConfig().contains("enchantment.contents." + key + ".enchantments")) {
                for (String enchant : plugin.fileUI.getConfig().getConfigurationSection("enchantment.contents." + key + ".enchantments").getKeys(false)) {
                    meta.addEnchant(Enchantment.getByName(plugin.fileUI.getConfig().getString("enchantment.contents." + key + ".enchantments." + enchant + ".type").toUpperCase()),
                            plugin.fileUI.getConfig().getInt("enchantment.contents." + key + ".enchantments." + enchant + ".type"),
                            false);
                }
            }
            item.setItemMeta(meta);

            if (plugin.fileUI.getConfig().getInt("enchantment.contents." + key + ".slot") == -1) {
                for (int i=0; i<plugin.fileUI.getConfig().getInt("enchantment.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.fileUI.getConfig().getInt("enchantment.contents." + key + ".slot") - 1, item);
            }
        }

        if (plugin.fileUI.getConfig().contains("enchantment.selected-item-slot")) {
            ItemMeta meta = activeItem.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            activeItem.setItemMeta(meta);
            inventory.setItem(plugin.fileUI.getConfig().getInt("enchantment.selected-item-slot") - 1, activeItem);
        }

        for (int i=0; i<inventory.getSize(); i++) {
            if (plugin.fileUI.getConfig().getStringList("enchantment.enchantment-slots").contains(String.valueOf(i))) {
                inventory.setItem(i, new ItemStack(Material.AIR));
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
