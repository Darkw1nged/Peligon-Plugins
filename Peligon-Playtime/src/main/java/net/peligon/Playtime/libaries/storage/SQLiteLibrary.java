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
            getServer().getConsoleSender().sendMessage(Utils.chatColor("&aConnection to database has been successful."));
        } catch (SQLException e) {
            throw new Error("Connection to database could not be made.\nPlease notify the developer of this.\n");
        }
    }

    public void loadTables() {
        try {
            getSQLConnection();
            String table = "CREATE TABLE IF NOT EXISTS server(uuid PRIMARY KEY, timePlayed, lastUpdated);";
            String createCollum = "ALTER TABLE server ADD COLUMN IF NOT EXISTS paused INTEGER DEFAULT 0;";
            String createCollum2 = "ALTER TABLE server ADD COLUMN IF NOT EXISTS hidden INTEGER DEFAULT 0;";

            try {
                Statement statement = connection.createStatement();
                {
                    statement.execute(table);
                    statement.execute(createCollum);
                    statement.execute(createCollum2);
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new Error("Connection to database could not be made.\nPlease notify the developer of this.\n");
        }

    }

}
