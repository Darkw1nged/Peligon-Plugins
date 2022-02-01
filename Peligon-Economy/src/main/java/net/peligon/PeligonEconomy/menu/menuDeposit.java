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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class menuDeposit implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuDeposit(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, plugin.fileATM.getConfig().getInt("deposit-inventory.size"),
                Utils.chatColor(plugin.fileATM.getConfig().getString("deposit-inventory.title")));
        for (String key : plugin.fileATM.getConfig().getConfigurationSection("deposit-inventory.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileATM.getConfig().getString("deposit-inventory.contents." + key + ".item").toUpperCase()));
            if (plugin.fileATM.getConfig().contains("deposit-inventory.contents." + key + ".amount")) {
                item.setAmount(plugin.fileATM.getConfig().getInt("deposit-inventory.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileATM.getConfig().getString("deposit-inventory.contents." + key + ".name")));
            if (plugin.fileATM.getConfig().contains("deposit-inventory.contents." + key + ".lore")) {
                ArrayList<String> lore = new ArrayList<>();
                for (String line : plugin.fileATM.getConfig().getStringList("deposit-inventory.contents." + key + ".lore")) {
                    lore.add(Utils.chatColor(line)
                            .replaceAll("%cash%", formatted(plugin.Economy.getAccount(player)))
                            .replaceAll("%bank%", formatted(plugin.Economy.getBank(player))));
                }
                meta.setLore(lore);
            }

            if (plugin.fileATM.getConfig().contains("deposit-inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.fileATM.getConfig().getStringList("deposit-inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }

            if (plugin.fileATM.getConfig().contains("deposit-inventory.contents." + key + ".enchantments")) {
                for (String enchant : plugin.fileATM.getConfig().getStringList("deposit-inventory.contents." + key + ".enchantments")) {
                    meta.addEnchant(Enchantment.getByName(plugin.fileATM.getConfig().getString("deposit-inventory.contents." + key + ".enchantments." + enchant + ".type").toUpperCase()),
                            plugin.fileATM.getConfig().getInt("deposit-inventory.contents." + key + ".enchantments." + enchant + ".type"),
                            false);
                }
            }
            item.setItemMeta(meta);

            if (plugin.fileATM.getConfig().getInt("deposit-inventory.contents." + key + ".slot") == -1) {
                for (int i=0; i<plugin.fileATM.getConfig().getInt("deposit-inventory.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.fileATM.getConfig().getInt("deposit-inventory.contents." + key + ".slot") - 1, item);
            }
        }
    }

    public void onClick(Main plugin, Player player, int slot, ClickType type) { }

    public void onOpen(Main plugin, Player player) { }

    public void onClose(Main plugin, Player player) {}

    public Inventory getInventory() {
        return this.inventory;
    }

    private String formatted(double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

}
