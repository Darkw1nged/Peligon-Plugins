package net.peligon.Teams.commands;

import net.peligon.Teams.Main;
import net.peligon.Teams.libaries.Team;
import net.peligon.Teams.libaries.Utils;
import net.peligon.Teams.libaries.teamSettings.Rank;
import net.peligon.Teams.libaries.teamSettings.Vault;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class cmdTeam implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("team")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(Utils.chatColor("&a&m----------{ &eTeams &a&m}----------"));
                sender.sendMessage(Utils.chatColor("&a/team create <name> &7- &eCreate a new team"));
                sender.sendMessage(Utils.chatColor("&a/team delete &7- &eDeletes your team"));
                sender.sendMessage(Utils.chatColor("&a/team join <name> &7- &eJoin a team"));
                sender.sendMessage(Utils.chatColor("&a/team leave &7- &eLeave your team"));
                sender.sendMessage(Utils.chatColor("&a/team invite <player> &7- &eInvite a player to your team"));
                sender.sendMessage(Utils.chatColor("&a/team kick <player> &7- &eKick a player from your team"));
                sender.sendMessage(Utils.chatColor("&a/team ban <player> &7- &eBan a player from your team"));
                sender.sendMessage(Utils.chatColor("&a/team unban <player> &7- &eUnban a player from your team"));
                sender.sendMessage(Utils.chatColor("&a/team promote <player> &7- &ePromote a player in your team"));
                sender.sendMessage(Utils.chatColor("&a/team demote <player> &7- &eDemote a player in your team"));
                sender.sendMessage(Utils.chatColor("&a/team info &7- &eDisplay your team info"));
                sender.sendMessage(Utils.chatColor("&a/team list &7- &eDisplay all teams"));
                sender.sendMessage(Utils.chatColor("&a/team chat <message> &7- &eSend a message to your team"));
                sender.sendMessage(Utils.chatColor("&a/team chat &7- &eToggle chat to your team"));
                sender.sendMessage(Utils.chatColor("&a&m-------------------------------"));
                return true;
            }
            Player player = (Player) sender;
            switch (args[0].toLowerCase()) {
                case "create":
                    if (args.length < 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-team-name")));
                        return true;
                    }
                    if (plugin.teamManager.inTeam(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-in-team")));
                        return true;
                    }
                    String name = args[1];
                    if (!Utils.isOnlyLetters(name)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-name")));
                        return true;
                    }
                    if (plugin.teamManager.teamAlreadyExists(name)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-already-exists")));
                        return true;
                    }

                    List<Vault> vaultList = new ArrayList<>();
                    for (int i=0; i<plugin.getConfig().getInt("Team-Settings.starter-vaults"); i++) {
                        vaultList.add(new Vault(
                                new ItemStack[]{},
                                plugin.getConfig().getInt("Team-Settings.vault-settings.size"),
                                i == 0 && plugin.getConfig().getBoolean("Team-Settings.vault-settings.secured")));
                    }

                    List<UUID> members = new ArrayList<>();
                    members.add(player.getUniqueId());

                    Map<UUID, Rank> membersRanks = new HashMap<>();
                    membersRanks.put(player.getUniqueId(), Rank.Leader);

                    Team team = new Team(
                            name,
                            player.getUniqueId(),
                            members,
                            membersRanks,
                            plugin.getConfig().getInt("Team-Settings.maximum-vaults"),
                            vaultList
                    );
                    Utils.teams.add(team);

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("team-created").replaceAll("%team%", name)));

                    break;
                case "delete":
                    // TODO delete team
                    break;
                case "join":
                    // TODO join team
                    break;
                case "leave":
                    // TODO leave team
                    break;
                case "invite":
                    // TODO invite player
                    break;
                case "kick":
                    // TODO kick player
                    break;
                case "ban":
                    // TODO ban player
                    break;
                case "unban":
                    // TODO unban player
                    break;
                case "promote":
                    // TODO promote player
                    break;
                case "demote":
                    // TODO demote player
                    break;
                case "chat":
                    // TODO send chat message
                    break;
                case "chat-toggle":
                    // TODO chat toggle
                    break;
                case "list":
                    // TODO list teams
                    break;
                case "info":
                    // TODO info team
                    break;
            }
        }
        return false;
    }

}
