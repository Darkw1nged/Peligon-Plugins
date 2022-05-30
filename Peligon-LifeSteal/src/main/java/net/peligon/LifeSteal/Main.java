package net.peligon.LifeSteal;

import net.milkbowl.vault.economy.Economy;
import net.peligon.LifeSteal.commands.*;
import net.peligon.LifeSteal.libaries.CustomConfig;
import net.peligon.LifeSteal.libaries.UpdateChecker;
import net.peligon.LifeSteal.libaries.Utils;
import net.peligon.LifeSteal.libaries.combatTagTimer;
import net.peligon.LifeSteal.libaries.storage.SQLibrary;
import net.peligon.LifeSteal.libaries.storage.SQLiteLibrary;
import net.peligon.LifeSteal.listeners.*;
import net.peligon.LifeSteal.manager.mgrBounty;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Arrays;

public final class Main extends JavaPlugin implements Listener {

    public static Main getInstance;
    public mgrBounty bounties;

    public SQLibrary sqlLibrary;
    public String storageType = "SQLite";

    private static Economy econ = null;

    public CustomConfig fileMessage;
    public CustomConfig fileDeathMessage = new CustomConfig(this, "death messages", true);

    public void onEnable() {
        // ---- [ Initializing instance of main class] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        fileDeathMessage.saveDefaultConfig();
        saveDefaultConfig();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.lang"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Setting up databases ] ----
        setupStorage();

        // ---- [ Calling repeating tasks ] ----
        new combatTagTimer().runTaskTimerAsynchronously(this, 20 * 2, 20 * 2);

        if (!setupEconomy() ) {
            System.out.println(Utils.chatColor(this.fileMessage.getConfig().getString("no-vault-dependency")));
            return;
        }

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
        getCommand("pellives").setExecutor(new cmdReload());
        getCommand("lifesteal").setExecutor(new cmdLifeSteal());
        getCommand("lives").setExecutor(new cmdLives());
        getCommand("combattag").setExecutor(new cmdCombatTag());
        getCommand("bounty").setExecutor(new cmdBounty());
    }

    public void loadEvents() {
        Arrays.asList(
                new lifeUpdate(),
                new deathPenalty(),
                new lightningStrike(),
                new keepExperience(),
                new keepInventory(),
                new autoRespawn(),
                new customDeathMessages(),
                new combatTag(),
                new deathChest(),
                new damageIndicator(),
                new healthIndicator(),
                new bountyEvents()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void versionChecker() {
        new UpdateChecker(this, 100900).getVersion(version -> {
            if (!version.equals(this.getDescription().getVersion())) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-link")));
            }
        });
    }

    public Economy getEconomy() {
        return econ;
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
                sqlLibrary.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS LifeSteal" +
                        " (uuid VARCHAR(36) NOT NULL, bounty INT(32) DEFAULT 0, PRIMARY KEY (uuid));").executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            this.storageType = "MySQL";
        }
    }
}
