package net.peligon.EnhancedStorage.libaries;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.struts.Backpack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BackpackCheck extends BukkitRunnable {

    private final Main plugin = Main.getInstance;
    String name = Utils.chatColor(plugin.getConfig().getString("Backpack.name"));
    int slot = plugin.getConfig().getInt("defaults.backpack.slot") - 1;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Utils.backpacks.containsKey(player.getUniqueId())) continue;
            Backpack backpack = Utils.backpacks.get(player.getUniqueId());
            if (plugin.getConfig().getBoolean("defaults.backpack.enabled", true)) {
                if (player.getInventory().getItem(slot) != null) {
                    if (!player.getInventory().getItem(slot).getItemMeta().getDisplayName().equals(name)) {
                        ItemStack item = player.getInventory().getItem(slot);
                        player.getInventory().setItem(slot, backpack.getBackpack());
                        backpack.addItem(item.getType(), item.getAmount());
                    }
                    player.getInventory().setItem(slot, backpack.getBackpack());
                } else {
                    player.getInventory().setItem(slot, backpack.getBackpack());
                }
            } else {
                if (player.getInventory().getItem(slot) != null && player.getInventory().getItem(slot).getItemMeta().getDisplayName().equals(name)) {
                    player.getInventory().getItem(slot).setAmount(0);
                }
            }
        }
    }
}
