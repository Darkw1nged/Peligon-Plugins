package net.peligon.PeligonSkyblock.libaries.storage;

import net.peligon.PeligonSkyblock.Main;
import net.peligon.PeligonSkyblock.libaries.Utils;

import java.io.File;
import java.sql.*;

import static org.bukkit.Bukkit.getServer;

public class SQLite {

    public static Connection connection;
    private final Main plugin = Main.getInstance;

    public void getSQLConnection() throws SQLException {
        File dbFolder = new File(plugin.getDataFolder(), "peligon.db");
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbFolder);

        try {
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
            }
            getServer().getConsoleSender().sendMessage(Utils.chatColor("&bConnection to database has been successful."));
        } catch (SQLException e) {
            throw new Error("Connection to database could not be made.\nPlease notify the developer of this.\n");
        }
    }

    public void loadTables() {
        try {
            getSQLConnection();
            String tbl_Users = "CREATE TABLE IF NOT EXISTS plg_users(uuid PRIMARY KEY, IslandID VARCHAR(255));";

            try {
                Statement statement = connection.createStatement();
                {
                    statement.execute(tbl_Users);
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
