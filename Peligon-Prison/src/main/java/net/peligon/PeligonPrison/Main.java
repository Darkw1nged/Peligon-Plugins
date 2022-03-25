package net.peligon.PeligonPrison;

import net.milkbowl.vault.economy.Economy;
import net.peligon.PeligonPrison.commands.*;
import net.peligon.PeligonPrison.libaries.CustomConfig;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.libaries.storage.SQLite;
import net.peligon.PeligonPrison.listeners.PickupEvent;
import net.peligon.PeligonPrison.listeners.SmeltEvent;
import net.peligon.PeligonPrison.listeners.accountSetup;
import net.peligon.PeligonPrison.manager.mgrPrestige;
import net.peligon.PeligonPrison.manager.mgrRank;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin implements Listener {

    public static Main getInstance;
    private Economy econ = null;
    public mgrRank rankManager;
    public mgrPrestige prestigeManager;

    public CustomConfig fileMessage;

    public HashMap<String, Integer> mines = new HashMap<>();
    public boolean resetMinesOnTimer = false;
    public HashMap<UUID, List<ItemStack>> backpack = new HashMap<>();

    public void onEnable() {
        // ---- [ Initializing instance of main class | manager classes | register placeholder ] ----
        getInstance = this;
        rankManager = new mgrRank();
        prestigeManager = new mgrPrestige();

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();

        // ---- [ Creating the SQLLite connection ] ----
        SQLite sqlLite = new SQLite();
        sqlLite.loadTables();

        // ---- [ Loading Economy ] ----
        setupEconomy();

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
        getCommand("rankup").setExecutor(new cmdRankup());
        getCommand("rankupall").setExecutor(new cmdRankupAll());
        getCommand("prestige").setExecutor(new cmdPrestige());
    }
    public void loadEvents() {
        Arrays.asList(
                new accountSetup(),
                new SmeltEvent(),
                new PickupEvent()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    public Economy getEconomy() {
        return econ;
    }
}
