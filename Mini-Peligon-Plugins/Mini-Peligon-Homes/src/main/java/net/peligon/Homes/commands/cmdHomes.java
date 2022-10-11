package net.peligon.Homes.commands;

import net.peligon.Homes.CustomConfig;
import net.peligon.Homes.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class cmdHomes implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("homes")) {
            if (!(sender instanceof Player)) {
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-player-found")));
                    return true;
                }
                CustomConfig config = new CustomConfig(plugin, String.valueOf(target.getUniqueId()), "Data");
                YamlConfiguration data = config.getConfig();

                if (data.getConfigurationSection("homes").getKeys(false).isEmpty()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-homes-found")));
                    return true;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.homes").replaceAll("%list%",
                        data.getConfigurationSection("homes").getKeys(false).toString().replaceAll("[\\[\\]]", ""))));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 1) {
                if (player.hasPermission("Peligon.Homes.Other") || player.hasPermission("Peligon.Homes.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-player-found")));
                        return true;
                    }
                    CustomConfig config = new CustomConfig(plugin, String.valueOf(target.getUniqueId()), "Data");
                    YamlConfiguration data = config.getConfig();

                    if (data.getConfigurationSection("homes").getKeys(false).isEmpty()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-homes-found")));
                        return true;
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("homes").replaceAll("%list%",
                            data.getConfigurationSection("homes").getKeys(false).toString().replaceAll("[\\[\\]]", ""))));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
                }
                return true;
            }
            if (player.hasPermission("Peligon.Homes.list") || player.hasPermission("Peligon.Homes.*")) {
                CustomConfig config = new CustomConfig(plugin, String.valueOf(player.getUniqueId()), "Data");
                YamlConfiguration data = config.getConfig();

                if (data.getConfigurationSection("homes").getKeys(false).isEmpty()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-homes-found")));
                    return true;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.homes").replaceAll("%list%",
                        data.getConfigurationSection("homes").getKeys(false).toString().replaceAll("[\\[\\]]", ""))));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
            }
        }
        return true;
    }
}