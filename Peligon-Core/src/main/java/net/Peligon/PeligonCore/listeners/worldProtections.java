package net.Peligon.PeligonCore.listeners;

import net.Peligon.PeligonCore.Main;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class worldProtections implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Block-Break", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getBlock().getWorld().getName())) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Block-Place", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getBlock().getWorld().getName())) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Item-Pickup", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getEntity().getWorld().getName())) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(EntityDropItemEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Item-Drop", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getEntity().getWorld().getName())) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(EntityInteractEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Interact", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getEntity().getWorld().getName())) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRedstoneProcess(BlockRedstoneEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Redstone", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getBlock().getWorld().getName())) return;
            event.setNewCurrent(0);
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Explosions", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getEntity().getWorld().getName())) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Fall-Damage", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getEntity().getWorld().getName())) return;
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Player-Damage", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getEntity().getWorld().getName())) return;
            if (!(event.getEntity() instanceof Player)) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMonsterDamage(EntityDamageEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Mob-Damage", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getEntity().getWorld().getName())) return;
            if (!(event.getEntity() instanceof Monster)) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMonsterSpawn(EntitySpawnEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Mob-Spawning", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getEntity().getWorld().getName())) return;
            if (!(event.getEntity() instanceof Monster)) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAnimalSpawn(EntitySpawnEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.Animal-Spawning", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getEntity().getWorld().getName())) return;
            if (!(event.getEntity() instanceof Animals)) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EXPDropEventOne(PlayerExpChangeEvent event) {
        if (!plugin.getConfig().getBoolean("World-Protection.XP-Drop", true)) {
            if (plugin.getConfig().getStringList("World-Protection.blacklisted-worlds").contains(event.getPlayer().getWorld().getName())) return;
            event.setAmount(0);
        }
    }

}
