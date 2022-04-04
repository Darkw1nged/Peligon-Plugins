package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.menu.menuSellGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class cmdSell implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sell")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player)sender;
            if (player.hasPermission("Peligon.Economy.Sell") || player.hasPermission("Peligon.Economy.*")) {
                if (plugin.getConfig().getBoolean("Sell-GUI.enabled", true)) {
                    menuSellGUI menu = new menuSellGUI(player);
                    player.openInventory(menu.getInventory());
                    return true;
                }
                Double amount = getSellableItems(player);
                if (amount <= 0) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-items")));
                    return true;
                }
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("sold-items"), amount));
                plugin.Economy.addAccount(player, amount);
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                return true;
            }
        }
        return false;
    }

    public Double getSellableItems(Player player) {
        double amount = 0;
        for (ItemStack item : player.getInventory()) {
            if (item != null) {
                if (plugin.fileWorth.getConfig().contains("worth." + item.getType().name().toUpperCase())) {
                    amount += plugin.fileWorth.getConfig().getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
                    item.setAmount(0);
                }
            }
        }
        return amount;
    }

}
