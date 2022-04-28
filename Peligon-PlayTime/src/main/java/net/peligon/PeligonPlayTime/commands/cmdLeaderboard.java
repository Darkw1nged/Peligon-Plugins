package net.peligon.PeligonPlayTime.commands;

import net.peligon.PeligonPlayTime.Main;
import net.peligon.PeligonPlayTime.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class cmdLeaderboard implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playtimetop")) {
            if (sender.hasPermission("Peligon.PlayTime.Top") || sender.hasPermission("Peligon.PlayTime.*")) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (plugin.playerTime.hasData(online)) {
                        plugin.playerTime.addTime(online);
                    }
                }
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("playtime-top")
                        .replaceAll("%number%", String.valueOf(plugin.getConfig().getInt("leaderboard.players")))));

                AtomicInteger i = new AtomicInteger();
                plugin.playerTime.getTimePlayedLeaderboard().entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(entry -> {
                    long timePlayed = plugin.playerTime.getTimePlayed(entry.getKey());
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

                    OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());

                    sender.sendMessage(Utils.chatColor(plugin.getConfig().getString("leaderboard.format").replaceAll("%position%", (i.getAndIncrement() + 1) + "")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%time_played%", time)));
                });
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
