package net.peligon.EnhancedStorage.menus;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.Backpack;
import net.peligon.EnhancedStorage.libaries.struts.BackpackItem;
import net.peligon.EnhancedStorage.libaries.struts.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class menuBackpack implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuBackpack(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, plugin.getConfig().getInt("Backpack-Inventory.size"), Utils.chatColor(plugin.getConfig().getString("Backpack-Inventory.title").replaceAll("%player%", player.getName())));
        if (Utils.backpacks.containsKey(player.getUniqueId())) {
            Backpack backpack = Utils.backpacks.get(player.getUniqueId());

            int backpackItemCount = 0;
            for (int i = 0; i < 54; i++) {
                if (backpackItemCount < backpack.getContents().size()) {
                    BackpackItem foundItem = backpack.getContents().get(backpackItemCount);
                    ItemStack itemToAdd = new ItemStack(foundItem.getMaterial());
                    itemToAdd.setAmount(Math.min(foundItem.getAmount(), 64));

                    ItemMeta itemToAddMeta = itemToAdd.getItemMeta();
                    itemToAddMeta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("Backpack-Inventory.show-item.name").replaceAll("%item%", Utils.formatWord(foundItem.getMaterial().name()))));

                    List<String> lore = new ArrayList<>();

                    for (String string : plugin.getConfig().getStringList("Backpack-Inventory.show-item.lore")) {
                        lore.add(Utils.chatColor(string)
                                .replaceAll("%amount%", foundItem.getAmount() + "")
                                .replaceAll("%item%", Utils.formatWord(foundItem.getMaterial().name()))
                                .replaceAll("%stacks%", foundItem.getStackSize() + "")
                        );
                    }
                    itemToAddMeta.setLore(lore);

                    if (plugin.getConfig().getBoolean("Backpack-Inventory.show-item.hide-attributes")) {
                        itemToAddMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    } else {
                        itemToAddMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    }

                    if (plugin.getConfig().getBoolean("Backpack-Inventory.show-item.hide-enchants")) {
                        itemToAddMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    } else {
                        itemToAddMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }

                    itemToAdd.setItemMeta(itemToAddMeta);

                    inventory.setItem(i, itemToAdd);
                    backpackItemCount++;
                } else {
                    inventory.setItem(i, new ItemStack(Material.AIR));
                }
            }
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(Utils.chatColor("&cBackpack not found for " + player.getName()));
            player.sendMessage(Utils.chatColor("&cAn error has occurred. Please contact an administrator."));
        }
    }

    public void onClick(Main plugin, Player player, int slot, ClickType type) {
    }

    public void onOpen(Main plugin, Player player) {
    }

    public void onClose(Main plugin, Player player) {
    }

    public Inventory getInventory() {
        return this.inventory;
    }

}
