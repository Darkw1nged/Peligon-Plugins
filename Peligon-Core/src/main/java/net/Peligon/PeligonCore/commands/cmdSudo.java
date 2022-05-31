package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdSudo implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sudo")) {
            if (sender.hasPermission("Peligon.Core.Sudo") || sender.hasPermission("Peligon.Core.*")) {
                if (args.length < 2) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("sudo-usage")));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                String message = sb.toString().trim();
                player.chat(message);
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
