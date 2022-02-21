package net.peligon.PeligonEnchants.commands;

import net.peligon.PeligonEnchants.Main;
import net.peligon.PeligonEnchants.libaries.Utils;
import net.peligon.PeligonEnchants.menus.menuEnchant;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdEnchants implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("enchants")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.CustomEnchants.Enchants") || player.hasPermission("Peligon.CustomEnchants.*")) {
                menuEnchant menu = new menuEnchant(player);
                player.openInventory(menu.getInventory());
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
