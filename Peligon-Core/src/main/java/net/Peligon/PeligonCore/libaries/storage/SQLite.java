package net.Peligon.PeligonCore.libaries.storage;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;

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
            getServer().getConsoleSender().sendMessage(Utils.chatColor("&aConnection to database has been successful."));
        } catch (SQLException e) {
            throw new Error("Connection to database could not be made.\nPlease notify the developer of this.\n");
        }
    }

    public void loadTables() {
        try {
            getSQLConnection();
            String table = "CREATE TABLE IF NOT EXISTS player(uuid PRIMARY KEY, days INTEGER, hours INTEGER, minutes INTEGER, seconds INTEGER);";
            String table2 = "CREATE TABLE IF NOT EXISTS server(totalPlayers INTEGER);";

            try {
                Statement statement = connection.createStatement();
                {
                    statement.execute(table);
                    statement.execute(table2);
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
