package net.peligon.Playtime;

import net.peligon.Playtime.commands.cmdLeaderboard;
import net.peligon.Playtime.commands.cmdReload;
import net.peligon.Playtime.commands.cmdTimePlayed;
import net.peligon.Playtime.libaries.CustomConfig;
import net.peligon.Playtime.libaries.UpdateChecker;
import net.peligon.Playtime.libaries.Utils;
import net.peligon.Playtime.libaries.storage.SQLibrary;
import net.peligon.Playtime.libaries.storage.SQLiteLibrary;
import net.peligon.Playtime.libaries.timePlayedTimer;
import net.peligon.Playtime.listeners.playTimeManage;
import net.peligon.Playtime.managers.mgrPlaceholders;
import net.peligon.Playtime.managers.mgrPlayTime;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Arrays;

@SuppressWarnings("ALL")
public final class Main extends JavaPlugin {

    public static Main getInstance;
    public mgrPlayTime playerTime;

    public SQLibrary sqlLibrary;
    public String storageType = "SQLite";

    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Checking if the server has the dependencies, if not disable ] ----
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("no-plugin-dependency")));
            getServer().getPluginManager().disablePlugin(this);
        }

        // ---- [ Setting up storage ] ----
        setupStorage();

        // ---- [ Initializing instance of manager classes | register placeholder ] ----
        playerTime = new mgrPlayTime();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new mgrPlaceholders().register();
        }

        // ---- [ Calling repeating tasks ] ----
        new timePlayedTimer().runTaskTimer(this, 20 * 5, 20 * 5);

        // ---- [ Startup message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));

        // ---- [ Check if server has most updated version ] ----
        if (getConfig().getBoolean("check-for-updates", true)) {
            versionChecker();
        }
    }

    public void onDisable() {
        // ---- [ shutdown message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("shutdown")));
    }

    public void loadCommands() {
        getCommand("pelplaytime").setExecutor(new cmdReload());
        getCommand("playtime").setExecutor(new cmdTimePlayed());
        getCommand("playtimetop").setExecutor(new cmdLeaderboard());
    }

    public void loadEvents() {
        Arrays.asList(
                new playTimeManage()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void versionChecker() {
        new UpdateChecker(this, 101707).getVersion(version -> {
            if (!version.equals(this.getDescription().getVersion())) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-link")));
            }
        });
    }

    private void setupStorage() {
        if (getConfig().getString("Storage.database", "SQLite").equalsIgnoreCase("sqlite")) {
            SQLiteLibrary sqlLite = new SQLiteLibrary();
            sqlLite.loadTables();
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
                        " (uuid VARCHAR(36) NOT NULL, timePlayed, lastUpdated, PRIMARY KEY (uuid));").executeUpdate();

                sqlLibrary.getConnection().prepareStatement("ALTER TABLE Playtime ADD COLUMN IF NOT EXISTS paused INTEGER DEFAULT 0;").executeUpdate();
                sqlLibrary.getConnection().prepareStatement("ALTER TABLE Playtime ADD COLUMN IF NOT EXISTS hidden INTEGER DEFAULT 0;").executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            this.storageType = "MySQL";
        }
    }
}
