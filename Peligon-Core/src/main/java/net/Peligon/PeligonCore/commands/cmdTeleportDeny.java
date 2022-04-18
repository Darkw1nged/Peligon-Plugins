package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.TeleportRequest;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdTeleportDeny implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleportdeny")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Core.TeleportRequest") || player.hasPermission("Peligon.Core.*")) {
                for (TeleportRequest request : Utils.teleportRequests) {
                    if (request.getReceiver().equals(player.getUniqueId())) {
                        Utils.teleportRequests.remove(request);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("teleport-request-denied").replaceAll("%player%", Bukkit.getPlayer(request.getSender()).getName() )));
                        return true;
                    }
                }
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-request")));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
