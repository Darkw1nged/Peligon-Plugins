package net.peligon.Playtime.libaries;

import net.peligon.Playtime.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Locale;

@SuppressWarnings("ALL")
public class playtimeRewardTimer extends BukkitRunnable {

    private final Main plugin = Main.getInstance;

    @Override
    public void run() {
        // Looping through all players in activeTimes
        Utils.activeTimes.forEach(uuid -> {
            // Getting the player from the uuid
            Player player = Bukkit.getPlayer(uuid);
            // Check if the player is null and that they are online.
            if (player == null || !player.isOnline()) return;

            // If the players playtime is paused, return.
            if (playerUtils.isPaused(player)) return;

            // Update the players playtime.
            playerUtils.addPlaytime(player);

            // Looping through all rewards from the config.
            for (String reward : plugin.getConfig().getConfigurationSection("playtime-rewards").getKeys(false)) {

                // Getting how long the delay is for the reward and formatting the time.
                long rewardDelay = formatTime(plugin.getConfig().getString("playtime-rewards." + reward + ".delay"));
                // Getting the player's playtime. in seconds.
                long playtime = playerUtils.getPlaytime(player) / 1000;
                // Getting how many times the player has claimed the reward.
                int timesClaimed = plugin.getConfig().getInt("playtime-rewards." + reward + ".awarded." + player.getUniqueId().toString() + ".times-claimed");

                // If the timesClaimed is 0, We don't need to do any extra calculations.
                if (timesClaimed == 0) {
                    // If the player has played long enough to claim the reward.
                    if (playtime >= rewardDelay) {
                        // Looping through all commands.
                        for (String action : plugin.getConfig().getStringList("playtime-rewards." + reward + ".commands")) {
                            // Replacing %player% with the player's name.
                            action = action.replaceAll("%player%", player.getName());
                            // If the action contains %msg% OR %message%, send the player a message.
                            if (action.contains("%message%") || action.contains("%msg%")) {
                                player.sendMessage(Utils.chatColor(action.replaceAll("%message%", "").replaceAll("%msg%", "")));
                            }
                            // If the action contains %broadcast%, broadcast the message.
                            else if (action.contains("%broadcast%")) {
                                Bukkit.broadcastMessage(Utils.chatColor(action.replaceAll("%broadcast%", "")));
                            }
                            // If the action contains %command% OR %cmd%, run the command.
                            else if (action.contains("%command%") || action.contains("%cmd%")) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action.replaceAll("%command%", "").replaceAll("%cmd%", ""));
                            }
                        }

                        // If the reward has a title-message, send the player a title.
                        if (plugin.getConfig().getBoolean("playtime-rewards." + reward + ".title-message.send-title")) {
                            player.sendTitle(Utils.chatColor(plugin.getConfig().getString("playtime-rewards." + reward + ".title-message.title")),
                                    Utils.chatColor(plugin.getConfig().getString("playtime-rewards." + reward + ".title-message.subtitle")),
                                    plugin.getConfig().getInt("playtime-rewards." + reward + ".title-message.fade-in"),
                                    plugin.getConfig().getInt("playtime-rewards." + reward + ".title-message.stay"),
                                    plugin.getConfig().getInt("playtime-rewards." + reward + ".title-message.fade-out"));
                        }

                        // Add player to awarded
                        plugin.getConfig().set("playtime-rewards." + reward + ".awarded." + player.getUniqueId().toString() + ".name", player.getName());
                        plugin.getConfig().set("playtime-rewards." + reward + ".awarded." + player.getUniqueId().toString() + ".times-claimed", 1);
                        plugin.saveConfig();
                    }
                    continue;
                }

                // Check if the reward is repeatable.
                if (plugin.getConfig().getBoolean("playtime-rewards." + reward + ".repeatable")) {
                    // If the player has played long enough to claim the reward.
                    if (playtime >= (rewardDelay * (timesClaimed + 1))) {
                        // Looping through all commands.
                        for (String action : plugin.getConfig().getStringList("playtime-rewards." + reward + ".commands")) {
                            // Replacing %player% with the player's name.
                            action = action.replaceAll("%player%", player.getName());
                            // If the action contains %msg% OR %message%, send the player a message.
                            if (action.contains("%message%") || action.contains("%msg%")) {
                                player.sendMessage(Utils.chatColor(action.replaceAll("%message%", "").replaceAll("%msg%", "")));
                            }
                            // If the action contains %broadcast%, broadcast the message.
                            else if (action.contains("%broadcast%")) {
                                Bukkit.broadcastMessage(Utils.chatColor(action.replaceAll("%broadcast%", "")));
                            }
                            // If the action contains %command% OR %cmd%, run the command.
                            else if (action.contains("%command%") || action.contains("%cmd%")) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action.replaceAll("%command%", "").replaceAll("%cmd%", ""));
                            }
                        }

                        // If the reward has a title-message, send the player a title.
                        if (plugin.getConfig().getBoolean("playtime-rewards." + reward + ".title-message.send-title")) {
                            player.sendTitle(Utils.chatColor(plugin.getConfig().getString("playtime-rewards." + reward + ".title-message.title")),
                                    Utils.chatColor(plugin.getConfig().getString("playtime-rewards." + reward + ".title-message.subtitle")),
                                    plugin.getConfig().getInt("playtime-rewards." + reward + ".title-message.fade-in"),
                                    plugin.getConfig().getInt("playtime-rewards." + reward + ".title-message.stay"),
                                    plugin.getConfig().getInt("playtime-rewards." + reward + ".title-message.fade-out"));
                        }

                        // Increment times-claimed
                        plugin.getConfig().set("playtime-rewards." + reward + ".awarded." + player.getUniqueId().toString() + ".times-claimed", timesClaimed + 1);
                        plugin.saveConfig();
                    }
                }
            }
        });
    }

    private long formatTime(String time) {
        long seconds = 0;

        if (time.contains("s")) {
            seconds += Long.parseLong(time.replace("s", ""));
        } else if (time.contains("m")) {
            seconds += Long.parseLong(time.replace("m", "")) * 60;
        } else if (time.contains("h")) {
            seconds += Long.parseLong(time.replace("h", "")) * 60 * 60;
        } else if (time.contains("d")) {
            seconds += Long.parseLong(time.replace("d", "")) * 60 * 60 * 24;
        } else if (time.contains("w")) {
            seconds += Long.parseLong(time.replace("w", "")) * 60 * 60 * 24 * 7;
        } else if (time.contains("mo")) {
            seconds += Long.parseLong(time.replace("mo", "")) * 60 * 60 * 24 * 30;
        } else if (time.contains("y")) {
            seconds += Long.parseLong(time.replace("y", "")) * 60 * 60 * 24 * 365;
        }
        return seconds;
    }

}
