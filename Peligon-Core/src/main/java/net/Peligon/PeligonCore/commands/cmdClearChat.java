package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdClearChat implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("clearchat")) {
            if (sender.hasPermission("Peligon.Core.ClearChat") || sender.hasPermission("Peligon.Core.*")) {
                for (int i = 0; i < 150; i++) {
                    sender.sendMessage(" ");
                }
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("chat-cleared").replaceAll("%player%", sender.getName())));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
