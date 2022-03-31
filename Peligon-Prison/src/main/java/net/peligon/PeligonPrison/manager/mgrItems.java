package net.peligon.PeligonPrison.manager;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class mgrItems {

    private final Main plugin = Main.getInstance;

    public ItemStack mineCreationWand;
    public static mgrItems getInstance;
    public Map<UUID, ItemStack> playerItem = new HashMap<>();

    public mgrItems() {
        getInstance = this;

        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&aMine Creation Wand"));
        item.setItemMeta(meta);
        mineCreationWand = item;
    }

    public void addItem(Player player) {
        if (!Utils.hasSpace(player, mineCreationWand)) {
            playerItem.put(player.getUniqueId(), player.getItemInHand());
        }
        player.getInventory().addItem(mineCreationWand);
    }

    public void removeItem(Player player) {
        player.getInventory().remove(mineCreationWand);
        if (playerItem.containsKey(player.getUniqueId())) {
            player.getInventory().addItem(playerItem.get(player.getUniqueId()));
            playerItem.remove(player.getUniqueId());
        }
    }

}
