package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
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
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")));
                    return true;
                }

                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("experience-other")
                        .replaceAll("%player%", target.getName()), (double) Utils.getPlayerExp(target)));
                return true;
            }
            Player player = (Player) sender;
            if (args.length < 1) {
                if (player.hasPermission("Peligon.Economy.Experience.View") || player.hasPermission("Peligon.Economy.*")) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("experience"), (double) Utils.getPlayerExp(player)));
                }
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")));
                return true;
            }
            if (player.hasPermission("Peligon.Economy.Experience.View.Other") || player.hasPermission("Peligon.Economy.*")) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("experience-other")
                        .replaceAll("%player%", target.getName()), (double) Utils.getPlayerExp(target)));
            }
        }
        return false;
    }

}
