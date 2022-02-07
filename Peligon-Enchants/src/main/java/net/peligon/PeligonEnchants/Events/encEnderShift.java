package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encEnderShift implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        Player target = (Player) event.getDamager();

        if (target.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().hasItemMeta() && player.getHealth() - event.getDamage() <= 4) {
            if (player.getInventory().getHelmet().getItemMeta().hasEnchant(CustomEnchants.ENDERSHIFT)) {
                if (player.getInventory().getHelmet().getItemMeta().getEnchantLevel(CustomEnchants.ENDERSHIFT) == 1) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 8, 1, true));
                } else if (player.getInventory().getHelmet().getItemMeta().getEnchantLevel(CustomEnchants.ENDERSHIFT) == 2) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 8, 2, true));
                } else if (player.getInventory().getHelmet().getItemMeta().getEnchantLevel(CustomEnchants.ENDERSHIFT) == 3) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 8, 3, true));
                }
            }
        }

        if (player.getInventory().getBoots() != null && player.getInventory().getBoots().hasItemMeta() && player.getHealth() - event.getDamage() <= 4) {
            if (player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.ENDERSHIFT)) {
                if (player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.ENDERSHIFT) == 1) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 8, 1, true));
                } else if (player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.ENDERSHIFT) == 2) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 8, 2, true));
                } else if (player.getInventory().getBoots().getItemMeta().getEnchantLevel(CustomEnchants.ENDERSHIFT) == 3) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 8, 3, true));
                }
            }
        }

    }

}
