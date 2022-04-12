package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdWorldWeather implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("weather")) {
            if (sender.hasPermission("Peligon.Core.Weather") || sender.hasPermission("Peligon.Core.*")) {
                if (args.length == 1) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("weather-usage")));
                        return true;
                    }
                    Player player = (Player) sender;
                    if (args[0].equalsIgnoreCase("clear")) {
                        player.getWorld().setStorm(false);
                        player.getWorld().setThundering(false);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-weather-updated")));
                    } else if (args[0].equalsIgnoreCase("rain")) {
                        player.getWorld().setStorm(true);
                        player.getWorld().setThundering(false);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-weather-updated")));
                    } else if (args[0].equalsIgnoreCase("storm")) {
                        player.getWorld().setStorm(true);
                        player.getWorld().setThundering(true);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-weather-updated")));
                    }
                    return true;
                }
                String world = args[0];
                if (Bukkit.getWorld(world) == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("world-not-found")));
                    return true;
                }
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("clear")) {
                        Bukkit.getWorld(world).setStorm(false);
                        Bukkit.getWorld(world).setThundering(false);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-weather-updated-specific").replaceAll("%world%", world)));
                    } else if (args[1].equalsIgnoreCase("rain")) {
                        Bukkit.getWorld(world).setStorm(true);
                        Bukkit.getWorld(world).setThundering(false);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-weather-updated-specific").replaceAll("%world%", world)));
                    } else if (args[1].equalsIgnoreCase("storm")) {
                        Bukkit.getWorld(world).setStorm(true);
                        Bukkit.getWorld(world).setThundering(true);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-weather-updated-specific").replaceAll("%world%", world)));
                    }
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }
}
