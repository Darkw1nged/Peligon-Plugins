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

public class cmdAutoSmelt implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private final List<UUID> cache = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("autosmelt")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player)sender;
            if (player.hasPermission("Peligon.Prison.AutoSmelt") || player.hasPermission("Peligon.Prison.*")) {
                UUID uuid = player.getUniqueId();
                if (cache.contains(uuid)) {
                    cache.remove(uuid);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("autosmelt-off")));
                } else {
                    cache.add(uuid);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("autosmelt-on")));
                }
                Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    if (!cache.contains(uuid)) return;
                    for (ItemStack item : player.getInventory().getContents()) {
                        switch (item.getType()) {
                            case COAL_ORE:
                            case DEEPSLATE_COAL_ORE:
                                item.setType(Material.COAL);
                                break;
                            case RAW_COPPER:
                                item.setType(Material.COPPER_INGOT);
                                break;
                            case RAW_COPPER_BLOCK:
                                item.setType(Material.COPPER_BLOCK);
                                break;
                            case RAW_GOLD:
                                item.setType(Material.GOLD_INGOT);
                                break;
                            case RAW_GOLD_BLOCK:
                                item.setType(Material.GOLD_BLOCK);
                                break;
                            case RAW_IRON:
                                item.setType(Material.IRON_INGOT);
                                break;
                            case RAW_IRON_BLOCK:
                                item.setType(Material.IRON_BLOCK);
                                break;
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
