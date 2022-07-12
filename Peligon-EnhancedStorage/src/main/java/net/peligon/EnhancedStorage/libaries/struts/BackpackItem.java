package net.peligon.EnhancedStorage.libaries.struts;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BackpackItem {

    private Material material;
    private int amount;

    public BackpackItem(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void removeAmount(int amount) {
        this.amount -= amount;
    }

    public int getStackSize() {
        if (this.amount < 64)
            return 0;
        return this.amount / 64;
    }

    public ItemStack getItemStack() {
        return new ItemStack(this.material, Math.min(this.amount, 64));
    }

}
