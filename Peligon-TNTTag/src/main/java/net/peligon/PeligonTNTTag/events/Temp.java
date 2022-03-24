package net.peligon.PeligonTNTTag.events;

import net.peligon.PeligonTNTTag.managers.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Temp implements Listener {

    @EventHandler
    public void onAction(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage().equalsIgnoreCase("peligonDebug:JoinGame")) {
           GameManager.getInstance.playerJoinGame(player);
           System.out.println(GameManager.getInstance.getGamesWaiting().get(0).getPlayers());
        }
    }

}
