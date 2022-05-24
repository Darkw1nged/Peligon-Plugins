package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.managers.Menu;
import net.peligon.PeligonEconomy.menu.menuBox;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class boxEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Menu)) return;
        ((Menu) holder).onClick(plugin, (Player) event.getWhoClicked(), event.getSlot(), event.getClick());

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();

        if (item == null || item.getType() == Material.AIR) return;
        menuBox box = new menuBox(player);

        if (inventoryName.equals(Utils.chatColor(plugin.fileBoxGUI.getConfig().getString("inventory.title")))) {
            for (String key : plugin.fileBoxGUI.getConfig().getConfigurationSection("inventory.contents").getKeys(false)) {
                if (item.getType().equals(Material.getMaterial(plugin.fileBoxGUI.getConfig().getString("inventory.contents." + key + ".item").toUpperCase()))
                        && item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.fileBoxGUI.getConfig().getString("inventory.contents." + key + ".name")))) {
                    if (plugin.fileBoxGUI.getConfig().contains("inventory.contents." + key + ".event")) {
                        switch (plugin.fileBoxGUI.getConfig().getString("inventory.contents." + key + ".event").toLowerCase()) {
                            case "nextpage":
                                box.getPlayerPage().put(player.getUniqueId(), box.getPlayerPage().get(player.getUniqueId()) + 1);
                                player.closeInventory();
                                player.openInventory(box.getInventory());
                                event.setCancelled(true);
                                return;
                            case "previouspage":
                                box.getPlayerPage().put(player.getUniqueId(), box.getPlayerPage().get(player.getUniqueId()) - 1);
                                player.closeInventory();
                                player.openInventory(box.getInventory());
                                event.setCancelled(true);
                                return;
                            case "close":
                                player.closeInventory();
                                event.setCancelled(true);
                                return;
                        }
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }


        }
    }

}
