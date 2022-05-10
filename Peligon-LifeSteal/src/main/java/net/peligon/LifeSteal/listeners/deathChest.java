package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import net.peligon.LifeSteal.manager.mgrDeathChest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class deathChest implements Listener {

    private final Main plugin = Main.getInstance;
    private Map<UUID, mgrDeathChest> deathStorage = new HashMap<>();
    private ArmorStand armorStand;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("death-chest")) {
            Player player = event.getEntity();
            if (player.getInventory().isEmpty()) return;

            Location deathLocation = event.getEntity().getLocation();
            Location spawnBlockLocation = new Location(deathLocation.getWorld(), deathLocation.getBlockX(), deathLocation.getBlockY(), deathLocation.getBlockZ());

            spawnBlockLocation.getBlock().setType(Material.getMaterial(plugin.getConfig().getString("Death-Chest.block")));
            if (plugin.getConfig().getBoolean("Death-Chest.hologram", true)) {
                Location spawnHologramLocation = new Location(deathLocation.getWorld(), deathLocation.getBlockX() + 0.5, deathLocation.getBlockY() + 0.25, deathLocation.getBlockZ() + 0.5);
                armorStand = Utils.hologram(plugin.getConfig().getString("Death-Chest.hologram-text").replaceAll("%player%", player.getName()),
                        spawnHologramLocation);
            }
            Inventory inventory = Bukkit.createInventory(player, 45, Utils.chatColor(plugin.getConfig().getString("Death-Chest.inventory-name").replaceAll("%player%", player.getName())));

            int pos = 0;
            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null) continue;
                inventory.setItem(pos, item);
                pos++;
            }
            mgrDeathChest deathChest = new mgrDeathChest(player.getUniqueId(), player.getName(), spawnBlockLocation,
                    plugin.getConfig().getBoolean("Death-Chest.hologram", true), armorStand, inventory);

            deathStorage.put(player.getUniqueId(), deathChest);
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("death-chest")) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Block block = event.getClickedBlock();
                if (block == null) return;

                Player player = event.getPlayer();
                if (!deathStorage.containsKey(player.getUniqueId())) return;

                mgrDeathChest deathChest = deathStorage.get(player.getUniqueId());
                if (!block.getLocation().equals(deathChest.getLocation())) return;

                player.openInventory(deathChest.getInventory());
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("death-chest")) {
            Player player = event.getPlayer();
            if (deathStorage.containsKey(player.getUniqueId()) && deathStorage.get(player.getUniqueId()).getLocation().equals(event.getBlock().getLocation())) {
                mgrDeathChest deathChest = deathStorage.get(player.getUniqueId());
                Block block = event.getBlock();
                if (!block.getLocation().equals(deathChest.getLocation())) return;

                for (ItemStack item : deathChest.getInventory().getContents()) {
                    if (item == null) continue;
                    event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), item);
                }

                deathStorage.remove(player.getUniqueId());
                deathChest.remove();
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("death-chest")) {

            if (event.getClickedInventory() != null) {
                Player player = (Player) event.getWhoClicked();
                if (deathStorage.containsKey(player.getUniqueId())) {
                    if (event.getClickedInventory() == player.getInventory() && event.getCurrentItem() != null &&
                            event.getView().getTitle().equals(Utils.chatColor(plugin.getConfig().getString("Death-Chest.inventory-name").replaceAll("%player%", player.getName())))) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("death-chest")) {
            Player player = (Player) event.getPlayer();
            if (deathStorage.containsKey(player.getUniqueId())) {

                if (event.getInventory() == deathStorage.get(player.getUniqueId()).getInventory()) {
                    if (event.getInventory().isEmpty() || event.getInventory().getContents().length <= 1) {
                        deathStorage.get(player.getUniqueId()).remove();
                        deathStorage.remove(player.getUniqueId());
                    }
                }
            }
        }
    }

}
