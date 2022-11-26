package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class payCommand implements CommandExecutor {

    // Getting instance of main class
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pay")) {
            // Check if the command is disabled.
            if (plugin.getConfig().getStringList("disabled-commands").contains("pay")) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-disabled-command")));
                return true;
            }

            // If the command is being executed by a console return.
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("console")));
                return true;
            }
            // We can safely cast the sender to a player.
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Economy.Pay") || player.hasPermission("Peligon.Economy.*")) {
                // Check if all the arguments are present.
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("usage-pay")));
                    return true;
                }

                // Get the target player.
                Player target = Bukkit.getPlayer(args[0]);
                // Check if the target is null.
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-found")));
                    return true;
                }

                // Check if the target is the same as the player.
                if (target == player) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-same-player")));
                    return true;
                }

                // Check if the amount is a number.
                double amount = Utils.getAbbreviation(args[1]);
                if (amount <= 0 || Double.isNaN(amount)) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-amount")));
                    return true;
                }

                // Check if the player has data.
                if (!playerUtils.hasData(player)) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
                    return true;
                }

                // Check if the target has data.
                if (!playerUtils.hasData(target)) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
                    return true;
                }

                // Check if banks are enabled.
                if (plugin.getConfig().getBoolean("Accounts.banks.enabled", true)) {
                    // Check if player has enough money in their bank.
                    if (!playerUtils.hasEnoughBankBalance(player, amount)) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-not-enough-money")));
                        return true;
                    }

                    // Remove money from the player's bank.
                    playerUtils.removeBankBalance(player, amount);
                    // Add money to the target's bank.
                    playerUtils.setBankBalance(target, playerUtils.getBankBalance(target) + amount);

                    // Send messages to the players.
                    sendMessage(player, target, amount);
                } else {
                    // Check if player has enough money in their wallet.
                    if (!playerUtils.hasEnoughBankBalance(player, amount)) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-not-enough-money")));
                        return true;
                    }

                    // Remove money from player.
                    playerUtils.removeCash(player, amount);
                    // Add money to target.
                    playerUtils.setCash(target, playerUtils.getCash(target) + amount);

                    // Send messages to the players.
                    sendMessage(player, target, amount);
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
            }

        }
        return false;
    }

    // To avoid duplicate code. This method sends the success message to the players.
    private void sendMessage(Player player, Player target, double amount) {
        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                plugin.languageFile.getConfig().getString("success-transfer")
                        .replaceAll("%amount%", Utils.format(amount))
                        .replaceAll("%player%", target.getName())));
        target.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                plugin.languageFile.getConfig().getString("transfer-received")
                        .replaceAll("%amount%", Utils.format(amount))
                        .replaceAll("%player%", player.getName())));
    }


}
