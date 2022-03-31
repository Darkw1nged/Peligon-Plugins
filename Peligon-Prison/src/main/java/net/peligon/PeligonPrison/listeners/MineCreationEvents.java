package net.peligon.PeligonPrison.listeners;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MineCreationEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Utils.mineCreationInProgress.containsKey(player.getUniqueId())) {
                if (player.getInventory().getItemInHand().equals(plugin.itemManager.mineCreationWand)) {
                    event.setCancelled(true);
                    Utils.mineCreationCornerOne.put(player.getUniqueId(), event.getClickedBlock().getLocation());
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("first-corner-set")));
                }
            }
        } else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (Utils.mineCreationInProgress.containsKey(player.getUniqueId())) {
                if (player.getInventory().getItemInHand().equals(plugin.itemManager.mineCreationWand)) {
                    event.setCancelled(true);
                    Utils.mineCreationCornerTwo.put(player.getUniqueId(), event.getClickedBlock().getLocation());
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("second-corner-set")));
                }
            }
        }
    }

}
