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

public class menuExperiencePouch  implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuExperiencePouch(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, plugin.filePouches.getConfig().getInt("experience-inventory.size"),
                Utils.chatColor(plugin.filePouches.getConfig().getString("experience-inventory.title")));


        for (String key : plugin.filePouches.getConfig().getConfigurationSection("experience-inventory.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.filePouches.getConfig().getString("experience-inventory.contents." + key + ".item").toUpperCase()));
            if (plugin.filePouches.getConfig().contains("experience-inventory.contents." + key + ".amount")) {
                item.setAmount(plugin.filePouches.getConfig().getInt("experience-inventory.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.filePouches.getConfig().getString("experience-inventory.contents." + key + ".name")));
            if (plugin.filePouches.getConfig().contains("experience-inventory.contents." + key + ".lore")) {
                ArrayList<String> lore = new ArrayList<>();

                for (String line : plugin.filePouches.getConfig().getStringList("experience-inventory.contents." + key + ".lore")) {
                    if (line.contains("%transactions%")) continue;
                    lore.add(Utils.chatColor(line)
                            .replaceAll("%cash%", formatted(plugin.Economy.getAccount(player)))
                            .replaceAll("%bank%", formatted(plugin.Economy.getBank(player))));

                }
                meta.setLore(lore);
            }

            if (plugin.filePouches.getConfig().contains("experience-inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.filePouches.getConfig().getStringList("experience-inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }

            if (plugin.filePouches.getConfig().contains("experience-inventory.contents." + key + ".enchantments")) {
                for (String enchant : plugin.filePouches.getConfig().getConfigurationSection("experience-inventory.contents." + key + ".enchantments").getKeys(false)) {
                    meta.addEnchant(Enchantment.getByName(plugin.filePouches.getConfig().getString("experience-inventory.contents." + key + ".enchantments." + enchant + ".type").toUpperCase()),
                            plugin.filePouches.getConfig().getInt("experience-inventory.contents." + key + ".enchantments." + enchant + ".type"),
                            false);
                }
            }
            item.setItemMeta(meta);

            if (plugin.filePouches.getConfig().getInt("experience-inventory.contents." + key + ".slot") == -1) {
                for (int i=0; i<plugin.filePouches.getConfig().getInt("experience-inventory.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.filePouches.getConfig().getInt("experience-inventory.contents." + key + ".slot") - 1, item);
            }
        }
    }

    public void onClick(Main plugin, Player player, int slot, ClickType type) { }

    public void onOpen(Main plugin, Player player) { }

    public void onClose(Main plugin, Player player) { }

    public Inventory getInventory() {
        return this.inventory;
    }

    private String formatted(double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

}
