package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class reloadCommand implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("peligoneconomy")) {
            if (sender.hasPermission("Peligon.Economy.Admin") || sender.hasPermission("Peligon.Economy.*")) {

                plugin.reloadConfig();
                plugin.languageFile.reloadConfig();
                plugin.custonItemsFile.reloadConfig();
                plugin.bankAccountInventoryFile.reloadConfig();

                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("admin-reload")));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
            }

        }
        return false;
    }

}
