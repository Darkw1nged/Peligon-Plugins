package net.peligon.Teams.libaries;

import net.peligon.Teams.Main;
import net.peligon.Teams.libaries.teamSettings.Rank;
import net.peligon.Teams.libaries.teamSettings.Upgrade;
import net.peligon.Teams.libaries.teamSettings.Vault;
import org.bukkit.ChatColor;
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
import java.util.stream.Collectors;

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
        return !s.matches("[a-zA-Z]+");
    }

    // ---- [ Cached Items ] ----
    public static Map<ArmorStand, Long> activeHolograms = new HashMap<>();
    public static List<Team> teams = new ArrayList<>();

    // ---- [ Manage players experience ] ----
    public static int getExpToLevelUp(int level){
        if(level <= 15){
            return 2 * level + 7;
        } else if(level <= 30){
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }

    public static int getExpAtLevel(int level){
        if(level <= 16){
            return (int) (Math.pow(level,2) + 6*level);
        } else if(level <= 31){
            return (int) (2.5*Math.pow(level,2) - 40.5*level + 360.0);
        } else {
            return (int) (4.5*Math.pow(level,2) - 162.5*level + 2220.0);
        }
    }

    public static int getPlayerExp(Player player){
        int exp = 0;
        int level = player.getLevel();

        exp += getExpAtLevel(level);

        exp += Math.round(getExpToLevelUp(level) * player.getExp());

        return exp;
    }

    public static void removePlayerExp(Player player, int exp){
        // Get player's current exp
        int currentExp = getPlayerExp(player);

        // Reset player's current exp to 0
        player.setExp(0);
        player.setLevel(0);

        // Give the player their exp back, with the difference
        int newExp = currentExp - exp;
        player.giveExp(newExp);
    }

    public static void addPlayerExp(Player player, int exp){
        // Get player's current exp
        int currentExp = getPlayerExp(player);

        // Reset player's current exp to 0
        player.setExp(0);
        player.setLevel(0);

        // Give the player their exp back, with the difference
        int newExp = currentExp + exp;
        player.giveExp(newExp);
    }

    // ---- [ Load all teams ] ----

    public static void loadTeams() {
        File teamsFolder = new File(plugin.getDataFolder(), "teams");
        File[] teamFiles = teamsFolder.listFiles();
        if (teamFiles == null) return;

        for (File file : teamFiles) {
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String name = configuration.getString("name");
            String description = configuration.getString("description");
            UUID leader = UUID.fromString(configuration.getString("leader"));

            List<String> rawList = configuration.getStringList("members");
            List<UUID> members = new ArrayList<>();
            for (String member : rawList) {
                members.add(UUID.fromString(member));
            }

            rawList = configuration.getStringList("banned");
            List<UUID> banned = new ArrayList<>();
            for (String bannedMember : rawList) {
                banned.add(UUID.fromString(bannedMember));
            }

            Map<UUID, Rank> playerRanks = configuration.getConfigurationSection("playerRanks").getKeys(false).stream().collect(Collectors.toMap(
                    key -> UUID.fromString(key),
                    key -> Rank.valueOf(configuration.getString("playerRanks." + key))
            ));

            Double teamBankVault = configuration.getDouble("teamBankVault");
            Integer teamExperienceVault = configuration.getInt("teamExperienceVault");

            Map<Upgrade, Boolean> unlockedUpgrades = configuration.getConfigurationSection("unlockedUpgrades").getKeys(false).stream().collect(Collectors.toMap(
                    key -> Upgrade.valueOf(configuration.getString("unlockedUpgrades." + key)),
                    key -> configuration.getBoolean("unlockedUpgrades." + key)
            ));

            Integer maximumVaults = configuration.getInt("maximumVaults");

            List<Vault> vaults = new ArrayList<>();
            for (String vault : configuration.getConfigurationSection("vaults").getKeys(false)) {
                List<String> rawVault = configuration.getStringList("vaults." + vault);
                ItemStack[] items = new ItemStack[rawVault.size()];
                vaults.add(new Vault(items, Integer.parseInt(vault), configuration.getBoolean("vaults." + vault + ".secured")));
            }

            teams.add(new Team(name, description, leader, members, banned, playerRanks, teamBankVault, teamExperienceVault, unlockedUpgrades, maximumVaults, vaults));
        }
    }
}
