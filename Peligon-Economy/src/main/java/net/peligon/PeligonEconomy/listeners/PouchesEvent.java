package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class PouchesEvent implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType().equals(Material.AIR)) return;

        if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "plugin"), PersistentDataType.STRING)) {
            String pluginName = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "plugin"), PersistentDataType.STRING);
            if (!pluginName.equals("PeligonEconomy")) return;

            if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "type"), PersistentDataType.STRING)) {
                String type = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "type"), PersistentDataType.STRING);
                if (type.equals("money")) {
                    if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "hasPermission"), PersistentDataType.STRING)) {
                        String hasPermission = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "hasPermission"), PersistentDataType.STRING);
                        if (!player.hasPermission(hasPermission)) return;
                    }

                    double minimum = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "minimum"), PersistentDataType.DOUBLE);
                    double maximum = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "maximum"), PersistentDataType.DOUBLE);

                    int toAdd = (int) (Math.random() * (maximum - minimum) + minimum);

                    Utils.sendPlayerMovingMessage(player, toAdd, plugin.filePouches.getConfig().getString("title"), true);
                } else if (type.equals("experience")) {
                    if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "hasPermission"), PersistentDataType.STRING)) {
                        String hasPermission = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "hasPermission"), PersistentDataType.STRING);
                        if (!player.hasPermission(hasPermission)) return;
                    }

                    int minimum = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "minimum"), PersistentDataType.INTEGER);
                    int maximum = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "maximum"), PersistentDataType.INTEGER);

                    int toAdd = (int) (Math.random() * (maximum - minimum) + minimum);

                    Utils.sendPlayerMovingMessage(player, toAdd, plugin.filePouches.getConfig().getString("title"), false);
                }
            }
            item.setAmount(item.getAmount() - 1);
        }

    }

}
