package net.peligon.LifeSteal.commands;

import com.mysql.cj.protocol.x.XpluginStatementCommand;
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

public class cmdHeart implements CommandExecutor, TabCompleter {

    // Normal: http://textures.minecraft.net/texture/cc739a48276973f04941ddf20cca98de44c82a414dd5d733417fbc27774753a0
    // Golden:
    // Exotic:
    // Mythical:

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("heart")) {
            if (sender.hasPermission("Peligon.LifeSteal.Heart") || sender.hasPermission("peligon.LifeSteal.*")) {
                if (args.length < 3) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("heart-give-usage")));
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
                    if (name.equalsIgnoreCase("heart") || name.equalsIgnoreCase("normal")) {
                        ItemStack item = plugin.customItems.hearts.getHeart();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("heart-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%type%",item.getItemMeta().getDisplayName())));
                        }
                    } else if (name.equalsIgnoreCase("golden") || name.equalsIgnoreCase("gold")) {
                        ItemStack item = plugin.customItems.hearts.getGoldenHeart();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("heart-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%type%", item.getItemMeta().getDisplayName())));
                        }
                    } else if (name.equalsIgnoreCase("exotic") || name.equalsIgnoreCase("exot")) {
                        ItemStack item = plugin.customItems.hearts.getExoticHeart();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("heart-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%type%", item.getItemMeta().getDisplayName())));
                        }
                    } else if (name.equalsIgnoreCase("mythical") || name.equalsIgnoreCase("myth")) {
                        ItemStack item = plugin.customItems.hearts.getMythicHeart();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("heart-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%type%", item.getItemMeta().getDisplayName())));
                        }
                    } else if (name.equalsIgnoreCase("scarce")) {
                        ItemStack item = plugin.customItems.sacrificialHearts.getScarceHeart();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("heart-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%type%", item.getItemMeta().getDisplayName())));
                        }
                    } else if (name.equalsIgnoreCase("shock")) {
                        ItemStack item = plugin.customItems.sacrificialHearts.getShockHeart();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("heart-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%type%", item.getItemMeta().getDisplayName())));
                        }
                    } else if (name.equalsIgnoreCase("rage")) {
                        ItemStack item = plugin.customItems.sacrificialHearts.getRageHeart();
                        item.setAmount(amount);

                        if (Utils.hasSpace(player, item)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("heart-give-success")
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%type%", item.getItemMeta().getDisplayName())));
                        }
                    } else {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-heart-type")));
                    }
                }
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("heart")) {
            if (args.length >= 3) {
                if (args[0].equalsIgnoreCase("give")) {
                    list.add("heart");
                    list.add("golden");
                    list.add("exotic");
                    list.add("mythical");
                    list.add("scarce");
                    list.add("shock");
                    list.add("rage");
                }
            }
        }
        return list;
    }
}
