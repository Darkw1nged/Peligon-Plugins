package net.peligon.PeligonSocial;

import net.peligon.PeligonSocial.libaries.CustomConfig;
import net.peligon.PeligonSocial.libaries.UpdateChecker;
import net.peligon.PeligonSocial.libaries.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    public String saveSetting = "file";

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

        // ---- [ Setting up database ] ----
        if (getConfig().getString("Storage.database", "NONE").equalsIgnoreCase("NONE")) {
            saveSetting = "file";
        } else if (getConfig().getString("Storage.database", "NONE").equalsIgnoreCase("MYSQL")) {
            saveSetting = "MYSQL";
        } else if (getConfig().getString("Storage.database", "NONE").equalsIgnoreCase("MongoDB")) {
            saveSetting = "MongoDB";
        }

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

    public void loadCommands() {
    }
    public void loadEvents() {
//        Arrays.asList(
//                new Authentication(),
//                new playerJoin()
//        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
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
