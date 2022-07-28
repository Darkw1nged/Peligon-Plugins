package net.peligon.Autosmelt.Listeners;

import net.peligon.Autosmelt.Main;
import net.peligon.Autosmelt.Utilities.Lists.Permissions;
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
        if (player.hasPermission(Permissions.use_permission.getPermission()) || player.hasPermission(Permissions.global_permission.getPermission())) {
            for (ItemStack drop : block.getDrops(player.getInventory().getItemInMainHand())) {
                switch (drop.getType()) {
                    case RAW_COPPER:
                        drop.setType(Material.COPPER_INGOT);
                        break;
                    case RAW_COPPER_BLOCK:
                        drop.setType(Material.COPPER_BLOCK);
                        break;
                    case RAW_IRON:
                        drop.setType(Material.IRON_INGOT);
                        break;
                    case RAW_IRON_BLOCK:
                        drop.setType(Material.IRON_BLOCK);
                        break;
                    case RAW_GOLD:
                        drop.setType(Material.GOLD_INGOT);
                        break;
                    case RAW_GOLD_BLOCK:
                        drop.setType(Material.GOLD_BLOCK);
                        break;
                }

                if (plugin.getConfig().getBoolean("auto-pickup", false)) {
                    if (player.hasPermission(Permissions.autopickup_permission.getPermission()) || player.hasPermission(Permissions.global_permission.getPermission())) {
                        if (!plugin.playerUtils.hasSpace(player, drop)) {
                            player.sendMessage(plugin.utils.chatColor(plugin.fileMessage.getConfig().getString("no-space")));
                            continue;
                        }
                        drop.setType(Material.AIR);
                    }
                }
            }
        }
    }

}
