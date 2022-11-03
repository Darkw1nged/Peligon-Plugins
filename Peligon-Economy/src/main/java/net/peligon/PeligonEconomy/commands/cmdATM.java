package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.struts.MenuOwnerUtil;
import net.peligon.PeligonEconomy.menu.menuATM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdATM implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("atm")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("console")));
                return true;
            }
            Player player = (Player)sender;
            if (player.hasPermission("Peligon.Economy.ATM") || player.hasPermission("Peligon.Economy.access.ATM") || player.hasPermission("Peligon.Economy.*")) {
                new menuATM(new MenuOwnerUtil(player)).open();
            } else {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
