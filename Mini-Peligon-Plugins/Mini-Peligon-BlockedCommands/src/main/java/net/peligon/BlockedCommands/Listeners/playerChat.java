package net.peligon.BlockedCommands.Listeners;

import net.peligon.BlockedCommands.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class playerChat implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        for (String s : plugin.getConfig().getStringList("blocked-commands")) {
            if (event.getMessage().toLowerCase().startsWith(s.toLowerCase())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(plugin.utils.chatColor(plugin.fileMessage.getConfig().getString("blocked-command")));
            }
        }
    }

}
