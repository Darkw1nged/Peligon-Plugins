package net.peligon.PeligonEconomy;

import net.peligon.PeligonEconomy.commands.*;
import net.peligon.PeligonEconomy.libaries.CustomConfig;
import net.peligon.PeligonEconomy.libaries.InterestTimer;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.storage.SQLite;
import net.peligon.PeligonEconomy.listeners.*;
import net.peligon.PeligonEconomy.managers.mgrEconomy;
import net.peligon.PeligonEconomy.managers.mgrPlaceholders;
import net.peligon.PeligonEconomy.managers.mgrSignFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    public mgrEconomy Economy;
    public mgrSignFactory signFactory;

    public CustomConfig fileWorth = new CustomConfig(this, "worth", true);
    public CustomConfig fileSigns = new CustomConfig(this, "signs", true);
    public CustomConfig fileATM = new CustomConfig(this, "ATM", true);
    public CustomConfig filePouches = new CustomConfig(this, "pouch shop", true);
    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Checking if the server has the dependencies, if not disable ] ----
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("no-plugin-dependency")));
            getServer().getPluginManager().disablePlugin(this);
        }
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("no-plugin-dependency")));
            getServer().getPluginManager().disablePlugin(this);
        }

        // ---- [ Initializing instance of main class | manager classes | register placeholder ] ----
        getInstance = this;
        Economy = new mgrEconomy();
        signFactory = new mgrSignFactory(this);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new mgrPlaceholders().register();
        }

        // ---- [ Calling repeating tasks ] ----
        new InterestTimer().runTaskLater(this, 20 * 3);

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        fileWorth.saveDefaultConfig();
        fileSigns.saveDefaultConfig();
        fileATM.saveDefaultConfig();
        filePouches.saveDefaultConfig();
        saveDefaultConfig();

        // ---- [ Setting up SQLite ] ----
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
        getCommand("economy").setExecutor(new cmdEconomy());
        getCommand("pelecon").setExecutor(new cmdReload());
        getCommand("balance").setExecutor(new cmdBalance());
        getCommand("atm").setExecutor(new cmdATM());
        getCommand("pouches").setExecutor(new cmdPouches());
        getCommand("balancetop").setExecutor(new cmdBalanceTop());
        getCommand("pay").setExecutor(new cmdPay());
        getCommand("withdraw").setExecutor(new cmdWithdraw());
        getCommand("bottle").setExecutor(new cmdWithdrawBottle());
        getCommand("sell").setExecutor(new cmdSell());
        getCommand("sellhand").setExecutor(new cmdSellHand());
        getCommand("autosell").setExecutor(new cmdSellAuto());
        getCommand("chestsell").setExecutor(new cmdSellChest());
        getCommand("sellwand").setExecutor(new cmdSellWand());
        getCommand("bounty").setExecutor(new cmdBounty());
    }

    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new accountSetup(), this);
        getServer().getPluginManager().registerEvents(new bountyEvents(), this);
        getServer().getPluginManager().registerEvents(new mobDropEvents(), this);
        getServer().getPluginManager().registerEvents(new redeemEvents(), this);
        getServer().getPluginManager().registerEvents(new signEvents(), this);
        getServer().getPluginManager().registerEvents(new inventoryEvents(), this);
    }

}
