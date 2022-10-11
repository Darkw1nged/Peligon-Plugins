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

public class cmdDeleteHome implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("deletehome")) {
            if (!(sender instanceof Player)) {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.delete-home-usage")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-player-found")));
                    return true;
                }
                String HomeName = args[1];
                if (HomeName.equalsIgnoreCase("")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.specify-name")));
                    return true;
                }
                CustomConfig config = new CustomConfig(plugin, String.valueOf(target.getUniqueId()), "data");
                YamlConfiguration data = config.getConfig();
                if (!data.contains("homes." + HomeName)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.home-not-found")));
                    return true;
                }
                data.set("homes." + HomeName, null);
                config.saveConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") +
                        plugin.getConfig().getString("messages.home-deleted").replace("%home%", HomeName)));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 2) {
                if (player.hasPermission("Peligon.Homes.DeleteHome.Other") || player.hasPermission("Peligon.Homes.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-player-found")));
                        return true;
                    }
                    String HomeName = args[1];
                    if (HomeName.equalsIgnoreCase("")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.specify-name")));
                        return true;
                    }
                    CustomConfig config = new CustomConfig(plugin, String.valueOf(target.getUniqueId()), "data");
                    YamlConfiguration data = config.getConfig();
                    if (!data.contains("homes." + HomeName)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.home-not-found")));
                        return true;
                    }
                    data.set("homes." + HomeName, null);
                    config.saveConfig();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") +
                            plugin.getConfig().getString("messages.home-deleted").replace("%home%", HomeName)));
                    return true;
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
                }
            }
            if (args.length == 1) {
                if (player.hasPermission("Peligon.Homes.DeleteHome") || player.hasPermission("Peligon.Core.*")) {
                    String HomeName = args[0];
                    if (HomeName.equalsIgnoreCase("")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.specify-name")));
                        return true;
                    }
                    CustomConfig config = new CustomConfig(plugin, String.valueOf(player.getUniqueId()), "data");
                    YamlConfiguration data = config.getConfig();
                    if (!data.contains("homes." + HomeName)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.home-not-found")));
                        return true;
                    }
                    data.set("homes." + HomeName, null);
                    config.saveConfig();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") +
                            plugin.getConfig().getString("messages.home-deleted").replace("%home%", HomeName)));
                    return true;
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
                }
            }
        }
        return true;
    }
}
