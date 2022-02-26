package net.peligon.PeligonPolls;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.peligon.PeligonPolls.commands.cmdPoll;
import net.peligon.PeligonPolls.commands.cmdReload;
import net.peligon.PeligonPolls.events.menuEvents;
import net.peligon.PeligonPolls.libaries.CustomConfig;
import net.peligon.PeligonPolls.libaries.UpdateChecker;
import net.peligon.PeligonPolls.libaries.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class Main extends JavaPlugin {

    public static Main getInstance;
    private JDA discord;
    public TextChannel discordChannel;
    private boolean isLoaded = false;

    public CustomConfig fileMessage;
    public CustomConfig fileCache = new CustomConfig(this, "cache", true);

    public void onEnable() {
        // ---- [ Initializing instance of main class | manager classes | register placeholder ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();
        fileCache.saveDefaultConfig();

        // ---- [ Loading Active Polls ] ----
        Utils.loadActivePolls();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Connecting to the bot ] ----
        initializeConnection();

        if (isLoaded) {
            // ---- [ Startup message ] ----
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));

            // ---- [ Check if server has most updated version ] ----
            if (getConfig().getBoolean("check-for-updates", true)) {
                versionChecker();
            }
        }
    }

    public void onDisable() {
        if (discord != null) {
            discord.shutdownNow();
        }

        // ---- [ Save all polls inside of the server ] ----
        Utils.saveActivePolls();

        // ---- [ shutdown message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("shutdown")));
    }

    public void loadCommands() {
        getCommand("poll").setExecutor(new cmdPoll());
        getCommand("pelpoll").setExecutor(new cmdReload());
    }
    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new menuEvents(), this);
    }

    public void initializeConnection() {
        if (getConfig().getString("Storage.client-token").equals("") || getConfig().getString("Storage.text-channel-id").equals("")) {
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("config-not-setup")));
            isLoaded = false;
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        try {
            discord = JDABuilder.createDefault(getConfig().getString("Storage.client-token")).build().awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
        if (discord == null) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (getConfig().getString("Storage.text-channel-id") == null) return;
        discordChannel = discord.getTextChannelById(getConfig().getString("Storage.text-channel-id"));
        isLoaded = true;
    }

    private void versionChecker() {
        new UpdateChecker(this, 100200).getVersion(version -> {
            if (!version.equals(this.getDescription().getVersion())) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-link")));
            }
        });
    }

}
