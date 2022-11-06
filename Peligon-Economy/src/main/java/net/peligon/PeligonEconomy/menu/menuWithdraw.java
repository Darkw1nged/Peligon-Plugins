package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import net.peligon.PeligonEconomy.libaries.struts.Menu;
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
    public menuWithdraw(Player player) {
        super(player);
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
                            amount = playerUtils.getBankBalance(player);
                            if (amount <= 0) event.setCancelled(true);

                            playerUtils.setCash(player, playerUtils.getCash(player) + amount);
                            playerUtils.setBankBalance(player, 0);
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") + plugin.languageFile.getConfig().getString("withdrawn-money"), amount));

                            Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                    .replaceAll("%player%", player.getName()));

                            new menuATM(player).open();
                            return;
                        case "withdrawhalf":
                            amount = (playerUtils.getBankBalance(player) * (50 / 100.0f));
                            if (amount <= 0) event.setCancelled(true);

                            playerUtils.setCash(player, playerUtils.getCash(player) + amount);
                            playerUtils.setBankBalance(player, playerUtils.getBankBalance(player) - amount);
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") + plugin.languageFile.getConfig().getString("withdrawn-money"), amount));

                            Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                    .replaceAll("%player%", player.getName()));

                            new menuATM(player).open();
                            return;
                        case "withdraw20":
                            amount = (playerUtils.getBankBalance(player) * (20 / 100.0f));
                            if (amount <= 0) event.setCancelled(true);

                            playerUtils.setCash(player, playerUtils.getCash(player) + amount);
                            playerUtils.setBankBalance(player, playerUtils.getBankBalance(player) - amount);
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") + plugin.languageFile.getConfig().getString("withdrawn-money"), amount));

                            Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                    .replaceAll("%player%", player.getName()));

                            new menuATM(player).open();
                            return;
                        case "withdrawspecific":
                            if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) return;
                            List<String> lines = new ArrayList<>();
                            lines.add("");
                            lines.add("^^^^^^^^^^^^^^^");
                            lines.add("Enter the amount");
                            lines.add("to withdraw");
//                            Utils.openSign(player, 0, "withdraw", lines);
                            event.setCancelled(true);
                            return;
                        case "goback":
                            new menuATM(player).open();
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
        Player player = owner;

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
                            .replaceAll("%cash%", Utils.format(playerUtils.getCash(player)))
                            .replaceAll("%bank%", Utils.format(playerUtils.getBankBalance(player))));
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

}
