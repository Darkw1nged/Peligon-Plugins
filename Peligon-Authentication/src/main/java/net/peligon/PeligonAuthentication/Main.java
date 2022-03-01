package net.peligon.PeligonAuthentication;

import net.peligon.PeligonAuthentication.commands.cmdAuthReset;
import net.peligon.PeligonAuthentication.libaries.CustomConfig;
import net.peligon.PeligonAuthentication.libaries.UpdateChecker;
import net.peligon.PeligonAuthentication.libaries.Utils;
import net.peligon.PeligonAuthentication.libaries.storage.SQLite;
import net.peligon.PeligonAuthentication.listener.Authentication;
import net.peligon.PeligonAuthentication.listener.playerJoin;
import net.peligon.PeligonAuthentication.manager.mgrAuthentication;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    public mgrAuthentication authentication;


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
        authentication = new mgrAuthentication();

        // ---- [ Setting up SQLite ] ----
        SQLite sqlLite = new SQLite();
        sqlLite.loadTables();

        // ---- [ Startup message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));

        // ---- [ Check if server has most updated version ] ----
//        if (getConfig().getBoolean("check-for-updates", true)) {
//            versionChecker();
//        }
    }

    public void onDisable() {
        // ---- [ shutdown message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("shutdown")));
    }

    public void loadCommands() {
        getCommand("authentication").setExecutor(new cmdAuthReset());
    }
    public void loadEvents() {
        Arrays.asList(
                new Authentication(),
                new playerJoin()
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
