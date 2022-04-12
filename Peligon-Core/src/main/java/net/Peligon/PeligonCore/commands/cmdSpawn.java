package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.CustomConfig;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class cmdSpawn implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private final Map<UUID, Long> cooldown = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spawn")) {;
            CustomConfig spawnConfig = new CustomConfig(plugin, "spawn", "");
            if (!spawnConfig.getCustomConfigFile().exists()){
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-spawn-set")));
                return true;
            }
            if (!(sender instanceof Player)) {
                if (args.length != 1) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = plugin.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                Location location = new Location(
                        Bukkit.getWorld(spawnConfig.getConfig().getString("world")),
                        spawnConfig.getConfig().getDouble("x"),
                        spawnConfig.getConfig().getDouble("y"),
                        spawnConfig.getConfig().getDouble("z"),
                        (float) spawnConfig.getConfig().getDouble("yaw"),
                        (float) spawnConfig.getConfig().getDouble("pitch")
                );
                if (target.hasPermission("Peligon.Core.Spawn.Bypass") || target.hasPermission("Peligon.Core.*")) {
                    target.teleport(location);
                    target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("teleported-to-spawn")));
                } else {
                    cooldown.put(target.getUniqueId(), System.currentTimeMillis());
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        if (cooldown.containsKey(target.getUniqueId())) {
                            cooldown.remove(target.getUniqueId());
                            target.teleport(location);
                            target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("teleported-to-spawn")));
                        }
                    }, 20L);
                }
                return true;
            }
            Player player = (Player) sender;
            Location location = new Location(
                    Bukkit.getWorld(spawnConfig.getConfig().getString("world")),
                    spawnConfig.getConfig().getDouble("x"),
                    spawnConfig.getConfig().getDouble("y"),
                    spawnConfig.getConfig().getDouble("z"),
                    (float) spawnConfig.getConfig().getDouble("yaw"),
                    (float) spawnConfig.getConfig().getDouble("pitch")
            );
            if (args.length > 0) {
                if (player.hasPermission("Peligon.Core.Spawn.Other") || player.hasPermission("Peligon.Core.*")) {
                    Player target = plugin.getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    if (target.hasPermission("Peligon.Core.Spawn.Bypass") || target.hasPermission("Peligon.Core.*")) {
                        target.teleport(location);
                        target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("teleported-to-spawn")));
                    } else {
                        cooldown.put(target.getUniqueId(), System.currentTimeMillis());
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (cooldown.containsKey(target.getUniqueId())) {
                                cooldown.remove(target.getUniqueId());
                                target.teleport(location);
                                target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("teleported-to-spawn")));
                            }
                        }, 20L);
                    }
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
                return true;
            }
            if (player.hasPermission("Peligon.Core.Spawn.Bypass") || player.hasPermission("Peligon.Core.*")) {
                player.teleport(location);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("teleported-to-spawn")));
            } else {
                cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (cooldown.containsKey(player.getUniqueId())) {
                        cooldown.remove(player.getUniqueId());
                        player.teleport(location);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("teleported-to-spawn")));
                    }
                }, 20L);
            }
        }
        return false;
    }

}
