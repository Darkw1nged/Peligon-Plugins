package net.peligon.ChatFilter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        log("[Peligon Mini] ChatFilter has been enabled.");
    }

    public void onDisable() {
        log("[Peligon Mini] ChatFilter has been disabled.");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("Peligon.ChatFilter.Bypass")) return;

        StringBuilder newMessage = new StringBuilder();
        for (String word : event.getMessage().split(" ")) {
            if (getConfig().getStringList("Filter").contains(word.toLowerCase())) {
                StringBuilder replacement = new StringBuilder();
                for (int i=0; i<word.length(); i++) {
                    replacement.append("*");
                }
                word = replacement.toString();
            }
            newMessage.append(word);
        }
        event.setMessage(newMessage.toString());
    }

    private static void log(String message) { System.out.println(message); }
}
