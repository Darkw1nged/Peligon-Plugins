package net.peligon.PeligonPlayTime.commands;

import net.peligon.PeligonPlayTime.Main;
import net.peligon.PeligonPlayTime.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdReset implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playtime")) {
            if (sender.hasPermission("Peligon.PlayTime.Reset") || sender.hasPermission("Peligon.PlayTime.*")) {
                if (args.length < 1) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("*")) {
                    for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
                        if (player.hasPlayedBefore()) {
                            plugin.playerTime.resetTime(player);
                        }
                    }
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("playtime-reset-all")));
                    return true;
                }
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                plugin.playerTime.resetTime(target);

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("playtime-reset").replace("%player%", target.getName())));

            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
