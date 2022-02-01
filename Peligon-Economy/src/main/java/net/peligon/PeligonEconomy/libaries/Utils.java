package net.peligon.PeligonEconomy.libaries;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.managers.mgrSignFactory;
import net.peligon.PeligonEconomy.menu.menuATM;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
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

    // ---- [ Managing holograms for small amount of features ] ----
    public static void hologram(String name, Location loc) {
        ArmorStand holo = loc.getWorld().spawn(loc, ArmorStand.class);

        // ---- [ Settings flags for entity ] ----
        holo.setCustomName(chatColor(name));
        holo.setCustomNameVisible(true);
        holo.setGravity(false);
        holo.setInvisible(true);
        holo.setInvulnerable(true);
    }

    public static void hologram(String name, Location loc, int length) {
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

        // ---- [ Disappear after a certain amount of time ] ----
        new BukkitRunnable() {
            int time = 0;

            public void run() {
                if (time >= length) {
                    holo.remove();
                    cancel();
                    return;
                }
                time += 1;
            }
        }.runTaskTimer(plugin, 20, 20);
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

    // ---- [ Cached Items ] ----
    public static Map<UUID, Integer> KillStreak = new HashMap<>();
    public static Map<UUID, Double> bounties = new HashMap<>();
    public static Map<UUID, List<String>> transactions = new HashMap<>();


    // ---- [ Add transactions ] ----
    public static void addTransaction(Player player, String transaction) {
        if (transactions.containsKey(player.getUniqueId())) {
            List<String> list = transactions.get(player.getUniqueId());
            list.add(transaction);
            transactions.put(player.getUniqueId(), list);
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(transaction);
            transactions.put(player.getUniqueId(), temp);
        }
    }

    // ---- [ Open Sign Editor ] ----
    public static void openSign(Player target, int result, String objective, List<String> lines) {
        mgrSignFactory.Menu menu = plugin.signFactory.newMenu(lines)
                .reopenIfFail(true)
                .response((player, strings) -> {
                    menuATM atm = new menuATM(player);
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
                        plugin.Economy.RemoveAccount(player, amount);
                        plugin.Economy.AddBankAccount(player, amount);

                        Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-add"), amount)
                                .replaceAll("%player%", player.getName()));

                    } else if (objective.equals("withdraw")) {
                        if (!plugin.Economy.hasEnoughBank(player, amount)) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                            return false;
                        }

                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("withdrawn-money"), amount));
                        plugin.Economy.AddAccount(player, amount);
                        plugin.Economy.RemoveBankAccount(player, amount);

                        Utils.addTransaction(player, Utils.chatColor(plugin.fileATM.getConfig().getString("Options.transaction-remove"), amount)
                                .replaceAll("%player%", player.getName()));

                    }
                    player.openInventory(atm.getInventory());
                    return true;
                });

        menu.open(target);
    }

}
