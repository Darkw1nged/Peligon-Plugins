package net.peligon.ClearChat.Commands;

import net.peligon.ClearChat.Main;
import net.peligon.ClearChat.Utilities.Lists.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdClearChat implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("clearchat")) {
            if (sender.hasPermission(Permissions.use_permission.getPermission())) {
                for (int i=0; i<plugin.getConfig().getInt("lines", 150); i++) {
                    Bukkit.broadcastMessage("");
                }
                Bukkit.broadcastMessage(plugin.utils.chatColor(plugin.fileMessage.getConfig().getString("cleared-chat").replaceAll("%player%", sender.getName())));
            } else {
                sender.sendMessage(plugin.utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }

        }
        return false;
    }

}
