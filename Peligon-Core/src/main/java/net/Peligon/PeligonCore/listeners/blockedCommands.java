package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class blockedCommands implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(PlayerCommandPreprocessEvent event) {
        for (String s : plugin.fileChatSettings.getConfig().getStringList("Blocked-Commands")) {
            if (event.getMessage().equalsIgnoreCase(s)) {
                if (event.getPlayer().hasPermission("Peligon.Core.Bypass") || event.getPlayer().hasPermission("Peligon.Core.*")) return;
                event.setCancelled(true);
                if (plugin.fileChatSettings.getConfig().getBoolean("Blocked-Command-Message")) {
                    event.getPlayer().sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("command-blocked")));
                }
            }
        }
    }

}
