package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.Main;
import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class encAutoSmelt implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("Enchantments.Autosmelt.enabled", true)) {
            Player player = event.getPlayer();
            Block block = event.getBlock();

            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) return;
            if (!player.getInventory().getItemInMainHand().hasItemMeta()) return;
            if (!player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.AUTOSMELT)) return;
            if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
            if (event.getBlock().getState() instanceof Container) return;

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
