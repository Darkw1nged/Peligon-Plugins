package net.peligon.PeligonPrison.listeners;

import net.peligon.PeligonPrison.libaries.Utils;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class PickupEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (Utils.autoPickup.contains(player.getUniqueId())) {
            if (event.getBlock().getState() instanceof Container) return;

            Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
            if (drops.isEmpty()) return;

            if (Utils.hasSpace(player, drops.iterator().next())) {
                event.setDropItems(false);
            }
        }
    }

}
