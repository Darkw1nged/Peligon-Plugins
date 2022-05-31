package net.peligon.PeligonCore;

import net.peligon.PeligonCore.commands.*;
import net.peligon.PeligonCore.libaries.CustomConfig;
import net.peligon.PeligonCore.libaries.UpdateChecker;
import net.peligon.PeligonCore.libaries.Utils;
import net.peligon.PeligonCore.libaries.storage.SQLite;
import net.peligon.PeligonCore.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@SuppressWarnings("ALL")
public final class Main extends JavaPlugin {

    public static Main getInstance;

    public CustomConfig fileMessage;
    public CustomConfig fileChatSettings = new CustomConfig(this, "Chat Settings", true);

    public void onEnable() {
        // ---- [ Initializing instance of main class ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();
        fileChatSettings.saveDefaultConfig();

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
        getCommand("day").setExecutor(new cmdWorldDay());
        getCommand("noon").setExecutor(new cmdWorldNoon());
        getCommand("night").setExecutor(new cmdWorldNight());
        getCommand("time").setExecutor(new cmdWorldTime());
        getCommand("weather").setExecutor(new cmdWorldWeather());
        getCommand("difficulty").setExecutor(new cmdWorldDifficulty());
        getCommand("fly").setExecutor(new cmdFly());
        getCommand("vanish").setExecutor(new cmdVanish());
        getCommand("godmode").setExecutor(new cmdGodmode());
        getCommand("kill").setExecutor(new cmdKill());
        getCommand("back").setExecutor(new cmdBack());
        getCommand("teleport").setExecutor(new cmdTeleport());
        getCommand("teleporthere").setExecutor(new cmdTeleportHere());
        getCommand("clearchat").setExecutor(new cmdClearChat());
        getCommand("broadcast").setExecutor(new cmdBroadcast());
        getCommand("sudo").setExecutor(new cmdSudo());

        // ---- [ Player commands ] ----
        getCommand("experience").setExecutor(new cmdExperience());
        getCommand("spawn").setExecutor(new cmdSpawn());
        getCommand("warp").setExecutor(new cmdWarp());
        getCommand("warps").setExecutor(new cmdWarps());
        getCommand("craft").setExecutor(new cmdCraft());
        getCommand("disposal").setExecutor(new cmdDisposal());
        getCommand("enderchest").setExecutor(new cmdEnderchest());
        getCommand("playertime").setExecutor(new cmdPlayerTime());
        getCommand("playerday").setExecutor(new cmdPlayerDay());
        getCommand("playernight").setExecutor(new cmdPlayerNight());
        getCommand("playernoon").setExecutor(new cmdPlayerNoon());
        getCommand("playerweather").setExecutor(new cmdPlayerWeather());
        getCommand("sethome").setExecutor(new cmdSetHome());
        getCommand("deletehome").setExecutor(new cmdDeleteHome());
        getCommand("home").setExecutor(new cmdHome());
        getCommand("homes").setExecutor(new cmdHomes());
        getCommand("teleporttoggle").setExecutor(new cmdTeleportToggle());
        getCommand("teleportblock").setExecutor(new cmdTeleportBlock());
        getCommand("teleportunblock").setExecutor(new cmdTeleportUnblock());
        getCommand("teleportrequest").setExecutor(new cmdTeleportRequest());
        getCommand("teleportcancel").setExecutor(new cmdTeleportCancel());
        getCommand("teleportaccept").setExecutor(new cmdTeleportAccept());
        getCommand("teleportdeny").setExecutor(new cmdTeleportDeny());
    }

    public void loadEvents() {
        Arrays.asList(
                new teleportBack(),
                new blockedCommands(),
                new durabilityWarning(),
                new teleportCancel(),
                new godmode(),
                new netherWater(),
                new worldProtections(),
                new bannedItems(),
                new itemCooldown(),
                new doubleJump(),
                new noVoid()
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
