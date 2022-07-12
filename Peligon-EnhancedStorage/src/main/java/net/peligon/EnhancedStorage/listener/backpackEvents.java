package net.peligon.EnhancedStorage.listener;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.Backpack;
import net.peligon.EnhancedStorage.menus.menuBackpack;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class backpackEvents implements Listener {

    private final Main plugin = Main.getInstance;
    String name = Utils.chatColor(plugin.getConfig().getString("Backpack.name"));

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("defaults.backpack.enabled")) {
            if (!Utils.backpacks.containsKey(player.getUniqueId())) {
                Utils.backpacks.put(player.getUniqueId(), new Backpack(player.getUniqueId()));
            }
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Utils.backpacks.containsKey(player.getUniqueId())) {
            ItemStack hand = player.getInventory().getItemInHand();
            if (hand.getType() == Material.AIR) return;

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (hand.getItemMeta().getDisplayName().equals(name)) {
                    player.openInventory(new menuBackpack(player).getInventory());
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (Utils.backpacks.containsKey(player.getUniqueId())) {
            ItemStack hand = player.getInventory().getItemInHand();
            if (hand.getType() == Material.AIR) return;

            if (hand.getItemMeta().getDisplayName().equals(name)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (Utils.backpacks.containsKey(player.getUniqueId())) {
            ItemStack hand = event.getItemDrop().getItemStack();
            if (hand.getType() == Material.AIR) return;

            if (hand.getItemMeta().getDisplayName().equals(name)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void inventoryPickup(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (Utils.backpacks.containsKey(player.getUniqueId())) {
            ItemStack hand = event.getCurrentItem();
            if (hand == null || !hand.hasItemMeta() || !hand.getItemMeta().hasDisplayName()) return;

            if (hand.getItemMeta().getDisplayName().equals(name)) {
                event.setCancelled(true);
                player.openInventory(new menuBackpack(player).getInventory());
                event.getCursor().setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Utils.backpacks.containsKey(player.getUniqueId())) {
                event.getDrops().remove(Utils.backpacks.get(player.getUniqueId()).getBackpack());
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (plugin.getConfig().getString("defaults.backpack.entry").equalsIgnoreCase("break")) {
            Player player = event.getPlayer();
            if (player.getGameMode().equals(GameMode.SPECTATOR) || player.getGameMode().equals(GameMode.CREATIVE)) return;


            for (ItemStack item : event.getBlock().getDrops(player.getItemInHand())) {
                if (Utils.backpacks.containsKey(player.getUniqueId())) {
                    Backpack backpack = Utils.backpacks.get(player.getUniqueId());
                    if (backpack.hasSpace(item.getType(), item.getAmount())) {
                        backpack.addItem(item.getType(), item.getAmount());
                    } else {
                        player.getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
                    }
                }
            }
            event.setDropItems(false);
        }
    }



    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (plugin.getConfig().getString("defaults.backpack.entry").equalsIgnoreCase("pickup")) {
            if (!(event.getEntity() instanceof Player)) return;

            Player player = (Player) event.getEntity();
            if (player.getGameMode().equals(GameMode.SPECTATOR) || player.getGameMode().equals(GameMode.CREATIVE)) return;

            ItemStack item = event.getItem().getItemStack();
            if (Utils.backpacks.containsKey(player.getUniqueId())) {
                Backpack backpack = Utils.backpacks.get(player.getUniqueId());
                if (backpack.hasSpace(item.getType(), item.getAmount())) {
                    backpack.addItem(item.getType(), item.getAmount());
                }
            }
        }
    }

}
