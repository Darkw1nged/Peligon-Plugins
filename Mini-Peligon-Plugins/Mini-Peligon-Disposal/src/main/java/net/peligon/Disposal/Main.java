package net.peligon.Disposal;

import net.peligon.Disposal.Commands.cmdDisposal;
import net.peligon.Disposal.Listeners.signClick;
import net.peligon.Disposal.Utilities.PlayerUtils;
import net.peligon.Disposal.Utilities.Storage.CustomConfig;
import net.peligon.Disposal.Utilities.Utils;
import net.peligon.Disposal.Utilities.WorldUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    public Utils utils;
    public WorldUtils worldUtils;
    public PlayerUtils playerUtils;

    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Creating Instance ] ----
        getInstance = this;

        // ---- [ Creating Utilities ] ----
        utils = new Utils();
        worldUtils = new WorldUtils();
        playerUtils = new PlayerUtils();

        // ---- [ Saving Default Configs ] ----
        saveDefaultConfig();
        /** File Message */ {
            fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
            fileMessage.saveDefaultConfig();
        }

        // ---- [ Registering Listeners & Commands ] ----
        registerEvents();
        registerCommands();

        // ---- [ Startup Message ] ----
        utils.log(fileMessage.getConfig().getString("startup"));

        // ---- [ Check for Updates ] ----
        if (getConfig().getBoolean("check-for-updates", true)) {
//            new UpdateChecker(0).getVersion(version -> {
//                if (!version.equals(this.getDescription().getVersion())) {
//                    getServer().getConsoleSender().sendMessage(utils.chatColor(fileMessage.getConfig().getString("plugin-outdated")));
//                    getServer().getConsoleSender().sendMessage(utils.chatColor(fileMessage.getConfig().getString("plugin-link")));
//                }
//            });
        }
    }

    public void onDisable() {
        // ---- [ Shutdown Message ] ----
        if (this.fileMessage == null) return;
        utils.log(fileMessage.getConfig().getString("shutdown"));
    }

    private void registerEvents() {
        Arrays.asList(
            new signClick()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void registerCommands() {
        getCommand("disposal").setExecutor(new cmdDisposal());
    }
}
