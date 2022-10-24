package net.peligon.Playtime.libaries;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.storage.SQLibrary;
import net.peligon.Playtime.libaries.storage.SQLiteLibrary;
import net.peligon.Playtime.libaries.struts.leaderboardResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

import static net.peligon.Playtime.libaries.storage.SQLiteLibrary.connection;

public class SystemUtils {

    private final static Main plugin = Main.getInstance;
    private static SQLibrary sqlLibrary;

    // Creating the database(s).
    public static void createDatabase() {
        switch (plugin.storageType) {
            case "mysql":
                // Creating an instance of the SQLibrary class.
                // Trying to connect to the database.
                sqlLibrary = new SQLibrary(plugin.getConfig().getString("Storage.MySQL.host"),
                        plugin.getConfig().getInt("Storage.MySQL.port"),
                        plugin.getConfig().getString("Storage.MySQL.database"),
                        plugin.getConfig().getString("Storage.MySQL.username"),
                        plugin.getConfig().getString("Storage.MySQL.password"));

                try {
                    // Checking if the connection is null.
                    if (sqlLibrary.getConnection() == null) {
                        System.out.println("Unable to establish a connection to MySQL.");
                        return;
                    }

                    // Creating the query.
                    String sqlQuery = "CREATE TABLE IF NOT EXISTS peligonPlaytime (" +
                            "uuid VARCHAR(36) NOT NULL PRIMARY KEY," +
                            "playtime BIGINT NOT NULL," +
                            "lastUpdated BIGINT NOT NULL," +
                            "paused INT NOT NULL DEFAULT 0," +
                            "hidden INT NOT NULL DEFAULT 0," +
                            "PRIMARY KEY (uuid)" +
                            ");";

                    // Executing the query.
                    sqlLibrary.getConnection().prepareStatement(sqlQuery).execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            case "sqlite":
                // Getting an instance of the SQLiteLibrary.
                SQLiteLibrary sql = new SQLiteLibrary();

                try {
                    // Getting the connection.
                    sql.getSQLConnection();

                    // Check if the connection is null.
                    if (connection == null) {
                        System.out.println("Unable to establish a connection to SQLite.");
                        return;
                    }

                    // Creating the query.
                    String sqlQuery = "CREATE TABLE IF NOT EXISTS peligonPlaytime (" +
                            "uuid VARCHAR(36) NOT NULL PRIMARY KEY," +
                            "playtime BIGINT NOT NULL," +
                            "lastUpdated BIGINT NOT NULL," +
                            "paused INT NOT NULL DEFAULT 0," +
                            "hidden INT NOT NULL DEFAULT 0," +
                            "PRIMARY KEY (uuid)" +
                            ");";

                    // Executing the query.
                    Statement statement = connection.createStatement();
                    statement.execute(sqlQuery);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Checking the database(s) for the correct columns.
    public static void checkDatabase() {
        switch (plugin.storageType) {
            case "mysql":
                try {
                    // Checking if the connection is null.
                    if (sqlLibrary.getConnection() == null) {
                        System.out.println("Unable to establish a connection to MySQL.");
                        return;
                    }

                    // Check if all columns are present.
                    try {
                        sqlLibrary.getConnection().prepareStatement("SELECT paused FROM peligonPlaytime").execute();
                    } catch (SQLException e) {
                        sqlLibrary.getConnection().prepareStatement("ALTER TABLE peligonPlaytime ADD paused INT NOT NULL DEFAULT 0").execute();
                    }

                    try {
                        sqlLibrary.getConnection().prepareStatement("SELECT hidden FROM peligonPlaytime").execute();
                    } catch (SQLException e) {
                        sqlLibrary.getConnection().prepareStatement("ALTER TABLE peligonPlaytime ADD hidden INT NOT NULL DEFAULT 0").execute();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            case "sqlite":
                // Getting an instance of the SQLiteLibrary.
                SQLiteLibrary sql = new SQLiteLibrary();

                try {
                    // Getting the connection.
                    sql.getSQLConnection();

                    // Check if the connection is null.
                    if (connection == null) {
                        System.out.println("Unable to establish a connection to SQLite.");
                        return;
                    }

                    // Check if all columns are present.
                    try {
                        connection.prepareStatement("SELECT paused FROM peligonPlaytime").execute();
                    } catch (SQLException e) {
                        connection.prepareStatement("ALTER TABLE peligonPlaytime ADD paused INT NOT NULL DEFAULT 0").execute();
                    }

                    try {
                        connection.prepareStatement("SELECT hidden FROM peligonPlaytime").execute();
                    } catch (SQLException e) {
                        connection.prepareStatement("ALTER TABLE peligonPlaytime ADD hidden INT NOT NULL DEFAULT 0").execute();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Getting the top 10 players playtime from the database.
    public static HashMap<UUID, leaderboardResult> getPlaytimeLeaderboard() {
        // Creating a new Hashmap to store results.
        HashMap<UUID, leaderboardResult> results = new HashMap<>();

        // Creating query.
        String sqlQuery = "SELECT uuid, playtime, hidden FROM peligonPlaytime ORDER BY playtime DESC";

        switch (plugin.storageType) {
            case "mysql":
                try {
                    // Checking if the connection is null.
                    if (sqlLibrary.getConnection() == null) {
                        System.out.println("Unable to establish a connection to MySQL.");
                        return results;
                    }

                    // Executing the query.
                    PreparedStatement statement = sqlLibrary.getConnection().prepareStatement(sqlQuery);

                    // To reduce the duplicated code for processing the results.
                    // We have created a private method that we can just call.
                    return processResults(statement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                // Getting an instance of the SQLiteLibrary.
                SQLiteLibrary sql = new SQLiteLibrary();

                try {
                    // Getting the connection.
                    sql.getSQLConnection();

                    // Check if the connection is null.
                    if (connection == null) {
                        System.out.println("Unable to establish a connection to SQLite.");
                        return results;
                    }

                    // Executing the query.
                    PreparedStatement statement = connection.prepareStatement(sqlQuery);
                    return processResults(statement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return results;
    }

    private static HashMap<UUID, leaderboardResult> processResults(PreparedStatement statement) {
        HashMap<UUID, leaderboardResult> results = new HashMap<>();

        try {
            ResultSet resultSet = statement.executeQuery();

            // Track position of the player.
            int position = 1;

            // Looping through the results.
            while (resultSet.next()) {
                // If player is hidden, skip them.
                if (resultSet.getInt("hidden") == 1) {
                    continue;
                }

                // Create a new LeaderboardResult object.
                leaderboardResult result = new leaderboardResult(
                        position,
                        resultSet.getLong("playtime")
                );

                // Adding the results to the hashmap.
                results.put(UUID.fromString(resultSet.getString("uuid")), result);

                // Incrementing the position.
                position++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SQLibrary getSqlLibrary() {
        return sqlLibrary;
    }
}
