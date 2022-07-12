package net.peligon.EnhancedStorage.listener;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.Backpack;
import net.peligon.EnhancedStorage.libaries.struts.Menu;
import net.peligon.EnhancedStorage.menus.menuBackpack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class approveInventory implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Menu)) return;
        ((Menu) holder).onClick(plugin, (Player) event.getWhoClicked(), event.getSlot(), event.getClick());

        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();

        if (!Utils.backpacks.containsKey(player.getUniqueId())) return;
        Backpack backpack = Utils.backpacks.get(player.getUniqueId());

        if (item == null || item.getType() == Material.AIR) return;
        if (inventoryName.equals(Utils.chatColor(plugin.fileApproveItem.getConfig().getString("name")))) {
            if (Utils.itemSlot.containsKey(player.getUniqueId())) {
                Map<Integer, String> slots = Utils.itemSlot.get(player.getUniqueId());
                String itemAction = slots.get(slot);
                if (itemAction != null) {
                    if (itemAction.equalsIgnoreCase("yes")) {
                        if (!Utils.backpackItemSelected.isEmpty()) {
                            backpack.removeItem(Utils.backpackItemSelected.get(player.getUniqueId()).getMaterial());
                            Utils.backpackItemSelected.remove(player.getUniqueId());
                        }
                        Utils.itemSlot.remove(player.getUniqueId());
                        player.openInventory(new menuBackpack(player).getInventory());
                    } else if (itemAction.equalsIgnoreCase("no")) {
                        if (!Utils.backpackItemSelected.isEmpty()) {
                            Utils.backpackItemSelected.remove(player.getUniqueId());
                        }
                        Utils.itemSlot.remove(player.getUniqueId());
                        player.openInventory(new menuBackpack(player).getInventory());
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
            event.setCancelled(true);
        }
    }

}