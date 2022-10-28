package net.peligon.EnhancedStorage.listener;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.playerVault;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

public class playerVaultEvent implements Listener {

    // Getting the main class.
    private final Main plugin = Main.getInstance;

    @EventHandler
    // Getting the correct event.
    // In this case we are checking when a player closes an inventory.
    public void onClose(InventoryCloseEvent event) {
        // Getting the inventory and player.
        InventoryView inventory = event.getView();
        HumanEntity player = event.getPlayer();

        // If the player is not inside the temporary openVaults map, return.
        if (!Utils.openVaults.containsKey(player.getUniqueId())) return;

        // Getting the vault and title.
        playerVault vault = Utils.openVaults.get(player.getUniqueId());
        String vaultTitle = Utils.chatColor(plugin.getConfig().getString("defaults.player-vault-title").replaceAll("%number%", vault.getNumber() + ""));

        // Checking if the inventory closed title is equal to vaultTitle.
        // If it is not return.
        if (!inventory.getTitle().equals(vaultTitle)) return;

        // Getting all items inside the inventory and saving them in the config.
        for (int i = 0; i < inventory.getTopInventory().getSize(); i++) {
            vault.getSaveLocation().getConfig().set("items." + i, inventory.getTopInventory().getItem(i));
        }
        vault.getSaveLocation().saveConfig();
    }

}
