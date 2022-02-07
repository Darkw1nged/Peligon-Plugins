package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encBlind implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity cause = event.getDamager();
        if (cause instanceof Arrow) {
            Arrow arrow = (Arrow) cause;
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player playerHit = (Player) entity;
                if (arrow.getShooter() instanceof Player) {
                    Player shooter = (Player) arrow.getShooter();
                    if (shooter.getInventory().getItemInMainHand() == null) return;
                    if (!shooter.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.BLIND)) return;
                    if (shooter.getGameMode() == GameMode.CREATIVE || shooter.getGameMode() == GameMode.SPECTATOR) return;

                    double chance = (Math.random() * 100);

                    if (shooter.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 1  && chance >= 80) {
                        playerHit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 8, 10, true));
                    } else if (shooter.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 2 && chance >= 70) {
                        playerHit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 8, 10, true));
                    } else if (shooter.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 3 && chance >= 60) {
                        playerHit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 8, 10, true));
                    }
                }
            }
        } else if (cause instanceof Player) {
            Entity entity = event.getEntity();
            Player target = (Player) event.getDamager();
            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (target.getInventory().getItemInMainHand() == null) return;
                if (!target.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.BLIND)) return;
                if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) return;

                double chance = (Math.random() * 100);

                if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 1  && chance >= 80) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 8, 10, true));
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 2 && chance >= 70) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 8, 10, true));
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 3 && chance >= 60) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 8, 10, true));
                }
            }
        }
    }

}
