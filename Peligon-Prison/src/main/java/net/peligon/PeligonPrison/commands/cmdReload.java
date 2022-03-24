package net.peligon.PeligonPrison.commands;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdReload implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pelprison")) {
            if (sender.hasPermission("Peligon.Prison.Reload") || sender.hasPermission("Peligon.Prison.*")) {

                plugin.reloadConfig();
                plugin.fileMessage.reloadConfig();

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("admin-reload")));
            }

        }
        return false;
    }

}
