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

public class cmdVanish implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vanish")) {
            if (sender.hasPermission("Peligon.Core.Vanish") || sender.hasPermission("Peligon.Core.*")) {
                if (args.length == 0) {
                    if (sender.hasPermission("Peligon.Core.Vanish") || sender.hasPermission("Peligon.Core.*")) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                            return true;
                        }
                        Player player = (Player) sender;
                        CustomConfig config = new CustomConfig(plugin, player.getUniqueId().toString(), "data");
                        YamlConfiguration data = config.getConfig();
                        if (data.getBoolean("Vanish")) {
                            data.set("Vanish", false);
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                online.showPlayer(player);
                            }
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("vanish-disabled")));
                        } else {
                            data.set("Vanish", true);
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                if (online.hasPermission("Peligon.Core.SeeVanish") || online.hasPermission("Peligon.Core.*")) continue;
                                online.hidePlayer(player);
                            }
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("vanish-enabled")));
                        }
                        config.saveConfig();
                    } else {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                    }
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                if (sender.hasPermission("Peligon.Core.Vanish.Other") || sender.hasPermission("Peligon.Core.*")) {
                    CustomConfig config = new CustomConfig(plugin, target.getUniqueId().toString(), "data");
                    YamlConfiguration data = config.getConfig();
                    if (data.getBoolean("Vanish")) {
                        data.set("Vanish", false);
                        target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vanish-disabled-other").replaceAll("%player%", target.getName())));
                    } else {
                        data.set("Vanish", true);
                        target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vanish-enabled-other").replaceAll("%player%", target.getName())));
                    }
                }
            }
        }
        return false;
    }

}
