package net.peligon.Teams.managers;

import net.peligon.Teams.libaries.Team;
import net.peligon.Teams.libaries.Utils;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class mgrTeam {

    private List<UUID> teamChat = new ArrayList<>();

    public static mgrTeam getInstance;

    public mgrTeam() {
        getInstance = this;
    }

    public boolean inTeam(OfflinePlayer player) {
        for (Team team : Utils.teams) {
            if (team.getMembers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public boolean teamAlreadyExists(String name) {
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

    public Boolean inTeamChat(OfflinePlayer player) {
        return teamChat.contains(player.getUniqueId());
    }

    public void toggleTeamChat(OfflinePlayer player) {
        if (inTeamChat(player)) {
            teamChat.remove(player.getUniqueId());
        } else {
            teamChat.add(player.getUniqueId());
        }
    }

}
