package net.peligon.PeligonPrison;

import net.peligon.PeligonPrison.commands.*;
import net.peligon.PeligonPrison.libaries.CustomConfig;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.libaries.storage.SQLite;
import net.peligon.PeligonPrison.listeners.PickupEvent;
import net.peligon.PeligonPrison.listeners.SmeltEvent;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin implements Listener {

    public static Main getInstance;

    public CustomConfig fileMessage;

    public HashMap<UUID, String> playerRanks = new HashMap<>();
    public HashMap<UUID, String> playerPrestige = new HashMap<>();
    public HashMap<String, Integer> mines = new HashMap<>();
    public boolean resetMinesOnTimer = false;
    public HashMap<UUID, List<UUID>> gangs = new HashMap<>();
    public HashMap<UUID, List<ItemStack>> backpack = new HashMap<>();
    public HashMap<UUID, Integer> experienceMultiplier = new HashMap<>();

    public void onEnable() {
        // ---- [ Initializing instance of main class | manager classes | register placeholder ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();

        // ---- [ Creating the SQLLite connection ] ----
        SQLite sqlLite = new SQLite();
        sqlLite.loadTables();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Startup message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));
    }

    public void onDisable() {
        // ---- [ shutdown message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("shutdown")));
    }

    public void loadCommands() {
        getCommand("pelprison").setExecutor(new cmdReload());
        getCommand("autoblock").setExecutor(new cmdAutoBlock());
        getCommand("autosmelt").setExecutor(new cmdAutoSmelt());
        getCommand("autopickup").setExecutor(new cmdAutoPickup());
        getCommand("autosell").setExecutor(new cmdAutoSell());
    }
    public void loadEvents() {
        Arrays.asList(
                new SmeltEvent(),
                new PickupEvent()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

}
