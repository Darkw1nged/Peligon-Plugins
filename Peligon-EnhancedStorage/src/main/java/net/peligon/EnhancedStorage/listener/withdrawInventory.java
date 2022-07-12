package net.peligon.EnhancedStorage.listener;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.Backpack;
import net.peligon.EnhancedStorage.libaries.struts.BackpackItem;
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

public class withdrawInventory implements Listener {

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
        if (inventoryName.equals(Utils.chatColor(plugin.fileWithdrawItem.getConfig().getString("name")))) {
            if (Utils.itemSlot.containsKey(player.getUniqueId())) {
                Map<Integer, String> slots = Utils.itemSlot.get(player.getUniqueId());
                String itemAction = slots.get(slot);
                if (itemAction != null) {
                    if (itemAction.equalsIgnoreCase("close")) {
                        player.closeInventory();
                        Utils.itemSlot.remove(player.getUniqueId());
                        player.openInventory(new menuBackpack(player).getInventory());
                    } else if (itemAction.equalsIgnoreCase("all")) {
                        if (Utils.backpackItemSelected.containsKey(player.getUniqueId())) {
                            BackpackItem backpackItem = Utils.backpackItemSelected.get(player.getUniqueId());
                            int amount = Utils.getMaxAmount(player, backpackItem.getItemStack().getType(), backpackItem.getAmount());

                            while (amount > 0) {
                                int amountToTake = Math.min(amount, 64);
                                Utils.hasSpace(player, new ItemStack(backpackItem.getMaterial(), amountToTake));
                                amount -= amountToTake;
                            }
                            backpack.removeItem(backpackItem.getMaterial(), Utils.getMaxAmount(player, backpackItem.getItemStack().getType(), backpackItem.getAmount()));

                            Utils.backpackItemSelected.remove(player.getUniqueId());
                            Utils.itemSlot.remove(player.getUniqueId());
                            player.closeInventory();
                            player.openInventory(new menuBackpack(player).getInventory());
                        }
                    } else if (itemAction.equalsIgnoreCase("64")) {
                        if (Utils.backpackItemSelected.containsKey(player.getUniqueId())) {
                            BackpackItem backpackItem = Utils.backpackItemSelected.get(player.getUniqueId());
                            int amount = Utils.getMaxAmount(player, backpackItem.getItemStack().getType(), 64);

                            Utils.hasSpace(player, new ItemStack(backpackItem.getMaterial(), amount));
                            backpack.removeItem(backpackItem.getMaterial(), amount);

                            Utils.backpackItemSelected.remove(player.getUniqueId());
                            Utils.itemSlot.remove(player.getUniqueId());
                            player.closeInventory();
                            player.openInventory(new menuBackpack(player).getInventory());
                        }
                    } else if (itemAction.equalsIgnoreCase("32")) {
                        if (Utils.backpackItemSelected.containsKey(player.getUniqueId())) {
                            BackpackItem backpackItem = Utils.backpackItemSelected.get(player.getUniqueId());
                            int amount = Utils.getMaxAmount(player, backpackItem.getItemStack().getType(), 32);

                            Utils.hasSpace(player, new ItemStack(backpackItem.getMaterial(), amount));
                            backpack.removeItem(backpackItem.getMaterial(), amount);

                            Utils.backpackItemSelected.remove(player.getUniqueId());
                            Utils.itemSlot.remove(player.getUniqueId());
                            player.closeInventory();
                            player.openInventory(new menuBackpack(player).getInventory());
                        }
                    } else if (itemAction.equalsIgnoreCase("1")) {
                        if (Utils.backpackItemSelected.containsKey(player.getUniqueId())) {
                            BackpackItem backpackItem = Utils.backpackItemSelected.get(player.getUniqueId());
                            int amount = Utils.getMaxAmount(player, backpackItem.getItemStack().getType(), 1);

                            Utils.hasSpace(player, new ItemStack(backpackItem.getMaterial(), amount));
                            backpack.removeItem(backpackItem.getMaterial(), amount);

                            Utils.backpackItemSelected.remove(player.getUniqueId());
                            Utils.itemSlot.remove(player.getUniqueId());
                            player.closeInventory();
                            player.openInventory(new menuBackpack(player).getInventory());
                        }
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
            event.setCancelled(true);
        }
    }

}

