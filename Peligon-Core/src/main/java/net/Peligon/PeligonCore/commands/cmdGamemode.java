package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdGamemode implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (!(sender instanceof Player)) {
                if (args.length != 2) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gamemode-usage")));
                    return true;
                }
                Player target = plugin.getServer().getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
                    target.setGameMode(GameMode.SURVIVAL);
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gamemode-changed-other").replace("%player%", target.getName())
                                    .replace("%gamemode%", "survival")));
                } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
                    target.setGameMode(GameMode.CREATIVE);
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gamemode-changed-other").replace("%player%", target.getName())
                                    .replace("%gamemode%", "creative")));
                } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
                    target.setGameMode(GameMode.ADVENTURE);
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gamemode-changed-other").replace("%player%", target.getName())
                                    .replace("%gamemode%", "adventure")));
                } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                    target.setGameMode(GameMode.SPECTATOR);
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gamemode-changed-other").replace("%player%", target.getName())
                                    .replace("%gamemode%", "spectator")));
                }
                return true;
            }
            Player player = (Player) sender;
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
                    if (args.length == 2) {
                        if (player.hasPermission("Peligon.Core.Gamemode.survival.other") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                            Player target = plugin.getServer().getPlayer(args[1]);
                            if (target == null) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                                return true;
                            }
                            target.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gamemode-changed-other").replace("%player%", target.getName())
                                            .replace("%gamemode%", "survival")));
                            return true;
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                    }
                    if (player.hasPermission("Peligon.Core.Gamemode.survival") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("gamemode-changed").replaceAll("%gamemode%", "survival")));
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                    }
                } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
                    if (args.length == 2) {
                        if (player.hasPermission("Peligon.Core.Gamemode.creative.other") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                            Player target = plugin.getServer().getPlayer(args[1]);
                            if (target == null) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                                return true;
                            }
                            target.setGameMode(GameMode.CREATIVE);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gamemode-changed-other").replace("%player%", target.getName())
                                            .replace("%gamemode%", "creative")));
                            return true;
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        if (player.hasPermission("Peligon.Core.Gamemode.creative") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                            player.setGameMode(GameMode.CREATIVE);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gamemode-changed").replaceAll("%gamemode%", "creative")));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
                    if (args.length == 2) {
                        if (player.hasPermission("Peligon.Core.Gamemode.adventure.other") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                            Player target = plugin.getServer().getPlayer(args[1]);
                            if (target == null) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                                return true;
                            }
                            target.setGameMode(GameMode.ADVENTURE);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gamemode-changed-other").replace("%player%", target.getName())
                                            .replace("%gamemode%", "adventure")));
                            return true;
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        if (player.hasPermission("Peligon.Core.Gamemode.adventure") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                            player.setGameMode(GameMode.ADVENTURE);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gamemode-changed").replaceAll("%gamemode%", "adventure")));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                    if (args.length == 2) {
                        if (player.hasPermission("Peligon.Core.Gamemode.spectator.other") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                            Player target = plugin.getServer().getPlayer(args[1]);
                            if (target == null) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                                return true;
                            }
                            target.setGameMode(GameMode.SPECTATOR);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gamemode-changed-other").replace("%player%", target.getName())
                                            .replace("%gamemode%", "spectator")));
                            return true;
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        if (player.hasPermission("Peligon.Core.Gamemode.spectator") || player.hasPermission("Peligon.Core.Gamemode.*") || player.hasPermission("Peligon.Core.*")) {
                            player.setGameMode(GameMode.SPECTATOR);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gamemode-changed").replaceAll("%gamemode%", "spectator")));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                    }
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gamemode-usage")));
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gamemode-usage")));
            }
        }
        return false;
    }

}
