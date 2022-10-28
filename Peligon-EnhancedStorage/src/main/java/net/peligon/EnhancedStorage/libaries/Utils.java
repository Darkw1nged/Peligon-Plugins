package net.peligon.EnhancedStorage.libaries;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.struts.playerVault;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.*;

public class Utils {

    // Getting the main class.
    private static final Main plugin = Main.getInstance;

    // Translating all color codes in a string.
    public static String chatColor(String s) {
        return s == null ? null : ChatColor.translateAlternateColorCodes('&', s);
    }

    // Opened vaults HashMap<>.
    public static Map<UUID, playerVault> openVaults = new HashMap<>();

    // Check if the player has enough space to add a new item.
    public static boolean hasSpace(Player player, ItemStack targetItem, int amount) {
        // Getting the players inventory.
        Inventory inventory = player.getInventory();

        // Looping through the players inventory.
        for (int i = 0; i < inventory.getSize(); i++) {
            // Check if i == 36, if so, break the loop.
            if (i == 36) break;

            // Checking if the item is null.
            if (inventory.getItem(i) == null) {
                // Adding the item to the inventory.
                inventory.setItem(i, targetItem);
                // Mining 1 from the amount.
                amount--;

                // Checking if the amount is greater than 0.
                if (amount > 0) {
                    // Set the amount to either amount + 1 or max stack size.
                    inventory.getItem(i).setAmount(inventory.getItem(i).getAmount() + Math.min(amount, (targetItem.getMaxStackSize() - inventory.getItem(i).getAmount())));
                    // Updating the amount.
                    amount -= (inventory.getItem(i).getAmount() - 1);

                    // If amount is greater than 0, continue the loop.
                    if (amount > 0) {
                        continue;
                    }
                }
                // return true if the amount is 0.
                return true;
            } else {
                // Checking if the item type is the same as the target item type.
                if (inventory.getItem(i).getType() == targetItem.getType()) {
                    // Checking if both items have the same meta data.
                    if (inventory.getItem(i).hasItemMeta() && targetItem.hasItemMeta()) {
                        if (inventory.getItem(i).getItemMeta().getDisplayName().equals(targetItem.getItemMeta().getDisplayName()) &&
                                inventory.getItem(i).getItemMeta().getLore().equals(targetItem.getItemMeta().getLore())) {
                            // Check if the item is at max stack size.
                            if (inventory.getItem(i).getAmount() == inventory.getItem(i).getMaxStackSize()) continue;

                            // Getting the amount of the item.
                            int itemAmountBefore = inventory.getItem(i).getAmount();

                            // Updating item amount.
                            inventory.getItem(i).setAmount(inventory.getItem(i).getAmount() + Math.min(amount, (targetItem.getMaxStackSize() - inventory.getItem(i).getAmount())));

                            // Updating the amount.
                            amount -= (inventory.getItem(i).getAmount() - itemAmountBefore);

                            // If amount is greater than 0, continue the loop.
                            if (amount > 0) {
                                continue;
                            }
                            // return true if the amount is 0.
                            return true;
                        }
                    } else {
                        /// Check if the item is at max stack size.
                        if (inventory.getItem(i).getAmount() == inventory.getItem(i).getMaxStackSize()) continue;

                        // Getting the amount of the item.
                        int itemAmountBefore = inventory.getItem(i).getAmount();

                        // Updating item amount.
                        inventory.getItem(i).setAmount(inventory.getItem(i).getAmount() + Math.min(amount, (targetItem.getMaxStackSize() - inventory.getItem(i).getAmount())));

                        // Updating the amount.
                        amount -= (inventory.getItem(i).getAmount() - itemAmountBefore);

                        // If amount is greater than 0, continue the loop.
                        if (amount > 0) {
                            continue;
                        }
                        // return true if the amount is 0.
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // ---- [ Format numbers ] ----
    public static String formatAmount(int amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

    public static String formatAmount(double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

    // ---- [ Format word ] ----
    public static String formatWord(String word) {
        String[] words = word.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(w.substring(0, 1).toUpperCase()).append(w.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    // ---- [ Managing holograms for small amount of features ] ----
    public static void moveUpHologram(String name, Location loc, int length) {
        ArmorStand holo = loc.getWorld().spawn(loc, ArmorStand.class);

        // ---- [ Settings flags for entity ] ----
        holo.setCustomName(chatColor(name));
        holo.setCustomNameVisible(true);
        holo.setGravity(false);
        holo.setInvisible(true);
        holo.setInvulnerable(false);
        holo.setSmall(true);
        holo.setArms(false);
        holo.setBasePlate(false);
        holo.setMetadata("hologram", new FixedMetadataValue(plugin, UUID.randomUUID().toString()));

        activeHolograms.put(holo, System.currentTimeMillis());
        new BukkitRunnable() {
            public void run() {
                if (!activeHolograms.isEmpty() && activeHolograms.containsKey(holo)) {
                    long timeLeft = ((activeHolograms.get(holo) / 1000) + length) - (System.currentTimeMillis() / 1000);
                    if (timeLeft <= 0) {
                        activeHolograms.remove(holo);
                        holo.remove();
                        cancel();
                    } else {
                        holo.teleport(new Location(holo.getWorld(), holo.getLocation().getX(), holo.getLocation().getY() + .01, holo.getLocation().getZ()));
                    }
                }
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    // ---- [ Converting a lore to include colors ] ----
    public static List<String> getConvertedLore(FileConfiguration config, String path) {
        if (config == null) return null;
        List<String> oldList = config.getStringList(path + ".lore");
        List<String> newList = new ArrayList<>();
        for (String a : oldList)
            newList.add(ChatColor.translateAlternateColorCodes('&', a));
        return newList;
    }

    // ---- [ Check if String is only letters ] ----
    public static boolean isOnlyLetters(String s) {
        return !s.matches("[a-zA-Z]+");
    }

    // ---- [ Cached Items ] ----
    public static Map<ArmorStand, Long> activeHolograms = new HashMap<>();
    public static Map<UUID, Map<Integer, String>> itemSlot = new HashMap<>();

}
