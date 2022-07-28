package net.peligon.PeligonEnchants.listeners;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class encVampire implements Listener {

    private Map<UUID, Long> requestTimeout = new HashMap<>();
    private final int timeout = 2;

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity cause = event.getDamager();
        if (cause instanceof Player) {
            Entity entity = event.getEntity();
            Player target = (Player) event.getDamager();
            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (target.getInventory().getItemInMainHand() == null) return;
                if (!target.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.VAMPIRE)) return;
                if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) return;

                double chance = (Math.random() * 100);

                if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.VAMPIRE) == 1  && chance >= 80) {
                    if (!requestTimeout.isEmpty() && requestTimeout.containsKey(player.getUniqueId())) {
                        long timeLeft = ((requestTimeout.get(player.getUniqueId()) / 1000) + (timeout * (long) 3)) - (System.currentTimeMillis() / 1000);
                        if (timeLeft > 0) return;
                        if (player.getHealth() > 18.0D) return;

                        player.setHealth(player.getHealth() + 2.0D);
                        requestTimeout.remove(player.getUniqueId());
                    } else {
                        requestTimeout.put(player.getUniqueId(), System.currentTimeMillis());
                    }
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.VAMPIRE) == 2 && chance >= 70) {
                    if (!requestTimeout.isEmpty() && requestTimeout.containsKey(player.getUniqueId())) {
                        long timeLeft = ((requestTimeout.get(player.getUniqueId()) / 1000) + (timeout * (long) 2)) - (System.currentTimeMillis() / 1000);
                        if (timeLeft > 0) return;
                        if (player.getHealth() >= 16.0D) {
                            player.setHealth(20.0D);
                        } else {
                            player.setHealth(player.getHealth() + 4.0D);
                        }
                        requestTimeout.remove(player.getUniqueId());
                    } else {
                        requestTimeout.put(player.getUniqueId(), System.currentTimeMillis());
                    }
                } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.VAMPIRE) == 3 && chance >= 60) {
                    if (!requestTimeout.isEmpty() && requestTimeout.containsKey(player.getUniqueId())) {
                        long timeLeft = ((requestTimeout.get(player.getUniqueId()) / 1000) + timeout) - (System.currentTimeMillis() / 1000);
                        if (timeLeft > 0) return;
                        if (player.getHealth() >= 14.0D) {
                            player.setHealth(20.0D);
                        } else {
                            player.setHealth(player.getHealth() + 6.0D);
                        }
                        requestTimeout.remove(player.getUniqueId());
                    } else {
                        requestTimeout.put(player.getUniqueId(), System.currentTimeMillis());
                    }
                }
            }
        }
    }

}
