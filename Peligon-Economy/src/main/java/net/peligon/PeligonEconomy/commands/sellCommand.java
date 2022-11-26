package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import net.peligon.PeligonEconomy.menu.menuSellGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class sellCommand implements CommandExecutor {

    // Getting the instance of the main class.
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sell")) {
            // Check if the command is disabled.
            if (plugin.getConfig().getStringList("disabled-commands").contains("sell")) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-disabled-command")));
                return true;
            }

            // Check if console sent the command.
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-console")));
                return true;
            }
            // We can safely assume that the sender is a player.
            Player player = (Player) sender;
            // Check if the player has permission to use the command.
            if (player.hasPermission("Peligon.Economy.Sell") || player.hasPermission("Peligon.Economy.*")) {
                // Check if sell gui is enabled.
                if (plugin.getConfig().getBoolean("defaults.sell-gui", true)) {
                    // Open the sell gui.
                    menuSellGUI menu = new menuSellGUI(player);
                    menu.open();
                    return true;
                }

                // Getting the value of the players inventory.
                double amount = Utils.getInventoryValue(player.getInventory());
                // Check if the player has any items to sell.
                if (amount <= 0) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-items-to-sell")));
                    return true;
                }

                // Add the amount to the players account.
                playerUtils.setCash(player, playerUtils.getCash(player) + amount);

                // Send the player a message.
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("sold-items"), amount));
            } else {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                return true;
            }
        }
        return false;
    }

}
