package net.peligon.PeligonPrison.manager;

import net.peligon.PeligonPrison.libaries.storage.SQLite;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mgrPrestige {

    public static mgrPrestige getInstance;
    public mgrPrestige() {
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
     * Sets the player's prestige
     *
     * @param prestige New prestige
     */
    public void setPrestige(OfflinePlayer player, String prestige) {
        if (!hasData(player)) return;
        if (prestige.equals("")) return;
        String query = "UPDATE server SET prestige=" + prestige + " WHERE uuid='" + player.getUniqueId() + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the player's prestige
     *
     * @return Players prestige
     */
    public String getPrestige(OfflinePlayer player) {
        if (!hasData(player)) return null;
        String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString("prestige");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
