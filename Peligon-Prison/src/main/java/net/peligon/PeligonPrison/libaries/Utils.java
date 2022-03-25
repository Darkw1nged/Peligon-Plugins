package net.peligon.PeligonPrison.libaries;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.struts.Prestige;
import net.peligon.PeligonPrison.struts.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.util.*;

public class Utils {

    private static final Main plugin = Main.getInstance;

    // ---- [ Managing chat color within the plugin ] ----
    public static String chatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
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
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() == targetItem.getType()) {
                if (item.getAmount() != item.getMaxStackSize()) {
                    item.setAmount(item.getAmount() + 1);
                    return true;
                }
            }
        }
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(targetItem);
            return true;
        }
        return false;
    }

    // ---- [ Cached Items ] ----
    public static Map<ArmorStand, Long> activeHolograms = new HashMap<>();
    public static List<UUID> autoPickup = new ArrayList<>();
    public static List<UUID> autoSmelt = new ArrayList<>();
    public static List<Rank> ranks = new ArrayList<>();
    public static List<Prestige> prestige = new ArrayList<>();

    // Temporary
    static {
        ranks.add(new Rank("Leather", 100.0, true));
        ranks.add(new Rank("Iron", 500.0));
        ranks.add(new Rank("Gold", 1000.0));
        ranks.add(new Rank("Diamond", 2500.0));
        ranks.add(new Rank("Emerald", 5000.0));
        ranks.add(new Rank("Obsidian", 10000.0));
        ranks.add(new Rank("End", 15000.0));
        ranks.add(new Rank("Nether", 20000.0));
        ranks.add(new Rank("Ender", 35000.0));
        ranks.add(new Rank("Infinity", 50000.0));
        ranks.add(new Rank("The End", 100000.0));
    }

    static {
        prestige.add(new Prestige("Leather", 100.0));
        prestige.add(new Prestige("Iron", 500.0));
        prestige.add(new Prestige("Gold", 1000.0));
        prestige.add(new Prestige("Diamond", 2500.0));
        prestige.add(new Prestige("Emerald", 5000.0));
        prestige.add(new Prestige("Obsidian", 10000.0));
        prestige.add(new Prestige("End", 15000.0));
        prestige.add(new Prestige("Nether", 20000.0));
        prestige.add(new Prestige("Ender", 35000.0));
        prestige.add(new Prestige("Infinity", 50000.0));
        prestige.add(new Prestige("The End", 100000.0));
    }

}
