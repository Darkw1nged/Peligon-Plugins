package net.peligon.PeligonEnchants.listeners;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encPoisoned implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent  event) {
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        Player target = (Player) event.getDamager();

        if (target.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        double chance = (Math.random() * 100) - 8;
        int level = 0;

        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getItemMeta().hasEnchant(CustomEnchants.POISONED)) {
            level = player.getInventory().getHelmet().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
            if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                level =  player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
                if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                    level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
                    if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                        level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
                    }
                } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
                }
            } else if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
                if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
                }
            } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
            }
        } else if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasEnchant(CustomEnchants.POISONED)) {
            level = player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
            if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
                if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
                }
            } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
            }
        } else if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
            level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
            if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
            }
        } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.POISONED) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED) > level) {
            level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.POISONED);
        }

        if (level == 1 && chance >= 80) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 8, 1, true));
        } else if (level== 2 && chance >= 70) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 8, 1, true));
        } else if (level == 3 && chance >= 60) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 8, 2, true));
        } else if (level == 4 && chance >= 50) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 8, 3, true));
        }
    }

}
