package net.peligon.Teams.commands;

import net.peligon.Teams.Main;
import net.peligon.Teams.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdExperience implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("experience")) {
            if (!(sender instanceof Player)) {
                if (args.length < 1) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("experience-other")
                        .replaceAll("%player%", target.getName()), (double) Utils.getPlayerExp(target)));
                return true;
            }
            Player player = (Player) sender;
            if (args.length < 1) {
                if (player.hasPermission("Peligon.Teams.Experience") || player.hasPermission("Peligon.Teams.*")) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("experience"), (double) Utils.getPlayerExp(player)));
                }
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                return true;
            }
            if (player.hasPermission("Peligon.Teams.Experience.Other") || player.hasPermission("Peligon.Teams.*")) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("experience-other")
                        .replaceAll("%player%", target.getName()), (double) Utils.getPlayerExp(target)));
            }
        }
        return false;
    }

}
