package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdGodmode implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("godmode")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("godmode-usage")));
                    return true;
                }
                Player player = (Player) sender;
                if (player.hasPermission("Peligon.Core.God") || player.hasPermission("Peligon.Core.*")) {
                    if (Utils.godmode.contains(player.getUniqueId())) {
                        Utils.godmode.remove(player.getUniqueId());
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("godmode-disabled")));
                    } else {
                        Utils.godmode.add(player.getUniqueId());
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("godmode-enabled")));
                    }
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                return true;
            }
            if (sender.hasPermission("Peligon.Core.God.Other") || sender.hasPermission("Peligon.Core.*")) {
                if (Utils.godmode.contains(target.getUniqueId())) {
                    Utils.godmode.remove(target.getUniqueId());
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("godmode-disabled-other").replaceAll("%player%", target.getName())));
                } else {
                    Utils.godmode.add(target.getUniqueId());
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("godmode-enabled-other").replaceAll("%player%", target.getName())));
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
