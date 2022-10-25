package net.peligon.Playtime.libaries;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.storage.SQLibrary;
import net.peligon.Playtime.libaries.storage.SQLiteLibrary;
import net.peligon.Playtime.libaries.struts.leaderboardResult;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.util.stream.Collectors.toMap;
import static net.peligon.Playtime.libaries.storage.SQLiteLibrary.connection;

public class systemUtils {

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
                            "uuid VARCHAR(36) NOT NULL," +
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
                            "uuid VARCHAR(36) NOT NULL," +
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
                try {
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
            case "file":
                // Getting the data folder.
                File dataFolder = new File(plugin.getDataFolder() + "/data");
                File[] files = dataFolder.listFiles();

                // If files are null, return the results.
                if (files == null) return results;

                // Create a temporary list to store the results.
                Map<UUID, Long> tempResults = new HashMap<>();

                // Loop through all files.
                for (File file : files) {
                    // Getting the UUID from the file name.
                    UUID uuid = UUID.fromString(file.getName().replace(".yml", ""));

                    // Get player from uuid.
                    OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

                    // If the player's playtime is hidden, skip.
                    if (playerUtils.isHidden(player)) continue;

                    // If the player is online, update the playtime.
                    if (player.isOnline()) playerUtils.addPlaytime(player);

                    // Getting the player's playtime.
                    long playtime = playerUtils.getPlaytime(player);
                    tempResults.put(uuid, playtime);
                }

                // Sorting the results by playtime in descending order.
                tempResults = tempResults
                        .entrySet()
                        .stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

                // Loop through the results.
                int i = 0;
                for (Map.Entry<UUID, Long> entry : tempResults.entrySet()) {
                    // Check if results is full.
                    if (i > Math.min(plugin.getConfig().getInt("leaderboard.players"), tempResults.size())) break;

                    // Adding the results to the hashmap.
                    results.put(entry.getKey(), new leaderboardResult(entry.getKey(), i + 1, entry.getValue()));

                    // Incrementing the counter.
                    i++;
                }
                return results;
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
                try {
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
                        UUID.fromString(resultSet.getString("uuid")),
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
