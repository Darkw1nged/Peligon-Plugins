package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdGamemodeSpectator implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gmspectator")) {
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
                target.setGameMode(GameMode.SPECTATOR);
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("gamemode-changed-other").replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", "SPECTATOR")));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("Peligon.Core.Gamemode.spectator") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gamemode-changed").replaceAll("%gamemode%", "SPECTATOR")));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            }
            if (args.length == 1) {
                if (player.hasPermission("Peligon.Core.Gamemode.spectator.other") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    target.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gamemode-changed-other").replaceAll("%player%", target.getName())
                                    .replaceAll("%gamemode%", "SPECTATOR")));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            }
        }
        return false;
    }

}
