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
        if (!plugin.getConfig().getStringList("Events").contains("word-filter")) return;

        for (String word : message.split(" ")) {
            if (!plugin.fileChatSettings.getConfig().getStringList("Chat-Filter").contains(word.toLowerCase())) continue;
            event.setMessage(message.replaceAll(word, censor));
        }
    }

}
