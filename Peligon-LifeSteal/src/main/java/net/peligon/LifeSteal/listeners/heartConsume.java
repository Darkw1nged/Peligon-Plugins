package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.items.CustomItems;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class heartConsume implements Listener {

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
                if (item.isSimilar(customItems.hearts.getHeart())) {
                    event.setCancelled(true);
                    event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(
                            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER)
                    );
                    item.setAmount(item.getAmount() - 1);
                } else if (item.isSimilar(customItems.hearts.getGoldenHeart())) {
                    event.setCancelled(true);
                    event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(
                            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER)
                    );
                    item.setAmount(item.getAmount() - 1);
                } else if (item.isSimilar(customItems.hearts.getExoticHeart())) {
                    event.setCancelled(true);
                    event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(
                            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER)
                    );
                    item.setAmount(item.getAmount() - 1);
                } else if (item.isSimilar(customItems.hearts.getMythicHeart())) {
                    event.setCancelled(true);
                    event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(
                            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amount"), PersistentDataType.INTEGER)
                    );
                    item.setAmount(item.getAmount() - 1);
                }
            }
        }
    }


}
