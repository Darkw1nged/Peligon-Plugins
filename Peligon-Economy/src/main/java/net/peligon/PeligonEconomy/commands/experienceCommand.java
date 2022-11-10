package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class experienceCommand implements CommandExecutor {

    // Get the instance of the main class.
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("experience")) {
            // Check if console sent the command.
            if (!(sender instanceof Player)) {
                // Check if all arguments are present.
                if (args.length < 1) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("usage-experience")));
                    return true;
                }

                // Get the target player from the args.
                Player target = Bukkit.getPlayer(args[0]);
                // Check if the target player is null.
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-found")));
                    return true;
                }

                // Send sender the experience of the target player.
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("experience-other")
                        .replaceAll("%player%", target.getName())
                        .replaceAll("%experience%", Utils.format(playerUtils.getPlayerExp(target)))));
                return true;
            }
            // We can safely assume that the sender is a player.
            Player player = (Player) sender;

            // Check if a target player is specified.
            if (args.length >= 1) {
                // Check if the player has permission to check other players experience.
                if (player.hasPermission("Peligon.Economy.Experience.Other") || player.hasPermission("Peligon.Economy.*")) {
                    // Get the target player from the args.
                    Player target = Bukkit.getPlayer(args[0]);
                    // Check if the target player is null.
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-found")));
                        return true;
                    }

                    // Send sender the experience of the target player.
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("experience-other")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%experience%", Utils.format(playerUtils.getPlayerExp(target)))));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                }
                return true;
            }

            // Check if the player has permission to check their own experience.
            if (player.hasPermission("Peligon.Economy.Experience") || player.hasPermission("Peligon.Economy.*")) {
                // Send the player their experience.
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("experience")
                        .replaceAll("%experience%", Utils.format(playerUtils.getPlayerExp(player)))));
            } else {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
            }
        }
        return false;
    }

}
