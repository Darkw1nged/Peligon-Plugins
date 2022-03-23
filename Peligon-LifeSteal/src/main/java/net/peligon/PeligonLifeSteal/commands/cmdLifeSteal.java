package net.peligon.PeligonLifeSteal.commands;

import net.peligon.PeligonLifeSteal.Main;
import net.peligon.PeligonLifeSteal.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdLifeSteal implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("lifesteal")) {
            if (sender.hasPermission("Peligon.LifeSteal.Modify") || sender.hasPermission("Peligon.LifeSteal.*")) {
                if (args.length < 2) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("lifesteal-usage")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")
                            .replaceAll("%player%", args[1])
                            .replaceAll("%target%", args[1])));
                    return true;
                }
                if (!plugin.lives.hasData(target)) {
                    sender.sendMessage(plugin.fileMessage.getConfig().getString("no-player-data")
                            .replaceAll("%player%", args[1])
                            .replaceAll("%target%", args[1]));
                    return true;
                }
                switch (args[0].toLowerCase()) {
                    case "view":
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("lives-other"))
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%amount%", "" + plugin.lives.getLives(target)));
                        break;
                    case "add":
                        if (args.length != 3) {

                            return true;
                        }
                        int toAdd = 0;
                        try {
                            toAdd = Integer.parseInt(args[2]);
                            if (toAdd < 0) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                                return true;
                            }
                        } catch (Exception e) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                        }
                        plugin.lives.addLives(target, toAdd);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("admin-lives-updated"))
                                .replaceAll("%player%", target.getName()));
                        break;
                    case "remove":
                        int toRemove = 0;
                        try {
                            toRemove = Integer.parseInt(args[2]);
                            if (toRemove < 0) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                                return true;
                            }
                        } catch (Exception e) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                        }
                        plugin.lives.removeLives(target, toRemove);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("admin-lives-updated"))
                                .replaceAll("%player%", target.getName()));
                        break;
                    case "set":
                        int toSet = 0;
                        try {
                            toSet = Integer.parseInt(args[2]);
                            if (toSet < 0) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                                return true;
                            }
                        } catch (Exception e) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                        }
                        plugin.lives.setLives(target, toSet);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("admin-lives-updated"))
                                .replaceAll("%player%", target.getName()));
                        break;
                    case "reset":
                        plugin.lives.setLives(target, plugin.getConfig().getInt("Basic-Settings.starting-lives"));
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("admin-lives-reset"))
                                .replaceAll("%player%", target.getName()));
                        break;

                }
            } else
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
        }
        return false;
    }

}
