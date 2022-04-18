package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class cmdTeleportHere implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleporthere")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Core.TeleportHere") || player.hasPermission("Peligon.Core.*")) {
                if (args.length < 1) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("teleport-here-usage")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("@e")) {
                    if (args[1].equalsIgnoreCase("!player")) {
                        for (Entity entity : player.getWorld().getEntities()) {
                            if (!(entity instanceof Player)) {
                                entity.teleport(player.getLocation());
                            }
                        }
                    } else {
                        for (Entity entity : player.getWorld().getEntities()) {
                            if (entity instanceof Player) continue;
                            entity.teleport(player.getLocation());
                        }
                    }
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("teleport-all-entities")));
                } else if (args[0].equalsIgnoreCase("@a") || args[0].equalsIgnoreCase("*")) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.teleport(player.getLocation());
                    }
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("teleport-all-players")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    target.teleport(player.getLocation());
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("teleport-here").replaceAll("%player%", target.getName())));
                }
            }
        }
        return false;
    }

}
