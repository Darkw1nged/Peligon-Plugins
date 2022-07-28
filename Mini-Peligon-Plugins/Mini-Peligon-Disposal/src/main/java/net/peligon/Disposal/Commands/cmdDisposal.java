package net.peligon.Disposal.Commands;

import net.peligon.Disposal.Main;
import net.peligon.Disposal.Utilities.Lists.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdDisposal implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("disposal")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission(Permissions.use_permission.getPermission()) || player.hasPermission(Permissions.global_permission.getPermission())) {
                player.openInventory(Bukkit.createInventory(player,
                        plugin.fileMessage.getConfig().getInt("inventory.size"),
                        plugin.utils.chatColor(plugin.fileMessage.getConfig().getString("inventory.title"))));
            } else {
                player.sendMessage(plugin.utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }

        }
        return false;
    }

}
