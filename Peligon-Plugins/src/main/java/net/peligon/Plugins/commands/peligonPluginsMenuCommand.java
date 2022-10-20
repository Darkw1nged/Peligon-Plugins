package net.peligon.Plugins.commands;

import net.peligon.Plugins.menus.menuCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class peligonPluginsMenuCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("peligon")) {
            if (!(sender instanceof Player)) return true;
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Plugins")) {
                new menuCore(player).open();
            }
        }
        return false;
    }

}
