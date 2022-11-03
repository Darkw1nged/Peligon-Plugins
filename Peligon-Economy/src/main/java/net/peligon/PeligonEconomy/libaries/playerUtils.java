package net.peligon.PeligonEconomy.libaries;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.storage.CustomConfig;
import net.peligon.PeligonEconomy.libaries.storage.SQLiteLibrary;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class playerUtils {

    // Getting instance of the main class.
    private static final Main plugin = Main.getInstance;

    // If the player does not have any data then we can use this method to create some.
    public static void createPlayerData(OfflinePlayer player) {
        // Check if the player has data first, If so then return.
        if (hasData(player)) return;

        // Setting up the query for SQL
        String SQLQuery = "INSERT INTO peligonEconomy values('" + player.getUniqueId() + "'," + 0 + ", " + System.currentTimeMillis() + ", 0, 0);";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                record.getConfig().set("cash", 0);
                record.getConfig().set("bankBalance", System.currentTimeMillis());
                record.saveConfig();
                break;
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Checking if the player has any data.
    public static boolean hasData(OfflinePlayer player) {
        // Setting up the query for SQL
        String SQLQuery = "SELECT 1 FROM peligonEconomy WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                return record.getConfig().contains("playtime");
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }

    // Get the players cash balance
    public static double getCash(OfflinePlayer player) {
        // Setting up the query for SQL
        String SQLQuery = "SELECT cash FROM peligonEconomy WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                return record.getConfig().getDouble("cash");
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.getDouble("cash");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.getDouble("cash");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return 0;
    }

    // Get the players bank balance
    public static double getBankBalance(OfflinePlayer player) {
        // Setting up the query for SQL
        String SQLQuery = "SELECT bankBalance FROM peligonEconomy WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                return record.getConfig().getDouble("bankBalance");
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.getDouble("bankBalance");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.getDouble("bankBalance");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return 0;
    }

}
