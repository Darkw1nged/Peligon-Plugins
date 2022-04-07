package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.CustomConfig;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdDeleteWarp implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("deletewarp")) {
            if (sender.hasPermission("Peligon.Core.Deletewarp") || sender.hasPermission("Peligon.Core.*")) {
                if (args.length == 0) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                    return true;
                }
                String warp = args[0];
                CustomConfig warpConfig = new CustomConfig(plugin, warp, "warps");
                if (!warpConfig.getCustomConfigFile().exists()) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-warp-found")));
                    return true;
                }
                warpConfig.getCustomConfigFile().delete();
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("warp-deleted").replace("%warp%", warp)));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
