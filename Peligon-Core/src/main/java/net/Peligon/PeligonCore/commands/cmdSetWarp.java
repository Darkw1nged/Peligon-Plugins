package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.CustomConfig;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class cmdSetWarp implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setwarp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Core.Setwarp") || player.hasPermission("Peligon.Core.*")) {
                if (args.length == 0) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                    return true;
                }
                CustomConfig config = new CustomConfig(plugin, args[0], "warps");
                if (config.getConfig().getString("name") != null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("warp-already-exists")));
                    return true;
                }
                YamlConfiguration data = config.getConfig();
                data.set("name", args[0]);
                data.set("world", player.getWorld().getName());
                data.set("x", player.getLocation().getX());
                data.set("y", player.getLocation().getY());
                data.set("z", player.getLocation().getZ());
                data.set("yaw", player.getLocation().getYaw());
                data.set("pitch", player.getLocation().getPitch());
                config.saveConfig();
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("warp-set").replace("%warp%", args[0])));
            }
        }
        return false;
    }

}
