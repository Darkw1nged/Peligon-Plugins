package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdFeed implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("feed")) {
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("*")) {
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        player.setFoodLevel(20);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("all-players-fed")));
                    }
                    return true;
                }
                Player target = plugin.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                    return true;
                }
                target.setFoodLevel(20);
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("fed-other").replaceAll("%player%", target.getName())));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 1) {
                if (player.hasPermission("Peligon.Core.Feed.Other") || player.hasPermission("Peligon.Core.*")) {
                    if (args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("*")) {
                        for (Player online : plugin.getServer().getOnlinePlayers()) {
                            online.setFoodLevel(20);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("all-players-fed")));
                        }
                        return true;
                    }
                    Player target = plugin.getServer().getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                        return true;
                    }
                    target.setFoodLevel(20);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("fed-other").replaceAll("%player%", target.getName())));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
                return true;
            }
            if (player.hasPermission("Peligon.Core.Feed") || player.hasPermission("Peligon.Core.*")) {
                player.setFoodLevel(20);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("fed")));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }
}