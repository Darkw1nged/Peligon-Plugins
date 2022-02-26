package net.peligon.PeligonChat.listener;

import net.peligon.PeligonChat.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatFormat implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String prefix = "";
        String suffix = "";

        if (plugin.getChat() != null) {
            prefix = plugin.getChat().getPlayerPrefix(player);
            suffix = plugin.getChat().getPlayerSuffix(player);
        }

        String format = plugin.getConfig().getString("chat-display-format", "%player%: %message%")
                .replaceAll("%player%", player.getName())
                .replaceAll("%message%", message)
                .replaceAll("%prefix%", prefix)
                .replaceAll("%suffix%", suffix);

        event.setFormat(format);
    }

}
