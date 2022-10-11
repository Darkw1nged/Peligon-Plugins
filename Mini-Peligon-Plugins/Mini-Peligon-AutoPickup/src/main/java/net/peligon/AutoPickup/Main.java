package net.peligon.AutoPickup;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        log("[Peligon Mini] AutoPickup has been enabled.");
    }

    public void onDisable() {
        log("[Peligon Mini] AutoPickup has been disabled.");
    }

    public boolean hasSpace(Player player, ItemStack targetItem) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                    targetItem.hasItemMeta() && targetItem.getItemMeta().hasDisplayName()) {
                if (item.getItemMeta().getDisplayName().equals(targetItem.getItemMeta().getDisplayName())) {
                    if (item.getType() == targetItem.getType()) {
                        if (item.getAmount() != item.getMaxStackSize()) {
                            item.setAmount(item.getAmount() + targetItem.getAmount());
                            return true;
                        }
                    }
                }
            } else {
                if (item.getType() == targetItem.getType()) {
                    if (item.getAmount() != item.getMaxStackSize()) {
                        item.setAmount(item.getAmount() + targetItem.getAmount());
                        return true;
                    }
                }
            }
        }
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(targetItem);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        for (ItemStack drop : block.getDrops(player.getInventory().getItemInMainHand())) {
            if (player.hasPermission("Peligon.AutoPickup.use")) {
                if (!hasSpace(player, drop)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-space")));
                    continue;
                }
                drop.setType(Material.AIR);
            }
        }
    }

    private static void log(String message) { System.out.println(message); }

}
