package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdEnderchest implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("enderchest")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 1) {
                if (player.hasPermission("Peligon.Core.Enderchest.Other") || player.hasPermission("Peligon.Core.*")) {
                    Player target = plugin.getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    player.openInventory(target.getEnderChest());
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
                return true;
            }
            if (player.hasPermission("Peligon.Core.Enderchest") || player.hasPermission("Peligon.Core.*")) {
                player.openInventory(player.getEnderChest());
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
