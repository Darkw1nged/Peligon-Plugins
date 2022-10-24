package net.peligon.Playtime.managers;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.storage.SQLiteLibrary;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class mgrPlayTime {

    private final Main plugin = Main.getInstance;

    public static mgrPlayTime instance;
    public mgrPlayTime() {
        instance = this;
    }

//
//    /**
//     * Gets the player's time played position
//     *
//     * @return Players time played position
//     */
//    public int getTimePlayedPosition(OfflinePlayer player) {
//        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
//            String query = "SELECT uuid, timePlayed FROM server ORDER BY timePlayed DESC;";
//            try {
//                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
//                ResultSet rs = statement.executeQuery();
//                int position = rs.getFetchSize();
//
//                while (rs.next()) {
//                    if (isHidden(UUID.fromString(rs.getString("uuid")))) continue;
//                    if (player.getUniqueId().toString().equals(rs.getString("uuid"))) {
//                        rs.close();
//                        return position;
//                    }
//                    position--;
//                }
//                rs.close();
//                return -1;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
//            String query = "SELECT uuid, timePlayed FROM Playtime ORDER BY timePlayed DESC;";
//            try {
//                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
//                ResultSet rs = statement.executeQuery();
//                int position = rs.getFetchSize();
//
//                while (rs.next()) {
//                    if (isHidden(UUID.fromString(rs.getString("uuid")))) continue;
//                    if (player.getUniqueId().toString().equals(rs.getString("uuid"))) {
//                        rs.close();
//                        return position;
//                    }
//                    position--;
//                }
//                rs.close();
//                return -1;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * Gets all players time played and sorts them
//     *
//     * @return List of players played time going from smallest to largest
//     */
//    public HashMap<UUID, Long> getTimePlayedLeaderboard() {
//        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
//            String query = "SELECT uuid, timePlayed FROM server ORDER BY timePlayed DESC;";
//            try {
//                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
//                ResultSet rs = statement.executeQuery();
//                HashMap<UUID, Long> leaderboard = new HashMap<>();
//
//                while (rs.next()) {
//                    if (isHidden(UUID.fromString(rs.getString("uuid")))) continue;
//                    leaderboard.put(UUID.fromString(rs.getString("uuid")), rs.getLong("timePlayed"));
//                }
//                rs.close();
//                return leaderboard;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
//            String query = "SELECT uuid, timePlayed FROM Playtime ORDER BY timePlayed DESC;";
//            try {
//                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
//                ResultSet rs = statement.executeQuery();
//                HashMap<UUID, Long> leaderboard = new HashMap<>();
//
//                while (rs.next()) {
//                    if (isHidden(UUID.fromString(rs.getString("uuid")))) continue;
//                    leaderboard.put(UUID.fromString(rs.getString("uuid")), rs.getLong("timePlayed"));
//                }
//                rs.close();
//                return leaderboard;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return new HashMap<>();
//    }

}
