package net.peligon.Teams.listeners;

import net.peligon.Teams.Main;
import net.peligon.Teams.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class teamChat implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.teamManager.inTeam(player) && plugin.teamManager.inTeamChat(player)) {
            event.setCancelled(true);

            for (UUID member : plugin.teamManager.getTeam(player).getMembers()) {
                Player memberPlayer = Bukkit.getPlayer(member);
                if (memberPlayer != null) {
                    memberPlayer.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("team-chat-format")
                            .replaceAll("%team%", plugin.teamManager.getTeam(player).getName())
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%message%", event.getMessage())));
                }
            }
        }
    }

}
