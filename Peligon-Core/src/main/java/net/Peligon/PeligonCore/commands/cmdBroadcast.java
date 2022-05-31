package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdBroadcast implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("broadcast")) {
            if (sender.hasPermission("Peligon.Core.Broadcast") || sender.hasPermission("Peligon.Core.*")) {
                if (args.length <= 0) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-message")));
                    return true;
                }
                String message = "";
                for (String arg : args) {
                    message = message + arg + " ";
                }
                plugin.getServer().broadcastMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("broadcast-format")
                        .replaceAll("%message%", message)));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
