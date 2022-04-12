package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class cmdKill implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kill")) {
            if (sender.hasPermission("Peligon.Core.Kill") || sender.hasPermission("Peligon.Core.*")) {
                if (args.length == 0) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("kill-usage")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("@e")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                        return true;
                    }
                    Player player = (Player) sender;
                    if (args[1].equalsIgnoreCase("!player")) {
                        for (Entity entity : player.getWorld().getEntities()) {
                            if (!(entity instanceof Player)) {
                                entity.remove();
                            }
                        }
                    } else {
                        for (Entity entity : player.getWorld().getEntities()) {
                            if (entity instanceof Player) continue;
                            entity.remove();
                        }
                    }
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("killed-all-entities")));
                } else if (args[0].equalsIgnoreCase("@a") || args[0].equalsIgnoreCase("*")) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.setHealth(0);
                    }
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("killed-all-players")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    target.setHealth(0);
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("player-killed").replaceAll("%player%", target.getName())));
                }
            }
        }
        return false;
    }

}
