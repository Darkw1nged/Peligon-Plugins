package net.peligon.PeligonPolls;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.peligon.PeligonPolls.commands.cmdPoll;
import net.peligon.PeligonPolls.events.menuEvents;
import net.peligon.PeligonPolls.libaries.CustomConfig;
import net.peligon.PeligonPolls.libaries.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class Main extends JavaPlugin {

    public static Main getInstance;
    private JDA discord;
    public TextChannel discordChannel;

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

        initializeConnection();

        // ---- [ Startup message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));
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
    }
    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new menuEvents(), this);
    }

    public void initializeConnection() {
        if (getConfig().getString("Storage.client-token").equals("") || getConfig().getString("Storage.text-channel-id").equals("")) return;
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
    }

}
