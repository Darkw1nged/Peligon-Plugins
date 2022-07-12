package net.peligon.EnhancedStorage.listener;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.PlayerVault;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

public class playerVaultEvent implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryView inventory = event.getView();
        HumanEntity player = event.getPlayer();
        if (!Utils.openVaults.containsKey(player.getUniqueId())) return;
        PlayerVault vault = Utils.openVaults.get(player.getUniqueId());
        String title = Utils.chatColor(plugin.getConfig().getString("defaults.player-vault-title").replaceAll("%number%",  vault.getNumber() + ""));

        if (inventory.getTitle().equalsIgnoreCase(title)) {
            for (int i = 0; i < inventory.getTopInventory().getSize(); i++) {
                vault.getSaveLocation().getConfig().set("items." + i, inventory.getTopInventory().getItem(i));
            }
            vault.getSaveLocation().saveConfig();
        }
    }

}
