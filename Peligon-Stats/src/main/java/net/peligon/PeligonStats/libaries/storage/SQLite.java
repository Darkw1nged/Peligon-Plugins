package net.peligon.PeligonStats.libaries.storage;

import net.peligon.PeligonStats.Main;
import net.peligon.PeligonStats.libaries.Utils;

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
                System.out.println("The driver name is " + meta.getDriverName());
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

            String tbl_Smelting = "CREATE TABLE IF NOT EXISTS plg_skill_Smelting(uuid PRIMARY KEY, rank INTEGER, experience INTEGER);";
            String tbl_Repair = "CREATE TABLE IF NOT EXISTS plg_skill_Repair(uuid PRIMARY KEY, rank INTEGER, experience INTEGER);";
            String tbl_Alchemy = "CREATE TABLE IF NOT EXISTS plg_skill_Alchemy(uuid PRIMARY KEY, rank INTEGER, experience INTEGER);";
            String tbl_Acrobatics = "CREATE TABLE IF NOT EXISTS plg_skill_Acrobatics(uuid PRIMARY KEY, rank INTEGER, experience INTEGER);";
            String tbl_Mining = "CREATE TABLE IF NOT EXISTS plg_skill_Mining(uuid PRIMARY KEY, rank INTEGER, experience INTEGER);";
            String tbl_Lumberjack = "CREATE TABLE IF NOT EXISTS plg_skill_Lumberjack(uuid PRIMARY KEY, rank INTEGER, experience INTEGER);";
            String tbl_Fishing = "CREATE TABLE IF NOT EXISTS plg_skill_Fishing(uuid PRIMARY KEY, rank INTEGER, experience INTEGER);";
            String tbl_Herbalism = "CREATE TABLE IF NOT EXISTS plg_skill_Herbalism(uuid PRIMARY KEY, rank INTEGER, experience INTEGER);";;
            String tbl_Excavation = "CREATE TABLE IF NOT EXISTS plg_skill_Excavation(uuid PRIMARY KEY, rank INTEGER, experience INTEGER);";


            try {
                Statement statement = connection.createStatement();
                {
                    statement.execute(tbl_Smelting);
                    statement.execute(tbl_Repair);
                    statement.execute(tbl_Alchemy);
                    statement.execute(tbl_Acrobatics);
                    statement.execute(tbl_Mining);
                    statement.execute(tbl_Lumberjack);
                    statement.execute(tbl_Fishing);
                    statement.execute(tbl_Herbalism);
                    statement.execute(tbl_Excavation);
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
