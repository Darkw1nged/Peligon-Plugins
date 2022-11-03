package net.peligon.EnhancedStorage;

import net.peligon.EnhancedStorage.commands.giveItemCommand;
import net.peligon.EnhancedStorage.commands.playerVaultCommand;
import net.peligon.EnhancedStorage.commands.reloadCommand;
import net.peligon.EnhancedStorage.libaries.CustomConfig;
import net.peligon.EnhancedStorage.libaries.UpdateChecker;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.listener.*;
import net.peligon.Plugins.commands.peligonPluginsMenuCommand;
import net.peligon.Plugins.listeners.PeligonPluginMenuEvent;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Main extends JavaPlugin {

    // Create an instance of the main class
    public static Main getInstance;

    // Creating instances of customConfig files
    public CustomConfig languageFile;
    public CustomConfig customItemsFile = new CustomConfig(this, "customItems", false);


    public CustomConfig fileApproveItem = new CustomConfig(this, "Inventories/Approval", true);
    public CustomConfig fileWithdrawItem = new CustomConfig(this, "Inventories/Withdraw", true);

    public void onEnable() {
        // Initializing instance of main class.
        getInstance = this;

        // Loading customConfig files.
        saveDefaultConfig();
        customItemsFile.saveDefaultConfig();
        fileApproveItem.saveDefaultConfig();
        fileWithdrawItem.saveDefaultConfig();

        // Initializing lang file and saving the default version.
        languageFile = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.lang"), true);
        languageFile.saveDefaultConfig();

        // Loading all commands and events.
        loadEvents();
        loadCommands();

        // Checking for updates.
        if (getConfig().getBoolean("check-for-updates", true)) versionChecker();

        // Sending startup message to console.
        if (this.languageFile != null)
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.languageFile.getConfig().getString("startup")));
    }

    public void onDisable() {
        // Sending plugin shutdown message if messages file is not null
        if (this.languageFile == null)
            getServer().getConsoleSender().sendMessage(Utils.chatColor(this.languageFile.getConfig().getString("shutdown")));
    }

    // Getting the version from https://www.spigot.org and comparing the version to current version.
    private void versionChecker() {
        new UpdateChecker(this, 101707).getVersion(version -> {
            // If spigot version does not equal than the current plugin version then send console a message
            // saving that new version is available along with the link to it.
            if (!version.equals(this.getDescription().getVersion())) {
                getServer().getConsoleSender().sendMessage(Utils.chatColor(languageFile.getConfig().getString("plugin-outdated")));
                getServer().getConsoleSender().sendMessage(Utils.chatColor(languageFile.getConfig().getString("plugin-link")));
            }
        });
    }

    // Registering all events.
    public void loadEvents() {
        Arrays.asList(
                // Listener for peligon plugin menu.
                new PeligonPluginMenuEvent(),

                // Other listeners.
                new playerVaultEvent(),
                new autoFill(),
                new autoFillLapisLazuli(),
                new fillBrewingStand(),
                new backpackHandles(),
                new minerBackpackHandle()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    // Registering all commands : Can ignore capitals.
    public void loadCommands() {
        // Command for peligon plugin menu.
        getCommand("peligon").setExecutor(new peligonPluginsMenuCommand());

        // Other commands.
        getCommand("peligonstorage").setExecutor(new reloadCommand());
        getCommand("giveitem").setExecutor(new giveItemCommand());
        getCommand("playervault").setExecutor(new playerVaultCommand());
    }





    // TODO --------------------------- Subject to be removed ---------------------------
//    public void saveBackpacks() {
//        for (Backpack backpack : Utils.backpacks.values()) {
//            CustomConfig config = new CustomConfig(this, "backpack", "data/" + backpack.getOwner());
//            YamlConfiguration yaml = config.getConfig();
//
//            yaml.set("Owner", backpack.getOwner().toString());
//            yaml.set("MaximumStorageLevel", backpack.getMaximumStorageLevel());
//            yaml.set("TypesAllowed", backpack.getTypesAllowed());
//            int pos = 0;
//            for (BackpackItem item : backpack.getContents()) {
//                yaml.set("Contents." + pos + ".Material", item.getMaterial().name());
//                yaml.set("Contents." + pos + ".Amount", item.getAmount());
//                pos++;
//            }
//            config.saveConfig();
//        }
//    }

    // TODO --------------------------- Subject to be removed ---------------------------
//    public void loadBackpacks() {
//        File folder = new File(this.getDataFolder() + "/data");
//        if (!folder.exists()) return;
//
//        for (String file : new File(getDataFolder() + "/data").list()) {
//            CustomConfig config = new CustomConfig(this, "backpack", "data/" + file);
//            YamlConfiguration yaml = config.getConfig();
//
//            if (yaml.getString("Owner") == null) {
//                continue;
//            }
//
//            UUID owner = UUID.fromString(yaml.getString("Owner"));
//            int maximumStorageLevel = yaml.getInt("MaximumStorageLevel");
//            int typesAllowed = yaml.getInt("TypesAllowed");
//
//            List<BackpackItem> contents = new ArrayList<>();
//            if (yaml.contains("Contents")) {
//                for (String key : yaml.getConfigurationSection("Contents").getKeys(false)) {
//                    contents.add(new BackpackItem(Material.valueOf(yaml.getString("Contents." + key + ".Material")),
//                            yaml.getInt("Contents." + key + ".Amount")));
//                }
//            }
//
//            Backpack backpack = new Backpack(owner, maximumStorageLevel, typesAllowed, contents);
//            Utils.backpacks.put(owner, backpack);
//        }
//    }

}
