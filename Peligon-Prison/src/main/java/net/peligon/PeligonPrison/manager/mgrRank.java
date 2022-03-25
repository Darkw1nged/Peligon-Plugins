package net.peligon.PeligonPrison.manager;

import net.peligon.PeligonPrison.libaries.storage.SQLite;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mgrRank {

    public static mgrRank getInstance;
    public mgrRank() {
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
     * Creates data for the player
     */
    public void createData(OfflinePlayer player, String rank) {
        if (hasData(player)) return;
        String query = "INSERT INTO server values('" + player.getUniqueId() + "', '" + rank + "', '');";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the player's rank
     *
     * @param rank New rank
     */
    public void setRank(OfflinePlayer player, String rank) {
        if (!hasData(player)) return;
        if (rank.equals("")) return;
        String query = "UPDATE server SET rank='" + rank + "' WHERE uuid='" + player.getUniqueId() + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the player's rank
     *
     * @return Players rank
     */
    public String getRank(OfflinePlayer player) {
        if (!hasData(player)) return null;
        String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString("rank");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
