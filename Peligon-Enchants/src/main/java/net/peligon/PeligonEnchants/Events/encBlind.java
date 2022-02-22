package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.Main;
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

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity cause = event.getDamager();
        if (cause instanceof Arrow) {
            if (plugin.getConfig().getBoolean("Enchantments.Blind.bow.enabled")) {
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

                        if (shooter.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 1 &&
                                chance >= plugin.getConfig().getInt("Enchantments.Blind.bow.level.one.chance")) {
                            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * plugin.getConfig().getInt("Enchantments.Blind.bow.level.one.duration"),
                                    plugin.getConfig().getInt("Enchantments.Blind.bow.level.one.amplifier"), true));
                        } else if (shooter.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 2 && chance >= plugin.getConfig().getInt("Enchantments.Blind.bow.level.two.chance")) {
                            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * plugin.getConfig().getInt("Enchantments.Blind.bow.level.two.duration"),
                                    plugin.getConfig().getInt("Enchantments.Blind.bow.level.two.amplifier"), true));
                        } else if (shooter.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 3 && chance >= plugin.getConfig().getInt("Enchantments.Blind.bow.level.three.chance")) {
                            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * plugin.getConfig().getInt("Enchantments.Blind.bow.level.three.duration"),
                                    plugin.getConfig().getInt("Enchantments.Blind.bow.level.three.amplifier"), true));
                        }
                    }
                }
            }
        } else if (cause instanceof Player) {
            if (plugin.getConfig().getBoolean("Enchantments.Blind.sword.enabled")) {
                Entity entity = event.getEntity();
                Player target = (Player) event.getDamager();
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    if (target.getInventory().getItemInMainHand() == null) return;
                    if (!target.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.BLIND)) return;
                    if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) return;

                    double chance = (Math.random() * 100);

                    if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 1 &&
                            chance >= plugin.getConfig().getInt("Enchantments.Blind.sword.level.one.chance")) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * plugin.getConfig().getInt("Enchantments.Blind.sword.level.one.duration"),
                                plugin.getConfig().getInt("Enchantments.Blind.sword.level.one.amplifier"), true));
                    } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 2 &&
                            chance >= plugin.getConfig().getInt("Enchantments.Blind.sword.level.two.chance")) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * plugin.getConfig().getInt("Enchantments.Blind.sword.level.two.duration"),
                                plugin.getConfig().getInt("Enchantments.Blind.sword.level.two.amplifier"), true));
                    } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.BLIND) == 3
                            && chance >= plugin.getConfig().getInt("Enchantments.Blind.sword.level.three.chance")) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * plugin.getConfig().getInt("Enchantments.Blind.sword.level.three.duration"),
                                plugin.getConfig().getInt("Enchantments.Blind.sword.level.three.amplifier"), true));
                    }
                }
            }
        }
    }

}
