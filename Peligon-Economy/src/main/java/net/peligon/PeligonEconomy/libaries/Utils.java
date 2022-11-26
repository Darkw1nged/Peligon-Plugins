package net.peligon.PeligonEconomy.libaries;

import net.peligon.PeligonEconomy.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Utils {

    private static final Main plugin = Main.getInstance;

    // Manage all ChatColor within the plugin
    public static String chatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // Manage all ChatColor within the plugin, formats doubles
    public static String chatColor(String s, Double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        String converted = nf.format(amount);
        return ChatColor.translateAlternateColorCodes('&', s)
                .replaceAll("%amount%", converted);
    }

    // Format a double to a string in the correct format
    public static String format(Double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

    // Format a int to a string in the correct format
    public static String format(Integer amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

    // Format time to a string in the correct format
    public static String formatTime(String time) {
        // Defining the time format
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        // Splitting the time into days, hours, minutes, and seconds
        String[] split = time.split(", ");
        for (String s : split) {
            if (s.contains("d")) {
                days = Integer.parseInt(s.replace("d", ""));
            } else if (s.contains("h")) {
                hours = Integer.parseInt(s.replace("h", ""));
            } else if (s.contains("m")) {
                minutes = Integer.parseInt(s.replace("m", ""));
            } else if (s.contains("s")) {
                seconds = Integer.parseInt(s.replace("s", ""));
            }
        }

        // Formatting the time
        String formatted = "";
        if (days > 0) {
            formatted += days + "d ";
        }
        if (hours > 0) {
            formatted += hours + "h ";
        }
        if (minutes > 0) {
            formatted += minutes + "m ";
        }
        if (seconds > 0) {
            formatted += seconds + "s ";
        }

        // Returning the formatted time
        return formatted.trim();
    }

    // Format time using date to a string in the correct format
    public static String formatTime(Date date) {
        // Get the current time
        Date now = new Date();

        // Get the difference between the current time and the date
        Duration diff = Duration.between(date.toInstant(), now.toInstant());
        //System.out.println("diff: " + diff);

        // Defining the time format
        long seconds = diff.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        //System.out.println("seconds: " + seconds + ", minutes: " + minutes + ", hours: " + hours + ", days: " + days);

        // Formatting the time
        String formatted = "";
        if (days > 0) {
            formatted += days + "d, ";
        }
        if (hours > 0) {
            formatted += hours + "h, ";
        }
        if (minutes > 0) {
            formatted += minutes + "m, ";
        }
        if (seconds > 0) {
            formatted += seconds + "s";
        }
        //System.out.println("formatted: " + formatted);

        // Returning the formatted time
        return formatted;
    }

    // Get abbreviation from a string and convert it to a double
    public static Double getAbbreviation(String s) {
        double amount;
        if (s.toLowerCase().endsWith("k")) {
            amount = Double.parseDouble(s.substring(0, s.length() - 1)) * 1000;
        } else if (s.toLowerCase().endsWith("m")) {
            amount = Double.parseDouble(s.substring(0, s.length() - 1)) * 1000000;
        } else if (s.toLowerCase().endsWith("b")) {
            amount = Double.parseDouble(s.substring(0, s.length() - 1)) * 1000000000;
        } else if (s.toLowerCase().endsWith("t")) {
            amount = Double.parseDouble(s.substring(0, s.length() - 1)) * 1000000000000.0;
        } else {
            amount = Double.parseDouble(s);
        }
        return amount;
    }

    // Get abbreviation from a string and convert it to an int
    public static Integer getAbbreviationInt(String s) {
        int amount = 0;
        if (s.toLowerCase().endsWith("k")) {
            amount = Integer.parseInt(s.substring(0, s.length() - 1)) * 1000;
        } else if (s.toLowerCase().endsWith("m")) {
            amount = Integer.parseInt(s.substring(0, s.length() - 1)) * 1000000;
        } else if (s.toLowerCase().endsWith("b")) {
            amount = Integer.parseInt(s.substring(0, s.length() - 1)) * 1000000000;
        } else {
            amount = Integer.parseInt(s);
        }
        return amount;
    }

    // Check if the player has enough space to add a new item.
    public static boolean hasSpace(Inventory inventory, ItemStack targetItem, int amount) {
        // If amount is 0, return true
        if (amount == 0) return true;


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

    // Getting the total amount an inventory is worth.
    public static Double getInventoryValue(Inventory inventory) {
        double value = 0;

        // Looping through the inventory.
        for (ItemStack item : inventory.getStorageContents()) {
            // Check if the item is null.
            if (item == null) continue;

            // Check if item is inside of worth.yml
            if (plugin.itemWorthFile.getConfig().contains("worth." + item.getType().name().toUpperCase())) {
                // Adding the value of the item to the total value.
                value += plugin.itemWorthFile.getConfig().getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
                // Set the item amount to 0.
                item.setAmount(0);
            }
        }
        // Return the value.
        return value;
    }

    // Getting the total amount of an item value in an inventory.
    public static Double getInventoryItemValue(Inventory inventory, ItemStack item) {
        double value = 0;

        // Looping through the inventory.
        for (ItemStack itemStack : inventory.getStorageContents()) {
            // Check if the item is null.
            if (itemStack == null) continue;
            // check if the item has cash-value in PersistentDataContainer
            if (itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE)) continue;

            // Check if item is inside of worth.yml
            if (plugin.itemWorthFile.getConfig().contains("worth." + itemStack.getType().name().toUpperCase())) {
                // Check if the item is the same as the target item.
                if (itemStack.getType() == item.getType()) {
                    // Adding the value of the item to the total value.
                    value += plugin.itemWorthFile.getConfig().getDouble("worth." + itemStack.getType().name().toUpperCase()) * itemStack.getAmount();
                    // Set the item amount to 0.
                    itemStack.setAmount(0);
                }
            }
        }
        // Return the value.
        return value;
    }


    // Check if a string is a number.
    public static boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    // TODO ---- SUBJECT FOR REMOVAL ----
    // Open an editable sign
//    public static void openSign(Player target, int result, String objective, List<String> lines) {
//        mgrSignFactory.Menu menu = plugin.signFactory.newMenu(lines)
//                .reopenIfFail(true)
//                .response((player, strings) -> {
//                    if (strings[result].equals("") || strings[result].equals("0")) return true;
//
//                    try {
//                        // Get the amount from the sign
//                        double amount = getAbbreviation(strings[result]);
//
//                        // Check if amount is negative
//                        if (amount < 0) {
//                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-amount")));
//                            return true;
//                        }
//
//                        // Check if the player has data.
//                        if (!playerUtils.hasData(player)) {
//                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-player-data")));
//                            return true;
//                        }
//
//                        // Check the objective
//                        if (objective.equalsIgnoreCase("deposit")) {
//                            // Check if the player has enough cash.
//                            if (!playerUtils.hasEnoughCash(player, amount)) {
//                                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-not-enough-cash")));
//                                return true;
//                            }
//
//                            // Deposit the cash
//                            playerUtils.setBankBalance(player, playerUtils.getBankBalance(player) + amount);
//                            // Remove the cash
//                            playerUtils.setCash(player, playerUtils.getCash(player) - amount);
//
//                            // Send the player a message
//                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") + plugin.languageFile.getConfig().getString("success-deposit"), amount));
//
//                            // Add transaction
//                            Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-add"), amount)
//                                    .replaceAll("%player%", player.getName()));
//                        } else if (objective.equalsIgnoreCase("withdraw")) {
//                            // Check if the player has enough money in their bank.
//                            if (!playerUtils.hasEnoughBankBalance(player, amount)) {
//                                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-not-enough-bank")));
//                                return true;
//                            }
//
//                            // Withdraw the cash
//                            playerUtils.setBankBalance(player, playerUtils.getBankBalance(player) - amount);
//                            // Add the cash
//                            playerUtils.setCash(player, playerUtils.getCash(player) + amount);
//
//                            // Send the player a message
//                            player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") + plugin.languageFile.getConfig().getString("success-withdraw"), amount));
//
//                            // Add transaction
//                            Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
//                                    .replaceAll("%player%", player.getName()));
//                        }
//                    } catch (NumberFormatException e) {
//                        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-amount")));
//                        return true;
//                    }
//                    new menuATM(player).open();
//                    return true;
//                });
//
//        menu.open(target);
//    }


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

//    static {
//        String path = plugin.getConfig().getString("Accounts.banks.options.interest.time");
//        if (path.contains("d")) {
//            RawInterestTimer = Integer.parseInt(Utils.formatTime(path)) * 24 * 60 * 60;
//        } else if (path.contains("h")) {
//            RawInterestTimer = Integer.parseInt(Utils.formatTime(path)) * 60 * 60;
//        } else if (path.contains("m")) {
//            RawInterestTimer = Integer.parseInt(Utils.formatTime(path)) * 60;
//        } else {
//            RawInterestTimer = Integer.parseInt(Utils.formatTime(path));
//        }
//    }

    public static int InterestTimer = 0;

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
                        playerUtils.addPlayerExp(player, amount.intValue());
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
        }.runTaskTimer(Main.getInstance, 0, 20);
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
                        playerUtils.addPlayerExp(player, amount.intValue());
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
        }.runTaskTimer(Main.getInstance, 0, 20);
    }

}
