package net.peligon.PeligonEconomy.libaries;

import org.bukkit.inventory.ItemStack;

public class Pouch {

    private String id;
    private String type;
    private double minimum;
    private double maximum;
    private ItemStack itemStack;
    private double cost;
    private Boolean permissionRequired;

    public Pouch(String id, String type, double minimum, double maximum, ItemStack itemStack, boolean permissionRequired) {
        this.id = id;
        this.type = type;
        this.minimum = minimum >= maximum ? maximum - 1 : minimum;
        this.maximum = maximum;
        this.itemStack = itemStack;
        this.cost = 0;
        this.permissionRequired = permissionRequired;
    }

    public Pouch(String id, String type, double minimum, double maximum, ItemStack itemStack, double cost, boolean permissionRequired) {
        this.id = id;
        this.type = type;
        this.minimum = minimum >= maximum ? maximum - 1 : minimum;
        this.maximum = maximum;
        this.itemStack = itemStack;
        this.cost = cost;
        this.permissionRequired = permissionRequired;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(long minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(long maximum) {
        this.maximum = maximum;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public Boolean getPermissionRequired() {
        return permissionRequired;
    }

    public void setPermissionRequired(Boolean permissionRequired) {
        this.permissionRequired = permissionRequired;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
