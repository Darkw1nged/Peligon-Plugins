package net.Peligon.PeligonCore;

import net.Peligon.PeligonCore.commands.*;
import net.Peligon.PeligonCore.libaries.CustomConfig;
import net.Peligon.PeligonCore.libaries.UpdateChecker;
import net.Peligon.PeligonCore.libaries.Utils;
import net.Peligon.PeligonCore.libaries.storage.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main getInstance;

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
        getCommand("pelcore").setExecutor(new cmdReload());

        // ---- [ Fun commands ] ----
        getCommand("hat").setExecutor(new cmdHat());
        getCommand("heal").setExecutor(new cmdHeal());
        getCommand("feed").setExecutor(new cmdFeed());
        getCommand("suicide").setExecutor(new cmdSuicide());

        // ---- [ Admin commands ] ----
        getCommand("gamemode").setExecutor(new cmdGamemode());
        getCommand("gmadventure").setExecutor(new cmdGamemodeAdventure());
        getCommand("gmsurvival").setExecutor(new cmdGamemodeSurvival());
        getCommand("gmcreative").setExecutor(new cmdGamemodeCreative());
        getCommand("gmspectator").setExecutor(new cmdGamemodeSpectator());
        getCommand("setspawn").setExecutor(new cmdSetSpawn());
        getCommand("setwarp").setExecutor(new cmdSetWarp());
        getCommand("deletewarp").setExecutor(new cmdDeleteWarp());

        // ---- [ Player commands ] ----
        getCommand("spawn").setExecutor(new cmdSpawn());
        getCommand("warp").setExecutor(new cmdWarp());
        getCommand("warps").setExecutor(new cmdWarps());
        getCommand("craft").setExecutor(new cmdCraft());
        getCommand("disposal").setExecutor(new cmdDisposal());
        getCommand("enderchest").setExecutor(new cmdEnderchest());
    }

    public void loadEvents() {
//        Arrays.asList(
//        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
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
