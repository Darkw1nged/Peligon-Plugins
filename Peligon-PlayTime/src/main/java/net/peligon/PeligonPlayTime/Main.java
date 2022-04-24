package net.peligon.PeligonPlayTime;

import net.peligon.PeligonPlayTime.commands.cmdLeaderboard;
import net.peligon.PeligonPlayTime.commands.cmdReload;
import net.peligon.PeligonPlayTime.commands.cmdReset;
import net.peligon.PeligonPlayTime.commands.cmdTimePlayed;
import net.peligon.PeligonPlayTime.libaries.CustomConfig;
import net.peligon.PeligonPlayTime.libaries.UpdateChecker;
import net.peligon.PeligonPlayTime.libaries.Utils;
import net.peligon.PeligonPlayTime.libaries.storage.SQLite;
import net.peligon.PeligonPlayTime.libaries.timePlayedTimer;
import net.peligon.PeligonPlayTime.listeners.playTimeManage;
import net.peligon.PeligonPlayTime.managers.mgrPlaceholders;
import net.peligon.PeligonPlayTime.managers.mgrPlayTime;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@SuppressWarnings("ALL")
public final class Main extends JavaPlugin {

    public static Main getInstance;
    public mgrPlayTime playerTime;

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

        // ---- [ Setting up SQLite ] ----
        SQLite sqlLite = new SQLite();
        sqlLite.loadTables();

        // ---- [ Initializing instance of manager classes | register placeholder ] ----
        playerTime = new mgrPlayTime();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new mgrPlaceholders().register();
        }

        // ---- [ Calling repeating tasks ] ----
        new timePlayedTimer().runTaskLaterAsynchronously(this, 20 * 5);

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
        getCommand("timeplayed").setExecutor(new cmdTimePlayed());
        getCommand("timeplayedtop").setExecutor(new cmdLeaderboard());
        getCommand("playtime").setExecutor(new cmdReset());
    }

    public void loadEvents() {
        Arrays.asList(
                new playTimeManage()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void versionChecker() {
        new UpdateChecker(this, 0).getVersion(version -> {
            if (!version.equals(this.getDescription().getVersion())) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-link")));
            }
        });
    }
}
