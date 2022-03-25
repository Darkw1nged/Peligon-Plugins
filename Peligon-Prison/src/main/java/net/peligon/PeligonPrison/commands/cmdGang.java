package net.peligon.PeligonPrison.commands;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.struts.Gang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class cmdGang implements CommandExecutor {

    public static Main plugin = Main.getInstance;
    private final Map<UUID, Long> requestTimeout = new HashMap<>();
    private final Map<UUID, String> gangInvite = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gang")) {
            if (!(sender instanceof Player)) {
                // TODO gang admin commands
                return true;
            }
            Player player = (Player) sender;
            if (args.length >= 1) {
                switch (args[0].toLowerCase()) {
                    case "help":
                        // TODO gang help
                        break;
                    case "admin":
                        if (player.hasPermission("Peligon.Prison.Gang.Admin") || player.hasPermission("Peligon.Prison.*")) {
                            // TODO gang admin commands
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        break;
                    case "create":
                        if (plugin.gangManager.getGang(player) != null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("can-not-create-gang")));
                            return true;
                        }
                        if (args.length != 2) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-gang-name")));
                            return true;
                        }
                        String gangName = args[1];
                        if (!Utils.isOnlyLetters(gangName)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-gang-name")));
                            return true;
                        }
                        for (Gang gang : Utils.gangs) {
                            if (gang.getName().equalsIgnoreCase(gangName)) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-already-exists")));
                                return true;
                            }
                            Gang newGang = new Gang(gangName, player.getUniqueId(), player.getName());
                            Utils.gangs.add(newGang);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gang-created").replaceAll("%gang%", gangName)));
                        }
                        break;
                    case "invite":
                        if (plugin.gangManager.getGang(player) == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-gang")));
                            return true;
                        }
                        if (!plugin.gangManager.isOwner(player)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("must-be-owner")));
                            return true;
                        }
                        if (args.length != 2) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                            return true;
                        }
                        Player target = plugin.getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                            return true;
                        }
                        if (plugin.gangManager.getGang(target) != null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-in-gang").replaceAll("%player%", target.getName())));
                            return true;
                        }
                        requestTimeout.put(player.getUniqueId(), System.currentTimeMillis());
                        gangInvite.put(target.getUniqueId(), plugin.gangManager.getGangName(player));
                        target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("request-received").replaceAll("%gang%", plugin.gangManager.getGangName(player))));
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("request-sent").replaceAll("%player%", target.getName())));
                        break;
                    case "accept":
                        if (plugin.gangManager.getGang(player) != null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-gang")));
                            return true;
                        }
                        if (!requestTimeout.isEmpty() && requestTimeout.containsKey(player.getUniqueId())) {
                            long timeLeft = ((requestTimeout.get(player.getUniqueId()) / 1000) + plugin.getConfig().getInt("invite-timeout")) - (System.currentTimeMillis() / 1000);
                            if (timeLeft <= 0) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-invite-request")));
                                return true;
                            }

                            if (gangInvite.containsKey(player.getUniqueId())) {
                                String name = gangInvite.get(player.getUniqueId());
                                plugin.gangManager.addMember(player, name);
                            }
                            requestTimeout.remove(player.getUniqueId());
                            gangInvite.remove(player.getUniqueId());
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("request-accepted")));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-invite-request")));
                        }
                        break;
                    case "deny":
                        if (!requestTimeout.isEmpty() && requestTimeout.containsKey(player.getUniqueId())) {
                            long timeLeft = ((requestTimeout.get(player.getUniqueId()) / 1000) + plugin.getConfig().getInt("invite-timeout")) - (System.currentTimeMillis() / 1000);
                            if (timeLeft <= 0) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-invite-request")));
                                return true;
                            }
                            requestTimeout.remove(player.getUniqueId());
                            gangInvite.remove(player.getUniqueId());
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("request-denied")));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-invite-request")));
                        }
                        break;
                    case "kick":
                        break;
                    case "leave":
                        break;
                    case "info":
                        break;
                    case "list":
                        break;
                    case "join":
                        break;
                    case "disband":
                        break;
                    case "edit":
                        break;
                    case "ban":
                        if (plugin.gangManager.getGang(player) == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-gang")));
                            return true;
                        }
                        if (!plugin.gangManager.isOwner(player)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("must-be-owner")));
                            return true;
                        }
                        if (args.length != 2) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                        }
                        Player banTarget = Bukkit.getPlayer(args[1]);
                        if (banTarget == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                            return true;
                        }
                        if (plugin.gangManager.getGang(player).getBanned().contains(banTarget.getUniqueId())) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-already-banned").replaceAll("%player%", banTarget.getName())));
                            return true;
                        }
                        plugin.gangManager.getGang(player).addBanned(banTarget.getUniqueId());
                        plugin.gangManager.getGang(player).addBannedName(banTarget.getName());
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("gang-ban")
                                        .replaceAll("%player%", banTarget.getName())
                                        .replaceAll("%gang%", plugin.gangManager.getGang(player).getName())));
                        break;
                    case "unban":
                        break;

                }
            }
        }
        return false;
    }

}