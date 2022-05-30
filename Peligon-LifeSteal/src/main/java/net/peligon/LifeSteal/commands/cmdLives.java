package net.peligon.LifeSteal.commands;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdLives implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("lives")) {
            if (!(sender instanceof Player)) {
                if (args.length >= 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found").replaceAll("%player%", args[0])));
                        return true;
                    }
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("lives-other"))
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%amount%", "" + target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                }
                return true;
            }
            Player player = (Player) sender;
            if (args.length >= 1) {
                if (sender.hasPermission("Peligon.LifeSteal.View.Other") || sender.hasPermission("Peligon.LifeSteal.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found").replaceAll("%player%", args[0])));
                        return true;
                    }
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("lives-other")
                                    .replaceAll("%amount%", "" + target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()))
                            .replaceAll("%player%", target.getName()));
                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("lives")
                        .replaceAll("%amount%", "" + player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue())));
            }
        }
        return false;
    }

}