package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import net.peligon.PeligonEconomy.libaries.struts.Menu;
import net.peligon.PeligonEconomy.libaries.struts.Transaction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class menuWithdraw extends Menu {

    private final Main plugin = Main.getInstance;
    public menuWithdraw(Player player) {
        super(player);
    }

    // Saving instance of default cheque item for later use
    private ItemStack cheque;

    @Override
    public String getMenuName() {
        return plugin.bankAccountInventoryFile.getConfig().getString("withdraw-inventory.title", "&eBank Withdrawal");
    }

    @Override
    public int getSlots() {
        return Math.min(plugin.bankAccountInventoryFile.getConfig().getInt("withdraw-inventory.size", 54), 54);
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
            case "withdraw100%":
                // Get amount of cash player has in the bank.
                double amount = playerUtils.getBankBalance(player);
                // If amount is 0, return.
                if (amount <= 0) return;

                // withdraw amount.
                playerUtils.setCash(player, playerUtils.getCash(player) + amount);

                // Create a new transaction.
                Transaction transaction = new Transaction(UUID.randomUUID(), player, Transaction.TransactionOperation.withdraw,
                        amount, new Date(), plugin.getConfig().getString("Accounts.banks.options.transaction-log.remove"));
                // Add transaction to transaction log.
                playerUtils.addTransaction(player, transaction);

                // Remove cash from player' bank.
                playerUtils.removeBankBalance(player, amount);
                // Send message to player.
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("success-withdraw").replaceAll("%amount%", Utils.format(amount))));

                // Open Bank Account Inventory.
                new menuBankAccount(player).open();
                return;
            case "withdraw50%":
                // Get 50% of cash player has in the bank.
                amount = playerUtils.getBankBalance(player) / 2;
                // If amount is 0, return.
                if (amount <= 0) return;

                // Withdraw amount.
                playerUtils.setCash(player, playerUtils.getCash(player) + amount);

                // Create a new transaction.
                transaction = new Transaction(UUID.randomUUID(), player, Transaction.TransactionOperation.withdraw,
                        amount, new Date(), plugin.getConfig().getString("Accounts.banks.options.transaction-log.remove"));
                // Add transaction to transaction log.
                playerUtils.addTransaction(player, transaction);

                // Remove cash from player' bank.
                playerUtils.removeBankBalance(player, amount);
                // Send message to player.
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("success-withdraw").replaceAll("%amount%", Utils.format(amount))));

                // Open Bank Account Inventory.
                new menuBankAccount(player).open();
                return;
            case "withdraw20%":
                // Get 20% of cash player has.
                amount = (playerUtils.getBankBalance(player) * (20 / 100.0f));
                // If amount is 0, return.
                if (amount <= 0) return;
                // Make sure amount is rounded to 2 decimal places.
                amount = Math.round(amount * Math.pow(10, 2)) / Math.pow(10, 2);

                // Withdraw amount.
                playerUtils.setCash(player, playerUtils.getCash(player) + amount);

                // Create a new transaction.
                transaction = new Transaction(UUID.randomUUID(), player, Transaction.TransactionOperation.withdraw,
                        amount, new Date(), plugin.getConfig().getString("Accounts.banks.options.transaction-log.remove"));
                // Add transaction to transaction log.
                playerUtils.addTransaction(player, transaction);

                // Remove cash from player.
                playerUtils.removeBankBalance(player, amount);
                // Send message to player.
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("success-withdraw").replaceAll("%amount%", Utils.format(amount))));

                // Open Bank Account Inventory.
                new menuBankAccount(player).open();
                return;
            case "withdrawspecific":
                // TODO get amount to withdraw from chat.
                return;
            case "addtowithdraw":
                // Check if item has PersistentDataContainer has worth key.
                if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "worth"), PersistentDataType.DOUBLE)) return;
                // Get how much should be added to withdraw.
                double worth = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "worth"), PersistentDataType.DOUBLE);

                // If amount is 0, return.
                if (worth <= 0) return;

                // Get cheque amount.
                double chequeAmount = cheque.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE);
                // Add worth to cheque amount.
                chequeAmount += worth;

                // Check if player has enough cash.
                if (playerUtils.hasEnoughBankBalance(player, chequeAmount)) {
                    // Add amount to cheque.
                    addToCheque(worth);
                } else {
                    // Send message to player.
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-not-enough-money")));

                    // Create a temporary item.
                    ItemStack tempItem = new ItemStack(Material.BARRIER);
                    ItemMeta tempItemMeta = tempItem.getItemMeta();
                    tempItemMeta.setDisplayName(Utils.chatColor(plugin.languageFile.getConfig().getString("error-not-enough-money-name")));
                    tempItem.setItemMeta(tempItemMeta);

                    // Update inventory.
                    event.getInventory().setItem(event.getSlot(), tempItem);
                    // After 2 seconds, replace item with original item.
                    Bukkit.getScheduler().runTaskLater(plugin, () -> event.getInventory().setItem(event.getSlot(), item), 40);
                }
                return;
            case "removefromwithdraw":
                // Check if item has PersistentDataContainer has worth key.
                if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "worth"), PersistentDataType.DOUBLE)) return;
                // Get how much should be removed from withdraw.
                worth = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "worth"), PersistentDataType.DOUBLE);

                // If amount is 0, return.
                if (worth <= 0) return;

                // Remove amount from cheque.
                removeFromCheque(worth);
                return;
            case "cheque":
                // Get cheque amount.
                chequeAmount = cheque.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE);

                // If amount is 0, return.
                if (chequeAmount <= 0) {
                    // Open Bank Account Inventory.
                    new menuBankAccount(player).open();
                    return;
                }

                // Withdraw amount.
                playerUtils.setCash(player, playerUtils.getCash(player) + chequeAmount);

                // Create a new transaction.
                transaction = new Transaction(UUID.randomUUID(), player, Transaction.TransactionOperation.withdraw,
                        chequeAmount, new Date(), plugin.getConfig().getString("Accounts.banks.options.transaction-log.remove"));
                // Add transaction to transaction log.
                playerUtils.addTransaction(player, transaction);

                // Remove cash from player.
                playerUtils.removeBankBalance(player, chequeAmount);
                // Send message to player.
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("success-withdraw").replaceAll("%amount%", Utils.format(chequeAmount))));

                // Open Bank Account Inventory.
                new menuBankAccount(player).open();
                return;
            case "goback":
                new menuBankAccount(player).open();
                event.setCancelled(true);
                return;
        }
    }

    @Override
    public void setMenuItems() {
// Keep track if item is a cheque.
        boolean isCheque = false;

        // Loop through all items in the config.
        for (String key : plugin.bankAccountInventoryFile.getConfig().getConfigurationSection("withdraw-inventory.contents").getKeys(false)) {
            // Get item from config.
            ItemStack item = new ItemStack(Material.getMaterial(plugin.bankAccountInventoryFile.getConfig().getString("withdraw-inventory.contents." + key + ".item").toUpperCase()));
            // Set amount of item.
            if (plugin.bankAccountInventoryFile.getConfig().contains("withdraw-inventory.contents." + key + ".amount")) {
                item.setAmount(Math.min(plugin.bankAccountInventoryFile.getConfig().getInt("withdraw-inventory.contents." + key + ".amount"), 64));
            }

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
            meta.setDisplayName(Utils.chatColor(plugin.bankAccountInventoryFile.getConfig().getString("withdraw-inventory.contents." + key + ".name")));

            // Getting the item lore.
            List<String> lore = new ArrayList<>();
            // Loop through all lore in the config.
            for (String loreLine : plugin.bankAccountInventoryFile.getConfig().getStringList("withdraw-inventory.contents." + key + ".lore")) {
                // Add lore to list.
                lore.add(Utils.chatColor(loreLine)
                        .replaceAll("%cash%", Utils.format(playerUtils.getCash(owner)))
                        .replaceAll("%bank%", Utils.format(playerUtils.getBankBalance(owner))));
            }

            // Set item lore.
            meta.setLore(lore);

            // Set item event.
            if (plugin.bankAccountInventoryFile.getConfig().contains("withdraw-inventory.contents." + key + ".event")) {
                // Get event key.
                String eventKey = plugin.bankAccountInventoryFile.getConfig().getString("withdraw-inventory.contents." + key + ".event");
                // Check if eventKey is equal to "Cheque".
                if (eventKey.equalsIgnoreCase("Cheque")) {
                    // Set isCheque to true.
                    isCheque = true;

                    // Adding relevant information to the item.
                    meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE, 0.0);

                    // Get slot of item.
                    int slot = plugin.bankAccountInventoryFile.getConfig().getInt("withdraw-inventory.contents." + key + ".slot");
                    // Add slot to item data.
                    meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "slot"), PersistentDataType.INTEGER, slot);
                } else if (eventKey.equalsIgnoreCase("AddToWithdraw") || eventKey.equalsIgnoreCase("RemoveFromWithdraw")) {
                    // Get amount to add/remove.
                    double amount = plugin.bankAccountInventoryFile.getConfig().getDouble("withdraw-inventory.contents." + key + ".worth");
                    // Check if amount is negative.
                    if (amount <= 0) amount = 1;

                    // Adding relevant information to the item.
                    meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "worth"), PersistentDataType.DOUBLE, amount);
                }

                meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "event"), PersistentDataType.STRING, eventKey);
            }

            // Get all item flags.
            if (plugin.bankAccountInventoryFile.getConfig().contains("withdraw-inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.bankAccountInventoryFile.getConfig().getStringList("withdraw-inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }

            // Set item meta.
            item.setItemMeta(meta);

            // If isCheque is true, set cheque to current item.
            if (isCheque) {
                cheque = item;
                isCheque = false;
            }

            // Add item to inventory.
            if (plugin.bankAccountInventoryFile.getConfig().getInt("withdraw-inventory.contents." + key + ".slot") == -1) {
                for (int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.bankAccountInventoryFile.getConfig().getInt("withdraw-inventory.contents." + key + ".slot"), item);
            }
        }

        // After all items have been added, update the cheque.
        updateCheque();
    }

    // Update the cheque.
    private void updateCheque() {
        // Create a temporary item.
        ItemStack item = cheque.clone();
        // Get cheque meta.
        ItemMeta meta = cheque.getItemMeta();

        // Check if item has PersistentDataContainer has amountToWithdraw key.
        if (!meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE)) return;
        // Get amount to withdraw.
        double amountToWithdraw = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE);
        // Get potential bank balance.
        double potentialBankBalance = playerUtils.getBankBalance(owner) - amountToWithdraw;
        // Get potential cash balance.
        double potentialCashBalance = playerUtils.getCash(owner) + amountToWithdraw;

        // Get item lore.
        List<String> lore = meta.getLore();
        // Loop through all lore.
        for (int i= 0; i < lore.size(); i++) {
            // Check if line contains %amount%.
            if (lore.get(i).contains("%amount%")) {
                // Set line to new line.
                lore.set(i, lore.get(i).replaceAll("%amount%", Utils.format(amountToWithdraw)));
            }

            // Check if line contains %new_bank%.
            if (lore.get(i).contains("%new_bank%")) {
                // Set line to new line.
                lore.set(i, lore.get(i).replaceAll("%new_bank%", Utils.format(potentialBankBalance)));
            }

            // Check if line contains %new_cash%.
            if (lore.get(i).contains("%new_cash%")) {
                // Set line to new line.
                lore.set(i, lore.get(i).replaceAll("%new_cash%", Utils.format(potentialCashBalance)));
            }
        }
        // Set item lore.
        meta.setLore(lore);

        // Set item meta.
        item.setItemMeta(meta);
        // Set item to inventory.
        inventory.setItem(meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "slot"), PersistentDataType.INTEGER), item);
        // Update inventory.
        owner.updateInventory();
    }

    // Add cash to cheque.
    private void addToCheque(double amount) {
        // Get cheque meta.
        ItemMeta meta = cheque.getItemMeta();
        // Check if cheque meta has amountToWithdraw key.
        if (!meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE)) return;

        // Get amount to withdraw.
        double amountToWithdraw = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE);
        // Add amount to amountToWithdraw.
        amountToWithdraw += amount;
        // Check if amountToWithdraw is negative.
        if (amountToWithdraw < 0) amountToWithdraw = 0;

        // Add amountToWithdraw to meta.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE, amountToWithdraw);
        // Update cheque meta.
        cheque.setItemMeta(meta);
        // Update cheque.
        updateCheque();
    }

    // Remove cash from cheque.
    private void removeFromCheque(double amount) {
        // Get cheque meta.
        ItemMeta meta = cheque.getItemMeta();

        // Check if cheque meta has amountToWithdraw key.
        if (!meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE)) return;
        // Get amount to withdraw.
        double amountToWithdraw = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE);

        // Remove amount from amountToWithdraw.
        amountToWithdraw -= amount;

        // Check if amountToWithdraw is negative.
        if (amountToWithdraw < 0) amountToWithdraw = 0;

        // Add amountToWithdraw to meta.
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "amountToWithdraw"), PersistentDataType.DOUBLE, amountToWithdraw);
        // Update cheque meta.
        cheque.setItemMeta(meta);
        // Update cheque.
        updateCheque();
    }

}
