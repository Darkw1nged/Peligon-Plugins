package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdBounty implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bounty")) {
            if (plugin.getConfig().getBoolean("Bounties.enabled", true)) {

                if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
                    if (sender.hasPermission("Peligon.Economy.Bounties") || sender.hasPermission("peligon.Economy.*")) {

                        Player target = Bukkit.getPlayer(args[1]);
                        double amount;
                        if (target == null) {
                            sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")
                                    .replaceAll("%player%", args[1])
                                    .replaceAll("%target%", args[1])));
                            return true;
                        }
                        try {
                            amount = Double.parseDouble(args[2]);
                        } catch (Exception e) {
                            sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-amount")));
                            return true;
                        }
                        if (amount < 0) {
                            sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-amount")));
                            return true;
                        }
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            if (!plugin.Economy.hasEnoughCash(player, amount)) {
                                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("not-enough-money")));
                                return true;
                            }
                        }
                        if (Utils.bounties.containsKey(target.getUniqueId())) {
                            Utils.bounties.put(target.getUniqueId(), Utils.bounties.get(target.getUniqueId()) + amount);
                            if (sender instanceof Player) {
                                Player player = (Player) sender;
                                plugin.Economy.removeAccount(player, amount);
                            }
                        } else {
                            Utils.bounties.put(target.getUniqueId(), amount);
                        }
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                        plugin.languageFile.getConfig().getString("bounty-added"), amount)
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%target%", target.getName()));

                    } else sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-permission")));
                } else if (args.length == 2 && args[0].equalsIgnoreCase("view")) {
                    if (sender.hasPermission("Peligon.Economy.Bounties") || sender.hasPermission("peligon.Economy.*")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")
                                    .replaceAll("%player%", args[1])
                                    .replaceAll("%target%", args[1])));
                            return true;
                        }
                        double bounty = 0.0;
                        if (Utils.bounties.containsKey(target.getUniqueId())) {
                            bounty = Utils.bounties.get(target.getUniqueId());
                        }
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("current-bounty"), bounty)
                                .replaceAll("%player%", target.getName()));

                    } else sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-permission")));
                } else sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("bounty-usage")));
            } else sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("command-disabled")));
        }
        return false;
    }

}
