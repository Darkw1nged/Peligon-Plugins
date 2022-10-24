package net.peligon.Playtime;

import net.peligon.Playtime.commands.playtimeTopCommand;
import net.peligon.Playtime.commands.reloadCommand;
import net.peligon.Playtime.commands.playtimeCommand;
import net.peligon.Playtime.libaries.*;
import net.peligon.Playtime.libaries.storage.CustomConfig;
import net.peligon.Playtime.listeners.teleportationCheck;
import net.peligon.Playtime.listeners.updatePlayerPlaytime;
import net.peligon.Plugins.commands.peligonPluginsMenuCommand;
import net.peligon.Plugins.listeners.PeligonPluginMenuEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

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

        // Setting up and checking storage.
        SystemUtils.createDatabase();
        SystemUtils.checkDatabase();

        // Loading all commands and events.
        loadEvents();
        loadCommands();

        // TODO ---- Subject to be removed -----
        // ---- [ Calling repeating tasks ] ----
        new timePlayedTimer().runTaskTimer(this, 20 * 5, 20 * 5);
        // TODO ---- Subject to be removed -----

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
                new PeligonPluginMenuEvent(),

                // Other listeners.
                new updatePlayerPlaytime(),
                new teleportationCheck()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    // Registering all commands : Can ignore capitals.
    public void loadCommands() {
        // Command for peligon plugin menu.
        getCommand("peligon").setExecutor(new peligonPluginsMenuCommand());

        // Other commands.
        getCommand("peligonplaytime").setExecutor(new reloadCommand());
        getCommand("playtime").setExecutor(new playtimeCommand());
        getCommand("playtimetop").setExecutor(new playtimeTopCommand());
    }
}
