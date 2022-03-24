package net.peligon.PeligonPrison.listeners;

import net.peligon.PeligonPrison.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class SmeltEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (Utils.autoSmelt.contains(player.getUniqueId())) {
            Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
            if (drops.isEmpty()) return;
            ItemStack item = drops.iterator().next();
            event.setDropItems(false);

            switch (item.getType()) {
                case RAW_COPPER:
                    player.getWorld().dropItem(block.getLocation(), new ItemStack(Material.COPPER_INGOT, item.getAmount()));
                    break;
                case RAW_IRON:
                    player.getWorld().dropItem(block.getLocation(), new ItemStack(Material.IRON_INGOT, item.getAmount()));
                    break;
                case RAW_GOLD:
                    player.getWorld().dropItem(block.getLocation(), new ItemStack(Material.GOLD_INGOT, item.getAmount()));
                    break;
            }
        }
    }

}
