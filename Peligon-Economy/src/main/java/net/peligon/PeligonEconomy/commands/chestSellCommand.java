package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class chestSellCommand implements CommandExecutor {

    // Getting the instance of the main class.
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chestsell")) {
            // Check if the command is disabled.
            if (plugin.getConfig().getStringList("disabled-commands").contains("chestsell")) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-disabled-command")));
                return true;
            }

            // Check if console sent the command.
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("console")));
                return true;
            }
            // We can safely assume that the sender is a player.
            Player player = (Player) sender;
            // Check if the player has permission to use the command.
            if (player.hasPermission("Peligon.Economy.Sellchest") || player.hasPermission("Peligon.Economy.*")) {
                // Get the block the player is looking at.
                Block block = player.getTargetBlock((Set<Material>) null, 5);
                // Check if the block is a chest.
                if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.TRAPPED_CHEST)) {
                    // Get the chest.
                    Chest chest = (Chest) block.getState();
                    // Get the value of the chest.
                    double amount = Utils.getInventoryValue(chest.getInventory());
                    // Check if the chest has any items to sell.
                    if (amount <= 0) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-items-to-sell")));
                        return true;
                    }

                    // Add the amount to the players account.
                    playerUtils.setCash(player, playerUtils.getCash(player) + amount);

                    // Send the player a message.
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                            plugin.languageFile.getConfig().getString("sold-items"), amount));
                } else {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-chest")));
                    return true;
                }
            } else {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                return true;
            }

        }
        return false;
    }

}