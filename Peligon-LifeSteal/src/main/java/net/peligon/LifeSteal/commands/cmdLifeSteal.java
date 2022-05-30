package net.peligon.LifeSteal.commands;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("ALL")
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
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                if (!plugin.bounties.hasData(target)) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-data")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("view")) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("lives-other"))
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%amount%", "" + target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length != 3) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("lifesteal-usage")));
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
                    target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + (toAdd * 2));
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("admin-lives-updated"))
                            .replaceAll("%player%", target.getName()));
                    return true;
                }
                if (args[0].equalsIgnoreCase("remove")) {
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
                    if (target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - toRemove < 2) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("lives-remove-failed")));
                        return true;
                    }

                    target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - (toRemove * 2));
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("admin-lives-updated"))
                            .replaceAll("%player%", target.getName()));
                    return true;
                }
                if (args[0].equalsIgnoreCase("set")) {
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
                    target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(toSet * 2);
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("admin-lives-updated"))
                            .replaceAll("%player%", target.getName()));
                }
                if (args[0].equalsIgnoreCase("reset")) {
                    target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("admin-lives-reset"))
                            .replaceAll("%player%", target.getName()));
                    return true;
                }
            } else
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
        }
        return false;
    }

}
