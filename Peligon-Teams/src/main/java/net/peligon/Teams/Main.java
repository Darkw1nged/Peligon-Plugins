package net.peligon.Teams;

import net.milkbowl.vault.economy.Economy;
import net.peligon.Teams.commands.cmdTeam;
import net.peligon.Teams.libaries.CustomConfig;
import net.peligon.Teams.libaries.struts.Team;
import net.peligon.Teams.libaries.UpdateChecker;
import net.peligon.Teams.libaries.Utils;
import net.peligon.Teams.listeners.chatChannel;
import net.peligon.Teams.managers.mgrTeam;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin {

    public static Main getInstance;
    public mgrTeam teamManager;
    private static Economy econ = null;

    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();

        // ----- [ Load all managers ] -----
        teamManager = new mgrTeam();

        // ---- [ Load all teams ] ----
        Utils.loadTeams();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.lang"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Setting up economy ] ----
        setupEconomy();

        // ---- [ Startup message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));

        // ---- [ Check if server has most updated version ] ----
        if (getConfig().getBoolean("check-for-updates", true)) {
            versionChecker();
        }
    }

    public void onDisable() {
        if (!Utils.teams.isEmpty()) {
            for (Team team : Utils.teams)
                team.save();
        }

        // ---- [ shutdown message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("shutdown")));
    }

    public void loadCommands() {
        getCommand("team").setExecutor(new cmdTeam());
    }

    public void loadEvents() {
        Arrays.asList(
                new chatChannel()
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

    public Economy getEconomy() {
        return econ;
    }
}
