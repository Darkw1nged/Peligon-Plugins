package net.peligon.PeligonPrison.listeners;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.struts.Rank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class accountSetup implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.rankManager.hasData(player)) {
            for (Rank rank : Utils.ranks) {
                if (rank.isDefault()) {
                    plugin.rankManager.createData(player, rank.getName());
                    return;
                }
            }
        }
        if (!player.getInventory().contains(plugin.backpackManager.backpack)) {
            player.getInventory().addItem(plugin.backpackManager.backpack);
        }
    }



}
