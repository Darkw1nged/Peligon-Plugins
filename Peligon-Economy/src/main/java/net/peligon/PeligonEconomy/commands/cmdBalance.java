package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdBalance implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("balance")) {
            if (!(sender instanceof Player)) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("bank")) {
                        if (args.length == 2) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target == null) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")
                                        .replaceAll("%player%", args[1])
                                        .replaceAll("%target%", args[1])));
                                return true;
                            }
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("balance-other"), plugin.Economy.getBank(target))
                                    .replaceAll("%player%", target.getName()));
                        } else {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                        }
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found").replaceAll("%player%", args[0])));
                        return true;
                    }
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("balance-other"), plugin.Economy.getAccount(target))
                            .replaceAll("%player%", target.getName()));
                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                }
                return true;
            }
            Player player = (Player) sender;
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("bank")) {
                    if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found").replaceAll("%player%", args[0])));
                            return true;
                        }
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("balance-other"), plugin.Economy.getBank(target))
                                .replaceAll("%player%", target.getName()));
                    } else {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("balance"), plugin.Economy.getBank(player)));
                    }
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found").replaceAll("%player%", args[0])));
                    return true;
                }
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("balance-other"), plugin.Economy.getAccount(target))
                        .replaceAll("%player%", target.getName()));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("balance"), plugin.Economy.getAccount(player)));
            }
        }
        return false;
    }

}
