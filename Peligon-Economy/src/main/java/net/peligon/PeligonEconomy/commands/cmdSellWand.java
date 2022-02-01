package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class cmdSellWand implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sellwand")) {
            if (args.length < 1) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("sellwand-usage")));
                return true;
            }
            if (sender.hasPermission("Peligon.Economy.Sellwand") || sender.hasPermission("Peligon.Economy.*")) {
                int amount;
                try {
                    amount = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                    return true;
                }

                ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Items.sell-wand.item").toUpperCase()));
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("Items.sell-wand.name")));

                List<String> lore = new ArrayList<>();
                for (String value : plugin.getConfig().getStringList("Items.sell-wand.lore")) {
                    lore.add(Utils.chatColor(value)
                            .replaceAll("%uses%", "" + amount));
                }
                meta.setLore(lore);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(meta);

                if (!(sender instanceof Player)) {
                    if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")
                                    .replaceAll("%player%", args[1])
                                    .replaceAll("%target%", args[1])));
                            return true;
                        }
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("sellwand-given")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%uses%", "" + amount)));
                        target.getInventory().addItem(item);
                    } else {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    }
                }
                Player player = (Player) sender;
                if (args.length == 2) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found").replaceAll("%player%", args[0])));
                        return true;
                    }
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("sellwand-given")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%uses%", "" + amount)));
                    target.getInventory().addItem(item);
                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("sellwand-given")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%uses%", "" + amount)));
                    // ---- [ Adding item to inventory ] ----
                    player.getInventory().addItem(item);
                }


            } else sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
        }
        return false;
    }

}
