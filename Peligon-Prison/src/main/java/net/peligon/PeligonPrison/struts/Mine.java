package net.peligon.PeligonPrison.struts;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class Mine {

    private String name;
    private boolean hasPermission;
    private String permission;
    private Location spawnLocation;
    private List<Material> blocks;
    private long lastReset;
    private long resetTime;
    private Location cornerOne;
    private Location cornerTwo;

    public Mine(String name) {
        this.name = name;
        this.hasPermission = false;
        this.permission = "";
        this.spawnLocation = null;
        this.blocks = null;
        this.lastReset = -1;
        this.resetTime = -1;
        this.cornerOne = null;
        this.cornerTwo = null;
    }

    public Mine(String name, Location cornerOne, Location cornerTwo) {
        this.name = name;
        this.hasPermission = false;
        this.permission = "";
        this.spawnLocation = null;
        this.blocks = null;
        this.lastReset = -1;
        this.resetTime = -1;
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
    }

    public Mine(String name, boolean hasPermission, String permission, Location spawnLocation, List<Material> blocks, long lastReset, long resetTime, Location cornerOne, Location cornerTwo) {
        this.name = name;
        this.hasPermission = hasPermission;
        this.permission = permission;
        this.spawnLocation = spawnLocation;
        this.blocks = blocks;
        this.lastReset = lastReset;
        this.resetTime = resetTime;
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasPermission() {
        return hasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public List<Material> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Material> blocks) {
        this.blocks = blocks;
    }

    public long getLastReset() {
        return lastReset;
    }

    public void setLastReset(long lastReset) {
        this.lastReset = lastReset;
    }

    public long getResetTime() {
        return resetTime;
    }

    public void setResetTime(long resetTime) {
        this.resetTime = resetTime;
    }

    public Location getCornerOne() {
        return cornerOne;
    }

    public void setCornerOne(Location cornerOne) {
        this.cornerOne = cornerOne;
    }

    public Location getCornerTwo() {
        return cornerTwo;
    }

    public void setCornerTwo(Location cornerTwo) {
        this.cornerTwo = cornerTwo;
    }

}
