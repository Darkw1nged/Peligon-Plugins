package net.peligon.EnhancedStorage;

import net.peligon.EnhancedStorage.commands.cmdPlayerVault;
import net.peligon.EnhancedStorage.commands.cmdReload;
import net.peligon.EnhancedStorage.libaries.BackpackCheck;
import net.peligon.EnhancedStorage.libaries.CustomConfig;
import net.peligon.EnhancedStorage.libaries.UpdateChecker;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.Backpack;
import net.peligon.EnhancedStorage.libaries.struts.BackpackItem;
import net.peligon.EnhancedStorage.listener.*;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin {

    public static Main getInstance;

    public CustomConfig fileMessage;
    public CustomConfig fileApproveItem = new CustomConfig(this, "Inventories/Approval", true);
    public CustomConfig fileWithdrawItem = new CustomConfig(this, "Inventories/Withdraw", true);

    public void onEnable() {
        // ---- [ Initializing instance of main class ] ----
        getInstance = this;

        // ---- [ Loading Commands | Loading Events | Loading YML Files ] ----
        loadCommands();
        loadEvents();
        saveDefaultConfig();
        fileApproveItem.saveDefaultConfig();
        fileWithdrawItem.saveDefaultConfig();

        // ---- [ Loading backpacks ] ----
        loadBackpacks();

        // ---- [ Loading lang file ] ----
        fileMessage = new CustomConfig(this, "lang/" + this.getConfig().getString("Storage.lang"), true);
        fileMessage.saveDefaultConfig();

        // ---- [ Calling Repeating Tasks ] ----
        new BackpackCheck().runTaskTimer(this, 20 * 5, 20 * 5);

        // ---- [ Startup message ] ----
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("startup")));

        // ---- [ Check if server has most updated version ] ----
//        if (getConfig().getBoolean("check-for-updates", true)) {
//            versionChecker();
//        }
    }

    public void onDisable() {
        // ---- [ Save Backpack ] ----
        saveBackpacks();

        // ---- [ shutdown message ] ----
        if (this.fileMessage == null) return;
        getServer().getConsoleSender().sendMessage(Utils.chatColor(this.fileMessage.getConfig().getString("shutdown")));
    }

    public void loadCommands() {
        getCommand("pelstorage").setExecutor(new cmdReload());
        getCommand("playervault").setExecutor(new cmdPlayerVault());
    }

    public void loadEvents() {
        Arrays.asList(
                new backpackEvents(),
                new playerVaultEvent(),

                new customGUIEvents(),
                new backpackInventory(),
                new approveInventory(),
                new withdrawInventory()
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

    public void saveBackpacks() {
        for (Backpack backpack : Utils.backpacks.values()) {
            CustomConfig config = new CustomConfig(this, "backpack", "data/" + backpack.getOwner());
            YamlConfiguration yaml = config.getConfig();

            yaml.set("Owner", backpack.getOwner().toString());
            yaml.set("MaximumStorageLevel", backpack.getMaximumStorageLevel());
            yaml.set("TypesAllowed", backpack.getTypesAllowed());
            int pos = 0;
            for (BackpackItem item : backpack.getContents()) {
                yaml.set("Contents." + pos + ".Material", item.getMaterial().name());
                yaml.set("Contents." + pos + ".Amount", item.getAmount());
                pos++;
            }
            config.saveConfig();
        }
    }

    public void loadBackpacks() {
        File folder = new File(this.getDataFolder() + "/data");
        if (!folder.exists()) return;

        for (String file : new File(getDataFolder() + "/data").list()) {
            CustomConfig config = new CustomConfig(this, "backpack", "data/" + file);
            YamlConfiguration yaml = config.getConfig();

            if (yaml.getString("Owner") == null) {
                continue;
            }

            UUID owner = UUID.fromString(yaml.getString("Owner"));
            int maximumStorageLevel = yaml.getInt("MaximumStorageLevel");
            int typesAllowed = yaml.getInt("TypesAllowed");

            List<BackpackItem> contents = new ArrayList<>();
            if (yaml.contains("Contents")) {
                for (String key : yaml.getConfigurationSection("Contents").getKeys(false)) {
                    contents.add(new BackpackItem(Material.valueOf(yaml.getString("Contents." + key + ".Material")),
                            yaml.getInt("Contents." + key + ".Amount")));
                }
            }

            Backpack backpack = new Backpack(owner, maximumStorageLevel, typesAllowed, contents);
            Utils.backpacks.put(owner, backpack);
        }
    }
}
