package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.struts.Menu;
import net.peligon.PeligonEconomy.libaries.struts.MenuOwnerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;

public class menuATM extends Menu {

    private final Main plugin = Main.getInstance;
    public menuATM(MenuOwnerUtil menuOwnerUtil) {
        super(menuOwnerUtil);
    }

    @Override
    public String getMenuName() {
        return Utils.chatColor(plugin.fileATM.getConfig().getString("atm-inventory.title"));
    }

    @Override
    public int getSlots() {
        return Math.min(plugin.fileATM.getConfig().getInt("atm-inventory.size"), 54);
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        for (String key : plugin.fileATM.getConfig().getConfigurationSection("atm-inventory.contents").getKeys(false)) {
            if (item.getType().equals(Material.getMaterial(plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".item").toUpperCase()))
                    && item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".name")))) {
                if (plugin.fileATM.getConfig().contains("atm-inventory.contents." + key + ".event")) {
                    switch (plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".event").toLowerCase()) {
                        case "deposit":
                            player.openInventory(new menuDeposit(new MenuOwnerUtil(player)).getInventory());
                            return;
                        case "withdraw":
                            player.openInventory(new menuWithdraw(new MenuOwnerUtil(player)).getInventory());
                            return;
                        case "close":
                            player.closeInventory();
                            event.setCancelled(true);
                            return;
                        case "transactions":
                            event.setCancelled(true);
                            return;
                    }
                } else {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        Player player = menuOwnerUtil.getOwner();

        for (String key : plugin.fileATM.getConfig().getConfigurationSection("atm-inventory.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".item").toUpperCase()));
            if (plugin.fileATM.getConfig().contains("atm-inventory.contents." + key + ".amount")) {
                item.setAmount(plugin.fileATM.getConfig().getInt("atm-inventory.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".name")));
            if (plugin.fileATM.getConfig().contains("atm-inventory.contents." + key + ".lore")) {
                ArrayList<String> lore = new ArrayList<>();

                String limit = plugin.fileMessage.getConfig().getString("no-bank-limit");
                if (plugin.fileATM.getConfig().getInt("Options.bank-limit") != -1) {
                    limit = getBracket(plugin.fileATM.getConfig().getInt("Options.bank-limit"), 0);
                }

                if (plugin.fileATM.getConfig().contains("atm-inventory.contents." + key + ".event") &&
                        plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".event").equalsIgnoreCase("transactions")) {
                    if (Utils.transactions.containsKey(player.getUniqueId()) && Utils.transactions.get(player.getUniqueId()).size() > 0) {
                        for (String line : plugin.fileATM.getConfig().getStringList("atm-inventory.contents." + key + ".lore")) {
                            if (line.contains("%transactions%")) continue;
                            lore.add(Utils.chatColor(line)
                                    .replaceAll("%cash%", formatted(plugin.Economy.getAccount(player)))
                                    .replaceAll("%bank%", formatted(plugin.Economy.getBank(player)))
                                    .replaceAll("%bank_limit%", limit));

                        }
                        for (LocalDateTime date : Utils.transactions.get(player.getUniqueId()).keySet()) {
                            System.out.println(Utils.transactions.get(player.getUniqueId()).get(date));
                            lore.add(Utils.chatColor(Utils.transactions.get(player.getUniqueId()).get(date)
                                    .replaceAll("%time%", Utils.formatDate(date, LocalDateTime.now()))));
                        }

                    } else {
                        for (String line : plugin.fileATM.getConfig().getStringList("atm-inventory.contents." + key + ".lore")) {
                            lore.add(Utils.chatColor(line)
                                    .replaceAll("%cash%", formatted(plugin.Economy.getAccount(player)))
                                    .replaceAll("%bank%", formatted(plugin.Economy.getBank(player)))
                                    .replaceAll("%bank_limit%", limit)
                                    .replaceAll("%transactions%", Utils.chatColor(plugin.fileMessage.getConfig().getString("no-transactions")))
                                    .replaceAll("%interest%", "" + plugin.fileATM.getConfig().getInt("Options.interest.percentage"))
                                    .replaceAll("%interest_cash%", Utils.formatAmount(plugin.fileATM.getConfig().getInt("Options.interest.cash")))
                                    .replaceAll("%raw_time%", plugin.fileATM.getConfig().getString("Options.interest.time"))
                                    .replaceAll("%time%", Utils.formatTime(plugin.fileATM.getConfig().getString("Options.interest.time"))));

                        }
                    }
                } else {
                    for (String line : plugin.fileATM.getConfig().getStringList("atm-inventory.contents." + key + ".lore")) {
                        lore.add(Utils.chatColor(line)
                                .replaceAll("%cash%", formatted(plugin.Economy.getAccount(player)))
                                .replaceAll("%bank%", formatted(plugin.Economy.getBank(player)))
                                .replaceAll("%bank_limit%", limit)
                                .replaceAll("%interest%", "" + plugin.fileATM.getConfig().getInt("Options.interest.percentage"))
                                .replaceAll("%interest_cash%", Utils.formatAmount(plugin.fileATM.getConfig().getInt("Options.interest.cash")))
                                .replaceAll("%raw_time%", plugin.fileATM.getConfig().getString("Options.interest.time"))
                                .replaceAll("%time%", Utils.formatTime(plugin.fileATM.getConfig().getString("Options.interest.time"))));

                    }
                }
                meta.setLore(lore);
            }

            if (plugin.fileATM.getConfig().contains("atm-inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.fileATM.getConfig().getStringList("atm-inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }

            if (plugin.fileATM.getConfig().contains("atm-inventory.contents." + key + ".enchantments")) {
                for (String enchant : plugin.fileATM.getConfig().getConfigurationSection("atm-inventory.contents." + key + ".enchantments").getKeys(false)) {
                    meta.addEnchant(Enchantment.getByName(plugin.fileATM.getConfig().getString("atm-inventory.contents." + key + ".enchantments." + enchant + ".type").toUpperCase()),
                            plugin.fileATM.getConfig().getInt("atm-inventory.contents." + key + ".enchantments." + enchant + ".type"),
                            false);
                }
            }
            item.setItemMeta(meta);

            if (plugin.fileATM.getConfig().getInt("atm-inventory.contents." + key + ".slot") == -1) {
                for (int i=0; i<plugin.fileATM.getConfig().getInt("atm-inventory.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.fileATM.getConfig().getInt("atm-inventory.contents." + key + ".slot") - 1, item);
            }
        }
    }

    private String formatted(double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

    private String getBracket(double n, int iteration) {
        String[] c = new String[]{"Thousand", "Million", "Billion", "Trillion"};
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) %10 == 0;
        return d < 1000? //this determines the class, i.e. 'k', 'm' etc
                (isRound || d > 9.99 ? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration]
                : getBracket(d, iteration+1);
    }

}
