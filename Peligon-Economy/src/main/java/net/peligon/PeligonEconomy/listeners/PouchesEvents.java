package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Pouch;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.managers.Menu;
import net.peligon.PeligonEconomy.menu.*;
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

public class PouchesEvents implements Listener {

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

        if (inventoryName.equals(Utils.chatColor(plugin.filePouches.getConfig().getString("shop-inventory.title")))) {
            for (String key : plugin.filePouches.getConfig().getConfigurationSection("shop-inventory.contents").getKeys(false)) {
                if (item.getType().equals(Material.getMaterial(plugin.filePouches.getConfig().getString("shop-inventory.contents." + key + ".item").toUpperCase()))
                        && item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.filePouches.getConfig().getString("shop-inventory.contents." + key + ".name")))) {
                    if (plugin.filePouches.getConfig().contains("shop-inventory.contents." + key + ".event")) {
                        switch (plugin.filePouches.getConfig().getString("shop-inventory.contents." + key + ".event").toLowerCase()) {
                            case "money":
                                player.openInventory(new menuMoneyPouch(player).getInventory());
                                return;
                            case "experience":
                                player.openInventory(new menuExperiencePouch(player).getInventory());
                                return;
                            case "close":
                                player.closeInventory();
                                event.setCancelled(true);
                                return;
                        }
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        } else if (inventoryName.equals(Utils.chatColor(plugin.filePouches.getConfig().getString("money-inventory.title")))) {
            for (String key : plugin.filePouches.getConfig().getConfigurationSection("money-inventory.contents").getKeys(false)) {
                if (item.getType().equals(Material.getMaterial(plugin.filePouches.getConfig().getString("money-inventory.contents." + key + ".item").toUpperCase()))
                        && item.getItemMeta().getDisplayName().equals(Utils.chatColor((plugin.filePouches.getConfig().getString("money-inventory.contents." + key + ".name"))))) {
                    if (plugin.filePouches.getConfig().contains("money-inventory.contents." + key + ".event")) {
                        switch (plugin.filePouches.getConfig().getString("money-inventory.contents." + key + ".event").toLowerCase()) {
                            case "purchasable":
                                for (Pouch pouch : Utils.pouches) {
                                    if (pouch.getItemStack().isSimilar(item)) {
                                        if (plugin.Economy.hasEnoughCash(player, pouch.getCost())) {
                                            if (plugin.Economy.getAccount(player) <= 0) {
                                                event.setCancelled(true);
                                                return;
                                            }
                                            plugin.Economy.RemoveAccount(player, pouch.getCost());

                                            if (!Utils.hasSpace(player, pouch.getItemStack())) {
                                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-space")));
                                                return;
                                            }
                                        } else if (plugin.Economy.hasEnoughBank(player, pouch.getCost())) {
                                            if (plugin.Economy.getBank(player) <= 0) {
                                                event.setCancelled(true);
                                                return;
                                            }
                                            plugin.Economy.RemoveBankAccount(player, pouch.getCost());


                                            if (!Utils.hasSpace(player, pouch.getItemStack())) {
                                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-space")));
                                                return;
                                            }
                                        }
                                    }
                                }


                                return;
                            case "goback":
                                player.openInventory(new menuPouches(player).getInventory());
                                return;
                        }
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        } else if (inventoryName.equals(Utils.chatColor(plugin.filePouches.getConfig().getString("withdraw-inventory.title")))) {
            for (String key : plugin.filePouches.getConfig().getConfigurationSection("withdraw-inventory.contents").getKeys(false)) {
                if (item.getType().equals(Material.getMaterial(plugin.filePouches.getConfig().getString("withdraw-inventory.contents." + key + ".item").toUpperCase()))
                        && item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.filePouches.getConfig().getString("withdraw-inventory.contents." + key + ".name")))) {
                    if (plugin.filePouches.getConfig().contains("withdraw-inventory.contents." + key + ".event")) {
                        double amount;
                        switch (plugin.filePouches.getConfig().getString("withdraw-inventory.contents." + key + ".event").toLowerCase()) {
                            case "withdrawall":
                                amount = plugin.Economy.getBank(player);
                                if (amount <= 0) event.setCancelled(true);

                                plugin.Economy.AddAccount(player, amount);
                                plugin.Economy.RemoveBankAccount(player, amount);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("withdrawn-money"), amount));

                                Utils.addTransaction(player, Utils.chatColor(plugin.filePouches.getConfig().getString("Options.transaction-remove"), amount)
                                        .replaceAll("%player%", player.getName()));

                                player.openInventory(new menuATM(player).getInventory());
                                return;
                            case "withdrawhalf":
                                amount = (plugin.Economy.getBank(player) * (50 / 100.0f));
                                if (amount <= 0) event.setCancelled(true);

                                plugin.Economy.AddAccount(player, amount);
                                plugin.Economy.RemoveBankAccount(player, amount);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("withdrawn-money"), amount));

                                Utils.addTransaction(player, Utils.chatColor(plugin.filePouches.getConfig().getString("Options.transaction-remove"), amount)
                                        .replaceAll("%player%", player.getName()));

                                player.openInventory(new menuATM(player).getInventory());
                                return;
                            case "withdraw20":
                                amount = (plugin.Economy.getBank(player) * (20 / 100.0f));
                                if (amount <= 0) event.setCancelled(true);

                                plugin.Economy.AddAccount(player, amount);
                                plugin.Economy.RemoveBankAccount(player, amount);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("withdrawn-money"), amount));

                                Utils.addTransaction(player, Utils.chatColor(plugin.filePouches.getConfig().getString("Options.transaction-remove"), amount)
                                        .replaceAll("%player%", player.getName()));

                                player.openInventory(new menuATM(player).getInventory());
                                return;
                            case "withdrawspecific":
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

    @EventHandler
    private void onOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu)
            ((Menu) holder).onOpen(plugin, (Player) event.getPlayer());
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu)
            ((Menu) holder).onClose(plugin, (Player) event.getPlayer());
    }

}
