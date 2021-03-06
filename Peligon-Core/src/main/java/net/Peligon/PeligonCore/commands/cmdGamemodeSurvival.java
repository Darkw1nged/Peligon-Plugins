package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdGamemodeSurvival implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gmsurvival")) {
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                target.setGameMode(GameMode.SURVIVAL);
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("gamemode-changed-other").replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", "survival")));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("Peligon.Core.Gamemode.survival") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gamemode-changed").replaceAll("%gamemode%", "survival")));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            }
            if (args.length == 1) {
                if (player.hasPermission("Peligon.Core.Gamemode.survival.other") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    target.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gamemode-changed-other").replaceAll("%player%", target.getName())
                                    .replaceAll("%gamemode%", "survival")));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            }
        }
        return false;
    }

}
