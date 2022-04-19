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

public class cmdDeleteHome implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("deletehome")) {
            if (!(sender instanceof Player)) {
                if (args.length < 2) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("delete-home-usage")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                String HomeName = args[1];
                if (HomeName.equalsIgnoreCase("")) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                    return true;
                }
                CustomConfig config = new CustomConfig(plugin, String.valueOf(target.getUniqueId()), "data");
                YamlConfiguration data = config.getConfig();
                if (!data.contains("homes." + HomeName)) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("home-not-exist")));
                    return true;
                }
                data.set("homes." + HomeName, null);
                config.saveConfig();
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("home-deleted").replace("%home%", HomeName)));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 2) {
                if (player.hasPermission("Peligon.Core.DeleteHome.Other") || player.hasPermission("Peligon.Core.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    String HomeName = args[1];
                    if (HomeName.equalsIgnoreCase("")) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                        return true;
                    }
                    CustomConfig config = new CustomConfig(plugin, String.valueOf(target.getUniqueId()), "data");
                    YamlConfiguration data = config.getConfig();
                    if (!data.contains("homes." + HomeName)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("home-not-exist")));
                        return true;
                    }
                    data.set("homes." + HomeName, null);
                    config.saveConfig();
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("home-deleted").replace("%home%", HomeName)));
                    return true;
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            }
            if (args.length == 1) {
                if (player.hasPermission("Peligon.Core.DeleteHome") || player.hasPermission("Peligon.Core.*")) {
                    String HomeName = args[0];
                    if (HomeName.equalsIgnoreCase("")) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                        return true;
                    }
                    CustomConfig config = new CustomConfig(plugin, String.valueOf(player.getUniqueId()), "data");
                    YamlConfiguration data = config.getConfig();
                    if (!data.contains("homes." + HomeName)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("home-not-exist")));
                        return true;
                    }
                    data.set("homes." + HomeName, null);
                    config.saveConfig();
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("home-deleted").replace("%home%", HomeName)));
                    return true;
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            }
        }
        return true;
    }
}
