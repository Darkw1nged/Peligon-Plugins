package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class cmdBalanceTop implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("balancetop")) {
            if (sender.hasPermission("Peligon.Economy.Balance.Top") || sender.hasPermission("Peligon.Economy.*")) {
                if (args.length == 1 && args[0].equalsIgnoreCase("bank")) {

                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-top").replaceAll("%number%", String.valueOf(plugin.getConfig().getInt("Economy-Leaderboard.bank.players")))));
                    if (plugin.getConfig().getBoolean("Economy-Leaderboard.bank.show-server-total", true)) {
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("server-total"), plugin.Economy.getServerTotalBank()));
                    }

                    AtomicInteger i = new AtomicInteger();
                    plugin.Economy.getBankLeaderboard().entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(entry -> {
                        double balance = plugin.Economy.getBank(entry.getKey());
                        OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());

                        sender.sendMessage(Utils.chatColor(plugin.getConfig().getString("Economy-Leaderboard.bank.format")
                                .replaceAll("%player%", player.getName())
                                .replaceAll("%position%", String.valueOf(i.getAndIncrement() + 1)), balance));
                    });

                    return true;
                }
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-top").replaceAll("%number%", String.valueOf(plugin.getConfig().getInt("Economy-Leaderboard.cash.players")))));
                if (plugin.getConfig().getBoolean("Economy-Leaderboard.cash.show-server-total", true)) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("server-total"), plugin.Economy.getServerTotalCash()));
                }

                AtomicInteger i = new AtomicInteger();
                plugin.Economy.getBankLeaderboard().entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(entry -> {
                    double balance = plugin.Economy.getAccount(entry.getKey());
                    OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());

                    sender.sendMessage(Utils.chatColor(plugin.getConfig().getString("Economy-Leaderboard.cash.format")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%position%", String.valueOf(i.getAndIncrement() + 1)), balance));
                });
            } else {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-permission")));
            }
        }
        return false;
    }
}
