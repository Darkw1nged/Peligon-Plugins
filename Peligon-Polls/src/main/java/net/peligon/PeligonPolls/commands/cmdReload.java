package net.peligon.PeligonPolls.commands;

import net.peligon.PeligonPolls.Main;
import net.peligon.PeligonPolls.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdReload implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pelpoll")) {
            if (sender.hasPermission("Peligon.Polls.Reload") || sender.hasPermission("Peligon.Polls.*")) {

                plugin.reloadConfig();
                plugin.fileMessage.reloadConfig();
                plugin.fileCache.reloadConfig();

                sender.sendMessage(Utils.chatColor("&eConfiguration files have has been reloaded."));
            }

        }
        return false;
    }

}