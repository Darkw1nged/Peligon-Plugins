package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdReload implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pelecon")) {
            if (sender.hasPermission("Peligon.Economy.Reload") || sender.hasPermission("Peligon.Economy.*")) {

                plugin.reloadConfig();
                plugin.fileMessage.reloadConfig();
                plugin.fileWorth.reloadConfig();
                plugin.filePouches.reloadConfig();
                plugin.fileSigns.reloadConfig();
                plugin.fileDailyReward.reloadConfig();
                plugin.fileSellGUI.reloadConfig();
                plugin.fileBoxGUI.reloadConfig();

                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("admin-reload")));
            }

        }
        return false;
    }

}
