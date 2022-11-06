package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class economyCommand implements CommandExecutor {

    // Creating an instance of the main class
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("economy")) {
            // The way the command is structured, we can treat sender and player the same way.
            // Check if sender has permission to use the command.
            if (sender.hasPermission("Peligon.Economy.Admin") || sender.hasPermission("Peligon.Economy.*")) {
                // Check if all the arguments are present.
                if (args.length < 3) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("usage-economy")));
                    return true;
                }

                // Getting the player from the arguments.
                // If bank is at argument 1 then we need to get the player from argument 2.
                Player player = Bukkit.getPlayer(args[1].equalsIgnoreCase("bank") ? args[2] : args[1]);
                if (player == null) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-found")));
                    return true;
                }

                // Check if the player has data.
                if (!playerUtils.hasData(player)) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
                }

                // Get the amount from the arguments.
                // If argument 0 is reset then we can set the amount to 0.
                double amount = args[0].equalsIgnoreCase("reset") ? 0 : args[1].equalsIgnoreCase("bank") ? Utils.getAbbreviation(args[3]) : Utils.getAbbreviation(args[2]);

                // If argument 1 is bank, then we need to add the amount to the bank.
                if (args[1].equalsIgnoreCase("bank")) {
                    // Check if operation is to add to cash balance.
                    if (args[0].equalsIgnoreCase("add")) {
                        playerUtils.setBankBalance(player, playerUtils.getBankBalance(player) + amount);
                    }
                    // Check if operation is to remove from cash balance.
                    else if (args[0].equalsIgnoreCase("remove")) {
                        playerUtils.setBankBalance(player, playerUtils.getBankBalance(player) - amount);
                    }
                    // Check if operation is to set cash balance.
                    else if (args[0].equalsIgnoreCase("set")) {
                        playerUtils.setBankBalance(player, amount);
                    }
                    // Check if operation is to reset cash balance.
                    else if (args[0].equalsIgnoreCase("reset")) {
                        playerUtils.setBankBalance(player, 0);
                    }

                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                            plugin.languageFile.getConfig().getString("success-account-update")
                                    .replaceAll("%player%", player.getName())));
                    return true;
                }

                // Check if operation is to add to cash balance.
                if (args[0].equalsIgnoreCase("add")) {
                    playerUtils.setCash(player, playerUtils.getCash(player) + amount);
                }
                // Check if operation is to remove from cash balance.
                else if (args[0].equalsIgnoreCase("remove")) {
                    playerUtils.setCash(player, playerUtils.getCash(player) - amount);
                }
                // Check if operation is to set cash balance.
                else if (args[0].equalsIgnoreCase("set")) {
                    playerUtils.setCash(player, amount);
                }
                // Check if operation is to reset cash balance.
                else if (args[0].equalsIgnoreCase("reset")) {
                    playerUtils.setCash(player, 0);
                }

                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("success-account-update")
                                .replaceAll("%player%", player.getName())));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
            }
        }
        return false;
    }

}
