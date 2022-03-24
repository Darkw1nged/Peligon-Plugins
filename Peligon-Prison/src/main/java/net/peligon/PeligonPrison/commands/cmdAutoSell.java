package net.peligon.PeligonPrison.commands;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.CustomConfig;
import net.peligon.PeligonPrison.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class cmdAutoSell implements CommandExecutor {

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
                    Plugin targetPlugin;
                    if (Bukkit.getServer().getPluginManager().getPlugin("Essentials") != null) {
                        targetPlugin = Bukkit.getServer().getPluginManager().getPlugin("Essentials");
                    } else if (Bukkit.getServer().getPluginManager().getPlugin("PeligonEconomy") != null) {
                        targetPlugin = Bukkit.getServer().getPluginManager().getPlugin("PeligonEconomy");
                    } else {
                        return;
                    }

                    if (targetPlugin == null) return;
                    CustomConfig config = new CustomConfig(targetPlugin, "worth", true);

                    double amount = 0;
                    for (int i = 0; i < player.getInventory().getSize(); i++) {
                        amount = getAmount(player, amount, i, config.getConfig());
                    }
                }, 0L, 20L);
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                return true;
            }
        }
        return false;
    }

    private double getAmount(Player player, double amount, int i, YamlConfiguration configuration) {
        try {
            ItemStack item = player.getInventory().getItem(i);
            if (configuration.contains("worth." + item.getType().name().toUpperCase())) {
                amount += configuration.getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
                item.setAmount(0);
            }
        } catch (Exception ignored) { }
        return amount;
    }

}
