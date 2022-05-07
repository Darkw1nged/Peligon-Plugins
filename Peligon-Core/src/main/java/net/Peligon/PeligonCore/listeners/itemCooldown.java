package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class itemCooldown implements Listener {

    private final Main plugin = Main.getInstance;
    private final Map<UUID, Map<ItemStack, Long>> cooldown = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getStringList("Events").contains("item-cooldown")) {
            for (String item : plugin.getConfig().getConfigurationSection("item-cooldown.items").getKeys(false)) {
                int timeout = plugin.getConfig().getInt("item-cooldown.items." + item, 5);
                if (player.getItemInHand().getType().name().equalsIgnoreCase(item)) {
                    if (cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()).containsKey(player.getItemInHand())) {
                        long timeLeft = ((cooldown.get(player.getUniqueId()).get(player.getItemInHand()) / 1000) + timeout) - (System.currentTimeMillis() / 1000);
                        if (timeLeft > 0) {
                            event.setCancelled(true);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("item-on-cooldown")));
                            return;
                        }
                        cooldown.remove(player.getUniqueId());
                    }
                }
            }
        }
    }

}
