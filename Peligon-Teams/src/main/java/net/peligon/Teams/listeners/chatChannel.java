package net.peligon.Teams.listeners;

import net.peligon.Teams.Core.Channel;
import net.peligon.Teams.Core.Ranks;
import net.peligon.Teams.Core.Team;
import net.peligon.Teams.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class chatChannel implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onTalk(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!plugin.teamManager.inTeam(player)) {
            if (plugin.teamManager.getPlayerChat(player) != Channel.Global) {
                plugin.teamManager.changePlayerChannel(player, Channel.Global);
            }
            return;
        }

        if (plugin.teamManager.getPlayerChat(player) == Channel.Team) {
            event.setCancelled(true);

            for (UUID member : plugin.teamManager.getTeam(player).getMembers()) {
                Player memberPlayer = plugin.getServer().getPlayer(member);
                if (memberPlayer != null) {
                    memberPlayer.sendMessage(plugin.getConfig().getString("Channels-Format.Team")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%message%", event.getMessage()));
                }
            }
            return;
        }

        if (plugin.teamManager.getPlayerChat(player) == Channel.Truce) {
            event.setCancelled(true);
            List<UUID> playersToSend = new ArrayList<>();

            for (Team team : plugin.teamManager.getTeam(player).getTruces()) {
                playersToSend.addAll(team.getMembers());
            }

            for (UUID member : playersToSend) {
                Player memberPlayer = plugin.getServer().getPlayer(member);
                if (memberPlayer != null) {
                    memberPlayer.sendMessage(plugin.getConfig().getString("Channels-Format.Truce")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%message%", event.getMessage()));
                }
            }
            return;
        }

        if (plugin.teamManager.getPlayerChat(player) == Channel.Ally) {
            event.setCancelled(true);
            List<UUID> playersToSend = new ArrayList<>();

            for (Team team : plugin.teamManager.getTeam(player).getAllies()) {
                playersToSend.addAll(team.getMembers());
            }

            for (UUID member : playersToSend) {
                Player memberPlayer = plugin.getServer().getPlayer(member);
                if (memberPlayer != null) {
                    memberPlayer.sendMessage(plugin.getConfig().getString("Channels-Format.Ally")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%message%", event.getMessage()));
                }
            }
            return;
        }

        if (plugin.teamManager.getPlayerChat(player) == Channel.Moderator) {
            event.setCancelled(true);
            List<UUID> playersToSend = new ArrayList<>();

            for (UUID moderator : plugin.teamManager.getTeam(player).getMembers()) {
                if (plugin.teamManager.getTeam(player).getRanks().get(moderator) == Ranks.Moderator) {
                    playersToSend.add(moderator);
                }
            }

            for (UUID member : playersToSend) {
                Player memberPlayer = plugin.getServer().getPlayer(member);
                if (memberPlayer != null) {
                    memberPlayer.sendMessage(plugin.getConfig().getString("Channels-Format.Moderator")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%message%", event.getMessage()));
                }
            }
        }
    }

}
