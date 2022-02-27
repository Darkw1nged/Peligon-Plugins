package net.peligon.PeligonChat;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.peligon.PeligonChat.libaries.CustomConfig;
import net.peligon.PeligonChat.libaries.UpdateChecker;
import net.peligon.PeligonChat.libaries.Utils;
import net.peligon.PeligonChat.libaries.storage.SQLite;
import net.peligon.PeligonChat.listener.*;
import net.peligon.PeligonChat.manager.mgrServerTotal;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    private static Chat chat = null;
    private static Economy econ = null;
    public mgrServerTotal serverTotal;

    public CustomConfig fileChatFilter = new CustomConfig(this, "chatFilter", true);
    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();
        fileChatFilter.saveDefaultConfig();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Checking if the server has the dependencies ] ----
        setupChat();
        setupEconomy();

        // ---- [ Initializing instance of manager classes ] ----
        serverTotal = new mgrServerTotal();

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

    public void loadCommands() {}
    public void loadEvents() {
        Arrays.asList(
                new chatColor(),
                new signColor(),
                new chatFormat(),
                new userMessages(),
                new chatPing(),
                new blockedCommands(),
                new chatFilter(),
                new antiSpam()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Chat getChat() {
        return chat;
    }

    public Economy getEconomy() {
        return econ;
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
