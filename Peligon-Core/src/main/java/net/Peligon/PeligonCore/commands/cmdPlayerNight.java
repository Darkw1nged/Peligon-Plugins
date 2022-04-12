package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdPlayerNight implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playernight")) {
            if (!(sender instanceof Player)){
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Core.Player.Time") || player.hasPermission("Peligon.Core.*")) {
                player.setPlayerTime(18000L, false);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("player-time-updated")));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
