package net.peligon.PeligonEnchants.listeners;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encTrap implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity cause = event.getDamager();
        if (cause instanceof Player) {
            Entity entity = event.getEntity();
            Player target = (Player) event.getDamager();
            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (target.getInventory().getItemInMainHand() == null) return;
                if (!target.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TRAP)) return;
                if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) return;

                double chance = (Math.random() * 100);

                if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.TRAP) == 1  && chance >= 80) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 4, true));
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.TRAP) == 2 && chance >= 70) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 4, true));
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.TRAP) == 3 && chance >= 60) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 5, true));
                }
            }
        }
    }

}
