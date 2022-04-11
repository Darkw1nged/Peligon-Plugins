package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdDay implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("day")) {
            if (sender.hasPermission("Peligon.Core.Day") || sender.hasPermission("Peligon.Core.*")) {
                if (args.length == 1) {
                    String world = args[0];
                    if (plugin.getServer().getWorld(world) != null) {
                        plugin.getServer().getWorld(world).setTime(0);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-time-updated-specific").replaceAll("%world%", world)));
                    } else {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("world-not-found")));
                    }
                    return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("time-usage")));
                    return true;
                }
                Player player = (Player) sender;
                player.getWorld().setTime(0);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("world-time-updated")));

            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
