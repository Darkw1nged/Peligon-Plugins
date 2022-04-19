package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.CustomConfig;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
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
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                CustomConfig config = new CustomConfig(plugin, String.valueOf(target.getUniqueId()), "Data");
                YamlConfiguration data = config.getConfig();

                if (data.getConfigurationSection("homes").getKeys(false).isEmpty()) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-homes-found")));
                    return true;
                }
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("homes").replaceAll("%list%",
                        data.getConfigurationSection("homes").getKeys(false).toString().replaceAll("[\\[\\]]", ""))));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 1) {
                if (player.hasPermission("Peligon.Core.Homes.Other") || player.hasPermission("Peligon.Core.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    CustomConfig config = new CustomConfig(plugin, String.valueOf(target.getUniqueId()), "Data");
                    YamlConfiguration data = config.getConfig();

                    if (data.getConfigurationSection("homes").getKeys(false).isEmpty()) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-homes-found")));
                        return true;
                    }
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("homes").replaceAll("%list%",
                            data.getConfigurationSection("homes").getKeys(false).toString().replaceAll("[\\[\\]]", ""))));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
                return true;
            }
            if (player.hasPermission("Peligon.Core.Homes") || player.hasPermission("Peligon.Core.*")) {
                CustomConfig config = new CustomConfig(plugin, String.valueOf(player.getUniqueId()), "Data");
                YamlConfiguration data = config.getConfig();

                if (data.getConfigurationSection("homes").getKeys(false).isEmpty()) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-homes-found")));
                    return true;
                }
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("homes").replaceAll("%list%",
                        data.getConfigurationSection("homes").getKeys(false).toString().replaceAll("[\\[\\]]", ""))));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return true;
    }
}