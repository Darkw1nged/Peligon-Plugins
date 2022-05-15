package net.peligon.LifeSteal.commands;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdBounty implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bounty")) {
            if (plugin.getConfig().getStringList("Events").contains("bounties")) {

                if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
                    if (sender.hasPermission("Peligon.LifeSteal.Bounties") || sender.hasPermission("peligon.LifeSteal.*")) {

                        Player target = Bukkit.getPlayer(args[1]);
                        double amount;
                        if (target == null) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")
                                    .replaceAll("%player%", args[1])
                                    .replaceAll("%target%", args[1])));
                            return true;
                        }
                        try {
                            amount = Double.parseDouble(args[2]);
                        } catch (Exception e) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                            return true;
                        }
                        if (amount < 0) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                            return true;
                        }
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            if (!plugin.getEconomy().has(player, amount)) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                                return true;
                            }
                        }

                        if (plugin.lives.hasData(target)) {
                            plugin.bounties.addBounty(target, amount);

                            if (sender instanceof Player) {
                                Player player = (Player) sender;
                                plugin.getEconomy().withdrawPlayer(player, amount);
                            }
                        }

                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("bounty-added"), amount)
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%target%", target.getName()));

                    } else sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                } else if (args.length == 2 && args[0].equalsIgnoreCase("view")) {
                    if (sender.hasPermission("Peligon.LifeSteal.Bounties") || sender.hasPermission("peligon.LifeSteal.*")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")
                                    .replaceAll("%player%", args[1])
                                    .replaceAll("%target%", args[1])));
                            return true;
                        }
                        double bounty = 0.0;

                        if (plugin.lives.hasData(target)) {
                            bounty = plugin.bounties.getBounty(target);
                        }

                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("current-bounty"), bounty)
                                .replaceAll("%player%", target.getName()));

                    } else sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                } else sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("bounty-usage")));
            } else sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("command-disabled")));
        }
        return false;
    }

}
