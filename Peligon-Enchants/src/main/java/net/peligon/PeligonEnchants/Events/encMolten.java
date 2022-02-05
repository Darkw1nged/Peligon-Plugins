package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class encMolten implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        Player target = (Player) event.getDamager();

        if (target.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        double chance = (Math.random() * 100) - 8;
        int level = 0;

        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getItemMeta().hasEnchant(CustomEnchants.MOLTEN)) {
            level = player.getInventory().getHelmet().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
            if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                level =  player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
                if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                    level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
                    if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                        level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
                    }
                } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
                }
            } else if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
                if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
                }
            } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
            }
        } else if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasEnchant(CustomEnchants.MOLTEN)) {
            level = player.getInventory().getChestplate().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
            if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
                if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                    level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
                }
            } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
            }
        } else if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
            level =  player.getInventory().getLeggings().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
            if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
                level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
            }
        } else if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.MOLTEN) && player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN) > level) {
            level =  player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.MOLTEN);
        }

        if (level == 1 && chance >= 80) {
            target.setFireTicks(500);
        } else if (level== 2 && chance >= 70) {
            target.setFireTicks(500);
        } else if (level == 3 && chance >= 60) {
            target.setFireTicks(500);
        } else if (level == 4 && chance >= 50) {
            target.setFireTicks(500);
        }
    }

}
