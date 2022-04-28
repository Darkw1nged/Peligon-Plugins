package net.peligon.PeligonPlayTime.managers;

import net.peligon.PeligonPlayTime.libaries.storage.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class mgrPlayTime {

    public static mgrPlayTime instance;

    public mgrPlayTime() {
        instance = this;
    }

    /**
     * Checking if player has any data in database
     *
     * @return if data exists in database
     */
    public boolean hasData(OfflinePlayer player) {
        String query = "SELECT 1 FROM server WHERE uuid='" + player.getUniqueId() + "';";
        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasData(UUID uuid) {
        String query = "SELECT 1 FROM server WHERE uuid='" + uuid.toString() + "';";
        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Creates data for the player
     */
    public void createData(OfflinePlayer player) {
        if (hasData(player)) return;
        String query = "INSERT INTO server values('" + player.getUniqueId() + "'," + 0 + ", " + System.currentTimeMillis() + ");";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds time to the player
     */
    public void addTime(OfflinePlayer player) {
        if (!hasData(player)) return;
        long difference = System.currentTimeMillis() - getLastUpdated(player);

        String query = "UPDATE server SET timePlayed = (SELECT timePlayed FROM server WHERE uuid='" + player.getUniqueId() + "') +" + difference + " WHERE uuid= '" + player.getUniqueId() + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
            setLastUpdated(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset time for the player
     */
    public void resetTime(OfflinePlayer player) {
        if (!hasData(player)) return;
        String query = "UPDATE server SET timePlayed = 0 WHERE uuid= '" + player.getUniqueId() + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
            setLastUpdated(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetTime(UUID uuid) {
        if (!hasData(uuid)) return;
        String query = "UPDATE server SET timePlayed = 0 WHERE uuid= '" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
            setLastUpdated(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the player's time played
     *
     * @return Players time played in array format
     */
    public long getTimePlayed(OfflinePlayer player) {
        if (!hasData(player)) return 0;
        String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getLong("timePlayed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long getTimePlayed(UUID uuid) {
        if (!hasData(uuid)) return 0;
        String query = "SELECT * FROM server WHERE uuid='" + uuid + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getLong("timePlayed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the player's last updated time
     *
     * @return Players last updated time in milliseconds
     */
    public long getLastUpdated(OfflinePlayer player) {
        if (!hasData(player)) return 0;
        String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getLong("lastUpdated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Sets the player's last updated time
     *
     * @param player
     */
    public void setLastUpdated(OfflinePlayer player) {
        if (!hasData(player)) return;
        String query = "UPDATE server SET lastUpdated=" + System.currentTimeMillis() + " WHERE uuid='" + player.getUniqueId() + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLastUpdated(UUID uuid) {
        if (!hasData(uuid)) return;
        String query = "UPDATE server SET lastUpdated=" + System.currentTimeMillis() + " WHERE uuid='" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the player's time played position
     *
     * @return Players time played position
     */
    public int getTimePlayedPosition(OfflinePlayer player) {
        String query = "SELECT uuid, timePlayed FROM server ORDER BY timePlayed DESC;";
        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            int position = rs.getFetchSize();

            while (rs.next()) {
                if (player.getUniqueId().toString().equals(rs.getString("uuid"))) {
                    rs.close();
                    return position;
                }
                position--;
            }
            rs.close();
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets all players time played and sorts them
     *
     * @return List of players played time going from shortest to longest
     */
    public HashMap<UUID, Long> getTimePlayedLeaderboard() {
        String query = "SELECT uuid, timePlayed FROM server ORDER BY timePlayed DESC;";
        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            HashMap<UUID, Long> leaderboard = new HashMap<>();

            while (rs.next()) {
                leaderboard.put(UUID.fromString(rs.getString("uuid")), rs.getLong("timePlayed"));
            }
            rs.close();
            return leaderboard;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

}
