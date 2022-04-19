package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdWorldTime implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("time")) {
            if (sender.hasPermission("Peligon.Core.Time") || sender.hasPermission("Peligon.Core.*")) {
                if (args.length == 1) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("time-usage")));
                        return true;
                    }
                    Player player = (Player) sender;
                    if (args[0].equalsIgnoreCase("day")) {
                        player.getWorld().setTime(0);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-time-updated")));
                    } else if (args[0].equalsIgnoreCase("night")) {
                        player.getWorld().setTime(18000);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-time-updated")));
                    } else if (args[0].equalsIgnoreCase("noon")) {
                        player.getWorld().setTime(6000);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-time-updated")));
                    }
                    return true;
                }
                String world = args[0];
                if (Bukkit.getWorld(world) == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("world-not-found")));
                    return true;
                }
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("day")) {
                        Bukkit.getWorld(world).setTime(0);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-time-updated-specific").replaceAll("%world%", world)));
                    } else if (args[1].equalsIgnoreCase("night")) {
                        Bukkit.getWorld(world).setTime(18000);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-time-updated-specific").replaceAll("%world%", world)));
                    } else if (args[1].equalsIgnoreCase("noon")) {
                        Bukkit.getWorld(world).setTime(6000);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-time-updated-specific").replaceAll("%world%", world)));
                    }
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
