package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import net.peligon.PeligonEnchants.libaries.Utils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encPoison implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity cause = event.getDamager();
        if (cause instanceof Player) {
            Entity entity = event.getEntity();
            Player target = (Player) event.getDamager();
            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (target.getInventory().getItemInMainHand() == null) return;
                if (!target.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.POISON)) return;
                if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) return;

                double chance = (Math.random() * 100);

                if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.POISON) == 1  && chance >= 80) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 8, 1, true));
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.POISON) == 2 && chance >= 70) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 8, 1, true));
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.POISON) == 3 && chance >= 60) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 8, 2, true));
                }
            }
        }
    }

}
