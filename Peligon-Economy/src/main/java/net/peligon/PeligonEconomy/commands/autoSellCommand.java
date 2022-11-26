package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class autoSellCommand implements CommandExecutor {

    // Getting the instance of the main class.
    private final Main plugin = Main.getInstance;
    // Stores the players who have auto sell enabled
    private final List<UUID> cache = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("autosell")) {
            // Check if the command is disabled.
            if (plugin.getConfig().getStringList("disabled-commands").contains("autosell")) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-disabled-command")));
                return true;
            }

            // Check if console sent the command.
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-console")));
                return true;
            }
            // We can safely assume that the sender is a player.
            Player player = (Player) sender;
            // Check if the player has permission to use the command.
            if (player.hasPermission("Peligon.Economy.Autosell") || player.hasPermission("Peligon.Economy.*")) {
                // Enable or disable autosell
                if (cache.contains(player.getUniqueId())) {
                    cache.remove(player.getUniqueId());
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                            plugin.languageFile.getConfig().getString("autosell-disabled")));
                } else {
                    cache.add(player.getUniqueId());
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                            plugin.languageFile.getConfig().getString("autosell-enabled")));
                }

                // Start the autosell task
                Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                    if (cache.contains(player.getUniqueId())) {
                        // Getting the value of the players inventory.
                        double amount = Utils.getInventoryValue(player.getInventory());
                        // Check if the player has any items to sell.
                        if (amount <= 0) return;

                        // Add the amount to the players account.
                        playerUtils.addCash(player, amount);

                        // Send the player a message.
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("sold-items"), amount));
                    }
                }, 0, 20);
            } else {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                return true;
            }
        }
        return false;
    }

}
