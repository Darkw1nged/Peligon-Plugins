package net.peligon.PeligonPrison.struts;

import org.bukkit.block.Block;

import java.util.List;

public class Mine {

    private String name;
    private long resetTime;
    private boolean hasPermission;
    private String permission;
    private List<Block> blocks;

    public Mine(String name, long resetTime, boolean hasPermission, String permission, List<Block> blocks) {
        this.name = name;
        this.resetTime = resetTime;
        this.hasPermission = hasPermission;
        this.permission = permission;
        this.blocks = blocks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getResetTime() {
        return resetTime;
    }

    public void setResetTime(long resetTime) {
        this.resetTime = resetTime;
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

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

}
