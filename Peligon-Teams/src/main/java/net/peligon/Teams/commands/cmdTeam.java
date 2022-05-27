package net.peligon.Teams.commands;

import net.peligon.Teams.Main;
import net.peligon.Teams.libaries.CustomConfig;
import net.peligon.Teams.libaries.Team;
import net.peligon.Teams.libaries.Utils;
import net.peligon.Teams.libaries.teamSettings.Rank;
import net.peligon.Teams.libaries.teamSettings.Vault;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class cmdTeam implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private Map<UUID, Long> inviteRequests = new HashMap<>();
    private Map<UUID, Team> inviteTeam = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("team")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(Utils.chatColor("&e----------{ &6Teams &e| &aHelp &e}----------"));
                player.sendMessage(Utils.chatColor("&e/team create <name> &7- Create a new team"));
                player.sendMessage(Utils.chatColor("&e/team delete &7- Deletes your team"));
                player.sendMessage(Utils.chatColor("&e/team join <name> &7- Join a team"));
                player.sendMessage(Utils.chatColor("&e/team leave &7- Leave your team"));
                player.sendMessage(Utils.chatColor("&e/team invite <player> &7- Invite a player to your team"));
                player.sendMessage(Utils.chatColor("&e/team kick <player> &7- Kick a player from your team"));
                player.sendMessage(Utils.chatColor("&e/team ban <player> &7- Ban a player from your team"));
                player.sendMessage(Utils.chatColor("&e/team unban <player> &7- Unban a player from your team"));
                player.sendMessage(Utils.chatColor("&e/team promote <player> &7- Promote a player in your team"));
                player.sendMessage(Utils.chatColor("&e/team demote <player> &7- Demote a player in your team"));
                player.sendMessage(Utils.chatColor("&e/team info &7- Display your team info"));
                player.sendMessage(Utils.chatColor("&e/team list &7- Display all teams"));
                player.sendMessage(Utils.chatColor("&e/team chat <message> &7- Send a message to your team"));
                player.sendMessage(Utils.chatColor("&e/team chat &7- Toggle chat to your team"));
                player.sendMessage(Utils.chatColor("&e---------------------------------"));
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "create":
                    if (plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-team")));
                        return true;
                    }

                    if (args.length < 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-team-name")));
                        return true;
                    }
                    String name = String.valueOf(args[1]);

                    if (!Utils.isOnlyLetters(name)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-name")));
                        return true;
                    }
                    if (plugin.teamManager.teamAlreadyExists(name)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-already-exists")));
                        return true;
                    }

                    List<Vault> vaultList = new ArrayList<>();
                    for (int i = 0; i < plugin.getConfig().getInt("Team-Settings.starter-vaults"); i++) {
                        vaultList.add(new Vault(
                                new ItemStack[]{},
                                plugin.getConfig().getInt("Team-Settings.vault-settings.size"),
                                i == 0 && plugin.getConfig().getBoolean("Team-Settings.vault-settings.secured")));
                    }

                    List<UUID> members = new ArrayList<>();
                    members.add(player.getUniqueId());

                    Map<UUID, Rank> membersRanks = new HashMap<>();
                    membersRanks.put(player.getUniqueId(), Rank.Leader);

                    Team teamToCreate = new Team(
                            name,
                            player.getUniqueId(),
                            members,
                            membersRanks,
                            plugin.getConfig().getInt("Team-Settings.maximum-vaults"),
                            vaultList
                    );

                    if (plugin.getConfig().getInt("Teams-Settings.creation-fee") > 0) {
                        if (plugin.getEconomy().getBalance(player) < plugin.getConfig().getInt("Teams-Settings.creation-fee")) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                            return true;
                        }
                        plugin.getEconomy().withdrawPlayer(player, plugin.getConfig().getInt("Teams-Settings.creation-fee"));
                    }
                    Utils.teams.add(teamToCreate);

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-created").replaceAll("%team%", name)));
                    break;
                case "delete":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }
                    Team teamToDelete = plugin.teamManager.getTeam(player);
                    if (!teamToDelete.getLeader().equals(player.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("not-leader")));
                        return true;
                    }
                    File file = new File(plugin.getDataFolder() + "/teams/" + teamToDelete.getName() + ".yml");
                    file.delete();

                    Utils.teams.remove(teamToDelete);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-deleted").replaceAll("%team%", teamToDelete.getName())));
                    break;
                case "leave":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }
                    Team teamToLeave = plugin.teamManager.getTeam(player);
                    if (teamToLeave.getLeader().equals(player.getUniqueId())) {
                        for (UUID member : teamToLeave.getMembers()) {
                            if (teamToLeave.getPlayerRanks().get(member).equals(Rank.Moderator)) {
                                teamToLeave.getPlayerRanks().put(member, Rank.Leader);
                                teamToLeave.setLeader(member);
                                break;
                            }
                        }
                        if (teamToLeave.getLeader().equals(player.getUniqueId())) {
                            player.performCommand("team delete");
                            return true;
                        }
                    }
                    teamToLeave.getMembers().remove(player.getUniqueId());

                    if (teamToLeave.getPlayerRanks().containsKey(player.getUniqueId())) {
                        teamToLeave.getPlayerRanks().remove(player.getUniqueId());
                    }

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("left-team")
                                    .replaceAll("%team%", teamToLeave.getName())));
                    break;
                case "invite":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("accept")) {
                        if (inviteRequests.isEmpty() || !inviteRequests.containsKey(player.getUniqueId())) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("no-invite-pending")));
                            return true;
                        }
                        long timeLeft = ((inviteRequests.get(player.getUniqueId()) / 1000) +
                                plugin.getConfig().getInt("Teams-Settings.invite-timeout")) - (System.currentTimeMillis() / 1000);

                        if (timeLeft > 0) {
                            Team teamToInvite = inviteTeam.get(player.getUniqueId());

                            teamToInvite.getMembers().add(player.getUniqueId());
                            teamToInvite.getPlayerRanks().put(player.getUniqueId(), Rank.Member);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("invite-accepted")
                                            .replaceAll("%team%", teamToInvite.getName())));

                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("no-invite-pending")));
                        }
                        inviteRequests.remove(player.getUniqueId());
                        inviteTeam.remove(player.getUniqueId());
                        return true;
                    } else if (args[1].equalsIgnoreCase("deny")) {
                        if (inviteRequests.isEmpty() || !inviteRequests.containsKey(player.getUniqueId())) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("no-invite-pending")));
                            return true;
                        }
                        long timeLeft = ((inviteRequests.get(player.getUniqueId()) / 1000) +
                                plugin.getConfig().getInt("Teams-Settings.invite-timeout")) - (System.currentTimeMillis() / 1000);

                        if (timeLeft > 0) {
                            inviteRequests.remove(player.getUniqueId());
                            inviteTeam.remove(player.getUniqueId());

                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("invite-denied").replaceAll("%team%", inviteTeam.get(player.getUniqueId()).getName())));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("no-invite-pending")));
                            inviteRequests.remove(player.getUniqueId());
                            inviteTeam.remove(player.getUniqueId());
                        }
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

                    Team teamToDeny = plugin.teamManager.getTeam(player);
                    if (teamToDeny.getMembers().contains(target.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-member")));
                        return true;
                    }

                    if (!teamToDeny.getPlayerRanks().get(player.getUniqueId()).equals(Rank.Leader)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }

                    if (inviteRequests.containsKey(target.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-invited")));
                        return true;
                    }

                    inviteRequests.put(target.getUniqueId(), System.currentTimeMillis());
                    inviteTeam.put(target.getUniqueId(), teamToDeny);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("invite-team")
                                    .replaceAll("%team%", teamToDeny.getName())));
                    target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("invite-received")
                                    .replaceAll("%team%", teamToDeny.getName())));
                    break;
                case "kick":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }

                    Team teamToKick = plugin.teamManager.getTeam(player);
                    if (!teamToKick.getLeader().equals(player.getUniqueId())) {
                        if (!teamToKick.getPlayerRanks().get(player.getUniqueId()).equals(Rank.Moderator)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        }
                        return true;
                    }
                    if (args.length < 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                        return true;
                    }

                    OfflinePlayer playerToKick = Bukkit.getOfflinePlayer(args[1]);
                    if (playerToKick == player) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-kick-yourself")));
                        return true;
                    }

                    if (plugin.teamManager.getTeam(playerToKick) != teamToKick) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("different-team")));
                        return true;
                    }

                    if (teamToKick.getLeader() == player.getUniqueId()) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }
                    if (plugin.teamManager.getTeam(playerToKick).getPlayerRanks().get(playerToKick.getUniqueId()).equals(Rank.Moderator)) {
                        if (teamToKick.getLeader() != player.getUniqueId()) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-kick-moderator")));
                            return true;
                        }
                    }

                    teamToKick.getMembers().remove(playerToKick.getUniqueId());
                    if (teamToKick.getPlayerRanks().containsKey(playerToKick.getUniqueId())) {
                        teamToKick.getPlayerRanks().remove(playerToKick.getUniqueId());
                    }

                    player.getPlayer().sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("kick-success")
                                    .replaceAll("%player%", playerToKick.getName())
                                    .replaceAll("%team%", teamToKick.getName())));
                    break;
                case "ban":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }

                    Team teamToBan = plugin.teamManager.getTeam(player);
                    if (!teamToBan.getLeader().equals(player.getUniqueId())) {
                        if (!teamToBan.getPlayerRanks().get(player.getUniqueId()).equals(Rank.Moderator)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                    if (args.length < 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                        return true;
                    }

                    OfflinePlayer playerToBan = Bukkit.getOfflinePlayer(args[1]);
                    if (playerToBan == player) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-ban-yourself")));
                        return true;
                    }

                    if (teamToBan.getBanned().contains(playerToBan.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-banned")));
                        return true;
                    }

                    if (teamToBan.getLeader() == player.getUniqueId()) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }

                    if (plugin.teamManager.getTeam(playerToBan) == teamToBan &&
                            plugin.teamManager.getTeam(playerToBan).getPlayerRanks().get(playerToBan.getUniqueId()).equals(Rank.Moderator)) {
                        if (teamToBan.getLeader() != player.getUniqueId()) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-ban-moderator")));
                            return true;
                        }
                    }

                    teamToBan.getMembers().remove(playerToBan.getUniqueId());
                    if (teamToBan.getPlayerRanks().containsKey(playerToBan.getUniqueId())) {
                        teamToBan.getPlayerRanks().remove(playerToBan.getUniqueId());
                    }
                    teamToBan.addBanned(playerToBan.getUniqueId());

                    player.getPlayer().sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("ban-success")
                                    .replaceAll("%player%", playerToBan.getName())
                                    .replaceAll("%team%", teamToBan.getName())));

                    break;
                case "unban":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }

                    Team teamToUnban = plugin.teamManager.getTeam(player);
                    if (!teamToUnban.getLeader().equals(player.getUniqueId())) {
                        if (!teamToUnban.getPlayerRanks().get(player.getUniqueId()).equals(Rank.Moderator)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                            return true;
                        }
                    }
                    if (args.length < 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                        return true;
                    }

                    OfflinePlayer playerToUnban = Bukkit.getOfflinePlayer(args[1]);
                    if (playerToUnban == player) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }

                    if (!teamToUnban.getBanned().contains(playerToUnban.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-banned")));
                        return true;
                    }

                    teamToUnban.getBanned().remove(playerToUnban.getUniqueId());

                    player.getPlayer().sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("unban-success")
                                    .replaceAll("%player%", playerToUnban.getName())
                                    .replaceAll("%team%", teamToUnban.getName())));
                    break;
                case "promote":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }

                    Team teamToPromote = plugin.teamManager.getTeam(player);
                    if (!teamToPromote.getLeader().equals(player.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }

                    if (args.length < 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                        return true;
                    }
                    OfflinePlayer playerToPromote = Bukkit.getOfflinePlayer(args[1]);
                    if (playerToPromote == player) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }

                    if (!teamToPromote.getMembers().contains(playerToPromote.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }

                    if (teamToPromote.getPlayerRanks().get(playerToPromote.getUniqueId()).equals(Rank.Moderator)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-moderator")));
                        return true;
                    }

                    if (teamToPromote.getPlayerRanks().get(playerToPromote.getUniqueId()).equals(Rank.Member)) {
                        teamToPromote.getPlayerRanks().put(playerToPromote.getUniqueId(), Rank.Moderator);
                        player.getPlayer().sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("promote-success")
                                        .replaceAll("%player%", playerToPromote.getName())
                                        .replaceAll("%team%", teamToPromote.getName())));
                    }
                    break;
                case "demote":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }

                    Team teamToDemote = plugin.teamManager.getTeam(player);
                    if (!teamToDemote.getLeader().equals(player.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }

                    if (args.length < 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                        return true;
                    }
                    OfflinePlayer playerToDemote = Bukkit.getOfflinePlayer(args[1]);
                    if (playerToDemote == player) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cant-do-that")));
                        return true;
                    }

                    if (!teamToDemote.getMembers().contains(playerToDemote.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }

                    if (teamToDemote.getPlayerRanks().get(playerToDemote.getUniqueId()).equals(Rank.Member)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-member")));
                        return true;
                    }

                    if (teamToDemote.getPlayerRanks().get(playerToDemote.getUniqueId()).equals(Rank.Moderator)) {
                        teamToDemote.getPlayerRanks().put(playerToDemote.getUniqueId(), Rank.Member);
                        player.getPlayer().sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("demote-success")
                                        .replaceAll("%player%", playerToDemote.getName())
                                        .replaceAll("%team%", teamToDemote.getName())));
                    }
                    break;
                case "chat":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }
                    if (args.length < 2) {
                        plugin.teamManager.toggleTeamChat(player);

                        if (plugin.teamManager.inTeamChat(player)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("team-chat-enabled")));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("team-chat-disabled")));
                        }
                        return true;
                    }

                    String message = "";
                    for (int i = 1; i < args.length; i++) {
                        message += args[i] + " ";
                    }

                    for (UUID member : plugin.teamManager.getTeam(player).getMembers()) {
                        Player memberPlayer = Bukkit.getPlayer(member);
                        if (memberPlayer != null) {
                            memberPlayer.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-chat-format")
                                    .replaceAll("%team%", plugin.teamManager.getTeam(player).getName())
                                    .replaceAll("%player%", player.getName())
                                    .replaceAll("%message%", message)));
                        }
                    }
                    break;
                case "list":
                    // TODO list teams
                    break;
                case "info":
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }
                    Team teamInfo = plugin.teamManager.getTeam(player);
                    player.sendMessage(Utils.chatColor("&e----------{ &6Teams &e| &aInformation &e}----------"));
                    player.sendMessage(Utils.chatColor("&eTeam Name: &6" + teamInfo.getName()));
                    player.sendMessage(Utils.chatColor("&eDescription: &f" + teamInfo.getDescription()));
                    player.sendMessage(Utils.chatColor("&eLeader: &f" + Bukkit.getOfflinePlayer(teamInfo.getLeader()).getName()));
                    player.sendMessage(Utils.chatColor("&eMembers: &f" + teamInfo.getMembers().size()));
                    player.sendMessage(Utils.chatColor("&eAvailable Vaults: &f" + teamInfo.getVaults().size() + "/" + teamInfo.getMaximumVaults()));
                    player.sendMessage(Utils.chatColor("&eBank Balance: &2$" + Utils.format(teamInfo.getTeamBankVault())));
                    player.sendMessage(Utils.chatColor("&eExperience Balance: &2" + Utils.format(teamInfo.getTeamExperienceVault())));
                    player.sendMessage(Utils.chatColor("&eUnlocked Upgrades: &f" + Collections.frequency(teamInfo.getUnlockedUpgrades().values(), true) + "/" + teamInfo.getUnlockedUpgrades().size()));
                    player.sendMessage(Utils.chatColor("&e---------------------------------------"));
                    break;
                case "admin":
                    if (player.hasPermission("Peligon.Teams.Admin") || player.hasPermission("Peligon.Teams.*")) {
                        if (Utils.adminMode.contains(player.getUniqueId())) {
                            Utils.adminMode.remove(player.getUniqueId());
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("admin-mode-disabled")));
                            return true;
                        }
                        Utils.adminMode.add(player.getUniqueId());
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("admin-mode-enabled")));
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                    }
                case "vault":
                    if (Utils.adminMode.contains(player.getUniqueId())) {
                        if (args.length < 4) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-admin-usage")));
                            return true;
                        }

                        if (args[1].equalsIgnoreCase("economy")) {
                            if (args[2].equalsIgnoreCase("reset")) {
                                String team = args[3];
                                if (plugin.teamManager.getTeam(team) == null) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                                    return true;
                                }

                                plugin.teamManager.getTeam(team).setTeamBankVault(0.0);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("vault-admin-reset").replaceAll("%team%", team)));
                            } else if (args[2].equalsIgnoreCase("set")) {
                                String team = args[3];
                                if (plugin.teamManager.getTeam(team) == null) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                                    return true;
                                }

                                double amount = Double.parseDouble(args[4]);
                                if (amount < 0) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                                    return true;
                                }

                                plugin.teamManager.getTeam(team).setTeamBankVault(amount);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("vault-admin-updated").replaceAll("%team%", team)));
                            }
                        } else if (args[1].equalsIgnoreCase("experience")) {
                            if (args[2].equalsIgnoreCase("reset")) {
                                String team = args[3];
                                if (plugin.teamManager.getTeam(team) == null) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                                    return true;
                                }

                                plugin.teamManager.getTeam(team).setTeamExperienceVault(0);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("vault-admin-reset").replaceAll("%team%", team)));
                            } else if (args[2].equalsIgnoreCase("set")) {
                                String team = args[3];
                                if (plugin.teamManager.getTeam(team) == null) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                                    return true;
                                }

                                int amount = Integer.parseInt(args[4]);
                                if (amount < 0) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                                    return true;
                                }

                                plugin.teamManager.getTeam(team).setTeamExperienceVault(amount);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("vault-admin-updated").replaceAll("%team%", team)));
                            }
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-admin-usage")));
                        }
                        return true;
                    }
                    if (!plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-team")));
                        return true;
                    }

                    if (plugin.teamManager.getTeam(player).getPlayerRanks().get(player.getUniqueId()) != Rank.Moderator) {
                        if (plugin.teamManager.getTeam(player).getPlayerRanks().get(player.getUniqueId()) != Rank.Leader) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                            return true;
                        }
                    }

                    if (args.length < 5) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-usage")));
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("economy")) {
                        if (plugin.getEconomy() == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("economy-disabled")));
                            return true;
                        }
                        if (args[2].equalsIgnoreCase("add") || args[2].equalsIgnoreCase("pay")) {
                            if (args[3].equalsIgnoreCase("all") || args[3].equalsIgnoreCase("*")) {
                                double amount = plugin.getEconomy().getBalance(player);
                                plugin.getEconomy().withdrawPlayer(player, amount);
                                plugin.teamManager.getTeam(player).addTeamBankVault(amount);

                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("vault-economy-added")
                                                .replaceAll("%amount%", Utils.format(amount))));
                                return true;
                            }
                            double amount = Double.parseDouble(args[3]);

                            if (amount > plugin.getEconomy().getBalance(player)) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                                return true;
                            }

                            plugin.getEconomy().withdrawPlayer(player, amount);
                            plugin.teamManager.getTeam(player).addTeamBankVault(amount);

                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("vault-economy-added")
                                            .replaceAll("%amount%", Utils.format(amount))));
                        } else if (args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("take")) {
                            if (args[3].equalsIgnoreCase("all") || args[3].equalsIgnoreCase("*")) {
                                double amount = plugin.teamManager.getTeam(player).getTeamBankVault();
                                plugin.getEconomy().depositPlayer(player, amount);
                                plugin.teamManager.getTeam(player).removeTeamBankVault(amount);

                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("vault-economy-removed")
                                                .replaceAll("%amount%", Utils.format(amount))));
                                return true;
                            }
                            double amount = Double.parseDouble(args[3]);

                            if (plugin.teamManager.getTeam(player).getTeamBankVault() < amount) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-enough-money")));
                                return true;
                            }
                            plugin.getEconomy().depositPlayer(player, amount);
                            plugin.teamManager.getTeam(player).removeTeamBankVault(amount);

                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("vault-economy-removed")
                                            .replaceAll("%amount%", Utils.format(amount))));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-usage")));
                        }
                    } else if (args[1].equalsIgnoreCase("experience")) {
                        if (args[2].equalsIgnoreCase("add") || args[2].equalsIgnoreCase("pay")) {
                            if (args[3].equalsIgnoreCase("all") || args[3].equalsIgnoreCase("*")) {
                                int amount = Utils.getPlayerExp(player);
                                Utils.removePlayerExp(player, amount);
                                plugin.teamManager.getTeam(player).addTeamExperienceVault(amount);

                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("vault-experience-added")
                                                .replaceAll("%amount%", Utils.format(amount))));
                                return true;
                            }
                            int amount = Integer.parseInt(args[3]);

                            if (amount > Utils.getPlayerExp(player)) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-experience")));
                                return true;
                            }
                            Utils.removePlayerExp(player, amount);
                            plugin.teamManager.getTeam(player).addTeamExperienceVault(amount);

                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("vault-experience-added")
                                            .replaceAll("%amount%", Utils.format(amount))));
                        } else if (args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("take")) {
                            if (args[3].equalsIgnoreCase("all") || args[3].equalsIgnoreCase("*")) {
                                int amount = plugin.teamManager.getTeam(player).getTeamExperienceVault();
                                Utils.addPlayerExp(player, amount);
                                plugin.teamManager.getTeam(player).removeTeamExperienceVault(amount);

                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("vault-experience-removed")
                                                .replaceAll("%amount%", Utils.format(amount))));
                                return true;
                            }
                            int amount = Integer.parseInt(args[3]);

                            if (plugin.teamManager.getTeam(player).getTeamExperienceVault() < amount) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-enough-experience")));
                                return true;
                            }

                            Utils.addPlayerExp(player, amount);
                            plugin.teamManager.getTeam(player).removeTeamExperienceVault(amount);

                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("vault-experience-removed")
                                            .replaceAll("%amount%", Utils.format(amount))));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-usage")));
                        }
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-usage")));
                    }
                    break;
            }
        }
        return false;
    }

}
