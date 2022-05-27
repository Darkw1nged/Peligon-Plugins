package net.peligon.Teams.commands;

import net.peligon.Teams.Main;
import net.peligon.Teams.libaries.Utils;
import net.peligon.Teams.libaries.teamSettings.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdVault implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vault")) {
            if (!(sender instanceof Player)) {
                if (args.length < 3) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-admin-usage")));
                    return true;
                }

                if (args[0].equalsIgnoreCase("economy")) {
                    if (args[1].equalsIgnoreCase("reset")) {
                        String team = args[2];
                        if (plugin.teamManager.getTeam(team) == null) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                            return true;
                        }

                        plugin.teamManager.getTeam(team).setTeamBankVault(0.0);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-admin-reset").replaceAll("%team%", team)));
                    } else if (args[1].equalsIgnoreCase("set")) {
                        String team = args[2];
                        if (plugin.teamManager.getTeam(team) == null) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                            return true;
                        }

                        double amount = Double.parseDouble(args[3]);
                        if (amount < 0) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                            return true;
                        }

                        plugin.teamManager.getTeam(team).setTeamBankVault(amount);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-admin-updated").replaceAll("%team%", team)));
                    }
                } else if (args[0].equalsIgnoreCase("experience")) {
                    if (args[1].equalsIgnoreCase("reset")) {
                        String team = args[2];
                        if (plugin.teamManager.getTeam(team) == null) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                            return true;
                        }

                        plugin.teamManager.getTeam(team).setTeamExperienceVault(0);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-admin-reset").replaceAll("%team%", team)));
                    } else if (args[1].equalsIgnoreCase("set")) {
                        String team = args[2];
                        if (plugin.teamManager.getTeam(team) == null) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                            return true;
                        }

                        int amount = Integer.parseInt(args[3]);
                        if (amount < 0) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                            return true;
                        }

                        plugin.teamManager.getTeam(team).setTeamExperienceVault(amount);
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-admin-updated").replaceAll("%team%", team)));
                    }
                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-admin-usage")));
                }
            }
            Player player = (Player) sender;
            if (Utils.adminMode.contains(player.getUniqueId())) {
                if (args.length < 3) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-admin-usage")));
                    return true;
                }

                if (args[0].equalsIgnoreCase("economy")) {
                    if (args[1].equalsIgnoreCase("reset")) {
                        String team = args[2];
                        if (plugin.teamManager.getTeam(team) == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                            return true;
                        }

                        plugin.teamManager.getTeam(team).setTeamBankVault(0.0);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-admin-reset").replaceAll("%team%", team)));
                    } else if (args[1].equalsIgnoreCase("set")) {
                        String team = args[2];
                        if (plugin.teamManager.getTeam(team) == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                            return true;
                        }

                        double amount = Double.parseDouble(args[3]);
                        if (amount < 0) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                            return true;
                        }

                        plugin.teamManager.getTeam(team).setTeamBankVault(amount);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-admin-updated").replaceAll("%team%", team)));
                    }
                } else if (args[0].equalsIgnoreCase("experience")) {
                    if (args[1].equalsIgnoreCase("reset")) {
                        String team = args[2];
                        if (plugin.teamManager.getTeam(team) == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                            return true;
                        }

                        plugin.teamManager.getTeam(team).setTeamExperienceVault(0);
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-admin-reset").replaceAll("%team%", team)));
                    } else if (args[1].equalsIgnoreCase("set")) {
                        String team = args[2];
                        if (plugin.teamManager.getTeam(team) == null) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-not-found")));
                            return true;
                        }

                        int amount = Integer.parseInt(args[3]);
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

            if (args.length < 3) {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("vault-usage")));
                return true;
            }

            if (args[0].equalsIgnoreCase("economy")) {
                if (plugin.getEconomy() == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("economy-disabled")));
                    return true;
                }
                if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("pay")) {
                    if (args[2].equalsIgnoreCase("all") || args[2].equalsIgnoreCase("*")) {
                        double amount = plugin.getEconomy().getBalance(player);
                        plugin.getEconomy().withdrawPlayer(player, amount);
                        plugin.teamManager.getTeam(player).addTeamBankVault(amount);

                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-economy-added")
                                        .replaceAll("%amount%", Utils.format(amount))));
                        return true;
                    }
                    double amount = Double.parseDouble(args[2]);

                    if (amount > plugin.getEconomy().getBalance(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                        return true;
                    }

                    plugin.getEconomy().withdrawPlayer(player, amount);
                    plugin.teamManager.getTeam(player).addTeamBankVault(amount);

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("vault-economy-added")
                                    .replaceAll("%amount%", Utils.format(amount))));
                } else if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("take")) {
                    if (args[2].equalsIgnoreCase("all") || args[2].equalsIgnoreCase("*")) {
                        double amount = plugin.teamManager.getTeam(player).getTeamBankVault();
                        plugin.getEconomy().depositPlayer(player, amount);
                        plugin.teamManager.getTeam(player).removeTeamBankVault(amount);

                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-economy-removed")
                                        .replaceAll("%amount%", Utils.format(amount))));
                        return true;
                    }
                    double amount = Double.parseDouble(args[2]);

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
            } else if (args[0].equalsIgnoreCase("experience")) {
                if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("pay")) {
                    if (args[2].equalsIgnoreCase("all") || args[2].equalsIgnoreCase("*")) {
                        int amount = Utils.getPlayerExp(player);
                        Utils.removePlayerExp(player, amount);
                        plugin.teamManager.getTeam(player).addTeamExperienceVault(amount);

                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-experience-added")
                                        .replaceAll("%amount%", Utils.format(amount))));
                        return true;
                    }
                    int amount = Integer.parseInt(args[2]);

                    if (amount > Utils.getPlayerExp(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-experience")));
                        return true;
                    }
                    Utils.removePlayerExp(player, amount);
                    plugin.teamManager.getTeam(player).addTeamExperienceVault(amount);

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("vault-experience-added")
                                    .replaceAll("%amount%", Utils.format(amount))));
                } else if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("take")) {
                    if (args[2].equalsIgnoreCase("all") || args[2].equalsIgnoreCase("*")) {
                        int amount = plugin.teamManager.getTeam(player).getTeamExperienceVault();
                        Utils.addPlayerExp(player, amount);
                        plugin.teamManager.getTeam(player).removeTeamExperienceVault(amount);

                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vault-experience-removed")
                                        .replaceAll("%amount%", Utils.format(amount))));
                        return true;
                    }
                    int amount = Integer.parseInt(args[2]);

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
        }
        return false;
    }

}
