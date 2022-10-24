package net.peligon.Playtime;

import net.peligon.Playtime.commands.cmdLeaderboard;
import net.peligon.Playtime.commands.cmdReload;
import net.peligon.Playtime.commands.playtimeCommand;
import net.peligon.Playtime.libaries.storage.CustomConfig;
import net.peligon.Playtime.libaries.UpdateChecker;
import net.peligon.Playtime.libaries.Utils;
import net.peligon.Playtime.libaries.storage.SQLibrary;
import net.peligon.Playtime.libaries.storage.SQLiteLibrary;
import net.peligon.Playtime.libaries.timePlayedTimer;
import net.peligon.Playtime.listeners.menuListener;
import net.peligon.Playtime.listeners.playTimeManage;
import net.peligon.Playtime.managers.placeholderAPI;
import net.peligon.Playtime.managers.mgrPlayTime;
import net.peligon.Plugins.commands.peligonPluginsMenuCommand;
import net.peligon.Plugins.listeners.PeligonPluginMenuEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static net.peligon.Playtime.libaries.storage.SQLiteLibrary.connection;

@SuppressWarnings("ALL")
public final class Main extends JavaPlugin {

    // Create an instance of the main class
    public static Main getInstance;

    // Creating instances of customConfig files
    public CustomConfig languageFile;

    // Storage type; File, MySQL, SQLite
    public String storageType = "file";

    public void onEnable() {
        // Initializing instance of main class.
        getInstance = this;

        // Loading customConfig files.
        saveDefaultConfig();

        // Initializing lang file and saving the default version.
        languageFile = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.lang"), true);
        languageFile.saveDefaultConfig();

        // Getting storage type from config
        if (getConfig().contains("Storage.database") && getConfig().getString("Storage.database").equalsIgnoreCase("File") ||
                getConfig().getString("Storage.database").equalsIgnoreCase("MySQL") ||
                getConfig().getString("Storage.database").equalsIgnoreCase("SQLite"))
            storageType = getConfig().getString("Storage.database").toLowerCase();

        // Loading all commands and events.
        loadEvents();
        loadCommands();

        // Registering placeholderAPI
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new placeholderAPI().register();
        }

        // Checking for updates.
        if (getConfig().getBoolean("check-for-updates", true)) versionChecker();

        // Sending startup message to console.
        if (this.languageFile != null)
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.languageFile.getConfig().getString("startup")));
    }

    public void onDisable() {
        // Sending plugin shutdown message if messages file is not null
        if (this.languageFile == null)
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.languageFile.getConfig().getString("shutdown")));
    }

    // Getting the version from https://www.spigot.org and comparing the version to current version.
    private void versionChecker() {
        new UpdateChecker(this, 101707).getVersion(version -> {
            // Creating variables to store version to make it easily readable.
            double spigotVersion = Double.parseDouble(version);
            double pluginVersion = Double.parseDouble(this.getDescription().getVersion());

            // If spigot version is greater than the current plugin version then send console a message
            // saving that new version is available along with the link to it.
            if (spigotVersion > pluginVersion) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(languageFile.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(languageFile.getConfig().getString("plugin-link")));
            }
        });
    }

    // Registering all events.
    public void loadEvents() {
        Arrays.asList(
                // Listener for peligon plugin menu.
                new PeligonPluginMenuEvent()

                // Other listeners.
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    // Registering all commands : Can ignore capitals.
    public void loadCommands() {
        // Command for peligon plugin menu.
        getCommand("peligon").setExecutor(new peligonPluginsMenuCommand());

        // Other commands.
        getCommand("playtime").setExecutor(new playtimeCommand());
    }













































    public mgrPlayTime playerTime;

    public SQLibrary sqlLibrary;

    public void onEsdfnable() {
        // ---- [ Setting up storage ] ----
        setupStorage();

        // ---- [ Initializing instance of manager classes | register placeholder ] ----
        playerTime = new mgrPlayTime();


        // ---- [ Calling repeating tasks ] ----
        new timePlayedTimer().runTaskTimer(this, 20 * 5, 20 * 5);

    }

    public void loadCosdfmmands() {
        getCommand("pelplaytime").setExecutor(new cmdReload());
//        getCommand("playtime").setExecutor(new cmdTimePlayed());
        getCommand("playtimetop").setExecutor(new cmdLeaderboard());
    }

    public void loadEvsfents() {
        Arrays.asList(
                new PeligonPluginMenuEvent(),
                new playTimeManage(),
                new menuListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void setupStorage() {
        if (getConfig().getString("Storage.database", "SQLite").equalsIgnoreCase("sqlite")) {
            SQLiteLibrary sqlLite = new SQLiteLibrary();

            try {
                sqlLite.getSQLConnection();

                if (connection == null) {
                    getServer().getConsoleSender().sendMessage("Unable to establish a connection to SQLite.");
                    return;
                }

                String table = "CREATE TABLE IF NOT EXISTS server(uuid PRIMARY KEY, timePlayed, " +
                        "lastUpdated, paused INTEGER DEFAULT 0, hidden INTEGER DEFAULT 0);";
                String updateTable = "ALTER TABLE server ADD COLUMN paused INTEGER DEFAULT 0;";
                String updateTable2 = "ALTER TABLE server ADD COLUMN hidden INTEGER DEFAULT 0;";

                Statement statement = connection.createStatement();
                statement.execute(table);

                ResultSet rs = statement.executeQuery("PRAGMA table_info(server)");
                boolean paused = false;
                boolean hidden = false;
                while (rs.next()) {
                    if (rs.getString("name").equals("paused")) {
                        paused = true;
                    }
                    if (rs.getString("name").equals("hidden")) {
                        hidden = true;
                    }
                }
                if (!paused) {
                    statement.execute(updateTable);
                }

                if (!hidden) {
                    statement.execute(updateTable2);
                }
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            this.storageType = "SQLite";
        } else if (getConfig().getString("Storage.database", "SQLite").equalsIgnoreCase("MySQL")) {
            sqlLibrary = new SQLibrary(getConfig().getString("Storage.MySQL.host"),
                    getConfig().getInt("Storage.MySQL.port"),
                    getConfig().getString("Storage.MySQL.database"),
                    getConfig().getString("Storage.MySQL.username"),
                    getConfig().getString("Storage.MySQL.password"));

            if (sqlLibrary.getConnection() == null) {
                System.out.println("Unable to establish a connection to MySQL.");
                return;
            }

            try {
                sqlLibrary.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Playtime" +
                        " (uuid VARCHAR(36) NOT NULL, timePlayed, lastUpdated, paused INTEGER DEFAULT 0," +
                        " hidden INTEGER DEFAULT 0, PRIMARY KEY (uuid));").executeUpdate();

                sqlLibrary.getConnection().prepareStatement("ALTER TABLE Playtime ADD COLUMN IF NOT EXISTS paused INTEGER DEFAULT 0;").executeUpdate();
                sqlLibrary.getConnection().prepareStatement("ALTER TABLE Playtime ADD COLUMN IF NOT EXISTS hidden INTEGER DEFAULT 0;").executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            this.storageType = "MySQL";
        }
    }
}
