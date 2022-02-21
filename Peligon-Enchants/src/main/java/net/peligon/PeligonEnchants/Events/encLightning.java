package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class encLightning implements Listener {

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
                    if (!shooter.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.LIGHTNING)) return;
                    if (shooter.getGameMode() == GameMode.CREATIVE || shooter.getGameMode() == GameMode.SPECTATOR) return;

                    double chance = (Math.random() * 100);

                    if (shooter.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.LIGHTNING) == 1  && chance >= 80) {
                        playerHit.getWorld().strikeLightning(playerHit.getLocation());
                    } else if (shooter.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.LIGHTNING) == 2 && chance >= 70) {
                        playerHit.getWorld().strikeLightning(playerHit.getLocation());
                    } else if (shooter.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.LIGHTNING) == 3 && chance >= 60) {
                        playerHit.getWorld().strikeLightning(playerHit.getLocation());
                        playerHit.getWorld().strikeLightning(playerHit.getLocation());
                    }
                }
            }

        }
    }

}
