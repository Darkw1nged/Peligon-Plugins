package net.peligon.LifeSteal;

import net.peligon.LifeSteal.commands.cmdLifeSteal;
import net.peligon.LifeSteal.commands.cmdLives;
import net.peligon.LifeSteal.commands.cmdReload;
import net.peligon.LifeSteal.libaries.CustomConfig;
import net.peligon.LifeSteal.libaries.UpdateChecker;
import net.peligon.LifeSteal.libaries.Utils;
import net.peligon.LifeSteal.libaries.storage.SQLite;
import net.peligon.LifeSteal.listeners.AccountSetup;
import net.peligon.LifeSteal.listeners.lifeUpdate;
import net.peligon.LifeSteal.manager.mgrLives;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin implements Listener {

    public static Main getInstance;
    public mgrLives lives;

    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Initializing instance of manager classes ] ----
        lives = new mgrLives();

        // ---- [ Setting up SQLite ] ----
        SQLite sqlLite = new SQLite();
        sqlLite.loadTables();

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
    }

    public void loadEvents() {
        Arrays.asList(
                new AccountSetup(),
                new lifeUpdate()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void versionChecker() {
        new UpdateChecker(this, 100900).getVersion(version -> {
            if (!version.equals(this.getDescription().getVersion())) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-link")));
            }
        });
    }
}
