package net.peligon.AutoPickup.Listeners;

import net.peligon.AutoPickup.Main;
import net.peligon.AutoPickup.Utilities.Lists.Permissions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class coreEvent implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        for (ItemStack drop : block.getDrops(player.getInventory().getItemInMainHand())) {
            if (player.hasPermission(Permissions.use_permission.getPermission())) {
                if (!plugin.playerUtils.hasSpace(player, drop)) {
                    player.sendMessage(plugin.utils.chatColor(plugin.fileMessage.getConfig().getString("no-space")));
                    continue;
                }
                drop.setType(Material.AIR);
            }
        }
    }

}