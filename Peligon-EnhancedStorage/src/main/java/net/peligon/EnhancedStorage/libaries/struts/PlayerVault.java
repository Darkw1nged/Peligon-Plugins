package net.peligon.EnhancedStorage.libaries.struts;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.CustomConfig;
import net.peligon.EnhancedStorage.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.UUID;

public class playerVault {

    // Creating variables
    private final int vaultNumber;
    private final UUID vaultOwner;
    private final CustomConfig vaultConfig;

    // Constructor
    public playerVault(int number, UUID owner) {
        this.vaultNumber = number;
        this.vaultOwner = owner;
        this.vaultConfig = new CustomConfig(Main.getInstance, "playerVaults/" + owner.toString() + "/" + number, false);
    }

    // Getters
    public int getNumber() {
        return vaultNumber;
    }

    public UUID getOwner() {
        return vaultOwner;
    }

    public CustomConfig getSaveLocation() {
        return vaultConfig;
    }

    // Loading the vault items and opening it for the player.
    public void open(Player player) {
        Inventory vault = Bukkit.createInventory(player, 54, Utils.chatColor(Main.getInstance.getConfig().getString("defaults.player-vault-title")
                .replaceAll("%number%", String.valueOf(vaultNumber))));

        if (vaultConfig.getConfig().contains("items")) {
            for (String key : vaultConfig.getConfig().getConfigurationSection("items").getKeys(false)) {
                vault.setItem(Integer.parseInt(key), vaultConfig.getConfig().getItemStack("items." + key));
            }
        }

        player.openInventory(vault);
    }

}