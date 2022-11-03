package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdEconomy implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("economy")) {
            if (args.length < 3) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("economy-usage")));
                return true;
            }
            Player target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")
                        .replaceAll("%player%", args[2])
                        .replaceAll("%target%", args[2])));
                return true;
            }

            if (sender.hasPermission("Peligon.Economy.Economy") || sender.hasPermission("Peligon.Economy.*")) {
                if (args[1].equalsIgnoreCase("bank") && plugin.getConfig().getBoolean("Storage.banks", true)) {
                    if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("give")) {
                        Double amount = getAmount(sender, target, args[3]);
                        plugin.Economy.addBankAccount(target, amount);
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix")
                                + plugin.languageFile.getConfig().getString("admin-money-updated")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%target%", target.getName())));

                    } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("take")) {
                        Double amount = getAmount(sender, target, args[3]);
                        plugin.Economy.removeBankAccount(target, amount);
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix")
                                + plugin.languageFile.getConfig().getString("admin-money-updated")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%target%", target.getName())));

                    } else if (args[0].equalsIgnoreCase("set")) {
                        Double amount = getAmount(sender, target, args[3]);
                        plugin.Economy.setBankAccount(target, amount);
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix")
                                + plugin.languageFile.getConfig().getString("admin-money-updated")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%target%", target.getName())));

                    } else if (args[0].equalsIgnoreCase("reset")) {
                        plugin.Economy.removeBankAccount(target, plugin.Economy.getBank(target));
                        plugin.Economy.addBankAccount(target, plugin.getConfig().getInt("Balance.Account Setup.bank"));
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix")
                                + plugin.languageFile.getConfig().getString("admin-money-reset")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%target%", target.getName())));
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("give")) {
                    Double amount = getAmount(sender, target, args[3]);
                    plugin.Economy.addAccount(target, amount);
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix")
                            + plugin.languageFile.getConfig().getString("admin-money-updated")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%target%", target.getName())));

                } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("take")) {
                    Double amount = getAmount(sender, target, args[3]);
                    plugin.Economy.removeAccount(target, amount);
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix")
                            + plugin.languageFile.getConfig().getString("admin-money-updated")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%target%", target.getName())));

                } else if (args[0].equalsIgnoreCase("set")) {
                    Double amount = getAmount(sender, target, args[3]);
                    plugin.Economy.setAccount(target, amount);
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix")
                            + plugin.languageFile.getConfig().getString("admin-money-updated")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%target%", target.getName())));

                } else if (args[0].equalsIgnoreCase("reset")) {
                    plugin.Economy.removeAccount(target, plugin.Economy.getAccount(target));
                    plugin.Economy.addAccount(target, plugin.getConfig().getInt("Balance.Account Setup.cash"));
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix")
                            + plugin.languageFile.getConfig().getString("admin-money-reset")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%target%", target.getName())));
                }
            }
        } else sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-permission")));
        return true;
    }

    private Double getAmount(CommandSender sender, Player target, String args) {
        if (!plugin.Economy.hasAccount(target)) return null;
        double amount;
        try {
            amount = Double.parseDouble(args);
            if (amount < 0) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("invalid-amount")));
                return null;
            }
            return amount;
        } catch (Exception e) {
            sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("invalid-amount")));
            return null;
        }
    }

}
