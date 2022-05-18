package net.peligon.PeligonEconomy.libaries;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.managers.mgrSignFactory;
import net.peligon.PeligonEconomy.menu.menuATM;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Utils {

    private static final Main plugin = Main.getInstance;

    // ---- [ Managing chat color within the plugin ] ----
    public static String chatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
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

    // ---- [ Add transactions ] ----
    public static void addTransaction(Player player, String transaction) {
        Map<LocalDateTime, String> list = new HashMap<>();

        if (Utils.transactions.containsKey(player.getUniqueId())) {
            for (LocalDateTime date : Utils.transactions.get(player.getUniqueId()).keySet()) {
                list.put(date, Utils.chatColor(Utils.transactions.get(player.getUniqueId()).get(date)));
            }
        } else {
            list.put(LocalDateTime.now(), transaction);
        }
        transactions.put(player.getUniqueId(), list);
    }

    // ---- [ Open Sign Editor ] ----
    public static void openSign(Player target, int result, String objective, List<String> lines) {
        mgrSignFactory.Menu menu = plugin.signFactory.newMenu(lines)
                .reopenIfFail(true)
                .response((player, strings) -> {
                    if (strings[result].equals("") || strings[result].equals("0")) return true;
                    double amount;
                    try {
                        amount = Double.parseDouble(strings[result]);
                    } catch (Exception e) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                        return false;
                    }
                    if (amount < 0) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                        return false;
                    }
                    if (!plugin.Economy.hasAccount(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("account-error")
                                .replaceAll("%player%", player.getName())
                                .replaceAll("%target%", player.getName())));
                        return true;
                    }

                    if (objective.equals("deposit")) {
                        if (!plugin.Economy.hasEnoughCash(player, amount)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                            return false;
                        }

                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("deposited-money"), amount));
                        plugin.Economy.removeAccount(player, amount);
                        plugin.Economy.addBankAccount(player, amount);

                        Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-add"), amount)
                                .replaceAll("%player%", player.getName()));

                    } else if (objective.equals("withdraw")) {
                        if (!plugin.Economy.hasEnoughBank(player, amount)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                            return false;
                        }

                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("withdrawn-money"), amount));
                        plugin.Economy.addAccount(player, amount);
                        plugin.Economy.removeBankAccount(player, amount);

                        Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                .replaceAll("%player%", player.getName()));

                    }
                    player.openInventory(new menuATM(player).getInventory());
                    return true;
                });

        menu.open(target);
    }

    /// --- [ Formatting Objects ] ----
    public static String formatDate(LocalDateTime dateOne, LocalDateTime dateTwo) {
        if (Duration.between(dateOne, dateTwo).getSeconds() < 60) {
            if (Duration.between(dateOne, dateTwo).getSeconds() == 1) {
                return Duration.between(dateOne, dateTwo).getSeconds() + " second";
            }
            return Duration.between(dateOne, dateTwo).getSeconds() + " seconds";
        } else if (Duration.between(dateOne, dateTwo).toMinutes() < 60) {
            if (Duration.between(dateOne, dateTwo).toMinutes() == 1) {
                return Duration.between(dateOne, dateTwo).toMinutes() + " minute";
            }
            return Duration.between(dateOne, dateTwo).toMinutes() + " minutes";
        } else if (Duration.between(dateOne, dateTwo).toHours() < 24) {
            if (Duration.between(dateOne, dateTwo).toHours() == 1) {
                return Duration.between(dateOne, dateTwo).toHours() + " hour";
            }
            return Duration.between(dateOne, dateTwo).toHours() + " hours";
        } else {
            if (Duration.between(dateOne, dateTwo).toDays() == 1) {
                return Duration.between(dateOne, dateTwo).toDays() + " day";
            }
            return Duration.between(dateOne, dateTwo).toDays() + " days";
        }
    }

    public static String formatTime(String time) {
        return time
                .replaceAll("d", "")
                .replaceAll("h", "")
                .replaceAll("m", "")
                .replaceAll("s", "");
    }

    public static String formatAmount(int amount) {
        return Math.abs(amount) >= 1e+9 ? String.format("%.0f", (Math.abs(amount) / 1e+9)) + " Billion"
                : Math.abs(amount) >= 1e+6 ? String.format("%.0f", (Math.abs(amount) / 1e+6)) + " Million"
                : Math.abs(amount) >= 1e+3 ? String.format("%.0f", (Math.abs(amount) / 1e+3)) + " Thousand"
                : String.valueOf(Math.abs(amount));
    }

    // ---- [ Cached Items ] ----
    public static Map<UUID, Integer> KillStreak = new HashMap<>();
    public static Map<UUID, Double> bounties = new HashMap<>();
    public static Map<UUID, Map<LocalDateTime, String>> transactions = new HashMap<>();
    public static Map<ArmorStand, Long> activeHolograms = new HashMap<>();
    public static Map<UUID, Long> dailyCooldown = new HashMap<>();

    // ---- [ Checking if a day has passed ] ----
    public static boolean canClaim(Player player) {
        if (dailyCooldown.containsKey(player.getUniqueId())) {
            return dailyCooldown.get(player.getUniqueId()) + 86400000 <= System.currentTimeMillis();
        }
        return true;
    }

    // ---- [ Global values ] ----
    public static int RawInterestTimer;
    static {
        String path = plugin.fileATM.getConfig().getString("Options.interest.time");
        if (path.contains("d")) {
            RawInterestTimer = Integer.parseInt(Utils.formatTime(path)) * 24 * 60 * 60;
        } else if (path.contains("h")) {
            RawInterestTimer = Integer.parseInt(Utils.formatTime(path)) * 60 * 60;
        } else if (path.contains("m")) {
            RawInterestTimer = Integer.parseInt(Utils.formatTime(path)) * 60;
        } else {
            RawInterestTimer = Integer.parseInt(Utils.formatTime(path));
        }
    }

    public static int InterestTimer = 0;

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

    public static void sendPlayerMovingMessage(Player player, Double amount, String title, Boolean isEconomy) {
        String sign = "&2$";
        String magic = "&k";

        String formatted = Utils.chatColor("%amount%", amount);

        new BukkitRunnable() {
            int pos = 0;
            public void run() {
                if (pos == formatted.length()) {
                    cancel();
                    if (isEconomy) {
                        plugin.Economy.addAccount(player, amount);
                    } else {
                        addPlayerExp(player, amount.intValue());
                    }
                }
                if (isEconomy) {
                    player.sendTitle(Utils.chatColor(title),
                            Utils.chatColor(sign + formatted.substring(0, pos) + magic + formatted.substring(pos)));
                } else {
                    player.sendTitle(Utils.chatColor(title),
                            Utils.chatColor("" + formatted.substring(0, pos) + magic + formatted.substring(pos)));
                }

                pos += 1;
            }
        }.runTaskTimer(Main.getInstance,0, 20);
    }

    public static void sendPlayerMovingMessage(Player player, Integer amount, String title, Boolean isEconomy) {
        String sign = "&2$";
        String magic = "&k";

        String formatted = Utils.chatColor("%amount%", Double.valueOf(amount));

        new BukkitRunnable() {
            int pos = 0;
            public void run() {
                if (pos == formatted.length()) {
                    cancel();
                    if (isEconomy) {
                        plugin.Economy.addAccount(player, amount);
                    } else {
                        addPlayerExp(player, amount.intValue());
                    }
                }
                if (isEconomy) {
                    player.sendTitle(Utils.chatColor(title),
                            Utils.chatColor(sign + formatted.substring(0, pos) + magic + formatted.substring(pos)));
                } else {
                    player.sendTitle(Utils.chatColor(title),
                            Utils.chatColor("" + formatted.substring(0, pos) + magic + formatted.substring(pos)));
                }
                pos += 1;
            }
        }.runTaskTimer(Main.getInstance,0, 20);
    }

}
