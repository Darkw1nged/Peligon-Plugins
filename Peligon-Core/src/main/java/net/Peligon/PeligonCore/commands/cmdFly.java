package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdFly implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fly")) {
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                    return true;
                }
                if (target.getAllowFlight()) {
                    target.setAllowFlight(false);
                    target.setFlying(false);
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("fly-disabled-other").replaceAll("%player%", target.getName())));
                } else {
                    target.setAllowFlight(true);
                    target.setFlying(true);
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("fly-enabled-other").replaceAll("%player%", target.getName())));
                }
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 1) {
                if (player.hasPermission("Peligon.Core.Fly.Other") || player.hasPermission("Peligon.Core.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                        return true;
                    }
                    if (target.getAllowFlight()) {
                        target.setAllowFlight(false);
                        target.setFlying(false);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("fly-disabled-other").replaceAll("%player%", target.getName())));
                    } else {
                        target.setAllowFlight(true);
                        target.setFlying(true);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("fly-enabled-other").replaceAll("%player%", target.getName())));
                    }
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            }
            if (player.hasPermission("Peligon.Core.Fly") || player.hasPermission("Peligon.Core.*")) {
                if (args.length == 0) {
                    if (player.getAllowFlight()) {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("fly-disabled")));
                    } else {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("fly-enabled")));
                    }
                    return true;
                }
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return true;
    }

}
