package net.peligon.PeligonEconomy;

import net.peligon.PeligonEconomy.commands.*;
import net.peligon.PeligonEconomy.libaries.*;
import net.peligon.PeligonEconomy.libaries.storage.SQLite;
import net.peligon.PeligonEconomy.listeners.*;
import net.peligon.PeligonEconomy.managers.mgrEconomy;
import net.peligon.PeligonEconomy.managers.mgrPlaceholders;
import net.peligon.PeligonEconomy.managers.mgrSignFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    private VaultHook vaultHook;
    public mgrEconomy Economy;
    public PeligonEconomy peligonEconomy;
    public mgrSignFactory signFactory;

    public CustomConfig fileWorth = new CustomConfig(this, "worth", true);
    public CustomConfig fileSigns = new CustomConfig(this, "signs", true);
    public CustomConfig fileATM = new CustomConfig(this, "ATM", true);
    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        fileWorth.saveDefaultConfig();
        fileSigns.saveDefaultConfig();
        fileATM.saveDefaultConfig();
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
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("no-plugin-dependency")));
            getServer().getPluginManager().disablePlugin(this);
        }

        // ---- [ Initializing instance of manager classes | register placeholder ] ----
        Economy = new mgrEconomy();
        peligonEconomy = new PeligonEconomy();
        vaultHook = new VaultHook();
        vaultHook.hook();
        signFactory = new mgrSignFactory(this);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new mgrPlaceholders().register();
        }

        // ---- [ Setting up SQLite ] ----
        SQLite sqlLite = new SQLite();
        sqlLite.loadTables();

        // ---- [ Calling repeating tasks ] ----
        new InterestTimer().runTaskLaterAsynchronously(this, 20 * 2);

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
        getServer().getPluginManager().registerEvents(new redeemEvents(), this);
        getServer().getPluginManager().registerEvents(new signEvents(), this);
        getServer().getPluginManager().registerEvents(new AtmEvents(), this);
        getServer().getPluginManager().registerEvents(new MobMoneyEvent(), this);
    }

    private void versionChecker() {
        try {
            String key = "key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=";
            int pluginID = 0;
            String current = "1.0";

            HttpURLConnection connection = (HttpURLConnection) new URL("https://www.spigotmc.org/api/general.php").openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.getOutputStream().write((key + pluginID).getBytes(StandardCharsets.UTF_8));

            String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            if (!version.equals(current)) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-link")));
            }
        } catch (IOException e) {
            getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("version-check-error")));
            e.printStackTrace();
        }
    }

}
