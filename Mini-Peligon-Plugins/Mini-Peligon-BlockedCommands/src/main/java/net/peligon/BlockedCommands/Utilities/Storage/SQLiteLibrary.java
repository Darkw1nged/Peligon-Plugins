package net.peligon.BlockedCommands.Utilities.Storage;


import net.peligon.BlockedCommands.Main;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteLibrary {

    public static Connection connection;
    private final Main plugin = Main.getInstance;

    public void getSQLConnection() throws SQLException {
        File dbFolder = new File(plugin.getDataFolder(), plugin.getName() + ".db");
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbFolder);

        try {
            if (connection != null) { DatabaseMetaData meta = connection.getMetaData(); }
        } catch (SQLException e) {
            log("An error occurred when trying to close the MySQL database connection.");
            log("If this error persists please report the following Error code to the plugin developer: ERSQL-101-A");
        }
    }

    private void log(String message) {
        System.out.println(message);
    }

}
