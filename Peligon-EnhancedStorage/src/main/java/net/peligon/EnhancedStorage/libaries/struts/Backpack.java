package net.peligon.EnhancedStorage.libaries.struts;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Backpack {

    private final Main plugin = Main.getInstance;

    private UUID owner;
    private int maximumStorageLevel = Integer.MAX_VALUE;
    private int typesAllowed = plugin.getConfig().getInt("Backpack-Inventory.size");
    private List<BackpackItem> contents = new ArrayList<>();

    public Backpack(UUID owner) {
        this.owner = owner;
    }

    public Backpack(UUID owner, int maximumStorageLevel, int typesAllowed, List<BackpackItem> contents) {
        this.owner = owner;
        this.maximumStorageLevel = maximumStorageLevel;
        this.typesAllowed = typesAllowed;
        this.contents = contents;
    }

    public UUID getOwner() {
        return owner;
    }

    public int getMaximumStorageLevel() {
        return maximumStorageLevel;
    }

    public int getTypesAllowed() {
        return typesAllowed;
    }

    public List<BackpackItem> getContents() {
        return contents;
    }

    public void setMaximumStorageLevel(int maximumStorageLevel) {
        this.maximumStorageLevel = maximumStorageLevel;
    }

    public void setTypesAllowed(int typesAllowed) {
        this.typesAllowed = typesAllowed;
    }

    public void setContents(List<BackpackItem> contents) {
        this.contents = contents;
    }

    public boolean isFull() {
        int total = 0;
        for (BackpackItem item : contents) {
            total += item.getAmount();
        }
        return total >= maximumStorageLevel;
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }

    public boolean hasSpaceTypes() {
        return contents.size() < typesAllowed;
    }

    public boolean hasSpace(Material material, int amount) {
        if (isEmpty()) return true;
        if (isFull()) return false;
        for (BackpackItem item : contents) {
            if (item.getMaterial() == material) {
                return (item.getAmount() + amount) < maximumStorageLevel;
            }
        }
        return hasSpaceTypes();
    }

    public double getFullProgress() {
        double total = 0.0;
        for (BackpackItem item : contents) {
            total += item.getAmount();
        }
        return (total / maximumStorageLevel) * 100;
    }

    public ItemStack getBackpack() {
        ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Backpack.item").toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("Backpack.name")));

        List<String> lore = new ArrayList<>();
        for (String string : plugin.getConfig().getStringList("Backpack.lore")) {
            if (string.contains("%contents%")) {
                if (contents.size() > plugin.getConfig().getInt("Backpack.number-of-contents")) {
                    for (int i = 0; i < plugin.getConfig().getInt("Backpack.number-of-contents"); i++) {
                        lore.add(Utils.chatColor(plugin.getConfig().getString("Backpack.contents-lore-format")
                                .replaceAll("%item%", Utils.formatWord(contents.get(i).getMaterial().name()))
                                .replaceAll("%amount%", String.valueOf(contents.get(i).getAmount()))));
                    }
                    lore.add(Utils.chatColor(plugin.getConfig().getString("Backpack.more-contents")));
                } else {
                    for (BackpackItem foundItem : contents) {
                        lore.add(Utils.chatColor(plugin.getConfig().getString("Backpack.contents-lore-format")
                                .replaceAll("%item%", Utils.formatWord(foundItem.getMaterial().name()))
                                .replaceAll("%amount%", String.valueOf(foundItem.getAmount()))));
                    }
                }
                continue;
            }
            lore.add(Utils.chatColor(string));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public BackpackItem getItem(Material material) {
        for (BackpackItem item : contents) {
            if (item.getMaterial() == material) {
                return item;
            }
        }
        return null;
    }

    public BackpackItem getItem(ItemStack item) {
        for (BackpackItem backpackItem : contents) {
            if (backpackItem.getMaterial() == item.getType()) {
                return backpackItem;
            }
        }
        return null;
    }

    public void addItem(Material material, int amount) {
        if (isFull()) return;
        if (isEmpty()) {
            contents.add(new BackpackItem(material, amount));
            return;
        }
        for (BackpackItem item : contents) {
            if (item.getMaterial() == material) {
                item.addAmount(amount);
                return;
            }
        }
        if (hasSpaceTypes()) {
            contents.add(new BackpackItem(material, amount));
        }
    }

    public void removeItem(Material material, int amount) {
        if (isEmpty()) return;
        for (BackpackItem item : contents) {
            if (item.getMaterial() == material) {
                item.removeAmount(amount);
                if (item.getAmount() <= 0) {
                    contents.remove(item);
                }
                return;
            }
        }
    }

    public void removeItem(Material material) {
        if (isEmpty()) return;
        for (BackpackItem item : contents) {
            if (item.getMaterial() == material) {
                contents.remove(item);
                return;
            }
        }
    }


}
