package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class antiSpam implements Listener {

    private final Main plugin = Main.getInstance;
    Map<UUID, Long> messageTimeout = new HashMap<>();
    Map<UUID, Integer> playerMessages = new HashMap<>();
    int delay = plugin.getConfig().getInt("chat-anti-spam.delay", 3);

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getConfig().getBoolean("chat-anti-spam.enabled", true)) return;
        if (player.hasPermission("Peligon.Chat.bypass") || player.hasPermission("Peligon.Chat.*")) return;

        if (!messageTimeout.isEmpty() && messageTimeout.containsKey(player.getUniqueId())) {
            long timeLeft = ((messageTimeout.get(player.getUniqueId()) / 1000) + delay) - (System.currentTimeMillis() / 1000);
            if (timeLeft > 0) {
                if (!playerMessages.isEmpty() && playerMessages.containsKey(player.getUniqueId())) {
                    if (playerMessages.get(player.getUniqueId()) >= plugin.getConfig().getInt("chat-anti-spam.messages", 5)) {
                        if (!plugin.getConfig().getBoolean("chat-anti-spam.kick-player.enabled", true)) return;
                        player.kickPlayer(Utils.chatColor(plugin.getConfig().getString("chat-anti-spam.kick-player.message", "&cKicked for spamming")));
                        playerMessages.remove(player.getUniqueId());
                        return;
                    }
                    playerMessages.put(player.getUniqueId(), playerMessages.get(player.getUniqueId()) + 1);
                } else {
                    playerMessages.put(player.getUniqueId(), 1);
                }
                return;
            }
            messageTimeout.remove(player.getUniqueId());
            playerMessages.remove(player.getUniqueId());
        }

    }

}
