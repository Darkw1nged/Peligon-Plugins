package net.peligon.PeligonTNTRun.events;

import net.peligon.PeligonTNTRun.libaries.Game;
import net.peligon.PeligonTNTRun.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.UUID;

public class Temp implements Listener {

    @EventHandler
    public void onAction(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage().equalsIgnoreCase("peligonDebug:JoinGame")) {
            if (Utils.activeGames.isEmpty())
            for (Game game : Utils.activeGames) {
                if (game.hasStarted()) return;
                if (game.getPlayers().size() >= game.getMaxPlayers()) return;

                List<UUID> toAdd = game.getPlayers();
                toAdd.add(player.getUniqueId());

                game.setPlayers(toAdd);
                break;
            }
        }
    }

}
