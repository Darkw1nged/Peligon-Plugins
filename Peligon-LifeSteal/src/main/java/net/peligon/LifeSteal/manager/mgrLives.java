package net.peligon.LifeSteal.manager;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.storage.SQLiteLibrary;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

public class mgrLives {

    private final Main plugin = Main.getInstance;

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
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT 1 FROM server WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT 1 FROM LifeSteal WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Creates lives data for the player
     */
    public void createData(OfflinePlayer player, int lives) {
        if (hasData(player)) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "INSERT INTO server values('" + player.getUniqueId() + "', " + lives + ", 0);";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "INSERT INTO LifeSteal values('" + player.getUniqueId() + "', " + lives + ", 0);";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET lives=" + lives + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE LifeSteal SET lives=" + lives + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET lives= (SELECT lives FROM server WHERE uuid='" + player.getUniqueId() + "') +" + lives + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE LifeSteal SET lives= (SELECT lives FROM LifeSteal WHERE uuid='" + player.getUniqueId() + "') +" + lives + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET lives= (SELECT lives FROM server WHERE uuid='" + player.getUniqueId() + "') -" + lives + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE LifeSteal SET lives= (SELECT lives FROM LifeSteal WHERE uuid='" + player.getUniqueId() + "') -" + lives + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the player's authentication token
     *
     * @return Players authentication token
     */
    public Integer getLives(OfflinePlayer player) {
        if (!hasData(player)) return null;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
            try {

                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt("lives");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM LifeSteal WHERE uuid='" + player.getUniqueId() + "';";
            try {

                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt("lives");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
