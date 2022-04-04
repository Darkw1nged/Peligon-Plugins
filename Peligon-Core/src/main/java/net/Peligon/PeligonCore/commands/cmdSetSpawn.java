package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdSetSpawn implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            if (sender.hasPermission("Peligon.Core.setspawn") || sender.hasPermission("Peligon.Core.*")) {
                Player player = (Player) sender;
                String world = player.getWorld().getName();
                double x = player.getLocation().getX();
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ();
                float pitch = player.getLocation().getPitch();
                float yaw = player.getLocation().getYaw();

                plugin.fileSpawn.getConfig().set("spawn.world", world);
                plugin.fileSpawn.getConfig().set("spawn.x", x);
                plugin.fileSpawn.getConfig().set("spawn.y", y);
                plugin.fileSpawn.getConfig().set("spawn.z", z);
                plugin.fileSpawn.getConfig().set("spawn.pitch", pitch);
                plugin.fileSpawn.getConfig().set("spawn.yaw", yaw);
                plugin.fileSpawn.saveConfig();

                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("spawn-set")));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }
}
