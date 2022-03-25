package net.peligon.PeligonPrison.commands;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.struts.Prestige;
import net.peligon.PeligonPrison.struts.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdPrestige implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("prestige")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Prison.Prestige") || player.hasPermission("Peligon.Prison.*")) {
                if (plugin.prestigeManager.getPrestige(player).equals(Utils.prestige.get(Utils.prestige.size() - 1).getName())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("max-prestige")));
                    return true;
                }

                for (Prestige prestige : Utils.prestige) {
                    if (prestige.getName().equals(plugin.prestigeManager.getPrestige(player))) {
                        Prestige nextPrestige = Utils.prestige.get(Utils.prestige.indexOf(prestige) + 1);
                        if (nextPrestige == null) nextPrestige = Utils.prestige.get(0);
                        if (nextPrestige != null) {
                            if (plugin.getEconomy().getBalance(player) >= nextPrestige.getCost()) {
                                plugin.getEconomy().withdrawPlayer(player, nextPrestige.getCost());
                                plugin.prestigeManager.setPrestige(player, nextPrestige.getName());

                                if (plugin.fileMessage.getConfig().getBoolean("Prestige.message", true)) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                            plugin.fileMessage.getConfig().getString("prestige").replaceAll("%prestige%", nextPrestige.getName())));
                                }

                                if (plugin.fileMessage.getConfig().getBoolean("Prestige.title-message.enabled", true)) {
                                    player.sendTitle(Utils.chatColor(plugin.getConfig().getString("Prestige.title-message.title")),
                                            Utils.chatColor(plugin.getConfig().getString("Prestige.title-message.subtitle")),
                                            plugin.getConfig().getInt("Prestige.title-message.fadeIn"),
                                            plugin.getConfig().getInt("Prestige.title-message.stay"),
                                            plugin.getConfig().getInt("Prestige.title-message.fadeOut"));
                                }
                            } else {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("not-enough-money")));
                            }
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
