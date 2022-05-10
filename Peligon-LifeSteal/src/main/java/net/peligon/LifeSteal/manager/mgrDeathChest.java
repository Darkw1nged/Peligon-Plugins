package net.peligon.LifeSteal.manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class mgrDeathChest {

    private final UUID ownerUUID;
    private final String ownerName;
    private final Location location;
    private final boolean hasHologram;
    private final ArmorStand hologram;
    private final Inventory inventory;

    public mgrDeathChest(UUID ownerUUID, String ownerName, Location location, boolean hasHologram, ArmorStand hologram, Inventory inventory) {
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
        this.location = location;
        this.hasHologram = hasHologram;
        this.hologram = hologram;
        this.inventory = inventory;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Location getLocation() {
        return location;
    }

    public boolean hasHologram() {
        return hasHologram;
    }

    public ArmorStand getHologram() {
        return hologram;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void removeHologram() {
        hologram.remove();
    }

    public void removeChest() {
        location.getBlock().setType(Material.AIR);
    }

    public void remove() {
        removeHologram();
        removeChest();
    }

}
