package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class balanceCommand implements CommandExecutor {

    // Getting the instance of the main class.
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("balance")) {
            // Check if console sent the command.
            if (!(sender instanceof Player)) {
                // Check if all arguments are present.
                if (args.length < 1) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("usage-balance")));
                    return true;
                }

                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("bank")) {
                        // Get the target player from the args.
                        Player target = Bukkit.getPlayer(args[1]);
                        // Check if the target player is null.
                        if (target == null) {
                            sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-found")));
                            return true;
                        }

                        // Check if the target player has data.
                        if (!playerUtils.hasData(target)) {
                            sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
                            return true;
                        }

                        // Send the player the balance of the target player.
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%balance%", Utils.format(playerUtils.getBankBalance(target)))));
                    }
                    return true;
                }

                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    // Check if the target player is null.
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-found")));
                        return true;
                    }

                    // Check if the target player has data.
                    if (!playerUtils.hasData(target)) {
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
                        return true;
                    }

                    // Send the sender the balance of the target player.
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-other")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%balance%", Utils.format(playerUtils.getCash(target)))));
                    return true;
                }


                return true;
            }
            // We can safely assume that the sender is a player.
            Player player = (Player) sender;

            // Check if args is greater than 2.
            if (args.length >= 2) {
                // Check if the player has permission to use the command.
                if (player.hasPermission("Peligon.Economy.Balance.Other") || player.hasPermission("Peligon.Economy.*")) {
                    if (args[0].equalsIgnoreCase("bank")) {
                        // Get the target player from the args.
                        Player target = Bukkit.getPlayer(args[1]);
                        // Check if the target player is null.
                        if (target == null) {
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-found")));
                            return true;
                        }

                        // Check if the target player has data.
                        if (!playerUtils.hasData(target)) {
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
                            return true;
                        }

                        // Send the player the balance of the target player.
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%balance%", Utils.format(playerUtils.getBankBalance(target)))));
                    }
                } else {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                }
            }

            if (args.length == 1) {
                if (player.hasPermission("Peligon.Economy.Balance") || player.hasPermission("Peligon.Economy.*")) {
                    if (args[0].equalsIgnoreCase("bank")) {
                        // Check if the target player has data.
                        if (!playerUtils.hasData(player)) {
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
                            return true;
                        }

                        // Send the player the balance of the target player.
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance")

                                .replaceAll("%balance%", Utils.format(playerUtils.getBankBalance(player)))));
                    }
                    return true;
                } else {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                }

                // Check if the player has permission to use the command.
                if (player.hasPermission("Peligon.Economy.Balance.Other") || player.hasPermission("Peligon.Economy.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    // Check if the target player is null.
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-found")));
                        return true;
                    }

                    // Check if the target player has data.
                    if (!playerUtils.hasData(target)) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
                        return true;
                    }

                    // Send the player the balance of the target player.
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-other")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%balance%", Utils.format(playerUtils.getCash(target)))));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                }
                return true;
            }

            // Check if the player has permission to use the command.
            if (player.hasPermission("Peligon.Economy.Balance") || player.hasPermission("Peligon.Economy.*")) {
                // Check if the target player has data.
                if (!playerUtils.hasData(player)) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
                    return true;
                }

                // Send the player the balance of the target player.
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance")
                        .replaceAll("%balance%", Utils.format(playerUtils.getCash(player)))));
            } else {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
            }
        }
        return false;
    }

}
