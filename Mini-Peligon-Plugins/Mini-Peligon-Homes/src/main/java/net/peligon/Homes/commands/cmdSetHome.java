package net.peligon.Homes.commands;

import net.peligon.Homes.CustomConfig;
import net.peligon.Homes.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class cmdSetHome implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sethome")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Homes.Sethome") || player.hasPermission("Peligon.Homes.*")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.specify-name")));
                    return true;
                }
                CustomConfig config = new CustomConfig(plugin, player.getUniqueId().toString(), "data");
                YamlConfiguration data = config.getConfig();
                data.set("homes." + args[0] + ".name", args[0]);
                data.set("homes." + args[0] + ".world", player.getLocation().getWorld().getName());
                data.set("homes." + args[0] + ".x", player.getLocation().getX());
                data.set("homes." + args[0] + ".y", player.getLocation().getY());
                data.set("homes." + args[0] + ".z", player.getLocation().getZ());
                data.set("homes." + args[0] + ".yaw", player.getLocation().getYaw());
                data.set("homes." + args[0] + ".pitch", player.getLocation().getPitch());
                config.saveConfig();

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") +
                        plugin.getConfig().getString("home-set").replace("%home%", args[0])));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
            }
        }
        return false;
    }

}
