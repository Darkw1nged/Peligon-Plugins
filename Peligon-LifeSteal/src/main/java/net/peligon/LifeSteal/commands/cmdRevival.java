package net.peligon.LifeSteal.commands;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class cmdRevival implements CommandExecutor, TabCompleter {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("revival")) {
            if (sender.hasPermission("Peligon.LifeSteal.revival") || sender.hasPermission("peligon.LifeSteal.*")) {
                if (args.length < 3) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("revival-give-usage")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("give")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                        return true;
                    }

                    int amount = 1;
                    if (args.length >= 4) {
                        try {
                            amount = Integer.parseInt(args[3]);
                        } catch (Exception e) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                            return true;
                        }
                        if (amount < 1) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                            return true;
                        }
                    }

                    String name = args[2];
                    if (name.equalsIgnoreCase("tier1")) {
                        ItemStack item = plugin.customItems.revivalBeacons.revivalBeaconTier1();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("revival-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%item%",item.getItemMeta().getDisplayName())));
                        }
                    } else if (name.equalsIgnoreCase("tier2")) {
                        ItemStack item = plugin.customItems.revivalBeacons.revivalBeaconTier2();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("revival-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%item%", item.getItemMeta().getDisplayName())));
                        }
                    } else if (name.equalsIgnoreCase("tier3")) {
                        ItemStack item = plugin.customItems.revivalBeacons.revivalBeaconTier3();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("revival-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%item%", item.getItemMeta().getDisplayName())));
                        }
                    } else if (name.equalsIgnoreCase("special")) {
                        ItemStack item = plugin.customItems.revivalBeacons.revivalBeaconTierSpecial();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("revival-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%item%", item.getItemMeta().getDisplayName())));
                        }
                    } else {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-revival-type")));
                    }
                }
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("revival")) {
            if (args.length >= 3) {
                if (args[0].equalsIgnoreCase("give")) {
                    list.add("tier1");
                    list.add("tier2");
                    list.add("tier3");
                    list.add("special");
                }
            }
        }
        return list;
    }

}
