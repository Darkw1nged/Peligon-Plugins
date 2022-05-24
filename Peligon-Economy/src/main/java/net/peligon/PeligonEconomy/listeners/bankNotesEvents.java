package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class bankNotesEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || item.getType().equals(Material.AIR)) return;

        if (item.getType().name().equalsIgnoreCase(plugin.getConfig().getString("Items.withdraw-cash.item"))) {
            if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "amount"), PersistentDataType.DOUBLE)) return;
            double amount = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amount"), PersistentDataType.DOUBLE);

            plugin.Economy.addAccount(player, amount);
            player.getInventory().remove(item);
        }
    }
}
