package net.peligon.PeligonEnchants.listeners;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class encOxygenate implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) return;

        if (player.getEyeLocation().getBlock().getType() == Material.WATER) {
            if (item.getItemMeta().hasEnchant(CustomEnchants.OXYGENATE)) {
                if (item.getItemMeta().getEnchantLevel(CustomEnchants.OXYGENATE) == 1) {
                    player.setRemainingAir(player.getRemainingAir() + 60);
                } else if (item.getItemMeta().getEnchantLevel(CustomEnchants.OXYGENATE) == 2) {
                    player.setRemainingAir(player.getRemainingAir() + 120);
                } else if (item.getItemMeta().getEnchantLevel(CustomEnchants.OXYGENATE) == 3) {
                    player.setRemainingAir(player.getRemainingAir() + 180);
                }
            }
        }
    }

}
