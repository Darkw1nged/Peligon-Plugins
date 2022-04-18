package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdTeleport implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleport")) {
            if (!(sender instanceof Player)) {
                if (args.length != 2) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("teleport-usage")));
                    return true;
                }
                Player targetOne = Bukkit.getPlayer(args[0]);
                Player targetTwo = Bukkit.getPlayer(args[1]);
                if (targetOne == null || targetTwo == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                targetOne.teleport(targetTwo);
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("teleport-players")));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 3) {
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int z = Integer.parseInt(args[2]);
                player.teleport(new Location(player.getWorld(), x, y, z));
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("teleport-player-to-coordinates").replaceAll("%x%", args[0])
                                .replaceAll("%y%", args[1]).replaceAll("%z%", args[2])));
            }
            if (args.length == 2) {
                Player targetOne = Bukkit.getPlayer(args[0]);
                Player targetTwo = Bukkit.getPlayer(args[1]);
                if (targetOne == null || targetTwo == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                targetOne.teleport(targetTwo);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("teleport-players")));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                return true;
            }
            player.teleport(target);
            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                    plugin.fileMessage.getConfig().getString("teleport-player")));
            return true;
        }
        return false;
    }

}
