package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class cmdBalanceTop implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("balancetop")) {
            if (sender.hasPermission("Peligon.Economy.Balance.Top") || sender.hasPermission("Peligon.Economy.*")) {
                if (args.length == 1 && args[0].equalsIgnoreCase("bank")) {
                    Map<UUID, Double> temp = new HashMap<>();
                    int pos = 0;
                    for (UUID uuid : plugin.Economy.getBankFromDescending().keySet()) {
                        Player player = Bukkit.getPlayer(uuid);
                        if (pos != plugin.getConfig().getInt("Economy-Leaderboard.bank.players")) {
                            temp.put(player.getUniqueId(), plugin.Economy.getBankFromDescending().get(uuid));
                            continue;
                        }
                        break;
                    }

                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("balance-top").replaceAll("%number%", String.valueOf(plugin.getConfig().getInt("Economy-Leaderboard.bank.players")))));
                    if (plugin.getConfig().getBoolean("Economy-Leaderboard.bank.show-server-total", true)) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("server-total"), plugin.Economy.getServerTotalBank()));
                    }

                    for (int i=0; i<temp.size(); i++) {
                        for (UUID uuid: temp.keySet()) {
                            sender.sendMessage(Utils.chatColor(plugin.getConfig().getString("Economy-Leaderboard.bank.format")
                                            .replaceAll("%player%", Bukkit.getPlayer(uuid).getName()), plugin.Economy.getBank(Bukkit.getPlayer(uuid)))
                                    .replaceAll("%position%", String.valueOf(i + 1)));
                        }
                    }
                    return true;
                }
                Map<UUID, Double> temp = new HashMap<>();
                int pos = 0;
                for (UUID uuid : plugin.Economy.getCashFromDescending().keySet()) {
                    Player player = Bukkit.getPlayer(uuid);
                    if (pos != plugin.getConfig().getInt("Economy-Leaderboard.cash.players")) {
                        temp.put(player.getUniqueId(), plugin.Economy.getCashFromDescending().get(uuid));
                        continue;
                    }
                    break;
                }

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("balance-top").replaceAll("%number%", String.valueOf(plugin.getConfig().getInt("Economy-Leaderboard.cash.players")))));
                if (plugin.getConfig().getBoolean("Economy-Leaderboard.cash.show-server-total", true)) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("server-total"), plugin.Economy.getServerTotalCash()));
                }

                for (int i=0; i<temp.size(); i++) {
                    for (UUID uuid: temp.keySet()) {
                        sender.sendMessage(Utils.chatColor(plugin.getConfig().getString("Economy-Leaderboard.cash.format")
                                        .replaceAll("%player%", Bukkit.getPlayer(uuid).getName()), plugin.Economy.getAccount(Bukkit.getPlayer(uuid)))
                                .replaceAll("%position%", String.valueOf(i + 1)));
                    }
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }
}
