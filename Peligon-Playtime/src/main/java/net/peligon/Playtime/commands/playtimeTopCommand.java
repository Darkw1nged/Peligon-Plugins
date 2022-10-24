package net.peligon.Playtime.commands;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.SystemUtils;
import net.peligon.Playtime.libaries.Utils;
import net.peligon.Playtime.libaries.playerUtils;
import net.peligon.Playtime.libaries.struts.leaderboardResult;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class playtimeTopCommand implements CommandExecutor {

    // Getting instance of the main class.
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playtimetop")) {
            if (sender.hasPermission("Peligon.Playtime.Top") || sender.hasPermission("Peligon.Playtime.*")) {

                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("playtime-top").replaceAll("%number%", String.valueOf(plugin.getConfig().getInt("leaderboard.players")))));

                SystemUtils.getPlaytimeLeaderboard().entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(entry -> {
                    leaderboardResult result = entry.getValue();
                    OfflinePlayer player = Bukkit.getOfflinePlayer(result.getUUID());

                    // Check if the player has data, if they don't then continue.
                    // This should be unnecessary but just in case.
                    if (playerUtils.hasData(player)) {
                        // Get the players playtime and put it in the correct format.
                        long rawPlaytime = playerUtils.getPlaytime(player);
                        long playtimeWeeks = TimeUnit.MILLISECONDS.toDays(rawPlaytime) / 7;
                        long playtimeDays = TimeUnit.MILLISECONDS.toDays(rawPlaytime);
                        long playtimeHours = TimeUnit.MILLISECONDS.toHours(rawPlaytime) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(rawPlaytime));
                        long playtimeMinutes = TimeUnit.MILLISECONDS.toMinutes(rawPlaytime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(rawPlaytime));
                        long playtimeSeconds = TimeUnit.MILLISECONDS.toSeconds(rawPlaytime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(rawPlaytime));

                        // Transfer the above data into a string.
                        String playtime = playtimeWeeks <= 0 ? "" : playtimeWeeks + "w, ";
                        playtime += playtimeDays <= 0 ? "" : playtimeDays + "d, ";
                        playtime += playtimeHours <= 0 ? "" : playtimeHours + "h, ";
                        playtime += playtimeMinutes <= 0 ? "" : playtimeMinutes + "m, ";
                        playtime += playtimeSeconds <= 0 ? "" : playtimeSeconds + "s";

                        sender.sendMessage(Utils.chatColor(plugin.getConfig().getString("leaderboard.format")
                                .replaceAll("%player%", player.getName())
                                .replaceAll("%position%", String.valueOf(result.getPosition()))
                                .replaceAll("%time_played%", playtime)));
                    }
                });
            } else {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
