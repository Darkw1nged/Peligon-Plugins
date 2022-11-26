package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class withdrawCommand implements CommandExecutor {

    // Get the instance of the main class.
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("withdraw")) {
            // Check if the command is disabled.
            if (plugin.getConfig().getStringList("disabled-commands").contains("withdraw")) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-disabled-command")));
                return true;
            }

            // If sender is not a player. return a console error message.
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-console")));
                return true;
            }
            // We can safely assume the sender is a player.
            Player player = (Player) sender;

            // Check if the player has the permission to use the command.
            if (player.hasPermission("Peligon.Economy.Withdraw") || player.hasPermission("Peligon.Economy.*")) {
                // Check if all arguments are present.
                if (args.length < 1) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("usage-withdraw")));
                    return true;
                }

                try {
                    // Get the amount from the args.
                    double amount = Utils.getAbbreviation(args[0]);

                    // Check if the amount is less than 0.
                    if (amount < 0) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-negative-amount")));
                        return true;
                    }

                    // Check if the player has enough money in their bank account.
                    if (!playerUtils.hasEnoughBankBalance(player, amount)) {
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-not-enough-money")));
                        return true;
                    }

                    // Check if physical money is enabled.
                    if (plugin.getConfig().getBoolean("Storage.physical", false)) {
                        // TODO Add physical money support.
                    } else {
                        // Create a new ItemStack.
                        ItemStack item = new ItemStack(Material.valueOf(plugin.custonItemsFile.getConfig().getString("money-note.item", "PAPER").toUpperCase()));
                        // Get the item meta.
                        ItemMeta meta = item.getItemMeta();
                        // Set the display name.
                        meta.setDisplayName(Utils.chatColor(plugin.custonItemsFile.getConfig().getString("money-note.name", "&2Bank Note")));

                        // Create a new list of lore.
                        List<String> lore = new ArrayList<>();
                        // Add the lore to the list.
                        for (String line : plugin.custonItemsFile.getConfig().getStringList("money-note.lore")) {
                            lore.add(Utils.chatColor(line)
                                    .replaceAll("%amount%", Utils.format(amount))
                                    .replaceAll("%transaction%", getTransactionID())
                                    .replaceAll("%player%", player.getName())
                                    .replaceAll("display_player%", player.getDisplayName()));
                        }
                        // Set the lore.
                        meta.setLore(lore);

                        // Set the persistent data.
                        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "money-note"), PersistentDataType.STRING, "true");
                        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "transaction-id"), PersistentDataType.STRING, getTransactionID());
                        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amount"), PersistentDataType.DOUBLE, amount);
                        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "player"), PersistentDataType.STRING, player.getUniqueId().toString());

                        // Set the item meta.
                        item.setItemMeta(meta);

                        // Try and add the note to the players inventory.
                        if (Utils.hasSpace(player.getInventory(), item, 1)) {
                            // Remove the money from the players bank account.
                            playerUtils.setBankBalance(player, playerUtils.getBankBalance(player) - amount);

                            // Send player a confirmation message.
                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                    plugin.languageFile.getConfig().getString("success-withdraw"), amount));
                            return true;
                        }

                        // We can assume the player does not have enough space in their inventory.
                        // So we don't remove any money and just send them a failure message.
                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                                plugin.languageFile.getConfig().getString("failed-withdraw"), amount));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-amount")));
                    return true;
                }
            } else {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                return true;
            }
        }
        return false;
    }

    // Generate a random transaction ID.
    private String getTransactionID() {
        return UUID.randomUUID().toString().split("-")[0] + "-" + UUID.randomUUID().toString().split("-")[3];
    }

}
