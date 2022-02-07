package net.peligon.PeligonEnchants.libaries.storage;

import net.peligon.PeligonEnchants.Main;
import net.peligon.PeligonEnchants.libaries.Utils;

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
                getServer().getConsoleSender().sendMessage("The driver name is " + meta.getDriverName());
                getServer().getConsoleSender().sendMessage(Utils.chatColor("A new database has been created."));
            }
            getServer().getConsoleSender().sendMessage(Utils.chatColor("&bConnection to database has been successful."));
        } catch (SQLException e) {
            throw new Error("Connection to database could not be made.\nPlease notify the developer of this.\n");
        }
    }

    public void loadTables() {
        try {
            getSQLConnection();
            String tbl_Money = "CREATE TABLE IF NOT EXISTS plg_money(uuid PRIMARY KEY, cash REAL, bank REAL);";

            try {
                Statement statement = connection.createStatement();
                {
                    statement.execute(tbl_Money);
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
