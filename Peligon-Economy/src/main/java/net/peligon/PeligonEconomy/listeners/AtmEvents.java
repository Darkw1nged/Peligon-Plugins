package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.managers.Menu;
import net.peligon.PeligonEconomy.menu.menuATM;
import net.peligon.PeligonEconomy.menu.menuDeposit;
import net.peligon.PeligonEconomy.menu.menuWithdraw;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AtmEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Menu)) return;
        ((Menu) holder).onClick(plugin, (Player) event.getWhoClicked(), event.getSlot(), event.getClick());

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();

        if (item == null || item.getType() == Material.AIR) return;

        if (inventoryName.equals(Utils.chatColor(plugin.fileATM.getConfig().getString("atm-inventory.title")))) {
            for (String key : plugin.fileATM.getConfig().getConfigurationSection("atm-inventory.contents").getKeys(false)) {
                if (item.getType().equals(Material.getMaterial(plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".item").toUpperCase()))
                        && item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".name")))) {
                    if (plugin.fileATM.getConfig().contains("atm-inventory.contents." + key + ".event")) {
                        switch (plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".event").toLowerCase()) {
                            case "deposit":
                                player.openInventory(new menuDeposit(player).getInventory());
                                return;
                            case "withdraw":
                                player.openInventory(new menuWithdraw(player).getInventory());
                                return;
                            case "close":
                                player.closeInventory();
                                event.setCancelled(true);
                                return;
                            case "transactions":
                                event.setCancelled(true);
                                return;
                        }
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        } else if (inventoryName.equals(Utils.chatColor(plugin.fileATM.getConfig().getString("deposit-inventory.title")))) {
            for (String key : plugin.fileATM.getConfig().getConfigurationSection("deposit-inventory.contents").getKeys(false)) {
                if (item.getType().equals(Material.getMaterial(plugin.fileATM.getConfig().getString("deposit-inventory.contents." + key + ".item").toUpperCase()))
                        && item.getItemMeta().getDisplayName().equals(Utils.chatColor((plugin.fileATM.getConfig().getString("deposit-inventory.contents." + key + ".name"))))) {
                    if (plugin.fileATM.getConfig().contains("deposit-inventory.contents." + key + ".event")) {
                        double amount;
                        switch (plugin.fileATM.getConfig().getString("deposit-inventory.contents." + key + ".event").toLowerCase()) {
                            case "depositall":
                                amount = plugin.Economy.getAccount(player);
                                if (amount <= 0) event.setCancelled(true);

                                plugin.Economy.removeAccount(player, amount);
                                plugin.Economy.addBankAccount(player, amount);

                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("deposited-money"), amount));
                                Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-add"), amount)
                                        .replaceAll("%player%", player.getName()));

                                player.openInventory(new menuATM(player).getInventory());
                                return;
                            case "deposithalf":
                                amount = (plugin.Economy.getAccount(player) * (50 / 100.0f));
                                if (amount <= 0) event.setCancelled(true);

                                plugin.Economy.removeAccount(player, amount);
                                plugin.Economy.addBankAccount(player, amount);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("deposited-money"), amount));
                                Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-add"), amount)
                                        .replaceAll("%player%", player.getName()));

                                player.openInventory(new menuATM(player).getInventory());
                                return;
                            case "depositspecific":
                                if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) return;
                                List<String> lines = new ArrayList<>();
                                lines.add("");
                                lines.add("^^^^^^^^^^^^^^^");
                                lines.add("Enter the amount");
                                lines.add("to deposit");
                                Utils.openSign(player, 0, "deposit", lines);
                                event.setCancelled(true);
                                return;
                            case "goback":
                                player.openInventory(new menuATM(player).getInventory());
                                return;
                        }
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        } else if (inventoryName.equals(Utils.chatColor(plugin.fileATM.getConfig().getString("withdraw-inventory.title")))) {
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
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("withdrawn-money"), amount));

                                Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                        .replaceAll("%player%", player.getName()));

                                player.openInventory(new menuATM(player).getInventory());
                                return;
                            case "withdrawhalf":
                                amount = (plugin.Economy.getBank(player) * (50 / 100.0f));
                                if (amount <= 0) event.setCancelled(true);

                                plugin.Economy.addAccount(player, amount);
                                plugin.Economy.removeBankAccount(player, amount);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("withdrawn-money"), amount));

                                Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                        .replaceAll("%player%", player.getName()));

                                player.openInventory(new menuATM(player).getInventory());
                                return;
                            case "withdraw20":
                                amount = (plugin.Economy.getBank(player) * (20 / 100.0f));
                                if (amount <= 0) event.setCancelled(true);

                                plugin.Economy.addAccount(player, amount);
                                plugin.Economy.removeBankAccount(player, amount);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("withdrawn-money"), amount));

                                Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                        .replaceAll("%player%", player.getName()));

                                player.openInventory(new menuATM(player).getInventory());
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
                                player.openInventory(new menuATM(player).getInventory());
                                return;
                        }
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

}
