package net.peligon.Teams.libaries;

import net.peligon.Teams.libaries.lists.Ranks;
import net.peligon.Teams.libaries.struts.Team;
import net.peligon.Teams.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.text.NumberFormat;
import java.util.*;

public class Utils {

    private static final Main plugin = Main.getInstance;

    // ---- [ Managing chat color within the plugin ] ----
    public static String chatColor(String s) {
        return s == null ? null : ChatColor.translateAlternateColorCodes('&', s);
    }

    // ---- [ Managing chat color within the plugin | Supports Amount ] ----
    public static String chatColor(String s, Double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        String converted = nf.format(amount);
        return ChatColor.translateAlternateColorCodes('&', s)
                .replaceAll("%amount%", converted);
    }

    // ---- [ Format numbers ] ----
    public static String format(Double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

    public static String format(Integer amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
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

    // ---- [ Check if String is only letters ] ----
    public static boolean isOnlyLetters(String s) {
        return s.matches("[a-zA-Z]+");
    }

    // ---- [ Cached Items ] ----
    public static Map<ArmorStand, Long> activeHolograms = new HashMap<>();
    public static List<Team> teams = new ArrayList<>();
    public static List<UUID> adminMode = new ArrayList<>();

    // ---- [ Manage players experience ] ----
    public static int getExpToLevelUp(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        } else if (level <= 30) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }

    public static int getExpAtLevel(int level) {
        if (level <= 16) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if (level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
        } else {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
        }
    }

    public static int getPlayerExp(Player player) {
        int exp = 0;
        int level = player.getLevel();

        exp += getExpAtLevel(level);

        exp += Math.round(getExpToLevelUp(level) * player.getExp());

        return exp;
    }

    public static void removePlayerExp(Player player, int exp) {
        // Get player's current exp
        int currentExp = getPlayerExp(player);

        // Reset player's current exp to 0
        player.setExp(0);
        player.setLevel(0);

        // Give the player their exp back, with the difference
        int newExp = currentExp - exp;
        player.giveExp(newExp);
    }

    public static void addPlayerExp(Player player, int exp) {
        // Get player's current exp
        int currentExp = getPlayerExp(player);

        // Reset player's current exp to 0
        player.setExp(0);
        player.setLevel(0);

        // Give the player their exp back, with the difference
        int newExp = currentExp + exp;
        player.giveExp(newExp);
    }

    public static void loadTeams() {
        File folder = new File(plugin.getDataFolder() + "/teams");
        if (!folder.exists()) return;

        for (String file : new File(plugin.getDataFolder() + "/teams").list()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/teams/" + file));

            System.out.println(config.getString("name"));

            List<UUID> members = new ArrayList<>();
            for (String member : config.getStringList("members")) {
                members.add(UUID.fromString(member));
            }

            Map<UUID, Ranks> ranks = new HashMap<>();
            for (String member : config.getStringList("ranks")) {
                String[] split = member.split(":");
                ranks.put(UUID.fromString(split[0]), Ranks.valueOf(split[1]));
            }

            Map<UUID, String> tags = new HashMap<>();
            for (String member : config.getStringList("tags")) {
                String[] split = member.split(":");
                tags.put(UUID.fromString(split[0]), split[1]);
            }

            List<UUID> banned = new ArrayList<>();
            for (String member : config.getStringList("banned")) {
                banned.add(UUID.fromString(member));
            }

            Map<String, Location> warps = new HashMap<>();
            for (String member : config.getStringList("warps")) {
                String[] split = member.split(":");
                warps.put(split[0], new Location(Bukkit.getWorld(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Double.parseDouble(split[4]), Float.parseFloat(split[5]), Float.parseFloat(split[6])));
            }

            Map<String, Integer> upgrades = new HashMap<>();
            for (String member : config.getStringList("upgrades")) {
                String[] split = member.split(":");
                upgrades.put(split[0], Integer.parseInt(split[1]));
            }

            List<Team> allies = new ArrayList<>();
            for (String name : config.getStringList("allies")) {
                allies.add(plugin.teamManager.getTeam(name));
            }

            List<Team> enemies = new ArrayList<>();
            for (String name : config.getStringList("enemies")) {
                enemies.add(plugin.teamManager.getTeam(name));
            }

            List<Team> truces = new ArrayList<>();
            for (String name : config.getStringList("truces")) {
                truces.add(plugin.teamManager.getTeam(name));
            }

            List<Chunk> claimed = new ArrayList<>();
            for (String chunk : config.getStringList("claimed")) {
                String[] split = chunk.split(":");
                claimed.add(Bukkit.getWorld(split[0]).getChunkAt(Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }

            Location home = config.contains("home") ? new Location(Bukkit.getWorld(config.getString("home.world")), Double.parseDouble(config.getString("home.x")), Double.parseDouble(config.getString("home.y")), Double.parseDouble(config.getString("home.z")), Float.parseFloat(config.getString("home.yaw")), Float.parseFloat(config.getString("home.pitch"))) : null;

            Team team = new Team(
                    config.getString("name"),
                    config.getString("descriptiom"),
                    config.getString("default-tag"),
                    UUID.fromString(config.getString("leader")),
                    members,
                    config.getInt("max-members"),
                    ranks,
                    tags,
                    banned,
                    warps,
                    home,
                    config.getDouble("bank"),
                    upgrades,
                    allies,
                    enemies,
                    truces,
                    config.getBoolean("open"),
                    claimed
            );

            teams.add(team);
        }
    }

}
