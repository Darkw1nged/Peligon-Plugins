package net.peligon.Playtime.libaries;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.storage.CustomConfig;
import net.peligon.Playtime.libaries.storage.SQLiteLibrary;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class playerUtils {

    // Getting instance of the main class.
    private static final Main plugin = Main.getInstance;

    // Getting the players datafile
    public static CustomConfig playerDataFile(OfflinePlayer player) {
        return new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
    }

    // If the player does not have any data then we can use this method to create some.
    public static void createPlayerData(OfflinePlayer player) {
        // Check if the player has data first, If so then return.
        if (hasData(player)) return;

        // Setting up the query for SQL
        String SQLQuery = "INSERT INTO peligonPlaytime values('" + player.getUniqueId() + "'," + 0 + ", " + System.currentTimeMillis() + ", 0, 0);";

        switch (plugin.storageType) {
            case "file":
                playerDataFile(player).getConfig().set("playtime", 0);
                playerDataFile(player).getConfig().set("lastUpdated", 0);
                playerDataFile(player).getConfig().set("paused", false);
                playerDataFile(player).getConfig().set("hidden", false);
                playerDataFile(player).saveConfig();
                break;
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
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
        String SQLQuery = "SELECT 1 FROM peligonPlaytime WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                return playerDataFile(player).getConfig() != null;
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
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

    // Getting players playtime.
    public static long getPlaytime(OfflinePlayer player) {
        // Check if the player has data first, If not then return 0.
        if (hasData(player)) return 0;

        // Setting up the query for SQL
        String SQLQuery = "SELECT * FROM peligonPlaytime WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                return playerDataFile(player).getConfig().getLong("playtime");
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    return rs.getLong("playtime");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    return rs.getLong("playtime");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return 0;
    }

    // Adding to the players total time played.
    public static void addPlaytime(OfflinePlayer player) {
        // Check if the player has data first, If not then return.
        if (!hasData(player)) return;

        // Finding out the difference between the last time the player's playtime was updated and now.
        long difference = System.currentTimeMillis() - getLastUpdated(player);

        // Setting up the query for SQL
        String SQLQuery = "UPDATE server SET peligonPlaytime = (SELECT playtime FROM server WHERE uuid='" + player.getUniqueId() + "') +" + difference + " WHERE uuid= '" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                playerDataFile(player).getConfig().set("playtime", difference);
                break;
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
                    statement.execute();
                    setLastUpdated(player);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    statement.execute();
                    setLastUpdated(player);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Resetting the players total time played.
    public static void resetPlaytime(OfflinePlayer player) {
        // Check if the player has data first, If not then return.
        if (!hasData(player)) return;

        // Setting up the query for SQL
        String SQLQuery = "UPDATE peligonPlaytime SET playtime = 0 WHERE uuid= '" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                playerDataFile(player).getConfig().set("playtime", 0);
                break;
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
                    statement.execute();
                    setLastUpdated(player);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    statement.execute();
                    setLastUpdated(player);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Getting when the players total time played was updated last.
    public static long getLastUpdated(OfflinePlayer player) {
        // Check if the player has data first, If not then return 0.
        if (!hasData(player)) return 0;

        // Setting up the query for SQL
        String SQLQuery = "SELECT * FROM peligonPlaytime WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                return playerDataFile(player).getConfig().getLong("lastUpdated");
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    return rs.getLong("lastUpdated");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    return rs.getLong("lastUpdated");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return 0;
    }


    // Setting when the player's total time played is updated.
    public static void setLastUpdated(OfflinePlayer player) {
        // Check if the player has data first, If not then return 0.
        if (!hasData(player)) return;

        // Setting up the query for SQL
        String SQLQuery = "UPDATE peligonPlaytime SET lastUpdated=" + System.currentTimeMillis() + " WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                playerDataFile(player).getConfig().set("lastUpdated", System.currentTimeMillis());
                break;
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
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

    // Check if the player's playtime is currently paused.
    public static boolean isPaused(OfflinePlayer player) {
        // Check if the player has data first, If not then return true.
        if (!hasData(player)) return true;

        // Setting up the query for SQL
        String SQLQuery = "SELECT * FROM peligonPlaytime WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                return playerDataFile(player).getConfig().getBoolean("paused");
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    return rs.getInt("paused") != 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    return rs.getInt("paused") != 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }

    // Toggle the player's playtime paused state.
    public static void togglePaused(OfflinePlayer player) {
        // Check if the player has data first, If not then return.
        if (!hasData(player)) return;

        // Getting the current state of the player's playtime.
        int state = isPaused(player) ? 0 : 1;

        // Setting up the query for SQL
        String SQLQuery = "UPDATE peligonPlaytime SET paused=" + state + " WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                playerDataFile(player).getConfig().set("paused", state);
                break;
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
                    // Not sure if this will be a bug. If so then modify `statement.execute();` to the line below.
                    // TODO - statement.executeUpdate();
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

    // Check if the player's playtime is currently hidden.
    public static boolean isHidden(OfflinePlayer player) {
        // Check if the player has data first, If not then return true.
        if (!hasData(player)) return true;

        // Setting up the query for SQL
        String SQLQuery = "SELECT * FROM peligonPlaytime WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                return playerDataFile(player).getConfig().getBoolean("hidden");
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    return rs.getInt("hidden") != 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    return rs.getInt("hidden") != 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }

    // Toggle the player's playtime hidden state.
    public static void toggleHidden(OfflinePlayer player) {
        // Check if the player has data first, If not then return.
        if (!hasData(player)) return;

        // Getting the current state of the player's playtime.
        int state = isHidden(player) ? 0 : 1;

        // Setting up the query for SQL
        String SQLQuery = "UPDATE peligonPlaytime SET hidden=" + state + " WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                playerDataFile(player).getConfig().set("hidden", state);
                break;
            case "mysql":
                try {
                    PreparedStatement statement = SystemUtils.getSqlLibrary().getConnection().prepareStatement(SQLQuery);
                    // Not sure if this will be a bug. If so then modify `statement.execute();` to the line below.
                    // TODO - statement.executeUpdate();
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

}
