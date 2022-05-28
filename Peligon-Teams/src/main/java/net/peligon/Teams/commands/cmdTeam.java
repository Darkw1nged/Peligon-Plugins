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

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("team")) {
            if (!(sender instanceof Player)) {
                // TODO console commands
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0 && !plugin.teamManager.inTeam(player)) {
                if (player.hasPermission("Peligon.Teams.Help") || player.hasPermission("Peligon.Teams.*")) {
                    // TODO send help
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

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-neutral-success")
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
                            plugin.fileMessage.getConfig().getString("team-neutral-denied")
                                    .replaceAll("%player%", teamRequests.get(team).getSender().getName())));
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
        }
        return false;
    }
}
