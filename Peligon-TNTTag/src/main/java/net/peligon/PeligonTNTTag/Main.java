package net.peligon.PeligonTNTTag;

import net.peligon.PeligonTNTTag.events.Temp;
import net.peligon.PeligonTNTTag.libaries.CustomConfig;
import net.peligon.PeligonTNTTag.libaries.Utils;
import net.peligon.PeligonTNTTag.libariesV2.Utilss;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    private Utilss utilss;

    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class | manager classes | register placeholder ] ----
        getInstance = this;
        utilss = new Utilss();

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Startup message ] ----
        utilss.getChatUtil().log(this.fileMessage.getConfig().getString("startup"));
    }

    public void onDisable() {
        // ---- [ shutdown message ] ----
        utilss.getChatUtil().log(this.fileMessage.getConfig().getString("shutdown"));
    }

    public void loadCommands() {
    }
    public void loadEvents() {
        Arrays.asList(
                new Temp()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public Utilss getUtilss() {
        return this.utilss;
    }
}
