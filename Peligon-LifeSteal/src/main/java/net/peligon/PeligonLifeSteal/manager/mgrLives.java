package net.peligon.PeligonLifeSteal.manager;

import net.peligon.PeligonLifeSteal.libaries.storage.SQLite;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mgrLives {

    public static mgrLives getInstance;
    public mgrLives() {
        getInstance = this;
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

    /**
     * Creates lives data for the player
     */
    public void createData(OfflinePlayer player, int lives) {
        if (hasData(player)) return;
        String query = "INSERT INTO server values('" + player.getUniqueId() + "', " + lives + ");";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the player's lives
     *
     * @param lives New lives
     */
    public void setLives(OfflinePlayer player, int lives) {
        if (!hasData(player)) return;
        if (lives == 0) return;
        String query = "UPDATE server SET lives=" + lives + " WHERE uuid='" + player.getUniqueId() + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds to the player's lives
     *
     * @param lives to add
     */
    public void addLives(OfflinePlayer player, int lives) {
        if (!hasData(player)) return;
        if (lives == 0) return;
        String query = "UPDATE server SET lives= (SELECT lives FROM server WHERE uuid='" + player.getUniqueId() + "') +" + lives + " WHERE uuid='" + player.getUniqueId() + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes from the player's lives
     *
     * @param lives to remove
     */
    public void removeLives(OfflinePlayer player, int lives) {
        if (!hasData(player)) return;
        if (lives == 0) return;
        String query = "UPDATE server SET lives= (SELECT lives FROM server WHERE uuid='" + player.getUniqueId() + "') -" + lives + " WHERE uuid='" + player.getUniqueId() + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the player's authentication token
     *
     * @return Players authentication token
     */
    public Integer getLives(OfflinePlayer player) {
        if (!hasData(player)) return null;
        String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("lives");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
