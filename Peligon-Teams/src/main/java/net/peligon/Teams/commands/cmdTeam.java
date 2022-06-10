package net.peligon.Teams.commands;

import net.peligon.Teams.Core.*;
import net.peligon.Teams.Main;
import net.peligon.Teams.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

@SuppressWarnings("ALL")
public class cmdTeam implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private final Map<Team, TeamRequest> teamRequests = new HashMap<>();
    private final List<InviteRequest> invites = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("team")) {
            if (!(sender instanceof Player)) {
                // TODO console commands
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0 && !plugin.teamManager.inTeam(player)) {
                if (player.hasPermission("Peligon.Teams.Help") || player.hasPermission("Peligon.Teams.*")) {
                    player.sendMessage(Utils.chatColor("&7----------{ &6Teams | &aHelp &7}----------"));
                    player.sendMessage(Utils.chatColor("&a/team create <teamname> - Creates a team"));
                    player.sendMessage(Utils.chatColor("&a/team delete &7- Deletes your team"));
                    player.sendMessage(Utils.chatColor("&a/team leave &7- Leaves your team"));
                    player.sendMessage(Utils.chatColor("&a/team sethome &7- Sets your team's home"));
                    player.sendMessage(Utils.chatColor("&a/team home &7- Teleports you to your team's home"));
                    player.sendMessage(Utils.chatColor("&a/team name <name> &7- Sets your team's name"));
                    player.sendMessage(Utils.chatColor("&a/team description <description> &7- Sets your team's description"));
                    player.sendMessage(Utils.chatColor("&a/team join <teamname> &7- Joins a team"));
                    player.sendMessage(Utils.chatColor("&a/team transfer <player> &7- Transfers a player to your team"));
                    player.sendMessage(Utils.chatColor("&a/team invite <player> &7- Invites a player to your team"));
                    player.sendMessage(Utils.chatColor("&a/team accept &7- Accepts a team invite"));
                    player.sendMessage(Utils.chatColor("&a/team deny &7- Denies a team invite"));
                    player.sendMessage(Utils.chatColor("&a/team promote <player> &7- Promotes a player to a higher rank"));
                    player.sendMessage(Utils.chatColor("&a/team demote <player> &7- Demotes a player to a lower rank"));
                    player.sendMessage(Utils.chatColor("&a/team title <player> <title> &7- Changes the players title"));
                    player.sendMessage(Utils.chatColor("&a/team kick <player> &7- Kicks a player from your team"));
                    player.sendMessage(Utils.chatColor("&a/team ban <player> &7- Bans a player from your team"));
                    player.sendMessage(Utils.chatColor("&a/team unban <player> &7- Unbans a player from your team"));
                    player.sendMessage(Utils.chatColor("&a/team open &7- Toggles your team's open status"));
                    player.sendMessage(Utils.chatColor("&a/team bank deposit <amount> &7- Deposits money into your team's bank"));
                    player.sendMessage(Utils.chatColor("&a/team bank withdraw <amount> &7- Withdraws money from your team's bank"));
                    player.sendMessage(Utils.chatColor("&a/team bank balance &7- Checks your team's bank balance"));
                    player.sendMessage(Utils.chatColor("&a/team neutral <team> &7- Changes realtion between two teams"));
                    player.sendMessage(Utils.chatColor("&a/team enemy <team> &7- Changes realtion between two teams"));
                    player.sendMessage(Utils.chatColor("&a/team truce <team> &7- Changes realtion between two teams"));
                    player.sendMessage(Utils.chatColor("&a/team ally <team> &7- Changes realtion between two teams"));
                    player.sendMessage(Utils.chatColor("&a/team chat <channel> &7- Changes current chat channel"));
                    player.sendMessage(Utils.chatColor("&a/team announce <message> &7- Announce a message to your team"));
                    player.sendMessage(Utils.chatColor("&7---------------------------------"));
                } else {
                    player.sendMessage(plugin.fileMessage.getConfig().getString("no-permission"));
                }
            } else {
                // TODO send player team info
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("create")) {
                if (plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-team")));
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                    return true;
                }
                String name = args[1];
                if (!Utils.isOnlyLetters(name)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-name")));
                }

                if (plugin.teamManager.teamExists(name)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-already-exists")));
                    return true;
                }

                Team team = new Team(name, "", player.getUniqueId());
                Utils.teams.add(team);

                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("team-created")
                                .replaceAll("%team%", name)));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("disband")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);

                if (!team.getLeader().equals(player.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }
                File file = new File(plugin.getDataFolder() + "/teams/" + team.getName() + ".yml");
                file.delete();
                Utils.teams.remove(team);

                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("team-deleted")
                                .replaceAll("%team%", team.getName())));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("leave")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);

                if (team.getLeader().equals(player.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }

                team.getMembers().remove(player.getUniqueId());
                team.getRanks().remove(player.getUniqueId());
                if (plugin.teamManager.getPlayerChat(player) != Channel.Global) {
                    plugin.teamManager.changePlayerChannel(player, Channel.Global);
                }
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("left-team")
                                .replaceAll("%team%", team.getName())));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("sethome")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);

                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }
                double x = player.getLocation().getX();
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ();
                float yaw = player.getLocation().getYaw();
                float pitch = player.getLocation().getPitch();

                team.setHome(new Location(player.getLocation().getWorld(), x, y, z, yaw, pitch));
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("home-set")
                                .replaceAll("%team%", team.getName())));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("home")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);

                if (team.getHome() == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-home")));
                    return true;
                }
                player.teleport(team.getHome());
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("name")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);

                if (!team.getLeader().equals(player.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                    return true;
                }
                String name = args[1];
                if (!Utils.isOnlyLetters(name)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-name")));
                }

                if (plugin.teamManager.teamExists(name)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-already-exists")));
                    return true;
                }

                team.setName(name);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("team-name-changed")
                                .replaceAll("%name%", team.getName())));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("description")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);

                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-description")));
                    return true;
                }
                String description = "";
                for (int i = 1; i < args.length; i++) {
                    description += args[i] + " ";
                }
                team.setDescription(description);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("team-description-set")
                                .replaceAll("%description%", team.getDescription())));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("join")) {
                if (plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-team")));
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-team")));
                    return true;
                }
                String name = args[1];
                if (!Utils.isOnlyLetters(name)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-name")));
                    return true;
                }
                if (!plugin.teamManager.teamExists(name)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-doesnt-exist")));
                    return true;
                }

                Team team = plugin.teamManager.getTeam(name);
                if (team.getMembers().contains(player.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-team")));
                    return true;
                }

                if (team.getMembers().size() >= team.getMaximumMembers()) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-full")));
                    return true;
                }
                if (!team.getOpen()) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-closed")));
                    return true;
                }

                team.addMember(player.getUniqueId());
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("team-join-success")
                                .replaceAll("%team%", team.getName())));

            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("transfer")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);

                if (!team.getLeader().equals(player.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                if (target.getUniqueId().equals(player.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }

                if (plugin.teamManager.getTeam(target) != team) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }

                team.setLeader(target.getUniqueId());
                team.getRanks().put(target.getUniqueId(), Ranks.Leader);
                team.getRanks().put(player.getUniqueId(), Ranks.CoLeader);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("transfer-success")
                                .replaceAll("%player%", target.getName())));

            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("invite")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }

                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                if (target.getUniqueId().equals(player.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }
                if (plugin.teamManager.getTeam(target) != null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-team")));
                    return true;
                }

                for (InviteRequest request : invites) {
                    if (request.getReceiver() == target && request.getSender() == team) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-invited")));
                        return true;
                    }
                }

                invites.add(new InviteRequest(team, target));
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("invite-success")
                                .replaceAll("%player%", target.getName())));

                target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("invite-received")
                                .replaceAll("%team%", team.getName())));
                return true;
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("accept")) {
                if (invites.isEmpty()) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-invites")));
                    return true;
                }

                for (InviteRequest request : invites) {
                    if (request.getReceiver() == player) {
                        long timeLeft = ((request.getTimeSent() / 1000) + plugin.getConfig().getInt("defaults.request-timeout")) - (System.currentTimeMillis() / 1000);
                        if (timeLeft > 0) {
                            Team team = request.getSender();
                            if (team.getMembers().size() >= team.getMaximumMembers()) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-full")));
                                invites.remove(request);
                                return true;
                            }
                            team.addMember(player.getUniqueId());
                            invites.remove(request);

                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("accept-success")
                                            .replaceAll("%team%", team.getName())));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-invite")));
                            invites.remove(request);
                        }
                        invites.remove(request);
                        return true;
                    }
                }
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("deny")) {
                if (invites.isEmpty()) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-invites")));
                    return true;
                }

                for (InviteRequest request : invites) {
                    if (request.getReceiver() == player) {
                        long timeLeft = ((request.getTimeSent() / 1000) + plugin.getConfig().getInt("defaults.request-timeout")) - (System.currentTimeMillis() / 1000);
                        if (timeLeft > 0) {
                            invites.remove(request);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("deny-success")));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-invite")));
                            invites.remove(request);
                        }
                        invites.remove(request);
                        return true;
                    }
                }
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("promote")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }

                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }

                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }
                }

                if (team.getRanks().get(target.getUniqueId()) == Ranks.Leader ||
                        team.getRanks().get(target.getUniqueId()) == Ranks.CoLeader) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }

                if (team.getRanks().get(target.getUniqueId()) == Ranks.Recruit) {
                    team.getRanks().put(target.getUniqueId(), Ranks.Member);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("promote-success")
                                    .replaceAll("%player%", target.getName())));
                    return true;
                }
                if (team.getRanks().get(target.getUniqueId()) == Ranks.Member) {
                    team.getRanks().put(target.getUniqueId(), Ranks.Moderator);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("promote-success")
                                    .replaceAll("%player%", target.getName())));
                    return true;
                }
                if (team.getRanks().get(target.getUniqueId()) == Ranks.Moderator) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.Leader) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }

                    team.getRanks().put(target.getUniqueId(), Ranks.CoLeader);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("promote-success")
                                    .replaceAll("%player%", target.getName())));
                    return true;
                }

            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("demote")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }

                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }

                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }
                }

                if (team.getRanks().get(target.getUniqueId()) == Ranks.Leader ||
                        team.getRanks().get(target.getUniqueId()) == Ranks.CoLeader) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }

                if (team.getRanks().get(target.getUniqueId()) == Ranks.Recruit) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }

                if (team.getRanks().get(target.getUniqueId()) == Ranks.Member) {
                    team.getRanks().put(target.getUniqueId(), Ranks.Recruit);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("demote-success")
                                    .replaceAll("%player%", target.getName())));
                    return true;
                }

                if (team.getRanks().get(target.getUniqueId()) == Ranks.Moderator) {
                    team.getRanks().put(target.getUniqueId(), Ranks.Member);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("demote-success")
                                    .replaceAll("%player%", target.getName())));
                    return true;
                }

                if (team.getRanks().get(target.getUniqueId()) == Ranks.CoLeader) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.Leader) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }

                    team.getRanks().put(target.getUniqueId(), Ranks.Moderator);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("demote-success")
                                    .replaceAll("%player%", target.getName())));
                    return true;
                }
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("title")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }

                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }
                }

                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                if (!team.getMembers().contains(target.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("member-not-found")));
                    return true;
                }

                if (args.length < 3) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-title")));
                    return true;
                }
                String title = args[2];
                if (!Utils.isOnlyLetters(title)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-player-title")));
                    return true;
                }

                team.getTags().put(target.getUniqueId(), title);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("title-success")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%title%", title)));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("kick")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }

                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                if (target.getUniqueId().equals(player.getUniqueId())
                        || team.getRanks().get(target.getUniqueId()) == Ranks.Leader ||
                        team.getRanks().get(target.getUniqueId()) == Ranks.CoLeader) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }

                if (plugin.teamManager.getTeam(target) != team) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }

                team.getRanks().remove(target.getUniqueId());
                team.getMembers().remove(target.getUniqueId());
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("player-kicked-success")
                                .replaceAll("%player%", target.getName())));

                if (target.isOnline()) {
                    target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("player-kicked")
                                    .replaceAll("%team%", team.getName())));
                }
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("ban")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }

                if (team.getBanned().contains(target.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-already-banned")));
                    return true;
                }

                if (target.getUniqueId().equals(player.getUniqueId())
                        || team.getRanks().get(target.getUniqueId()) == Ranks.Leader ||
                        team.getRanks().get(target.getUniqueId()) == Ranks.CoLeader) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                    return true;
                }

                team.getRanks().remove(target.getUniqueId());
                team.getMembers().remove(target.getUniqueId());
                team.getBanned().add(target.getUniqueId());

                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("player-banned-success")
                                .replaceAll("%player%", target.getName())));
                target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("player-banned")
                                .replaceAll("%team%", team.getName())));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("unban")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }

                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }

                if (!team.getBanned().contains(target.getUniqueId())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-not-banned")));
                    return true;
                }

                team.getRanks().remove(target.getUniqueId());
                team.getMembers().remove(target.getUniqueId());
                team.getBanned().remove(target.getUniqueId());

                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("player-unbanned-success")
                                .replaceAll("%player%", target.getName())));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("open")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }
                }

                if (!team.getOpen()) {
                    team.setOpen(true);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-open-success")));
                    return true;
                }
                team.setOpen(false);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("team-close-success")));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("bank")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);
                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-bank-usage")));
                    return true;
                }
                if (args[1].equalsIgnoreCase("deposit") || args[1].equalsIgnoreCase("pay")) {
                    if (args.length < 3) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-bank-usage")));
                        return true;
                    }

                    double amount;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                        return true;
                    }

                    if (amount > plugin.getEconomy().getBalance(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                        return true;
                    }

                    plugin.getEconomy().withdrawPlayer(player, amount);
                    team.addBank(amount);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-bank-deposit")
                                    .replaceAll("%amount%", Utils.format(amount))));
                } else if (args[1].equalsIgnoreCase("withdraw") || args[1].equalsIgnoreCase("take")) {
                    if (!team.getLeader().equals(player.getUniqueId())) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                            if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                                return true;
                            }
                        }
                    }

                    if (args.length < 3) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-bank-usage")));
                        return true;
                    }

                    double amount;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                        return true;
                    }

                    if (amount > team.getBank()) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-enough-money")));
                        return true;
                    }

                    plugin.getEconomy().depositPlayer(player, amount);
                    team.removeBank(amount);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-bank-withdraw")
                                    .replaceAll("%amount%", Utils.format(amount))));
                } else if (args[1].equalsIgnoreCase("balance") || args[1].equalsIgnoreCase("bal")) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-bank-balance")
                                    .replaceAll("%amount%", Utils.format(team.getBank()))));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-bank-balance")
                                    .replaceAll("%amount%", Utils.format(team.getBank()))));
                }
                return true;
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("neutral")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }

                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }

                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-status-usage")));
                    return true;
                }

                String targetTeamName = args[1];
                Team targetTeam = plugin.teamManager.getTeam(targetTeamName);
                if (targetTeam == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                    return true;
                }

                if (team.getTruces().contains(targetTeam) || team.getEnemies().contains(targetTeam) ||
                        team.getAllies().contains(targetTeam) || targetTeam.getTruces().contains(team) ||
                        targetTeam.getEnemies().contains(team) || targetTeam.getAllies().contains(team)) {
                    TeamRequest request = new TeamRequest(RequestType.Neutral, team, targetTeam);

                    if (teamRequests.containsKey(targetTeam)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("request-already-pending")));
                        return true;
                    }

                    teamRequests.put(targetTeam, request);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-neutral-request")
                                    .replaceAll("%team%", targetTeam.getName())));

                    List<UUID> members = new ArrayList<>();
                    for (UUID uuid : targetTeam.getMembers()) {
                        if (targetTeam.getRanks().get(uuid) == Ranks.Leader || targetTeam.getRanks().get(uuid) == Ranks.CoLeader ||
                                targetTeam.getRanks().get(uuid) == Ranks.Moderator) {
                            members.add(uuid);
                        }
                    }

                    for (UUID uuid : members) {
                        Player member = Bukkit.getPlayer(uuid);
                        if (member != null) {
                            member.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("team-neutral-request-received")
                                            .replaceAll("%team%", team.getName())));
                        }
                    }
                } else {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-already-neutral")));
                }
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("enemy")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }

                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }

                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-status-usage")));
                    return true;
                }
                String targetTeamName = args[1];
                Team targetTeam = plugin.teamManager.getTeam(targetTeamName);
                if (targetTeam == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                    return true;
                }

                if (team.getEnemies().contains(targetTeam)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-already-enemy")
                            .replaceAll("%team%", targetTeamName)));
                    return true;
                }
                team.getTruces().remove(targetTeam);
                team.getEnemies().remove(targetTeam);
                team.getAllies().remove(targetTeam);

                targetTeam.getTruces().remove(team);
                targetTeam.getEnemies().remove(team);
                targetTeam.getAllies().remove(team);

                team.addEnemy(targetTeam);
                targetTeam.addEnemy(team);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("team-enemy-success")
                                .replaceAll("%team%", targetTeamName)));
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("truce")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }

                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }

                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-status-usage")));
                    return true;
                }

                if (args[1].equalsIgnoreCase("accept")) {
                    if (!teamRequests.containsKey(team)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-requests")));
                        return true;
                    }

                    long timeLeft = ((teamRequests.get(team).getTimeSent() / 1000) + plugin.getConfig().getInt("request-timeout")) - (System.currentTimeMillis() / 1000);
                    if (timeLeft <= 0) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-request")));
                        teamRequests.remove(team);
                        return true;
                    }

                    team.removeAlly(teamRequests.get(team).getSender());
                    team.removeEnemy(teamRequests.get(team).getSender());
                    team.removeTruce(teamRequests.get(team).getSender());

                    teamRequests.get(team).getSender().removeTruce(team);
                    teamRequests.get(team).getSender().removeAlly(team);
                    teamRequests.get(team).getSender().removeEnemy(team);

                    team.addTruce(teamRequests.get(team).getSender());
                    teamRequests.get(team).getSender().addTruce(team);

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-truce-success")
                                    .replaceAll("%player%", teamRequests.get(team).getSender().getName())));

                    teamRequests.remove(team);
                } else if (args[1].equalsIgnoreCase("deny")) {
                    if (!teamRequests.containsKey(team)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-requests")));
                        return true;
                    }

                    long timeLeft = ((teamRequests.get(team).getTimeSent() / 1000) + plugin.getConfig().getInt("request-timeout")) - (System.currentTimeMillis() / 1000);
                    if (timeLeft <= 0) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-request")));
                        teamRequests.remove(team);
                        return true;
                    }

                    teamRequests.remove(team);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-truce-denied")
                                    .replaceAll("%player%", teamRequests.get(team).getSender().getName())));
                }

                String targetTeamName = args[1];
                Team targetTeam = plugin.teamManager.getTeam(targetTeamName);
                if (targetTeam == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                    return true;
                }

                if (team.getTruces().contains(targetTeam)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-already-truce")
                            .replaceAll("%team%", targetTeamName)));
                    return true;
                }
                TeamRequest request = new TeamRequest(RequestType.Truce, team, targetTeam);

                teamRequests.put(targetTeam, request);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("team-truce-request")
                                .replaceAll("%team%", targetTeamName)));

                List<UUID> members = new ArrayList<>();
                for (UUID uuid : targetTeam.getMembers()) {
                    if (targetTeam.getRanks().get(uuid) == Ranks.Leader || targetTeam.getRanks().get(uuid) == Ranks.CoLeader ||
                            targetTeam.getRanks().get(uuid) == Ranks.Moderator) {
                        members.add(uuid);
                    }
                }

                for (UUID uuid : members) {
                    Player member = Bukkit.getPlayer(uuid);
                    if (member != null) {
                        member.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("team-truce-request-received")
                                        .replaceAll("%team%", team.getName())));
                    }
                }
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("ally")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                    return true;
                }

                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        if (team.getRanks().get(player.getUniqueId()) != Ranks.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                }

                if (args[1].equalsIgnoreCase("accept")) {
                    if (!teamRequests.containsKey(team)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-requests")));
                        return true;
                    }

                    long timeLeft = ((teamRequests.get(team).getTimeSent() / 1000) + plugin.getConfig().getInt("request-timeout")) - (System.currentTimeMillis() / 1000);
                    if (timeLeft <= 0) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-request")));
                        teamRequests.remove(team);
                        return true;
                    }

                    team.removeAlly(teamRequests.get(team).getSender());
                    team.removeEnemy(teamRequests.get(team).getSender());
                    team.removeTruce(teamRequests.get(team).getSender());

                    teamRequests.get(team).getSender().removeTruce(team);
                    teamRequests.get(team).getSender().removeAlly(team);
                    teamRequests.get(team).getSender().removeEnemy(team);

                    team.addAlly(teamRequests.get(team).getSender());
                    teamRequests.get(team).getSender().addAlly(team);

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-ally-success")
                                    .replaceAll("%player%", teamRequests.get(team).getSender().getName())));

                    teamRequests.remove(team);
                } else if (args[1].equalsIgnoreCase("deny")) {
                    if (!teamRequests.containsKey(team)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-requests")));
                        return true;
                    }

                    long timeLeft = ((teamRequests.get(team).getTimeSent() / 1000) + plugin.getConfig().getInt("request-timeout")) - (System.currentTimeMillis() / 1000);
                    if (timeLeft <= 0) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-request")));
                        teamRequests.remove(team);
                        return true;
                    }

                    teamRequests.remove(team);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-ally-denied")
                                    .replaceAll("%player%", teamRequests.get(team).getSender().getName())));
                }

                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-status-usage")));
                    return true;
                }

                String targetTeamName = args[1];
                Team targetTeam = plugin.teamManager.getTeam(targetTeamName);
                if (targetTeam == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                    return true;
                }

                if (team.getAllies().contains(targetTeam)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-already-ally")
                            .replaceAll("%team%", targetTeamName)));
                    return true;
                }

                TeamRequest request = new TeamRequest(RequestType.Ally, team, targetTeam);
                teamRequests.put(targetTeam, request);
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("team-ally-request")
                                .replaceAll("%team%", targetTeamName)));

                List<UUID> members = new ArrayList<>();
                for (UUID uuid : targetTeam.getMembers()) {
                    if (targetTeam.getRanks().get(uuid) == Ranks.Leader || targetTeam.getRanks().get(uuid) == Ranks.CoLeader ||
                            targetTeam.getRanks().get(uuid) == Ranks.Moderator) {
                        members.add(uuid);
                    }
                }

                for (UUID uuid : members) {
                    Player member = Bukkit.getPlayer(uuid);
                    if (member != null) {
                        member.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("team-ally-request-received")
                                        .replaceAll("%team%", team.getName())));
                    }
                }
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("chat")) {
                if (player.hasPermission("Peligon.Teams.Chat") || player.hasPermission("Peligon.Teams.*")) {
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(plugin.fileMessage.getConfig().getString("no-in-team"));
                        return true;
                    }

                    if (args.length != 2) {
                        player.sendMessage(plugin.fileMessage.getConfig().getString("specify-channel"));
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("team")) {
                        if (plugin.teamManager.getPlayerChat(player) == Channel.Team) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-chat")));
                            return true;
                        }

                        plugin.teamManager.changePlayerChannel(player, Channel.Team);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("channel-changed")
                                        .replace("%channel%", "team")));
                    } else if (args[1].equalsIgnoreCase("truce") || args[1].equalsIgnoreCase("t")) {
                        if (plugin.teamManager.getPlayerChat(player) == Channel.Truce) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-chat")));
                            return true;
                        }

                        plugin.teamManager.changePlayerChannel(player, Channel.Truce);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("channel-changed")
                                        .replaceAll("%channel%", "truce")));
                    } else if (args[1].equalsIgnoreCase("ally") || args[1].equalsIgnoreCase("a")) {
                        if (plugin.teamManager.getPlayerChat(player) == Channel.Ally) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-chat")));
                            return true;
                        }

                        plugin.teamManager.changePlayerChannel(player, Channel.Ally);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("channel-changed")
                                        .replaceAll("%channel%", "ally")));
                    } else if (args[1].equalsIgnoreCase("mod") || args[1].equalsIgnoreCase("m")) {
                        if (plugin.teamManager.getPlayerChat(player) == Channel.Moderator) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-chat")));
                            return true;
                        }

                        plugin.teamManager.changePlayerChannel(player, Channel.Moderator);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("channel-changed")
                                        .replaceAll("%channel%", "moderator")));
                    } else if (args[1].equalsIgnoreCase("public") || args[1].equalsIgnoreCase("p")) {
                        if (plugin.teamManager.getPlayerChat(player) == Channel.Global) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-chat")));
                            return true;
                        }

                        plugin.teamManager.changePlayerChannel(player, Channel.Global);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("channel-changed")
                                        .replaceAll("%channel%", "global")));
                    }

                } else {
                    player.sendMessage(plugin.fileMessage.getConfig().getString("no-permission"));
                }
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("accnounce")) {
                if (!plugin.teamManager.inTeam(player)) {
                    player.sendMessage(plugin.fileMessage.getConfig().getString("not-in-team"));
                    return true;
                }
                Team team = plugin.teamManager.getTeam(player);
                if (!team.getLeader().equals(player.getUniqueId())) {
                    if (team.getRanks().get(player.getUniqueId()) != Ranks.CoLeader) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }
                }

                if (args.length < 2) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-message")));
                    return true;
                }
                String message = "";
                for (int i = 1; i < args.length; i++) {
                    message += args[i] + " ";
                }
                message = message.substring(0, message.length() - 1);
                for (UUID uuid : team.getMembers()) {
                    Player member = Bukkit.getPlayer(uuid);
                    if (member != null) {
                        member.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("defaults.team-announcement")
                                        .replaceAll("%team%", team.getName())
                                        .replaceAll("%message%", message)));
                    }
                }
            }
        }
        return false;
    }
}
