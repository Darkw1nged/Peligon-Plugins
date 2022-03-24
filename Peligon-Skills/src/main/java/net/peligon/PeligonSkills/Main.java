package net.peligon.PeligonSkills;

import net.peligon.PeligonSkills.libaries.CustomConfig;
import net.peligon.PeligonSkills.libaries.Utils;
import net.peligon.PeligonSkills.libaries.storage.SQLite;
import net.peligon.PeligonSkills.listeneres.*;
import net.peligon.PeligonSkills.managers.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;


public final class Main extends JavaPlugin {

    public static Main getInstance;
    public mgrAcrobatics Acrobatics;
    public mgrAlchemy Alchemy;
    public mgrExcavation Excavation;
    public mgrFishing Fishing;
    public mgrHerbalism Herbalism;
    public mgrLumberjack Lumberjack;
    public mgrMining Mining;
    public mgrRepair Repair;
    public mgrSmelting Smelting;

    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class | manager classes | register placeholder ] ----
        getInstance = this;
        Acrobatics = new mgrAcrobatics();
        Alchemy = new mgrAlchemy();
        Excavation = new mgrExcavation();
        Fishing = new mgrFishing();
        Herbalism = new mgrHerbalism();
        Lumberjack = new mgrLumberjack();
        Mining = new mgrMining();
        Repair = new mgrRepair();
        Smelting = new mgrSmelting();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new mgrPlaceholders().register();
        }

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

    public void loadCommands() {}
    public void loadEvents() {
        Arrays.asList(
                new accountSetup(),
                new acrobaticsEvents(), // TODO
                new alchemyEvents(), // TODO
                new excavationEvents(),
                new fishingEvents(),
                new herbalismEvents(),
                new lumberjackEvents(),
                new miningEvents(),
                new repairEvents(),
                new smeltingEvents()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }
}
