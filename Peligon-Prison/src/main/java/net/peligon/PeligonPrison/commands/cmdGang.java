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
                if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("delete")) {
                        if (args.length >= 3) {
                            Gang gang = plugin.gangManager.getGang(args[2]);
                            if (gang == null) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                return true;
                            }
                            plugin.gangManager.disbandGang(gang.getName());
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gang-deleted").replace("%gang%", gang.getName())));
                        }
                    } else if (args[1].equalsIgnoreCase("kick")) {
                        if (args.length >= 3) {
                            Gang gang = plugin.gangManager.getGang(args[2]);
                            if (gang == null) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                return true;
                            }
                            Player target = Bukkit.getPlayer(args[3]);
                            if (target == null) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                                return true;
                            }
                            plugin.gangManager.removeMember(target, gang.getName());
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gang-kicked").replace("%gang%", gang.getName())));
                        }
                    } else if (args[1].equalsIgnoreCase("ban")) {
                        if (args.length >= 3) {
                            Gang gang = plugin.gangManager.getGang(args[2]);
                            if (gang == null) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                return true;
                            }
                            Player target = Bukkit.getPlayer(args[3]);
                            if (target == null) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                                return true;
                            }
                            plugin.gangManager.addBanned(target, gang.getName());
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gang-banned").replace("%gang%", gang.getName())));
                        }
                    } else if (args[1].equalsIgnoreCase("unban")) {
                        if (args.length >= 3) {
                            Gang gang = plugin.gangManager.getGang(args[2]);
                            if (gang == null) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                return true;
                            }
                            Player target = Bukkit.getPlayer(args[3]);
                            if (target == null) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                                return true;
                            }
                            plugin.gangManager.removeBanned(target, gang.getName());
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("gang-unbanned").replace("%gang%", gang.getName())));
                        }
                    }
                }
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("Peligon.Prison.Gang.Help") || player.hasPermission("Peligon.Prison.*")) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("help-menu-header")));
                    for (String s : plugin.fileMessage.getConfig().getStringList("gang-help-menu")) {
                        player.sendMessage(Utils.chatColor(s));
                    }
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("help-menu-footer")));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "help":
                    if (player.hasPermission("Peligon.Prison.Gang.Help") || player.hasPermission("Peligon.Prison.*")) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("help-menu-header")));
                        for (String s : plugin.fileMessage.getConfig().getStringList("gang-help-menu")) {
                            player.sendMessage(Utils.chatColor(s));
                        }
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("help-menu-footer")));
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                    }
                    break;
                case "admin":
                    if (player.hasPermission("Peligon.Prison.Gang.Admin") || player.hasPermission("Peligon.Prison.*")) {
                        if (args.length >= 2) {
                            if (args[1].equalsIgnoreCase("delete")) {
                                if (args.length >= 3) {
                                    Gang gang = plugin.gangManager.getGang(args[2]);
                                    if (gang == null) {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                        return true;
                                    }
                                    if (player.hasPermission("Peligon.Prison.Gang.Admin.Delete") || player.hasPermission("Peligon.Prison.*")) {
                                        plugin.gangManager.disbandGang(gang.getName());
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                                plugin.fileMessage.getConfig().getString("gang-deleted").replace("%gang%", gang.getName())));
                                    } else {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                    }
                                }
                            } else if (args[1].equalsIgnoreCase("join")) {
                                if (args.length >= 3) {
                                    Gang gang = plugin.gangManager.getGang(args[2]);
                                    if (gang == null) {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                        return true;
                                    }
                                    if (player.hasPermission("Peligon.Prison.Gang.Admin.Join") || player.hasPermission("Peligon.Prison.*")) {
                                        plugin.gangManager.addMember(player, gang.getName());
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                                plugin.fileMessage.getConfig().getString("gang-joined").replace("%gang%", gang.getName())));
                                    } else {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                                    }
                                }
                            } else if (args[1].equalsIgnoreCase("kick")) {
                                if (args.length >= 3) {
                                    Gang gang = plugin.gangManager.getGang(args[2]);
                                    if (gang == null) {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                        return true;
                                    }
                                    if (player.hasPermission("Peligon.Prison.Gang.Admin.Kick") || player.hasPermission("Peligon.Prison.*")) {
                                        Player target = Bukkit.getPlayer(args[3]);
                                        if (target == null) {
                                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                                            return true;
                                        }
                                        plugin.gangManager.removeMember(target, gang.getName());
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                                plugin.fileMessage.getConfig().getString("gang-kicked").replace("%gang%", gang.getName())));
                                    } else {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                                    }
                                }
                            } else if (args[1].equalsIgnoreCase("ban")) {
                                if (args.length >= 3) {
                                    Gang gang = plugin.gangManager.getGang(args[2]);
                                    if (gang == null) {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                        return true;
                                    }
                                    if (player.hasPermission("Peligon.Prison.Gang.Admin.Ban") || player.hasPermission("Peligon.Prison.*")) {
                                        Player target = Bukkit.getPlayer(args[3]);
                                        if (target == null) {
                                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                                            return true;
                                        }
                                        plugin.gangManager.addBanned(target, gang.getName());
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                                plugin.fileMessage.getConfig().getString("gang-banned").replace("%gang%", gang.getName())));
                                    } else {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                                    }
                                }
                            } else if (args[1].equalsIgnoreCase("unban")) {
                                if (args.length >= 3) {
                                    Gang gang = plugin.gangManager.getGang(args[2]);
                                    if (gang == null) {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                                        return true;
                                    }
                                    if (player.hasPermission("Peligon.Prison.Gang.Admin.Unban") || player.hasPermission("Peligon.Prison.*")) {
                                        Player target = Bukkit.getPlayer(args[3]);
                                        if (target == null) {
                                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-found")));
                                            return true;
                                        }
                                        plugin.gangManager.removeBanned(target, gang.getName());
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                                plugin.fileMessage.getConfig().getString("gang-unbanned").replace("%gang%", gang.getName())));
                                    } else {
                                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                                    }
                                }
                            }
                        }
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
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                        return true;
                    }
                    String gangName = args[1];
                    if (Utils.isOnlyLetters(gangName)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-name")));
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
                    Player kickTarget = plugin.getServer().getPlayer(args[1]);
                    if (kickTarget == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    if (plugin.gangManager.getGang(kickTarget) == null || !(plugin.gangManager.getGang(kickTarget).equals(plugin.gangManager.getGang(player)))) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-in-gang")));
                        return true;
                    }
                    plugin.gangManager.removeMember(kickTarget, plugin.gangManager.getGang(player).getName());

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gang-kick")
                                    .replaceAll("%player%", kickTarget.getName())
                                    .replaceAll("%gang%", plugin.gangManager.getGang(player).getName())));
                    break;
                case "leave":
                    if (plugin.gangManager.getGang(player) == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-gang")));
                        return true;
                    }
                    if (!plugin.gangManager.isOwner(player)) {
                        player.performCommand("gang disband");
                        return true;
                    }
                    plugin.gangManager.removeMember(player, plugin.gangManager.getGang(player).getName());
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("successfully-left-gang").replaceAll("%gang%", plugin.gangManager.getGang(player).getName())));
                    break;
                case "disband":
                    if (plugin.gangManager.getGang(player) == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-gang")));
                        return true;
                    }
                    if (!plugin.gangManager.isOwner(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("must-be-owner")));
                        return true;
                    }
                    plugin.gangManager.disbandGang(plugin.gangManager.getGang(player).getName());
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gang-disbanded").replaceAll("%gang%", plugin.gangManager.getGang(player).getName())));
                    break;
                case "join":
                    if (plugin.gangManager.getGang(player) != null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-gang")));
                        return true;
                    }
                    if (args.length != 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                        return true;
                    }
                    if (plugin.gangManager.getGang(args[1]) == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-found")));
                        return true;
                    }
                    if (!plugin.gangManager.getGang(args[1]).isPublic()) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-not-public")));
                        return true;
                    }
                    plugin.gangManager.addMember(player, plugin.gangManager.getGang(args[1]).getName());
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("successfully-joined-gang").replaceAll("%gang%", plugin.gangManager.getGang(args[1]).getName())));
                    break;
                case "edit":
                    if (plugin.gangManager.getGang(player) == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-gang")));
                        return true;
                    }
                    if (!plugin.gangManager.isOwner(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("must-be-owner")));
                        return true;
                    }
                    if (args.length < 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-edit-usage")));
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("description")) {
                        StringBuilder description = new StringBuilder();
                        for (int i = 2; i < args.length; i++) {
                            description.append(args[i]).append(" ");
                        }
                        plugin.gangManager.getGang(player).setDescription(description.toString());
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("gang-edit-description-success").replaceAll("%gang%", plugin.gangManager.getGang(player).getName())));
                    } else if (args[1].equalsIgnoreCase("tag")) {
                        String tag = args[2];
                        plugin.gangManager.getGang(player).setTag(tag);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("gang-edit-tag-success").replaceAll("%gang%", plugin.gangManager.getGang(player).getName())));
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("gang-edit-usage")));
                    }
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
                    Player unbanTarget = Bukkit.getPlayer(args[1]);
                    if (unbanTarget == null) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }
                    if (!plugin.gangManager.getGang(player).getBanned().contains(unbanTarget.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-banned").replaceAll("%player%", unbanTarget.getName())));
                        return true;
                    }
                    plugin.gangManager.getGang(player).removeBanned(unbanTarget.getUniqueId());
                    plugin.gangManager.getGang(player).removeBannedName(unbanTarget.getName());
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("gang-unban")
                                    .replaceAll("%player%", unbanTarget.getName())
                                    .replaceAll("%gang%", plugin.gangManager.getGang(player).getName())));
                    break;

            }
        }
        return false;
    }

}