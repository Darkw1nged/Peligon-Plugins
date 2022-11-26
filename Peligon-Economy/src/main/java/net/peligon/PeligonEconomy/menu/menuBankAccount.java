package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import net.peligon.PeligonEconomy.libaries.struts.Menu;
import net.peligon.PeligonEconomy.libaries.struts.Transaction;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class menuBankAccount extends Menu {

    private final Main plugin = Main.getInstance;

    public menuBankAccount(Player player) {
        super(player);
    }

    @Override
    public String getMenuName() {
        return plugin.bankAccountInventoryFile.getConfig().getString("bank-inventory.title", "&eBank Account");
    }

    @Override
    public int getSlots() {
        return Math.min(plugin.bankAccountInventoryFile.getConfig().getInt("bank-inventory.size", 27), 54);
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        // Check if item is null.
        if (item == null) return;

        // Check if item has meta.
        if (!item.hasItemMeta()) return;
        // Check if item has PersistentDataContainer has event key.
        if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "event"), PersistentDataType.STRING)) return;

        // Get event key.
        String eventKey = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "event"), PersistentDataType.STRING);
        // Check if event key is null or is empty.
        if (eventKey == null || eventKey.equals("")) return;

        // Check if event key is valid.
        switch (eventKey.toLowerCase()) {
            case "deposit":
                new menuDeposit(player).open();
                return;
            case "withdraw":
                new menuWithdraw(player).open();
                return;
            case "close":
                player.closeInventory();
                event.setCancelled(true);
                return;
            case "transactions":
                event.setCancelled(true);
                break;
        }
    }

    @Override
    public void setMenuItems() {
        // Loop through all items in the config.
        for (String key : plugin.bankAccountInventoryFile.getConfig().getConfigurationSection("bank-inventory.contents").getKeys(false)) {
            // Get item from config.
            ItemStack item = new ItemStack(Material.getMaterial(plugin.bankAccountInventoryFile.getConfig().getString("bank-inventory.contents." + key + ".item").toUpperCase()));
            // Get item meta.
            ItemMeta meta = item.getItemMeta();

            // Check if meta is null.
            if (meta == null) {
                // Add item to inventory.
                if (plugin.bankAccountInventoryFile.getConfig().getInt("bank-inventory.contents." + key + ".slot") == -1) {
                    for (int i = 0; i < inventory.getSize(); i++) {
                        inventory.setItem(i, item);
                    }
                } else {
                    inventory.setItem(plugin.bankAccountInventoryFile.getConfig().getInt("bank-inventory.contents." + key + ".slot"), item);
                }

                // Continue to next item.
                continue;
            }

            // Set item name.
            meta.setDisplayName(Utils.chatColor(plugin.bankAccountInventoryFile.getConfig().getString("bank-inventory.contents." + key + ".name")));

            // Getting bank limit.
            String bankLimit = plugin.getConfig().getInt("Accounts.banks.options.bank-limit") == -1 ? plugin.languageFile.getConfig().getString("no-bank-limit") : String.valueOf(plugin.getConfig().getInt("Accounts.banks.options.bank-limit"));

            // Getting the item lore.
            List<String> lore = new ArrayList<>();

            // Check if item "bank-inventory.contents." + key contains ".event".
            if (plugin.bankAccountInventoryFile.getConfig().contains("bank-inventory.contents." + key + ".event") &&
                    plugin.bankAccountInventoryFile.getConfig().getString("bank-inventory.contents." + key + ".event").equalsIgnoreCase("transactions")) {

                for (String line : plugin.bankAccountInventoryFile.getConfig().getStringList("bank-inventory.contents." + key + ".lore")) {
                    if (line.contains("%transactions%")) {
                        line.replaceAll("%transactions%", "");

                        // Get the limit of transactions to show.
                        int transactionLimit = plugin.getConfig().getInt("Accounts.banks.options.transaction-log.limit");

                        // Loop through all transactions. (Reverse order)
                        for (int i = playerUtils.getTransactions(owner).size() - 1; i >= 0; i--) {
                            // Get the transaction.
                            Transaction transaction = playerUtils.getTransactions(owner).get(i);

                            // Check if transaction is null.
                            if (transaction == null) continue;

                            // Check if transaction limit is reached.
                            if (transactionLimit != -1 && i >= transactionLimit) break;

                            // Add transaction to lore.
                            lore.add(Utils.chatColor(transaction.getLogMessage()));
                        }
                        continue;
                    }

                    lore.add(Utils.chatColor(line)
                            .replaceAll("%cash%", Utils.format(playerUtils.getCash(owner)))
                            .replaceAll("%bank%", Utils.format(playerUtils.getBankBalance(owner)))
                            .replaceAll("%bank_limit%", bankLimit == null ? "0" : bankLimit)
                            .replaceAll("%interest%", "" + plugin.getConfig().getDouble("Accounts.banks.options.interest.percentage", 2.0))
                            .replaceAll("%interest_cash%", Utils.format(Utils.getAbbreviation(plugin.getConfig().getString("Accounts.banks.options.interest.maximum", "1M"))))
                            .replaceAll("%raw_time%", plugin.getConfig().getString("Accounts.banks.options.interest.time", "24h"))
                            .replaceAll("%time%", Utils.formatTime(plugin.getConfig().getString("Accounts.banks.options.interest.time", "24h"))));
                }
            } else {
                for (String line : plugin.bankAccountInventoryFile.getConfig().getStringList("bank-inventory.contents." + key + ".lore")) {
                    lore.add(Utils.chatColor(line)
                            .replaceAll("%cash%", Utils.format(playerUtils.getCash(owner)))
                            .replaceAll("%bank%", Utils.format(playerUtils.getBankBalance(owner)))
                            .replaceAll("%bank_limit%", bankLimit == null ? "0" : bankLimit)
                            .replaceAll("%interest%", "" + plugin.getConfig().getDouble("Accounts.banks.options.interest.percentage", 2.0))
                            .replaceAll("%interest_cash%", Utils.format(Utils.getAbbreviation(plugin.getConfig().getString("Accounts.banks.options.interest.maximum", "1M"))))
                            .replaceAll("%raw_time%", plugin.getConfig().getString("Accounts.banks.options.interest.time", "24h"))
                            .replaceAll("%time%", Utils.formatTime(plugin.getConfig().getString("Accounts.banks.options.interest.time", "24h"))));
                }
            }

            // Set item lore.
            meta.setLore(lore);
            // Clear lore list.
            lore.clear();

            // Set item event.
            if (plugin.bankAccountInventoryFile.getConfig().contains("bank-inventory.contents." + key + ".event")) {
                meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "event"), PersistentDataType.STRING, plugin.bankAccountInventoryFile.getConfig().getString("bank-inventory.contents." + key + ".event"));
            }

            // Get all item flags.
            if (plugin.bankAccountInventoryFile.getConfig().contains("bank-inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.bankAccountInventoryFile.getConfig().getStringList("bank-inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }

            // Set item meta.
            item.setItemMeta(meta);
            // Add item to inventory.
            if (plugin.bankAccountInventoryFile.getConfig().getInt("bank-inventory.contents." + key + ".slot") == -1) {
                for (int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.bankAccountInventoryFile.getConfig().getInt("bank-inventory.contents." + key + ".slot"), item);
            }
        }
    }

}
