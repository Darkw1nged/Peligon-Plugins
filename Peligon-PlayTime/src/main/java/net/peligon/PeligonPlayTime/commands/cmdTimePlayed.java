package net.peligon.PeligonPlayTime.commands;

import net.peligon.PeligonPlayTime.Main;
import net.peligon.PeligonPlayTime.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class cmdTimePlayed implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("timeplayed")) {
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
                if (!plugin.playerTime.hasData(target)) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-data-found")));
                    return true;
                }
                if (target.isOnline()) {
                    plugin.playerTime.addTime(target);
                }

                long timePlayed = plugin.playerTime.getTimePlayed(target);
                long weeks = TimeUnit.MILLISECONDS.toDays(timePlayed) / 7;
                long days = TimeUnit.MILLISECONDS.toDays(timePlayed);
                long hours = TimeUnit.MILLISECONDS.toHours(timePlayed) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(timePlayed));
                long minutes = TimeUnit.MILLISECONDS.toMinutes(timePlayed) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timePlayed));
                long seconds = TimeUnit.MILLISECONDS.toSeconds(timePlayed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timePlayed));

                String time = plugin.getConfig().getString("format")
                        .replaceAll("%weeks%", weeks + "")
                        .replaceAll("%days%", days + "")
                        .replaceAll("%hours%", hours + "")
                        .replaceAll("%minutes%", minutes + "")
                        .replaceAll("%seconds%", seconds + "");

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("playtime-player").replaceAll("%player%", target.getName())
                                .replaceAll("%time%", time)));
                return true;
            }
            Player player = (Player) sender;
            if (args.length >= 1) {
                if (player.hasPermission("Peligon.PlayTime.View.Other") || player.hasPermission("Peligon.PlayTime.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    if (!plugin.playerTime.hasData(target)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-data-found")));
                        return true;
                    }
                    if (target.isOnline()) {
                        plugin.playerTime.addTime(target);
                    }

                    long timePlayed = plugin.playerTime.getTimePlayed(target);
                    long weeks = TimeUnit.MILLISECONDS.toDays(timePlayed) / 7;
                    long days = TimeUnit.MILLISECONDS.toDays(timePlayed);
                    long hours = TimeUnit.MILLISECONDS.toHours(timePlayed) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(timePlayed));
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(timePlayed) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timePlayed));
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(timePlayed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timePlayed));

                    String time = plugin.getConfig().getString("format")
                            .replaceAll("%weeks%", weeks + "")
                            .replaceAll("%days%", days + "")
                            .replaceAll("%hours%", hours + "")
                            .replaceAll("%minutes%", minutes + "")
                            .replaceAll("%seconds%", seconds + "");

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("playtime-player").replaceAll("%player%", target.getName())
                                    .replaceAll("%time%", time)));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
                return true;
            }
            if (player.hasPermission("Peligon.PlayTime.View") || player.hasPermission("Peligon.PlayTime.*")) {
                if (!plugin.playerTime.hasData(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-data-found")));
                    return true;
                }
                plugin.playerTime.addTime(player);

                long timePlayed = plugin.playerTime.getTimePlayed(player);
                long weeks = TimeUnit.MILLISECONDS.toDays(timePlayed) / 7;
                long days = TimeUnit.MILLISECONDS.toDays(timePlayed);
                long hours = TimeUnit.MILLISECONDS.toHours(timePlayed) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(timePlayed));
                long minutes = TimeUnit.MILLISECONDS.toMinutes(timePlayed) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timePlayed));
                long seconds = TimeUnit.MILLISECONDS.toSeconds(timePlayed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timePlayed));

                String time = plugin.getConfig().getString("format")
                        .replaceAll("%weeks%", weeks + "")
                        .replaceAll("%days%", days + "")
                        .replaceAll("%hours%", hours + "")
                        .replaceAll("%minutes%", minutes + "")
                        .replaceAll("%seconds%", seconds + "");

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("playtime")
                                .replaceAll("%time%", time)));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }


        }
        return true;
    }

}
