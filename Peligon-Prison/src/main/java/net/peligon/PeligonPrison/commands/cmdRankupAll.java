package net.peligon.PeligonPrison.commands;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.struts.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdRankupAll implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankupall")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Prison.Rankup") || player.hasPermission("Peligon.Prison.*")) {
                if (plugin.rankManager.getRank(player).equals(Utils.ranks.get(Utils.ranks.size() - 1).getName())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("max-rank")));
                    return true;
                }

                int amount = 0;
                Rank newRank = null;
                for (Rank rank : Utils.ranks) {
                    if (plugin.getEconomy().getBalance(player) >= rank.getCost() && !(amount > plugin.getEconomy().getBalance(player))) {
                        amount += rank.getCost();
                        newRank = rank;
                    }
                }
                if (newRank == null) return true;
                plugin.getEconomy().withdrawPlayer(player, amount);
                plugin.rankManager.setRank(player, newRank.getName());
                if (plugin.getConfig().getBoolean("Rankup.message", true)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("rankup").replaceAll("%rank%", newRank.getName())));
                }

                if (plugin.getConfig().getBoolean("Rankup.title-message.enabled", true)) {
                    player.sendTitle(Utils.chatColor(plugin.getConfig().getString("Rankup.title-message.title")),
                            Utils.chatColor(plugin.getConfig().getString("Rankup.title-message.subtitle")),
                            plugin.getConfig().getInt("Rankup.title-message.fadeIn"),
                            plugin.getConfig().getInt("Rankup.title-message.stay"),
                            plugin.getConfig().getInt("Rankup.title-message.fadeOut"));
                }
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("noPermission")));
            }
        }
        return false;
    }

}
