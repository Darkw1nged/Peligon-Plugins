package net.peligon.PeligonPrison.commands;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class cmdAutoBlock implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private final List<UUID> cache = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("autoblock")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player)sender;
            if (player.hasPermission("Peligon.Prison.AutoBlock") || player.hasPermission("Peligon.Prison.*")) {
                UUID uuid = player.getUniqueId();
                if (cache.contains(uuid)) {
                    cache.remove(uuid);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("autoblock-off")));
                } else {
                    cache.add(uuid);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("autoblock-on")));
                }
                Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    if (!cache.contains(uuid)) return;
                    for (ItemStack item : player.getInventory().getContents()) {
                        if (item.getType().equals(Material.COAL) && item.getAmount() >= 9) {
                            item.setAmount(item.getAmount() - 9);
                            ItemStack block = new ItemStack(Material.COAL_BLOCK);
                            player.getInventory().addItem(block);
                        } else if (item.getType().equals(Material.COPPER_INGOT) && item.getAmount() >= 9) {
                            item.setAmount(item.getAmount() - 9);
                            ItemStack block = new ItemStack(Material.COPPER_BLOCK);
                            player.getInventory().addItem(block);
                        } else if (item.getType().equals(Material.IRON_INGOT) && item.getAmount() >= 9) {
                            item.setAmount(item.getAmount() - 9);
                            ItemStack block = new ItemStack(Material.IRON_BLOCK);
                            player.getInventory().addItem(block);
                        } else if (item.getType().equals(Material.GOLD_INGOT) && item.getAmount() >= 9) {
                            item.setAmount(item.getAmount() - 9);
                            ItemStack block = new ItemStack(Material.GOLD_BLOCK);
                            player.getInventory().addItem(block);
                        } else if (item.getType().equals(Material.DIAMOND) && item.getAmount() >= 9) {
                            item.setAmount(item.getAmount() - 9);
                            ItemStack block = new ItemStack(Material.DIAMOND_BLOCK);
                            player.getInventory().addItem(block);
                        } else if (item.getType().equals(Material.EMERALD) && item.getAmount() >= 9) {
                            item.setAmount(item.getAmount() - 9);
                            ItemStack block = new ItemStack(Material.EMERALD_BLOCK);
                            player.getInventory().addItem(block);
                        } else if (item.getType().equals(Material.LAPIS_LAZULI) && item.getAmount() >= 9) {
                            item.setAmount(item.getAmount() - 9);
                            ItemStack block = new ItemStack(Material.LAPIS_BLOCK);
                            player.getInventory().addItem(block);
                        } else if (item.getType().equals(Material.REDSTONE) && item.getAmount() >= 9) {
                            item.setAmount(item.getAmount() - 9);
                            ItemStack block = new ItemStack(Material.REDSTONE_BLOCK);
                            player.getInventory().addItem(block);
                        } else if (item.getType().equals(Material.NETHERITE_INGOT) && item.getAmount() >= 9) {
                            item.setAmount(item.getAmount() - 9);
                            ItemStack block = new ItemStack(Material.NETHERITE_BLOCK);
                            player.getInventory().addItem(block);
                        }
                    }
                }, 0L, 20L);
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                return true;
            }
        }
        return false;
    }
}
