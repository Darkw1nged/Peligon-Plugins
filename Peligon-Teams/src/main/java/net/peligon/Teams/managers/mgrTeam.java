package net.peligon.Teams.managers;

import net.peligon.Teams.Core.Channel;
import net.peligon.Teams.Core.Team;
import net.peligon.Teams.libaries.Utils;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class mgrTeam {

    private Map<UUID, Channel> playerChat = new HashMap<>();

    public static mgrTeam getInstance;
    public mgrTeam() {
        getInstance = this;
    }

    public Channel getPlayerChat(OfflinePlayer player) {
        return playerChat.get(player.getUniqueId());
    }

    public void changePlayerChannel(OfflinePlayer player, Channel channel) {
        playerChat.put(player.getUniqueId(), channel);
    }

    public Boolean inTeam(OfflinePlayer player) {
        for (Team team : Utils.teams) {
            if (team.getMembers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public Boolean teamExists(String name) {
        for (Team team : Utils.teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Team getTeam(OfflinePlayer player) {
        for (Team team : Utils.teams) {
            if (team.getMembers().contains(player.getUniqueId())) {
                return team;
            }
        }
        return null;
    }

    public Team getTeam(String name) {
        for (Team team : Utils.teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

}
