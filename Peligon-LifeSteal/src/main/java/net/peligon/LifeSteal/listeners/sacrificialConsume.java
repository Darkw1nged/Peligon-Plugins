package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import net.peligon.LifeSteal.libaries.items.CustomItems;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class sacrificialConsume implements Listener {

    private final Main plugin = Main.getInstance;
    private final CustomItems customItems = plugin.customItems;

    @EventHandler
    public void onConsume(PlayerInteractEvent event) {
        EquipmentSlot hand = event.getHand();
        if (hand == null) return;
        ItemStack item = event.getPlayer().getItemInHand();
        if (item.getType() == Material.AIR) return;
        if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER)) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (hand.equals(EquipmentSlot.HAND)) {
                if (item.isSimilar(customItems.sacrificialHearts.getScarceHeart())) {
                    event.setCancelled(true);
                    event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(
                            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER)
                    );
                    item.setAmount(item.getAmount() - 1);

                    Player player = event.getPlayer();
                    for (String key : plugin.fileItems.getConfig().getConfigurationSection("Scarce_Heart.buffs").getKeys(false)) {
                        PotionEffectType type = PotionEffectType.getByName(plugin.fileItems.getConfig().getString("Scarce_Heart.buffs." + key + ".type"));
                        int amplifier = plugin.fileItems.getConfig().getInt("Scarce_Heart.buffs." + key + ".amplifier");
                        int duration = plugin.fileItems.getConfig().getInt("Scarce_Heart.buffs." + key + ".duration") * 20;

                        if (type == null) continue;
                        player.addPotionEffect(new PotionEffect(type, duration, amplifier));
                    }
                } else if (item.isSimilar(customItems.sacrificialHearts.getShockHeart())) {
                    event.setCancelled(true);
                    event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(
                            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER)
                    );
                    item.setAmount(item.getAmount() - 1);

                    Player player = event.getPlayer();
                    for (String key : plugin.fileItems.getConfig().getConfigurationSection("Shock_Heart.buffs").getKeys(false)) {
                        PotionEffectType type = PotionEffectType.getByName(plugin.fileItems.getConfig().getString("Shock_Heart.buffs." + key + ".type"));
                        int amplifier = plugin.fileItems.getConfig().getInt("Shock_Heart.buffs." + key + ".amplifier");
                        int duration = plugin.fileItems.getConfig().getInt("Shock_Heart.buffs." + key + ".duration") * 20;

                        if (type == null) continue;
                        player.addPotionEffect(new PotionEffect(type, duration, amplifier));
                    }
                } else if (item.isSimilar(customItems.sacrificialHearts.getRageHeart())) {
                    event.setCancelled(true);
                    event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(
                            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER)
                    );
                    item.setAmount(item.getAmount() - 1);

                    Player player = event.getPlayer();
                    for (String key : plugin.fileItems.getConfig().getConfigurationSection("Rage_Heart.buffs").getKeys(false)) {
                        PotionEffectType type = PotionEffectType.getByName(plugin.fileItems.getConfig().getString("Rage_Heart.buffs." + key + ".type"));
                        int amplifier = plugin.fileItems.getConfig().getInt("Rage_Heart.buffs." + key + ".amplifier");
                        int duration = plugin.fileItems.getConfig().getInt("Rage_Heart.buffs." + key + ".duration") * 20;

                        if (type == null) continue;
                        player.addPotionEffect(new PotionEffect(type, duration, amplifier));
                    }
                }
            }
        }
    }

}
