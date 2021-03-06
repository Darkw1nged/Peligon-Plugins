package net.peligon.Playtime.commands;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdReload implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pelcore")) {
            if (sender.hasPermission("Peligon.Core.Reload") || sender.hasPermission("Peligon.Core.*")) {

                plugin.reloadConfig();
                plugin.fileMessage.reloadConfig();

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("admin-reload")));
            }

        }
        return false;
    }

}
