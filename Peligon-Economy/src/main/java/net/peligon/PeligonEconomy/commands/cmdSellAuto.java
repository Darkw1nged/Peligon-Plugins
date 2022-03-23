package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class cmdSellAuto implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private final List<UUID> cache = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("autosell")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player)sender;
            if (player.hasPermission("Peligon.Economy.Autosell") || player.hasPermission("Peligon.Economy.*")) {
                UUID uuid = player.getUniqueId();
                if (cache.contains(uuid)) {
                    cache.remove(uuid);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("autosell-off")));
                } else {
                    cache.add(uuid);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("autosell-on")));
                }
                Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    if (!cache.contains(uuid)) return;
                    Double amount = getSellableItems(player);
                    if (amount <= 0) return;
                    // message
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("sold-items"), amount));
                    plugin.Economy.addAccount(player, amount);

                }, 0L, 20L);
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                return true;
            }
        }
        return false;
    }

    public Double getSellableItems(Player player) {
        double amount = 0;
        for (ItemStack item : player.getInventory()) {
            if (item != null) {
                if (plugin.fileWorth.getConfig().contains("worth." + item.getType().name().toUpperCase())) {
                    amount += plugin.fileWorth.getConfig().getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
                    item.setAmount(0);
                }
            }
        }
        return amount;
    }

}
