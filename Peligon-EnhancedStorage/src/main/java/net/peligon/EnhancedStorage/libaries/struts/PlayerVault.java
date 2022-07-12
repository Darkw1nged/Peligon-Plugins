package net.peligon.EnhancedStorage.libaries.struts;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.CustomConfig;
import net.peligon.EnhancedStorage.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.UUID;

public class PlayerVault {

    private final int number;
    private final UUID owner;
    private final CustomConfig config;

    public PlayerVault(int number, UUID owner) {
        this.number = number;
        this.owner = owner;
        this.config = new CustomConfig(Main.getInstance, "playerVaults/" + owner.toString() + "/" + number, false);
    }

    public int getNumber() {
        return number;
    }

    public UUID getOwner() {
        return owner;
    }

    public CustomConfig getSaveLocation() {
        return config;
    }

    public void open(Player player) {
        Inventory vault = Bukkit.createInventory(player, 54, Utils.chatColor(Main.getInstance.getConfig().getString("defaults.player-vault-title").replaceAll("%number%", String.valueOf(number))));

        if (config.getConfig().contains("items")) {
            for (String key : config.getConfig().getConfigurationSection("items").getKeys(false)) {
                vault.setItem(Integer.parseInt(key), config.getConfig().getItemStack("items." + key));
            }
        }

        player.openInventory(vault);
    }

}