package net.peligon.PeligonAuthentication.manager;

import net.peligon.PeligonAuthentication.libaries.storage.SQLite;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mgrAuthentication {

    public static mgrAuthentication getInstance;
    public mgrAuthentication() {
        getInstance = this;
    }

    /**
     * Checking if player has authentication data
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
     * Creates authentication data for the player
     */
    public void createData(OfflinePlayer player, String token) {
        if (hasData(player)) return;
        String query = "INSERT INTO server values('" + player.getUniqueId() + "', '" + token + "');";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the player's authentication token
     *
     * @param token New authentication token
     */
    public void setToken(OfflinePlayer player, String token) {
        if (!hasData(player)) return;
        if (token.equals("")) return;
        String query = "UPDATE server SET token=" + token + " WHERE uuid='" + player.getUniqueId() + "';";
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
    public String getToken(OfflinePlayer player) {
        if (!hasData(player)) return "";
        String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString("token");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}
