package net.peligon.LifeSteal.libaries.storage;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;

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
            if (connection != null) { DatabaseMetaData meta = connection.getMetaData(); }
            getServer().getConsoleSender().sendMessage(Utils.chatColor("&aConnection to database has been successful."));
        } catch (SQLException e) {
            throw new Error("Connection to database could not be made.\nPlease notify the developer of this.\n");
        }
    }

    public void loadTables() {
        try {
            getSQLConnection();
            String table = "CREATE TABLE IF NOT EXISTS server(uuid PRIMARY KEY, lives INTEGER);";
            String collum = "ALTER TABLE server ADD COLUMN bounty INT(32) DEFAULT 0;";

            try {
                Statement statement = connection.createStatement();
                {
                    statement.execute(table);
                    ResultSet rs = statement.executeQuery("PRAGMA table_info(server)");
                    boolean columnExists = false;
                    while (rs.next()) {
                        if (rs.getString("name").equalsIgnoreCase("bounty")) {
                            columnExists = true;
                        }
                    }
                    if (!columnExists) {
                        statement.execute(collum);
                    }
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
