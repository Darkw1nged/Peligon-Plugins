package net.peligon.EnhancedStorage.libaries;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.struts.Backpack;
import net.peligon.EnhancedStorage.libaries.struts.BackpackItem;
import net.peligon.EnhancedStorage.libaries.struts.PlayerVault;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
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
    public static Map<UUID, PlayerVault> openVaults = new HashMap<>();













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

    // ---- [ Available space ] ----
    public static boolean hasSpace(Player player, ItemStack targetItem) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(targetItem);
            return true;
        }
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) {
                player.getInventory().addItem(targetItem);
                return true;
            }
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                    targetItem.hasItemMeta() && targetItem.getItemMeta().hasDisplayName()) {
                if (item.getItemMeta().getDisplayName().equals(targetItem.getItemMeta().getDisplayName())) {
                    if (item.getType() == targetItem.getType()) {
                        if (item.getAmount() != item.getMaxStackSize()) {
                            item.setAmount(item.getAmount() + targetItem.getAmount());
                            return true;
                        }
                    }
                }
            } else {
                if (item.getType() == targetItem.getType()) {
                    if (item.getAmount() != item.getMaxStackSize()) {
                        item.setAmount(item.getAmount() + targetItem.getAmount());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // ---- [ Get maximum amount that can be added to players inventory ] ----
    public static int getMaxAmount(Player player, Material material, int targetAmount) {
        int maxAmount = 0;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) == null) {
                maxAmount += Math.min(backpacks.get(player.getUniqueId()).getItem(material).getAmount(), 64);
            } else {
                if (player.getInventory().getItem(i).getType() == material) {
                    maxAmount += player.getInventory().getItem(i).getMaxStackSize() - Math.min(player.getInventory().getItem(i).getAmount(), 64);
                }
            }
        }
        return Math.min(maxAmount, targetAmount);
    }

    // ---- [ Check if String is only letters ] ----
    public static boolean isOnlyLetters(String s) {
        return !s.matches("[a-zA-Z]+");
    }

    // ---- [ Cached Items ] ----
    public static Map<ArmorStand, Long> activeHolograms = new HashMap<>();
    public static Map<UUID, Backpack> backpacks = new HashMap<>();
    public static Map<UUID, BackpackItem> backpackItemSelected = new HashMap<>();
    public static Map<UUID, Map<Integer, String>> itemSlot = new HashMap<>();

}
