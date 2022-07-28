package net.peligon.PeligonEnchants.listeners;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encVoodoo implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        Player target = (Player) event.getDamager();

        if (target.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        double chance = (Math.random() * 100) - 8;
        int level = 0;

        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getItemMeta().hasEnchant(CustomEnchants.VOODOO)) {
            level = player.getInventory().getHelmet().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
            if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                level =  player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
                if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                    level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
                    if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                        level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
                    }
                } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
                }
            } else if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
                if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
                }
            } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
            }
        } else if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasEnchant(CustomEnchants.VOODOO)) {
            level = player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
            if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
                if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
                }
            } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
            }
        } else if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
            level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
            if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
            }
        } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.VOODOO) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO) > level) {
            level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.VOODOO);
        }

        if (level == 1 && chance >= 85) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 1, true));
        } else if (level == 2 && chance >= 75) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 1, true));
        } else if (level == 3 && chance >= 65) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 2, true));
        } else if (level == 4 && chance >= 55) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 2, true));
        } else if (level == 5 && chance >= 45) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 3, true));
        } else if (level == 6 && chance >= 35) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 8, 3, true));
        }
    }

}
