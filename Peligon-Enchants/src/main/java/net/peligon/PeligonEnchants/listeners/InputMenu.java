package net.peligon.PeligonEnchants.listeners;

import net.peligon.PeligonEnchants.Main;
import net.peligon.PeligonEnchants.libaries.Menu;
import net.peligon.PeligonEnchants.libaries.Utils;
import net.peligon.PeligonEnchants.menus.menuEnchant;
import net.peligon.PeligonEnchants.menus.menuEnchantEquip;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InputMenu implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Menu)) return;
        ((Menu) holder).onClick(plugin, (Player) event.getWhoClicked(), event.getSlot(), event.getClick());

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        menuEnchant menuEnchant = new menuEnchant(player);
        String inventoryName = event.getView().getTitle();

        if (item == null || item.getType() == Material.AIR) return;

        if (inventoryName.equals(Utils.chatColor(plugin.fileUI.getConfig().getString("main.title")))) {
            for (String key : plugin.fileUI.getConfig().getConfigurationSection("main.contents").getKeys(false)) {
                if (item.getType().equals(Material.getMaterial(plugin.fileUI.getConfig().getString("main.contents." + key + ".item").toUpperCase()))
                        && item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.fileUI.getConfig().getString("main.contents." + key + ".name")))) {
                    if (plugin.fileUI.getConfig().contains("main.contents." + key + ".event")) {
                        switch (plugin.fileUI.getConfig().getString("main.contents." + key + ".event").toLowerCase()) {
                            case "noitem":
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-item")));
                                event.setCancelled(true);
                                return;
                            case "helmet":
                                if (player.getInventory().getHelmet() == null) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-item")));
                                    event.setCancelled(true);
                                    return;
                                }
                                player.openInventory(new menuEnchantEquip(player, player.getInventory().getHelmet()).getInventory());
                                return;
                            case "chestplate":
                                if (player.getInventory().getChestplate() == null) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-item")));
                                    event.setCancelled(true);
                                    return;
                                }
                                player.openInventory(new menuEnchantEquip(player, player.getInventory().getChestplate()).getInventory());
                                return;
                            case "leggings":
                                if (player.getInventory().getLeggings() == null) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-item")));
                                    event.setCancelled(true);
                                    return;
                                }
                                player.openInventory(new menuEnchantEquip(player, player.getInventory().getLeggings()).getInventory());
                                return;
                            case "boots":
                                if (player.getInventory().getBoots() == null) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-item")));
                                    event.setCancelled(true);
                                    return;
                                }
                                player.openInventory(new menuEnchantEquip(player, player.getInventory().getBoots()).getInventory());
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

    @EventHandler
    private void onOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu)
            ((Menu) holder).onOpen(plugin, (Player) event.getPlayer());
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu)
            ((Menu) holder).onClose(plugin, (Player) event.getPlayer());
    }

}