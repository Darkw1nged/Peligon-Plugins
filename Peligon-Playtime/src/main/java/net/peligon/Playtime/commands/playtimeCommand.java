package net.peligon.Playtime.commands;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.Utils;
import net.peligon.Playtime.libaries.playerUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class playtimeCommand implements CommandExecutor {

    // Getting instance of the main plugin.
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playtime")) {
            // If the command was not run by a player we can give them other options.
            // 9/10 times it will be console sending this command which is an operator.
            if (!(sender instanceof Player)) {
                // If they do not provide any arguments send them the usage message.
                if (args.length < 1) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("usage-playtime")));
                    return true;
                }

                // Check if they have specified a player, Also includes '@a' and '*'
                if (args.length < 2) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-specify-player")));
                    return true;
                }

                if (args[0].equalsIgnoreCase("view")) {
                    // We need a target, It can't be all players.
                    // But we also need to validate the player.
                    Player target = validatePlayer(sender, args[1]);
                    // If the target is null return.
                    if (target == null) return true;

                    // Moving method inside a private local variables to reduce amount of duplicated code.
                    sendPlaytime(sender, target);
                }
                else if (args[0].equalsIgnoreCase("reset")) {
                    // If the command sender has specified that they want all players.
                    // If so reset all player's playtime.
                    if (args[1].equalsIgnoreCase("@a") || args[1].equalsIgnoreCase("*")) {
                        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                            if (playerUtils.hasData(offlinePlayer)) {
                                playerUtils.resetPlaytime(offlinePlayer);
                            }
                        }
                        // Send the command sender a message.
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-reset-all")));
                    } else {
                        // Again we need to validate the player and return if null.
                        Player target = validatePlayer(sender, args[1]);
                        if (target == null) return true;

                        // Reset the player's playtime and send the command sender a message.
                        playerUtils.resetPlaytime(target);
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-reset")
                                        .replaceAll("%player%", target.getName())));
                    }
                }
                else if (args[0].equalsIgnoreCase("pause")) {
                    if (args[1].equalsIgnoreCase("@a") || args[1].equalsIgnoreCase("*")) {
                        // Pause all player's playtime.
                        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                            if (playerUtils.hasData(offlinePlayer)) {
                                if (!playerUtils.isPaused(offlinePlayer)) {
                                    playerUtils.togglePaused(offlinePlayer);
                                }
                            }
                        }
                        // Send the command sender a message.
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-paused-all")));
                    } else {
                        // Again we need to validate the player and return if null.
                        Player target = validatePlayer(sender, args[1]);
                        if (target == null) return true;

                        // Pause the player's playtime and send the command sender a message.
                        if (!playerUtils.isPaused(target)) {
                            playerUtils.togglePaused(target);
                        }
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-paused")
                                        .replaceAll("%player%", target.getName())));
                    }
                }
                else if (args[0].equalsIgnoreCase("unpause")) {
                    if (args[1].equalsIgnoreCase("@a") || args[1].equalsIgnoreCase("*")) {
                        // Unpause all player's playtime.
                        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                            if (playerUtils.hasData(offlinePlayer)) {
                                if (playerUtils.isPaused(offlinePlayer)) {
                                    playerUtils.togglePaused(offlinePlayer);
                                }
                            }
                        }
                        // Send the command sender a message.
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-unpaused-all")));
                    } else {
                        // Again we need to validate the player and return if null.
                        Player target = validatePlayer(sender, args[1]);
                        if (target == null) return true;

                        // Unpause the player's playtime and send the command sender a message.
                        if (playerUtils.isPaused(target)) {
                            playerUtils.togglePaused(target);
                        }
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-unpaused")
                                        .replaceAll("%player%", target.getName())));
                    }
                }
                return true;
            }
            // We can safely assume that it is a player performing this command.
            Player player = (Player) sender;
            if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("view")) {
                    // Check if the player has the correct permission.
                    if (player.hasPermission("Peligon.Playtime.View") || player.hasPermission("Peligon.Playtime.*")) {
                        // We need a target, It can't be all players.
                        // But we also need to validate the player.
                        Player target = validatePlayer(player, args[1]);
                        // If the target is null return.
                        if (target == null) return true;

                        // If the target's playtime is currently hidden then dont show it to the player.
                        if (playerUtils.isHidden(target)) {
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-playtime-hidden")));
                            return true;
                        }

                        sendPlaytime(player, target);
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                    }

                }
                else if (args[0].equalsIgnoreCase("reset")) {
                    if (player.hasPermission("Peligon.Playtime.Reset") || player.hasPermission("Peligon.Playtime.*")) {
                        // If the command sender has specified that they want all players.
                        // If so reset all player's playtime.
                        if (args[1].equalsIgnoreCase("@a") || args[1].equalsIgnoreCase("*")) {
                            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                                if (playerUtils.hasData(offlinePlayer)) {
                                    playerUtils.resetPlaytime(offlinePlayer);
                                }
                            }
                            // Send the command sender a message.
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                    plugin.languageFile.getConfig().getString("playtime-reset-all")));
                        } else {
                            // Again we need to validate the player and return if null.
                            Player target = validatePlayer(player, args[1]);
                            if (target == null) return true;

                            // Reset the player's playtime and send the command sender a message.
                            playerUtils.resetPlaytime(target);
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                    plugin.languageFile.getConfig().getString("playtime-reset")
                                            .replaceAll("%player%", target.getName())));
                        }
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                    }
                }
                else if (args[0].equalsIgnoreCase("pause")) {
                    if (player.hasPermission("Peligon.Playtime.Pause") || player.hasPermission("Peligon.Playtime.*")) {
                        if (args[1].equalsIgnoreCase("@a") || args[1].equalsIgnoreCase("*")) {
                            // Pause all player's playtime.
                            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                                if (playerUtils.hasData(offlinePlayer)) {
                                    if (!playerUtils.isPaused(offlinePlayer)) {
                                        playerUtils.togglePaused(offlinePlayer);
                                    }
                                }
                            }
                            // Send the command sender a message.
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                    plugin.languageFile.getConfig().getString("playtime-paused-all")));
                        } else {
                            // Again we need to validate the player and return if null.
                            Player target = validatePlayer(player, args[1]);
                            if (target == null) return true;
                            // Pause the player's playtime and send the command sender a message.
                            if (!playerUtils.isPaused(target)) {
                                playerUtils.togglePaused(target);
                            }
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                    plugin.languageFile.getConfig().getString("playtime-paused")
                                            .replaceAll("%player%", target.getName())));
                        }
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                    }
                }
                else if (args[0].equalsIgnoreCase("unpause")) {
                    if (player.hasPermission("Peligon.Playtime.Pause") || player.hasPermission("Peligon.Playtime.*")) {
                        if (args[1].equalsIgnoreCase("@a") || args[1].equalsIgnoreCase("*")) {
                            // Unpause all player's playtime.
                            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                                if (playerUtils.hasData(offlinePlayer)) {
                                    if (playerUtils.isPaused(offlinePlayer)) {
                                        playerUtils.togglePaused(offlinePlayer);
                                    }
                                }
                            }
                            // Send the command sender a message.
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                    plugin.languageFile.getConfig().getString("playtime-unpaused-all")));
                        } else {
                            // Again we need to validate the player and return if null.
                            Player target = validatePlayer(player, args[1]);
                            if (target == null) return true;

                            // Unpause the player's playtime and send the command sender a message.
                            if (playerUtils.isPaused(target)) {
                                playerUtils.togglePaused(target);
                            }
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                    plugin.languageFile.getConfig().getString("playtime-unpaused")
                                            .replaceAll("%player%", target.getName())));
                        }
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                    }
                }
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("view")) {
                    // Check if the player has data, If not then return true.
                    if (!playerUtils.hasData(player)) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-data-found")));
                        return true;
                    }

                    // Send the player their playtime.
                    sendPlaytime(player, player);
                }
                else if (args[0].equalsIgnoreCase("reset")) {
                    if (player.hasPermission("Peligon.Playtime.Reset") || player.hasPermission("Peligon.Playtime.*")) {
                        // Reset the player's playtime and send the command sender a message.
                        playerUtils.resetPlaytime(player);
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-reset")
                                        .replaceAll("%player%", player.getName())));
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                    }
                }
                else if (args[0].equalsIgnoreCase("pause")) {
                    if (player.hasPermission("Peligon.Playtime.Pause") || player.hasPermission("Peligon.Playtime.*")) {
                        // Pause the player's playtime and send the command sender a message.
                        if (!playerUtils.isPaused(player)) {
                            playerUtils.togglePaused(player);
                        }
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-paused")
                                        .replaceAll("%player%", player.getName())));
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                    }
                }
                else if (args[0].equalsIgnoreCase("unpause")) {
                    if (player.hasPermission("Peligon.Playtime.Pause") || player.hasPermission("Peligon.Playtime.*")) {
                        if (playerUtils.isPaused(player)) {
                            playerUtils.togglePaused(player);
                        }
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-unpaused")
                                        .replaceAll("%player%", player.getName())));
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                    }
                }
                else if (args[0].equalsIgnoreCase("hide")) {
                    if (player.hasPermission("Peligon.Playtime.Hide") || player.hasPermission("Peligon.Playtime.*")) {
                        playerUtils.toggleHidden(player);

                        if (playerUtils.isHidden(player)) {
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                    plugin.languageFile.getConfig().getString("playtime-hidden")
                                            .replaceAll("%player%", player.getName())));
                            return true;
                        }
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("playtime-unhidden")
                                        .replaceAll("%player%", player.getName())));
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                    }
                }
                return true;
            }

            // Check if the player has data, If not then return true.
            if (!playerUtils.hasData(player)) {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-data-found")));
                return true;
            }

             // Send the player their playtime.
            sendPlaytime(player, player);
        }
        return false;
    }

    private Player validatePlayer(CommandSender sender, String potentialPlayer) {
        // Trying to get the player. Does not work for offline players.
        Player target = Bukkit.getPlayer(potentialPlayer);
        if (target == null) {
            sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-player")));
            return null;
        }

        if (!playerUtils.hasData(target)) {
            sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-data-found")));
            return null;
        }
        return target;
    }

    private void sendPlaytime(CommandSender sender, Player player) {
        // If the player is online then update their playtime.
        if (player.isOnline()) playerUtils.addPlaytime(player);

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

        // Send the command sender the player's playtime.
        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                plugin.languageFile.getConfig().getString("playtime-player")
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%time%", playtime)));
    }

}
