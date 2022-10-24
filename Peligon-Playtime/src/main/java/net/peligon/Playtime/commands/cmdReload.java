package net.peligon.Playtime.commands;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdReload implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("peligonplaytime")) {
            if (sender.hasPermission("Peligon.Playtime.Reload") || sender.hasPermission("Peligon.Playtime.*")) {

                plugin.reloadConfig();
                plugin.languageFile.reloadConfig();

                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("admin-reload")));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
            }

        }
        return false;
    }

}
