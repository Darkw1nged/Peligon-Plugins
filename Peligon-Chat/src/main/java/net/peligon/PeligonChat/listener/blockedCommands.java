package net.peligon.PeligonChat.listener;

import net.peligon.PeligonChat.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class blockedCommands implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getConfig().getBoolean("blocked-commands.enabled", true)) return;
        for (String word : event.getMessage().split(" ")) {
            if (plugin.getConfig().getStringList("blocked-commands.commands").contains(word)) {
                if (player.hasPermission("Peligon.Chat.bypass") || player.hasPermission("Peligon.Chat.*")) return;
                event.setCancelled(true);
            }
        }
    }

}
