package net.peligon.PeligonEconomy.libaries;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.storage.SQLibrary;
import net.peligon.PeligonEconomy.libaries.storage.SQLiteLibrary;

import java.sql.SQLException;
import java.sql.Statement;

import static net.peligon.PeligonEconomy.libaries.storage.SQLiteLibrary.connection;

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
                    String sqlQuery = "CREATE TABLE IF NOT EXISTS peligonEconomy (" +
                            "uuid VARCHAR(36) NOT NULL," +
                            "cash INT(16) NOT NULL," +
                            "bankBalance INT(16) NOT NULL," +
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
                    String sqlQuery = "CREATE TABLE IF NOT EXISTS peligonEconomy (" +
                            "uuid VARCHAR(36) NOT NULL," +
                            "cash INT(16) NOT NULL," +
                            "bankBalance INT(16) NOT NULL," +
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

    // Getting SQLibrary instance.
    public static SQLibrary getSQLibrary() {
        return sqlLibrary;
    }

}