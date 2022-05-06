package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatFilter implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        String censor = "#$@&%*!";
        if (!plugin.getConfig().getBoolean("enable-chat-filter", true)) return;

        for (String word : message.split(" ")) {
            if (!plugin.fileChatFilter.getConfig().getStringList("words").contains(word.toLowerCase())) continue;
            event.setMessage(message.replaceAll(word, censor));
        }
    }

}
