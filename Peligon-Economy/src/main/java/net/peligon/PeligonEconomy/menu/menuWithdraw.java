package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.struts.Menu;
import net.peligon.PeligonEconomy.libaries.struts.MenuOwnerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class menuWithdraw extends Menu {

    private final Main plugin = Main.getInstance;
    public menuWithdraw(MenuOwnerUtil menuOwnerUtil) {
        super(menuOwnerUtil);
    }

    @Override
    public String getMenuName() {
        return Utils.chatColor(plugin.fileATM.getConfig().getString("withdraw-inventory.title"));
    }

    @Override
    public int getSlots() {
        return plugin.fileATM.getConfig().getInt("withdraw-inventory.size");
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        for (String key : plugin.fileATM.getConfig().getConfigurationSection("withdraw-inventory.contents").getKeys(false)) {
            if (item.getType().equals(Material.getMaterial(plugin.fileATM.getConfig().getString("withdraw-inventory.contents." + key + ".item").toUpperCase()))
                    && item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.fileATM.getConfig().getString("withdraw-inventory.contents." + key + ".name")))) {
                if (plugin.fileATM.getConfig().contains("withdraw-inventory.contents." + key + ".event")) {
                    double amount;
                    switch (plugin.fileATM.getConfig().getString("withdraw-inventory.contents." + key + ".event").toLowerCase()) {
                        case "withdrawall":
                            amount = plugin.Economy.getBank(player);
                            if (amount <= 0) event.setCancelled(true);

                            plugin.Economy.addAccount(player, amount);
                            plugin.Economy.removeBankAccount(player, amount);
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") + plugin.languageFile.getConfig().getString("withdrawn-money"), amount));

                            Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                    .replaceAll("%player%", player.getName()));

                            player.openInventory(new menuATM(new MenuOwnerUtil(player)).getInventory());
                            return;
                        case "withdrawhalf":
                            amount = (plugin.Economy.getBank(player) * (50 / 100.0f));
                            if (amount <= 0) event.setCancelled(true);

                            plugin.Economy.addAccount(player, amount);
                            plugin.Economy.removeBankAccount(player, amount);
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") + plugin.languageFile.getConfig().getString("withdrawn-money"), amount));

                            Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                    .replaceAll("%player%", player.getName()));

                            player.openInventory(new menuATM(new MenuOwnerUtil(player)).getInventory());
                            return;
                        case "withdraw20":
                            amount = (plugin.Economy.getBank(player) * (20 / 100.0f));
                            if (amount <= 0) event.setCancelled(true);

                            plugin.Economy.addAccount(player, amount);
                            plugin.Economy.removeBankAccount(player, amount);
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") + plugin.languageFile.getConfig().getString("withdrawn-money"), amount));

                            Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                    .replaceAll("%player%", player.getName()));

                            player.openInventory(new menuATM(new MenuOwnerUtil(player)).getInventory());
                            return;
                        case "withdrawspecific":
                            if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) return;
                            List<String> lines = new ArrayList<>();
                            lines.add("");
                            lines.add("^^^^^^^^^^^^^^^");
                            lines.add("Enter the amount");
                            lines.add("to withdraw");
                            Utils.openSign(player, 0, "withdraw", lines);
                            event.setCancelled(true);
                            return;
                        case "goback":
                            player.openInventory(new menuATM(new MenuOwnerUtil(player)).getInventory());
                            return;
                    }
                } else {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        Player player = menuOwnerUtil.getOwner();

        for (String key : plugin.fileATM.getConfig().getConfigurationSection("withdraw-inventory.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileATM.getConfig().getString("withdraw-inventory.contents." + key + ".item").toUpperCase()));
            if (plugin.fileATM.getConfig().contains("withdraw-inventory.contents." + key + ".amount")) {
                item.setAmount(plugin.fileATM.getConfig().getInt("withdraw-inventory.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileATM.getConfig().getString("withdraw-inventory.contents." + key + ".name")));
            if (plugin.fileATM.getConfig().contains("withdraw-inventory.contents." + key + ".lore")) {
                ArrayList<String> lore = new ArrayList<>();
                for (String line : plugin.fileATM.getConfig().getStringList("withdraw-inventory.contents." + key + ".lore")) {
                    lore.add(Utils.chatColor(line)
                            .replaceAll("%cash%", formatted(plugin.Economy.getAccount(player)))
                            .replaceAll("%bank%", formatted(plugin.Economy.getBank(player))));
                }
                meta.setLore(lore);
            }

            if (plugin.fileATM.getConfig().contains("withdraw-inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.fileATM.getConfig().getStringList("withdraw-inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }

            if (plugin.fileATM.getConfig().contains("withdraw-inventory.contents." + key + ".enchantments")) {
                for (String enchant : plugin.fileATM.getConfig().getStringList("withdraw-inventory.contents." + key + ".enchantments")) {
                    meta.addEnchant(Enchantment.getByName(plugin.fileATM.getConfig().getString("withdraw-inventory.contents." + key + ".enchantments." + enchant + ".type").toUpperCase()),
                            plugin.fileATM.getConfig().getInt("withdraw-inventory.contents." + key + ".enchantments." + enchant + ".type"),
                            false);
                }
            }
            item.setItemMeta(meta);

            if (plugin.fileATM.getConfig().getInt("withdraw-inventory.contents." + key + ".slot") == -1) {
                for (int i=0; i<plugin.fileATM.getConfig().getInt("withdraw-inventory.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.fileATM.getConfig().getInt("withdraw-inventory.contents." + key + ".slot") - 1, item);
            }
        }
    }

    private String formatted(double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

}
