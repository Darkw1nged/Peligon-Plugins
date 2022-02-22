package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encRavenous implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity cause = event.getDamager();
        if (cause instanceof Player) {
            Entity entity = event.getEntity();
            Player target = (Player) event.getDamager();
            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (target.getInventory().getItemInMainHand() == null) return;
                if (!target.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.RAVENOUS)) return;
                if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) return;

                double chance = (Math.random() * 100);

                if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.RAVENOUS) == 1  && chance >= 80) {
                    if (player.getFoodLevel() >= 18) {
                        player.setFoodLevel(20);
                        return;
                    }
                    player.setFoodLevel(player.getFoodLevel() + 2);
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.RAVENOUS) == 2 && chance >= 70) {
                    if (player.getFoodLevel() >= 16) {
                        player.setFoodLevel(20);
                        return;
                    }
                    player.setFoodLevel(player.getFoodLevel() + 4);
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.RAVENOUS) == 3 && chance >= 60) {
                    if (player.getFoodLevel() >= 14) {
                        player.setFoodLevel(20);
                        return;
                    }
                    player.setFoodLevel(player.getFoodLevel() + 6);
                }  else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.RAVENOUS) == 4 && chance >= 50) {
                    if (player.getFoodLevel() >= 12) {
                        player.setFoodLevel(20);
                        return;
                    }
                    player.setFoodLevel(player.getFoodLevel() + 8);
                }
            }
        }
    }

}
