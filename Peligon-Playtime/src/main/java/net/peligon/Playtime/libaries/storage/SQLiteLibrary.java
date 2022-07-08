package net.peligon.Playtime.libaries.storage;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.Utils;

import java.io.File;
import java.sql.*;

import static org.bukkit.Bukkit.getServer;

public class SQLiteLibrary {

    public static Connection connection;
    private final Main plugin = Main.getInstance;

    public void getSQLConnection() throws SQLException {
        File dbFolder = new File(plugin.getDataFolder(), "peligon.db");
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbFolder);

        try {
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
            }
            getServer().getConsoleSender().sendMessage("[PeligonPlaytime] Connection to database has been successful.");
        } catch (SQLException e) {
            throw new Error("Connection to database could not be made.\nPlease notify the developer of this.\n");
        }
    }

}
