package net.peligon.EnhancedStorage.listener;
import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.Backpack;
import net.peligon.EnhancedStorage.libaries.struts.Menu;
import net.peligon.EnhancedStorage.menus.menuApprove;
import net.peligon.EnhancedStorage.menus.menuWithdraw;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class backpackInventory implements Listener {

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
        if (event.getClickedInventory() == event.getView().getTopInventory()) {
            if (inventoryName.equalsIgnoreCase(Utils.chatColor(plugin.getConfig().getString("Backpack-Inventory.title").replaceAll("%player%", player.getName())))) {
                if (event.getClick() == ClickType.RIGHT) {
                    player.openInventory(new menuApprove(player).getInventory());
                    Utils.backpackItemSelected.put(player.getUniqueId(), backpack.getItem(item));
                } else {
                    Utils.backpackItemSelected.put(player.getUniqueId(), backpack.getItem(item));
                    player.openInventory(new menuWithdraw(player).getInventory());
                }
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }
    }

}
