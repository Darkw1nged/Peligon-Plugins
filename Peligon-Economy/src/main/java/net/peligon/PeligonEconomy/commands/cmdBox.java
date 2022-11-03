package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.menu.menuBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdBox implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("box")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Economy.Box") || player.hasPermission("Peligon.Economy.*")) {
                menuBox menu = new menuBox(player);
                player.openInventory(menu.getInventory());
            } else {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
