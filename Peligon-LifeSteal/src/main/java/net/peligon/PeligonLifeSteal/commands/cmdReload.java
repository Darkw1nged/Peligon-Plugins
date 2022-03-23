package net.peligon.PeligonLifeSteal.commands;

import net.peligon.PeligonLifeSteal.Main;
import net.peligon.PeligonLifeSteal.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdReload implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pellives")) {
            if (sender.hasPermission("Peligon.LifeSteal.Reload") || sender.hasPermission("Peligon.LifeSteal.*")) {

                plugin.reloadConfig();
                plugin.fileMessage.reloadConfig();

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("admin-reload")));
            }

        }
        return false;
    }

}
