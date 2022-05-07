package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
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
                plugin.fileChatSettings.reloadConfig();

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("admin-reload")));
            }

        }
        return false;
    }

}
