package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.CustomConfig;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class cmdWarp implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private final Map<UUID, Long> cooldown = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 1) {
                String warp = args[0];
                if (player.hasPermission("Peligon.Core.Warp." + warp) || player.hasPermission("Peligon.Core.Warp.*") || player.hasPermission("Peligon.Core.*")) {
                    CustomConfig warpConfig = new CustomConfig(plugin, warp, "warps");
                    if (!warpConfig.getCustomConfigFile().exists()) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-warp-found")));
                        return true;
                    }
                    World world = Bukkit.getWorld(warpConfig.getConfig().getString("world"));
                    double x = warpConfig.getConfig().getDouble("x");
                    double y = warpConfig.getConfig().getDouble("y");
                    double z = warpConfig.getConfig().getDouble("z");
                    float pitch = (float)  warpConfig.getConfig().getDouble("pitch");
                    float yaw = (float)  warpConfig.getConfig().getDouble("yaw");
                    Location location = new Location(world, x, y, z, yaw, pitch);

                    if (player.hasPermission("Peligon.Core.Spawn.Bypass") || player.hasPermission("Peligon.Core.*")) {
                        player.teleport(location);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("teleported-to-warp").replaceAll("%warp%", warp)));
                    } else {
                        cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (cooldown.containsKey(player.getUniqueId())) {
                                cooldown.remove(player.getUniqueId());
                                player.teleport(location);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("teleported-to-warp").replaceAll("%warp%", warp)));
                            }
                        }, 20L);
                    }
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            }
        }
        return false;
    }

}
