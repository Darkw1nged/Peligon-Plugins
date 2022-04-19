package net.peligon.PeligonCore.commands;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.TeleportRequest;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class cmdTeleportAccept implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private final Map<UUID, Long> cooldown = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleportaccept")) {
            if (!(sender instanceof org.bukkit.entity.Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Core.TeleportRequest") || player.hasPermission("Peligon.Core.*")) {
                for (TeleportRequest request : Utils.teleportRequests) {
                    if (request.getReceiver().equals(player.getUniqueId())) {
                        if (request.getType().equalsIgnoreCase("TeleportToPlayer")) {
                            Player target = Bukkit.getPlayer(request.getSender());
                            if (target != null) {
                                if (player.hasPermission("Peligon.Core.Teleport.Bypass") || player.hasPermission("Peligon.Core.*")) {
                                    target.teleport(player);
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                            plugin.fileMessage.getConfig().getString("teleport-request-accepted").replaceAll("%player%", target.getName())));
                                } else {
                                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                                    Utils.isTeleporting.add(player.getUniqueId());
                                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                        if (cooldown.containsKey(player.getUniqueId()) && Utils.isTeleporting.contains(player.getUniqueId())) {
                                            cooldown.remove(player.getUniqueId());
                                            target.teleport(player);
                                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                                    plugin.fileMessage.getConfig().getString("teleport-request-accepted").replaceAll("%player%", target.getName())));
                                        }
                                    }, 20L);
                                }
                            }
                            Utils.teleportRequests.remove(request);
                            return true;
                        }
                    } else if (request.getReceiver().equals(player.getUniqueId())) {
                        if (request.getType().equalsIgnoreCase("TeleportPlayerHere")) {
                            Player target = Bukkit.getPlayer(request.getSender());
                            if (target != null) {
                                if (player.hasPermission("Peligon.Core.Teleport.Bypass") || player.hasPermission("Peligon.Core.*")) {
                                    player.teleport(target);
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                            plugin.fileMessage.getConfig().getString("teleport-request-accepted").replaceAll("%player%", target.getName())));
                                } else {
                                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                                    Utils.isTeleporting.add(player.getUniqueId());
                                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                        if (cooldown.containsKey(player.getUniqueId()) && Utils.isTeleporting.contains(player.getUniqueId())) {
                                            cooldown.remove(player.getUniqueId());
                                            player.teleport(target);
                                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                                    plugin.fileMessage.getConfig().getString("teleport-request-accepted").replaceAll("%player%", target.getName())));
                                        }
                                    }, 20L);
                                }
                            }
                            Utils.teleportRequests.remove(request);
                            return true;
                        }
                    }
                }
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-request")));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
