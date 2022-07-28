package net.peligon.PeligonEnchants.listeners;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class encStormcaller implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        Player target = (Player) event.getDamager();

        if (target.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        double chance = (Math.random() * 100) - 8;
        int level = 0;

        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER)) {
            level = player.getInventory().getHelmet().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
            if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                level =  player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
                if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                    level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
                    if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                        level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
                    }
                } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
                }
            } else if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
                if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
                }
            } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
            }
        } else if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER)) {
            level = player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
            if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
                if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
                }
            } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
            }
        } else if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
            level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
            if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
            }
        } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.STORMCALLER) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER) > level) {
            level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.STORMCALLER);
        }

        if (level == 1 && chance >= 80) {
            player.getWorld().strikeLightning(target.getLocation());
        } else if (level == 2 && chance >= 70) {
            player.getWorld().strikeLightning(target.getLocation());
        } else if (level == 3 && chance >= 60) {
            player.getWorld().strikeLightning(target.getLocation());
            player.getWorld().strikeLightning(target.getLocation());
        } else if (level == 4 && chance >= 50) {
            player.getWorld().strikeLightning(target.getLocation());
            player.getWorld().strikeLightning(target.getLocation());
        }
    }

}
