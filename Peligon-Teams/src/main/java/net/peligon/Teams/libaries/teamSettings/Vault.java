package net.peligon.Teams.libaries.teamSettings;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Vault {

    private ItemStack[] items;
    private Integer size;
    private Boolean secured;

    public Vault(ItemStack[] items, Integer size, Boolean secured) {
        this.items = items;
        this.size = size;
        this.secured = secured;
    }

    // getters and setters
    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getSecured() {
        return secured;
    }

    public void setSecured(Boolean secured) {
        this.secured = secured;
    }

    // methods
    public void addItem(ItemStack item) {
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack i : this.items) {
            items.add(i);
        }
        items.add(item);
        this.items = items.toArray(new ItemStack[items.size()]);
    }

    public void removeItem(Integer index) {
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack i : this.items) {
            if (items.size() != index) {
                items.add(i);
            }
        }
        this.items = items.toArray(new ItemStack[items.size()]);
    }

    public Integer getItemIndex(ItemStack item) {
        for (Integer i = 0; i < this.items.length; i++) {
            if (this.items[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public ItemStack getItem(Integer index) {
        return this.items[index];
    }

    public void setItem(Integer index, ItemStack item) {
        this.items[index] = item;
    }

    public Boolean isItemInVault(ItemStack item) {
        for (ItemStack i : this.items) {
            if (i.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void clearItems() {
        this.items = new ItemStack[0];
    }

    public Integer getItemCount() {
        return this.items.length;
    }

    public Boolean isEmpty() {
        return this.items.length == 0;
    }

    public Boolean isFull() {
        return this.items.length == this.size;
    }

    public Boolean isSecured() {
        return this.secured;
    }

}
