package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class cmdGoal implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("goal")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (args.length < 1) {
                for (String key : plugin.getConfig().getConfigurationSection("Server-Goals.targets").getKeys(false)) {
                    if (plugin.getConfig().getBoolean("Server-Goals.targets." + key + ".data.completed", false)) continue;
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("current-goal")
                                    .replaceAll("%name%", plugin.getConfig().getString("Server-Goals.targets." + key).replaceAll("_", " "))
                                    .replaceAll("%raised%", "" + plugin.getConfig().getInt("Server-Goals.targets." + key + ".data.raised"))
                                    .replaceAll("%goal%", "" + plugin.getConfig().getInt("Server-Goals.targets." + key + ".goal"))));
                    return true;
                }
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-goals-found")));
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                for (String key : plugin.getConfig().getConfigurationSection("Server-Goals.targets").getKeys(false)) {
                    if (plugin.getConfig().getBoolean("Server-Goals.targets." + key + ".data.completed", false)) continue;
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("goal")
                                    .replaceAll("%name%", plugin.getConfig().getString("Server-Goals.targets." + key).replaceAll("_", " "))
                                    .replaceAll("%raised%", "" + plugin.getConfig().getInt("Server-Goals.targets." + key + ".data.raised"))
                                    .replaceAll("%goal%", "" + plugin.getConfig().getInt("Server-Goals.targets." + key + ".goal"))));
                }
                return true;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("contribute") || args[0].equalsIgnoreCase("pay")) {
                    for (String key : plugin.getConfig().getConfigurationSection("Server-Goals.targets").getKeys(false)) {
                        if (args[1].equalsIgnoreCase(plugin.getConfig().getString("Server-Goals.targets." + key))) {
                            if (plugin.getConfig().getBoolean("Server-Goals.targets." + key + ".data.completed")) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("goal-already-reached")));
                                return true;
                            }
                            double amount;
                            try {
                                amount = Double.parseDouble(args[2]);
                            } catch (Exception e) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                                return true;
                            }
                            if (amount < 0) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                                return true;
                            }

                            if (!plugin.Economy.hasAccount(player)) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("account-error")
                                        .replaceAll("%player%", player.getName())
                                        .replaceAll("%target%", player.getName())));
                                return true;
                            }
                            if (!plugin.Economy.hasEnoughCash(player, amount)) {
                                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                                return true;
                            }

                            if (plugin.getConfig().getInt("Server-Goals.targets." + key + ".data.raised") + amount > plugin.getConfig().getInt("Server-Goals.targets." + key + ".goal")) {
                                amount = (plugin.getConfig().getInt("Server-Goals.targets." + key + ".data.raised") + amount) - plugin.getConfig().getInt("Server-Goals.targets." + key + ".goal");
                            }

                            if (plugin.getConfig().getInt("Server-Goals.targets." + key + ".data.raised") == plugin.getConfig().getInt("Server-Goals.targets." + key + ".goal")) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("goal-already-reached")));
                                return true;
                            }

                            plugin.Economy.removeAccount(player, amount);
                            plugin.getConfig().set("Server-Goals.targets." + key + ".data.raised", plugin.getConfig().getInt("Server-Goals.targets." + key + ".data.raised") + amount);

                            if (!plugin.getConfig().getStringList("Server-Goals.targets." + key + ".data.contributors").contains(player.getUniqueId().toString())) {
                                plugin.getConfig().getStringList("Server-Goals.targets." + key + ".data.contributors").add(player.getUniqueId().toString());
                            }
                            plugin.saveDefaultConfig();

                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("current-goal")
                                            .replaceAll("%name%", plugin.getConfig().getString("Server-Goals.targets." + key).replaceAll("_", " "))
                                            .replaceAll("%raised%", "" + plugin.getConfig().getInt("Server-Goals.targets." + key + ".data.raised"))
                                            .replaceAll("%goal%", "" + plugin.getConfig().getInt("Server-Goals.targets." + key + ".goal"))));

                            if (plugin.getConfig().getInt("Server-Goals.targets." + key + ".data.raised") == plugin.getConfig().getInt("Server-Goals.targets." + key + ".goal")) {

                                List<UUID> rewardContributors = new ArrayList<>();
                                for (String uuid : plugin.getConfig().getStringList("Server-Goals.targets." + key + ".data.contributors")) {
                                    rewardContributors.add(UUID.fromString(uuid));
                                }

                                System.out.println(rewardContributors);

                                for (UUID uuid : rewardContributors) {
                                    for (String line : plugin.getConfig().getStringList("Server-Goals.targets." + key + ".rewards")) {
                                        if (plugin.getConfig().getString("Server-Goals.targets." + key + ".rewards." + line).contains("%econ%")) {
                                            line.replaceAll("%econ%", "");
                                            plugin.Economy.addAccount(Bukkit.getOfflinePlayer(uuid), Double.parseDouble(line));
                                        }
                                        if (plugin.getConfig().getString("Server-Goals.targets." + key + ".rewards." + line).contains("%message%")) {
                                            line.replaceAll("%message%", "");
                                            if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                                                Bukkit.getPlayer(uuid).sendMessage(Utils.chatColor(line));
                                            }
                                        }
                                        if (plugin.getConfig().getString("Server-Goals.targets." + key + ".rewards." + line).contains("%item%")) {
                                            line.replaceAll("%item%", "");
                                            if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                                                ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Server-Goals.targets." + key + ".rewards." + line).split(" ")[0].toUpperCase()));
                                                item.setAmount(Integer.parseInt(plugin.getConfig().getString("Server-Goals.targets." + key + ".rewards." + line).split(" ")[1]));
                                            }
                                        }
                                        if (plugin.getConfig().getString("Server-Goals.targets." + key + ".rewards." + line).contains("%command%")) {
                                            line.replaceAll("%command%", "");
                                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), line);
                                        }
                                    }
                                }

                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("goal-reached")));
                            }


                        }
                    }
                }
            }
        }
        return false;
    }

}
