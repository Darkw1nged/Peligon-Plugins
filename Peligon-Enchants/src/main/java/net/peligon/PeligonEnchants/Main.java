package net.peligon.PeligonEnchants;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import net.milkbowl.vault.economy.Economy;
import net.peligon.PeligonEnchants.listeners.*;
import net.peligon.PeligonEnchants.commands.cmdEnchants;
import net.peligon.PeligonEnchants.libaries.CustomConfig;
import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import net.peligon.PeligonEnchants.libaries.Utils;
import net.peligon.PeligonEnchants.listeners.inventories.customGUIEvents;
import net.peligon.PeligonEnchants.listeners.inventories.enchantInventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main getInstance;
    private static Economy econ = null;

    public CustomConfig fileUI = new CustomConfig(this, "enchantments GUI", true);
    public CustomConfig fileMessage;

    public void onEnable() {
        // ---- [ Initializing instance of main class | manager classes | register placeholder ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();
        fileUI.saveDefaultConfig();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.Language File"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Loading Enchantments ] ----
        CustomEnchants.register();
        if (!setupEconomy() ) {
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("autosell-disabled")));
        }

        // ---- [ Startup message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));
    }

    public void onDisable() {
        // ---- [ shutdown message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("shutdown")));
    }

    public void loadCommands() {
        getCommand("enchants").setExecutor(new cmdEnchants());
    }

    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new customGUIEvents(), this);
        getServer().getPluginManager().registerEvents(new enchantInventory(), this);


        getServer().getPluginManager().registerEvents(new encTelepathy(), this);
        getServer().getPluginManager().registerEvents(new encAutoSell(), this);
        getServer().getPluginManager().registerEvents(new encGlowing(), this);
        getServer().getPluginManager().registerEvents(new encAquatic(), this);
        getServer().getPluginManager().registerEvents(new encHaste(), this);
        getServer().getPluginManager().registerEvents(new encAutoSmelt(), this);
        getServer().getPluginManager().registerEvents(new encOxygenate(), this);
        getServer().getPluginManager().registerEvents(new encSprings(), this);
        getServer().getPluginManager().registerEvents(new encPoisoned(), this);
        getServer().getPluginManager().registerEvents(new encStormcaller(), this);
        getServer().getPluginManager().registerEvents(new encVoodoo(), this);
        getServer().getPluginManager().registerEvents(new encVenom(), this);
        getServer().getPluginManager().registerEvents(new encMolten(), this);
        getServer().getPluginManager().registerEvents(new encEnderShift(), this);
        getServer().getPluginManager().registerEvents(new encBlind(), this);
        getServer().getPluginManager().registerEvents(new encTrap(), this);
        getServer().getPluginManager().registerEvents(new encPoison(), this);
        getServer().getPluginManager().registerEvents(new encHeadless(), this);
        getServer().getPluginManager().registerEvents(new encDecapitation(), this);
        getServer().getPluginManager().registerEvents(new encConfusion(), this);
        getServer().getPluginManager().registerEvents(new encFeatherweight(), this);
        getServer().getPluginManager().registerEvents(new encLightning(), this);
        getServer().getPluginManager().registerEvents(new encRavenous(), this);
        getServer().getPluginManager().registerEvents(new encVampire(), this);
        ArmorEquipEvent.registerListener(this);
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
