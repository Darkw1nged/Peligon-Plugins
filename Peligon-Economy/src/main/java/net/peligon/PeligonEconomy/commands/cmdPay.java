package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Locale;

public class cmdPay implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pay")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Economy.Pay") || player.hasPermission("Peligon.Economy.*")) {
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("pay-usage")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                double amount;

                // ---- [ Verifying target ] ----
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")
                            .replaceAll("%player%", args[1])
                            .replaceAll("%target%", args[1])));
                    return true;
                }
                if (target == player) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }

                // ---- [ Getting the amount ] ----
                try {
                    amount = Double.parseDouble(args[1]);
                } catch (Exception e) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                    return true;
                }
                if (amount < 0) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                    return true;
                }

                // ---- [ Checking accounts of players ] ----
                if (!plugin.Economy.hasAccount(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("account-error")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%target%", player.getName())));
                    return true;
                }
                if (!plugin.Economy.hasAccount(target)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("account-error")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%target%", target.getName())));
                    return true;
                }
                if (!plugin.Economy.hasEnoughCash(player, amount)) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                    return true;
                }

                // ---- [ Removing money ] ----
                plugin.Economy.removeAccount(player, amount);
                // ---- [ Adding money ] ----
                plugin.Economy.addAccount(target, amount);

                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("money-transferred"), amount).replaceAll("%player%", target.getName()));
                target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("money-received"), amount).replaceAll("%player%", player.getName()));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }

        }
        return false;
    }


}
