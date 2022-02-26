package net.peligon.PeligonChat;

import net.milkbowl.vault.chat.Chat;
import net.peligon.PeligonChat.libaries.CustomConfig;
import net.peligon.PeligonChat.libaries.UpdateChecker;
import net.peligon.PeligonChat.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    private static Chat chat = null;

    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Checking if the server has the dependencies ] ----
        setupChat();

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
    public void loadEvents() {}

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public Chat getChat() {
        return chat;
    }

    private void versionChecker() {
        new UpdateChecker(this, 100259).getVersion(version -> {
            if (!version.equals(this.getDescription().getVersion())) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(fileMessage.getConfig().getString("plugin-link")));
            }
        });
    }
}
