package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdDifficulty implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("difficulty")) {
            if (sender.hasPermission("Peligon.Core.Difficulty") || sender.hasPermission("Peligon.Core.*")) {
                if (args.length == 1) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("difficulty-usage")));
                        return true;
                    }
                    Player player = (Player) sender;
                    if (args[0].equalsIgnoreCase("peaceful")) {
                        player.getWorld().setDifficulty(Difficulty.PEACEFUL);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-difficulty-updated")));
                    } else if (args[0].equalsIgnoreCase("easy")) {
                        player.getWorld().setDifficulty(Difficulty.EASY);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-difficulty-updated")));
                    } else if (args[0].equalsIgnoreCase("normal")) {
                        player.getWorld().setDifficulty(Difficulty.NORMAL);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-difficulty-updated")));
                    } else if (args[0].equalsIgnoreCase("hard")) {
                        player.getWorld().setDifficulty(Difficulty.HARD);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-difficulty-updated")));
                    }
                    return true;
                }
                String world = args[0];
                if (Bukkit.getWorld(world) == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("world-not-found")));
                    return true;
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("peaceful")) {
                        Bukkit.getWorld(world).setDifficulty(Difficulty.PEACEFUL);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-difficulty-updated")));
                    } else if (args[1].equalsIgnoreCase("easy")) {
                        Bukkit.getWorld(world).setDifficulty(Difficulty.EASY);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-difficulty-updated-specific").replaceAll("%world%", world)));
                    } else if (args[1].equalsIgnoreCase("normal")) {
                        Bukkit.getWorld(world).setDifficulty(Difficulty.NORMAL);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-difficulty-updated-specific").replaceAll("%world%", world)));
                    } else if (args[1].equalsIgnoreCase("hard")) {
                        Bukkit.getWorld(world).setDifficulty(Difficulty.HARD);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("world-difficulty-updated-specific").replaceAll("%world%", world)));
                    }
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }
}
