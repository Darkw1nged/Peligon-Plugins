package net.peligon.EnhancedStorage.commands;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdReload implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pelstorage")) {
            if (sender.hasPermission("Peligon.EnhancedStorage.Reload") || sender.hasPermission("Peligon.EnhancedStorage.*")) {

                plugin.reloadConfig();
                plugin.languageFile.reloadConfig();
                plugin.fileApproveItem.reloadConfig();
                plugin.fileWithdrawItem.reloadConfig();

                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("admin-reload")));
            }

        }
        return false;
    }

}
