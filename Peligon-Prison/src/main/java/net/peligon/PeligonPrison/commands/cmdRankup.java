package net.peligon.PeligonPrison.commands;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.struts.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdRankup implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankup")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Prison.Rankup") || player.hasPermission("Peligon.Prison.*")) {
                if (plugin.rankManager.getRank(player).equals(Utils.ranks.get(Utils.ranks.size() - 1).getName())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("max-rank")));
                    return true;
                }

                for (Rank rank : Utils.ranks) {
                    if (rank.getName().equals(plugin.rankManager.getRank(player))) {
                        Rank nextRank = Utils.ranks.get(Utils.ranks.indexOf(rank) + 1);
                        if (nextRank != null) {
                            if (plugin.getEconomy().getBalance(player) >= nextRank.getCost()) {
                                plugin.getEconomy().withdrawPlayer(player, nextRank.getCost());
                                plugin.rankManager.setRank(player, nextRank.getName());

                                if (plugin.getConfig().getBoolean("Rankup.message", true)) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                            plugin.fileMessage.getConfig().getString("rankup").replaceAll("%rank%", nextRank.getName())));
                                }

                                if (plugin.getConfig().getBoolean("Rankup.title-message.enabled", true)) {
                                    player.sendTitle(Utils.chatColor(plugin.getConfig().getString("Rankup.title-message.title")),
                                            Utils.chatColor(plugin.getConfig().getString("Rankup.title-message.subtitle")),
                                            plugin.getConfig().getInt("Rankup.title-message.fadeIn"),
                                            plugin.getConfig().getInt("Rankup.title-message.stay"),
                                            plugin.getConfig().getInt("Rankup.title-message.fadeOut"));
                                }
                            } else {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("not-enough-money")));
                            }
                            return true;
                        }
                    }
                }
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
