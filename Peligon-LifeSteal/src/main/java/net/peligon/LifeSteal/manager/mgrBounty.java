package net.peligon.LifeSteal.manager;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.storage.SQLiteLibrary;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mgrBounty {

    private final Main plugin = Main.getInstance;

    public static mgrBounty getInstance;
    public mgrBounty() {
        getInstance = this;
    }

    /**
     * Sets the player's bounty
     *
     * @param bounty New bounty
     */
    public void setBounty(OfflinePlayer player, double bounty) {
        if (!plugin.lives.hasData(player)) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET bounty=" + bounty + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE LifeSteal SET bounty=" + bounty + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds to the player's bounty
     *
     * @param bounty to add
     */
    public void addBounty(OfflinePlayer player, double bounty) {
        if (!plugin.lives.hasData(player)) return;
        if (bounty == 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET bounty= (SELECT bounty FROM server WHERE uuid='" + player.getUniqueId() + "') +" + bounty + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE LifeSteal SET bounty= (SELECT bounty FROM LifeSteal WHERE uuid='" + player.getUniqueId() + "') +" + bounty + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Removes from the player's bounty
     *
     * @param bounty to remove
     */
    public void removeBounty(OfflinePlayer player, double bounty) {
        if (!plugin.lives.hasData(player)) return;
        if (bounty == 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET bounty= (SELECT bounty FROM server WHERE uuid='" + player.getUniqueId() + "') -" + bounty + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE LifeSteal SET bounty= (SELECT bounty FROM LifeSteal WHERE uuid='" + player.getUniqueId() + "') -" + bounty + " WHERE uuid='" + player.getUniqueId() + "';";
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
    public Double getBounty(OfflinePlayer player) {
        if (!plugin.lives.hasData(player)) return null;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
            try {

                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("bounty");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM LifeSteal WHERE uuid='" + player.getUniqueId() + "';";
            try {

                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("bounty");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
