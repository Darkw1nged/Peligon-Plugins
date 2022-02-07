package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encHaste implements Listener {

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());

        if (item == null) {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            return;
        }

        if (item.getItemMeta().hasEnchant(CustomEnchants.HASTE)) {
            if (item.getItemMeta().getEnchantLevel(CustomEnchants.HASTE) == 1) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10000000, 0));
            } else if (item.getItemMeta().getEnchantLevel(CustomEnchants.HASTE) == 2) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10000000, 1));
            } else if (item.getItemMeta().getEnchantLevel(CustomEnchants.HASTE) == 3) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10000000, 2));
            }
        } else {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
    }

}
