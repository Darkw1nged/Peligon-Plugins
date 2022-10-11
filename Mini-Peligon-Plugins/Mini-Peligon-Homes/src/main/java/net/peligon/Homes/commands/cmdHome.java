package net.peligon.Homes.commands;

import net.peligon.Homes.CustomConfig;
import net.peligon.Homes.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class cmdHome implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private final Map<UUID, Long> cooldown = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("home")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Homes.Home") || player.hasPermission("Peligon.Homes.*")) {
                if (cooldown.containsKey(player.getUniqueId())) return true;

                CustomConfig config = new CustomConfig(plugin, player.getUniqueId().toString(), "data");
                YamlConfiguration data = config.getConfig();
                String home = args[0];
                if (!data.contains("homes." + home)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.home-not-found")));
                    return true;
                }
                World world = Bukkit.getWorld(data.getString("homes." + home + ".world"));
                double x = data.getDouble("homes." + home + ".x");
                double y = data.getDouble("homes." + home + ".y");
                double z = data.getDouble("homes." + home + ".z");
                float yaw = (float) data.getDouble("homes." + home + ".yaw");
                float pitch = (float) data.getDouble("homes." + home + ".pitch");
                Location location = new Location(world, x, y, z, yaw, pitch);

                if (player.hasPermission("Peligon.Homes.Bypass") || player.hasPermission("Peligon.Homes.*")) {
                    player.teleport(location);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") +
                            plugin.getConfig().getString("messages.home-teleported").replaceAll("%home%", home)));
                } else {
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                    plugin.isTeleporting.add(player.getUniqueId());
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        if (cooldown.containsKey(player.getUniqueId()) && plugin.isTeleporting.contains(player.getUniqueId())) {
                            cooldown.remove(player.getUniqueId());
                            player.teleport(location);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") +
                                    plugin.getConfig().getString("messages.home-teleported").replaceAll("%home%", home)));
                        }
                    }, 20L);
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
            }
        }

        return false;
    }
}
