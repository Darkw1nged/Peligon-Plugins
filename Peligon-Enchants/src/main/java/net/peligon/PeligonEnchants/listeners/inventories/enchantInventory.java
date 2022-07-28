package net.peligon.PeligonEnchants.listeners.inventories;

import net.peligon.PeligonEnchants.Main;
import net.peligon.PeligonEnchants.libaries.Menu;
import net.peligon.PeligonEnchants.libaries.Utils;
import net.peligon.PeligonEnchants.menus.menuEnchantEquip;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class enchantInventory implements Listener {

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

        if (item == null || item.getType() == Material.AIR) return;
        if (inventoryName.equals(Utils.chatColor(plugin.fileUI.getConfig().getString("main.title")))) {
            if (event.isRightClick()) {

                ItemStack selectedItem = new ItemStack(Material.NETHERITE_CHESTPLATE);
                ItemMeta meta = selectedItem.getItemMeta();
                meta.setDisplayName(Utils.chatColor("&f&lTitty Milker 3000"));
                List<String> lore = new ArrayList<>();
                lore.add(Utils.chatColor("&7A experience you can't get anywhere else."));
                lore.add(Utils.chatColor("&7Get yours today!"));
                lore.add("");
                lore.add(Utils.chatColor("&ePrice: &a$0.00"));
                lore.add(Utils.chatColor("&cThats right! Its free!"));
                lore.add("");
                lore.add(Utils.chatColor("&8Click to buy!"));
                meta.setLore(lore);
                selectedItem.setItemMeta(meta);

                player.closeInventory();
                player.openInventory(new menuEnchantEquip(player, selectedItem).getInventory());
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
        }
    }

}
