package net.peligon.PeligonEconomy;

import net.peligon.PeligonEconomy.commands.*;
import net.peligon.PeligonEconomy.libaries.*;
import net.peligon.PeligonEconomy.libaries.storage.SQLibrary;
import net.peligon.PeligonEconomy.libaries.storage.SQLiteLibary;
import net.peligon.PeligonEconomy.listeners.*;
import net.peligon.PeligonEconomy.managers.mgrEconomy;
import net.peligon.PeligonEconomy.managers.mgrPlaceholders;
import net.peligon.PeligonEconomy.managers.mgrSignFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Arrays;

@SuppressWarnings("ALL")
public final class Main extends JavaPlugin {

    public static Main getInstance;
    private VaultHook vaultHook;
    public mgrEconomy Economy;
    public PeligonEconomy peligonEconomy;
    public mgrSignFactory signFactory;

    public SQLibrary sqlLibrary;
    public String storageType = "SQLite";

    public CustomConfig fileWorth = new CustomConfig(this, "worth", true);
    public CustomConfig fileSigns = new CustomConfig(this, "signs", true);
    public CustomConfig filePouches = new CustomConfig(this, "pouches", true);
    public CustomConfig fileATM = new CustomConfig(this, "Inventories/ATM", true);
    public CustomConfig fileDailyReward = new CustomConfig(this, "Inventories/daily", true);
    public CustomConfig fileSellGUI = new CustomConfig(this, "Inventories/sellGUI", true);
    public CustomConfig fileBoxGUI = new CustomConfig(this, "Inventories/box", true);
    public CustomConfig filedailyTaskGUI = new CustomConfig(this, "Inventories/dauly-tasks", true);
    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        fileWorth.saveDefaultConfig();
        fileSigns.saveDefaultConfig();
        filePouches.saveDefaultConfig();
        fileATM.saveDefaultConfig();
        fileDailyReward.saveDefaultConfig();
        filedailyTaskGUI.saveDefaultConfig();
        fileSellGUI.saveDefaultConfig();
        fileBoxGUI.saveDefaultConfig();
        saveDefaultConfig();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Checking if the server has the dependencies, if not disable ] ----
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("no-plugin-dependency")));
            getServer().getPluginManager().disablePlugin(this);
        }

        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("no-protocolLib")));
        } else {
            signFactory = new mgrSignFactory(this);
        }

        // ---- [ Initializing instance of manager classes | register placeholder ] ----
        Economy = new mgrEconomy();
        peligonEconomy = new PeligonEconomy();
        vaultHook = new VaultHook();
        vaultHook.hook();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new mgrPlaceholders().register();
        }

        // ---- [ Setting up databases ] ----
        setupStorage();

        // ---- [ Calling repeating tasks ] ----
        new InterestTimer().runTaskTimerAsynchronously(this, 20 * 2, 20 * 2);

        // ---- [ Startup message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));

        // ---- [ Check if server has most updated version ] ----
        if (getConfig().getBoolean("check-for-updates", true)) {
            versionChecker();
        }
    }

    public void onDisable() {
        vaultHook.unhook();

        // ---- [ shutdown message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("shutdown")));
    }

    public void loadCommands() {
        getCommand("economy").setExecutor(new cmdEconomy());
        getCommand("pelecon").setExecutor(new cmdReload());
        getCommand("balance").setExecutor(new cmdBalance());
        getCommand("atm").setExecutor(new cmdATM());
        getCommand("balancetop").setExecutor(new cmdBalanceTop());
        getCommand("pay").setExecutor(new cmdPay());
        getCommand("withdraw").setExecutor(new cmdWithdraw());
        getCommand("experience").setExecutor(new cmdExperience());
        getCommand("experienceBottle").setExecutor(new cmdWithdrawBottle());
        getCommand("sell").setExecutor(new cmdSell());
        getCommand("sellhand").setExecutor(new cmdSellHand());
        getCommand("autosell").setExecutor(new cmdSellAuto());
        getCommand("chestsell").setExecutor(new cmdSellChest());
        getCommand("sellwand").setExecutor(new cmdSellWand());
        getCommand("bounty").setExecutor(new cmdBounty());
        getCommand("daily").setExecutor(new cmdDaily());
//        getCommand("gift").setExecutor(new cmdGift());
//        getCommand("box").setExecutor(new cmdBox());
        getCommand("pouches").setExecutor(new cmdPouches());
    }

    public void loadEvents() {
        Arrays.asList(
                new accountSetup(),
                new bountyEvents(),
                new redeemEvents(),
                new signEvents(),
                new atmEvents(),
                new mobMoneyEvent(),
                new deathPenaltyEvent(),
                new grassScavengeEvents(),
                new miningRewardsEvents(),
                new luckyBlockEvents(),
                new dailyInventoryEvents(),
                new sellGUIEvents(),
                new globalInventoryEvents(),
//                new BoxEvents()
                new experienceBottleEvent(),
                new bankNotesEvents(),
                new pouchesEvent()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void versionChecker() {
        new UpdateChecker(this, 100259).getVersion(version -> {
            if (!version.equals(this.getDescription().getVersion())) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-link")));
            }
        });
    }

    private void setupStorage() {
        if (getConfig().getString("Storage.database", "SQLite").equalsIgnoreCase("sqlite")) {
            SQLiteLibary sqlLite = new SQLiteLibary();
            sqlLite.loadTables();
            this.storageType = "SQLite";
        } else if (getConfig().getString("Storage.database", "SQLite").equalsIgnoreCase("MYSQL")) {
            sqlLibrary = new SQLibrary(getConfig().getString("Storage.MySQL.host"),
                    getConfig().getInt("Storage.MySQL.port"),
                    getConfig().getString("Storage.MySQL.database"),
                    getConfig().getString("Storage.MySQL.username"),
                    getConfig().getString("Storage.MySQL.password"));

            if (sqlLibrary.getConnection() == null) {
                System.out.println("Unable to establish a connection to MySQL.");
                return;
            }

            try {
                sqlLibrary.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS accounts" +
                        " (uuid VARCHAR(36) NOT NULL, cash INT(16) DEFAULT 0, bank INT(16) DEFAULT 0, PRIMARY KEY (uuid));").executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            this.storageType = "MySQL";
        }
    }

}
