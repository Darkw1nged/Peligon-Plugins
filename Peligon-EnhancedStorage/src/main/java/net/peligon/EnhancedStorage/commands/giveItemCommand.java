package net.peligon.EnhancedStorage.commands;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.CustomItems;
import net.peligon.EnhancedStorage.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class giveItemCommand implements CommandExecutor, TabCompleter {

    // Getting instance of main class.
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("giveitem")) {
            // Checking if sender has permission to use command.
            if (sender.hasPermission("Peligon.EnhancedStorage.GiveItem") || sender.hasPermission("Peligon.EnhancedStorage.*")) {
                // Check if all arguments are present.
                if (args.length < 2) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("usage-giveitem")));
                    return true;
                }

                // Getting player from argument.
                Player target = Bukkit.getPlayer(args[0]);
                // Checking if player is null.
                if (target == null) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-found")));
                    return true;
                }

                // Checking if item is valid.
                ItemStack item = getItem(args[1]);
                if (item == null) {
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-item")));
                    return true;
                }

                // Checking if amount is valid.
                int amount = 1;
                if (args.length >= 3) {
                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-amount")));
                        return true;
                    }
                }

                // Checking if player has space in inventory.
                if (Utils.hasSpace(target.getInventory(), item, amount)) {
                    // Sending a success message to sender.
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                            plugin.languageFile.getConfig().getString("giveitem-success")
                                    .replaceAll("%player%", target.getName())
                                    .replaceAll("%item%", item.getItemMeta().getDisplayName())
                                    .replaceAll("%amount%", String.valueOf(amount))));
                } else {
                    // Sending a failure message to sender.
                    sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                            plugin.languageFile.getConfig().getString("giveitem-failure")
                                    .replaceAll("%player%", target.getName())
                                    .replaceAll("%item%", item.getItemMeta().getDisplayName())
                                    .replaceAll("%amount%", String.valueOf(amount))));
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return null;
        if (sender.hasPermission("Peligon.EnhancedStorage.GiveItem") || sender.hasPermission("Peligon.EnhancedStorage.*")) {
            if (args.length == 2) {
                return Arrays.asList(
                        "small",
                        "medium",
                        "large",
                        "massive",
                        "adventure:beginner",
                        "adventure:experienced",
                        "miner"
                );
            } else {
                return Arrays.asList(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
            }
        }
        return null;
    }

    // Converting string to itemstack.
    private ItemStack getItem(String potentialItem) {
        if (potentialItem.equalsIgnoreCase("small")) {
            return CustomItems.regularSmallBackpack();
        } else if (potentialItem.equalsIgnoreCase("medium")) {
            return CustomItems.regularMediumBackpack();
        } else if (potentialItem.equalsIgnoreCase("large")) {
            return CustomItems.regularLargeBackpack();
        } else if (potentialItem.equalsIgnoreCase("massive")) {
            return CustomItems.regularMassiveBackpack();
        } else if (potentialItem.equalsIgnoreCase("adventure:beginner")) {
            return CustomItems.adventureBeginnerBackpack();
        } else if (potentialItem.equalsIgnoreCase("adventure:experienced")) {
            return CustomItems.adventureExperiencedBackpack();
        } else if (potentialItem.equalsIgnoreCase("miner")) {
            return CustomItems.minersBackpack();
        } else {
            return null;
        }
    }

}
