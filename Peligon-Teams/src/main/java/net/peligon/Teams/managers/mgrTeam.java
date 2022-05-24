package net.peligon.Teams.managers;

import net.peligon.Teams.libaries.Team;
import net.peligon.Teams.libaries.Utils;
import org.bukkit.OfflinePlayer;

public class mgrTeam {

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

}
