package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdBalance implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("balance")) {
            if (!(sender instanceof Player)) {
                if (args.length < 1) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("specify-player")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("bank")) {
                    if (args.length < 2) {
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("specify-player")));
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")));
                        return true;
                    }

                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-other")
                            .replaceAll("%player%", target.getName()), plugin.Economy.getBank(target)));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")));
                    return true;
                }

                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-other")
                        .replaceAll("%player%", target.getName()), plugin.Economy.getAccount(target)));
                return true;
            }
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance"), plugin.Economy.getAccount(player)));
                return true;
            }
            if (args[0].equalsIgnoreCase("bank")) {
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance"), plugin.Economy.getBank(player)));
                    return true;
                }
                if (player.hasPermission("Peligon.Economy.Balance.Other") || player.hasPermission("Peligon.Economy.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")));
                        return true;
                    }

                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-other")
                            .replaceAll("%player%", target.getName()), plugin.Economy.getBank(target)));
                    return true;
                }
            }
            if (args.length >= 1) {
                if (player.hasPermission("Peligon.Economy.Balance.Other") || player.hasPermission("Peligon.Economy.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")));
                        return true;
                    }
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("balance-other")
                            .replaceAll("%player%", target.getName()), plugin.Economy.getAccount(target)));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-permission")));
                }
            }
        }
        return false;
    }

}
