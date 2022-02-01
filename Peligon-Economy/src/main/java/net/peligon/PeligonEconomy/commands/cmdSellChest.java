package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class cmdSellChest implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chestsell")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Economy.Sellchest") || player.hasPermission("Peligon.Economy.*")) {
                Set<Material> material_set = null;
                Block block = player.getTargetBlock(material_set, 5);
                if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.TRAPPED_CHEST)) {
                    Chest chest = (Chest) block.getState();
                    double amount = 0;
                    if (chest.getInventory().getSize() == 54) {
                        for (int i = 0; i <= 54; i++) {
                            amount = getAmount(chest, amount, i);
                        }
                    } else if (chest.getInventory().getSize() == 27) {
                        for (int i = 0; i <= 27; i++) {
                            amount = getAmount(chest, amount, i);
                        }
                    }
                    if (amount <= 0) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-items")));
                        return true;
                    }
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("sold-items"), amount));
                    plugin.Economy.AddAccount(player, amount);
                }
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                return true;
            }

        }
        return false;
    }

    private double getAmount(Chest chest, double amount, int i) {
        try {
            ItemStack item = chest.getInventory().getItem(i);
            if (plugin.fileWorth.getConfig().contains("worth." + item.getType().name().toUpperCase())) {
                amount += plugin.fileWorth.getConfig().getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
            }
            item.setAmount(0);
        } catch (Exception ignored) { }
        return amount;
    }
}