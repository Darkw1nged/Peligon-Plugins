package net.peligon.PeligonEconomy;

import net.peligon.PeligonEconomy.commands.*;
import net.peligon.PeligonEconomy.libaries.*;
import net.peligon.PeligonEconomy.libaries.storage.CustomConfig;
import net.peligon.PeligonEconomy.listeners.*;
import net.peligon.PeligonEconomy.managers.mgrEconomy;
import net.peligon.PeligonEconomy.managers.mgrPlaceholders;
import net.peligon.PeligonEconomy.managers.mgrSignFactory;
import net.peligon.Plugins.commands.peligonPluginsMenuCommand;
import net.peligon.Plugins.listeners.PeligonPluginMenuEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@SuppressWarnings("ALL")
public final class Main extends JavaPlugin {

    // Create an instance of the main class
    public static Main getInstance;

    // Creating instances of customConfig files
    public CustomConfig languageFile;
    public CustomConfig fileWorth = new CustomConfig(this, "worth", true);
    public CustomConfig fileSigns = new CustomConfig(this, "signs", true);
    public CustomConfig filePouches = new CustomConfig(this, "pouches", true);
    public CustomConfig fileATM = new CustomConfig(this, "Inventories/ATM", true);
    public CustomConfig fileDailyReward = new CustomConfig(this, "Inventories/daily", true);
    public CustomConfig fileSellGUI = new CustomConfig(this, "Inventories/sellGUI", true);
    public CustomConfig fileBoxGUI = new CustomConfig(this, "Inventories/box", true);
    public CustomConfig filedailyTaskGUI = new CustomConfig(this, "Inventories/daily-tasks", true);

    // Getting vault hook.
    private VaultHook vaultHook;

    // Storage type; File, MySQL, SQLite
    public String storageType = "file";


    public void onEnable() {
        // Initializing instance of main class.
        getInstance = this;

        // Loading customConfig files.
        saveDefaultConfig();
        // TODO -------- Subject to removeal --------
        fileWorth.saveDefaultConfig();
        fileSigns.saveDefaultConfig();
        filePouches.saveDefaultConfig();
        fileATM.saveDefaultConfig();
        fileDailyReward.saveDefaultConfig();
        filedailyTaskGUI.saveDefaultConfig();
        fileSellGUI.saveDefaultConfig();
        fileBoxGUI.saveDefaultConfig();
        // TODO -------- Subject to removeal --------

        // Check for vault dependency.
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            // Vault not found, disabling plugin.
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.languageFile.getConfig().getString("no-plugin-dependency")));
            getServer().getPluginManager().disablePlugin(this);
        }

        // Hooking into vault.
        vaultHook = new VaultHook();
        vaultHook.hook();

        // TODO -------- Subject to removeal --------
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.languageFile.getConfig().getString("no-protocolLib")));
        } else {
            signFactory = new mgrSignFactory(this);
        }

        Economy = new mgrEconomy();
        peligonEconomy = new PeligonEconomy();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new mgrPlaceholders().register();
        }

        // ---- [ Calling repeating tasks ] ----
        new InterestTimer().runTaskTimerAsynchronously(this, 20 * 2, 20 * 2);
        // TODO -------- Subject to removeal --------

        // Initializing lang file and saving the default version.
        languageFile = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.lang"), true);
        languageFile.saveDefaultConfig();

        // Getting storage type from config
        if (getConfig().contains("Storage.database") && getConfig().getString("Storage.database").equalsIgnoreCase("File") ||
                getConfig().getString("Storage.database").equalsIgnoreCase("MySQL") ||
                getConfig().getString("Storage.database").equalsIgnoreCase("SQLite"))
            storageType = getConfig().getString("Storage.database").toLowerCase();

        // Setting up and checking storage.
        systemUtils.createDatabase();

        // Loading all commands and events.
        loadEvents();
        loadCommands();

        // Checking for updates.
        if (getConfig().getBoolean("check-for-updates", true)) versionChecker();

        // Sending startup message to console.
        if (this.languageFile != null)
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.languageFile.getConfig().getString("startup")));
    }

    public void onDisable() {
        // Unhooking from Vault
        vaultHook.unhook();

        // Sending plugin shutdown message if messages file is not null
        if (this.languageFile != null)
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.languageFile.getConfig().getString("shutdown")));
    }

    // Getting the version from https://www.spigot.org and comparing the version to current version.
    private void versionChecker() {
        new UpdateChecker(this, 100259).getVersion(version -> {
            // If spigot version does not equal than the current plugin version then send console a message
            // saving that new version is available along with the link to it.
            if (!version.equals(this.getDescription().getVersion())) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(languageFile.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(languageFile.getConfig().getString("plugin-link")));
            }
        });
    }

    // Registering all events.
    public void loadEvents() {
        Arrays.asList(
                // Listener for peligon plugin menu.
                new PeligonPluginMenuEvent(),

                // Other listeners.
                new accountSetup(),
                new menuListener(),
                new bountyEvents(),
                new redeemEvents(),
                new signEvents(),
                new mobMoneyEvent(),
                new deathPenaltyEvent(),
                new grassScavengeEvents(),
                new miningRewardsEvents(),
                new luckyBlockEvents(),
                new sellGUIEvents(),
                new globalInventoryEvents(),
                new experienceBottleEvent(),
                new bankNotesEvents(),
                new pouchesEvent()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    // Registering all commands : Can ignore capitals.
    public void loadCommands() {
        // Command for peligon plugin menu.
        getCommand("peligon").setExecutor(new peligonPluginsMenuCommand());

        // Other commands.
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
        getCommand("gift").setExecutor(new cmdGift());
        getCommand("box").setExecutor(new cmdBox());
        getCommand("pouches").setExecutor(new cmdPouches());
    }















    public mgrEconomy Economy;
    public PeligonEconomy peligonEconomy;
    public mgrSignFactory signFactory;

}
