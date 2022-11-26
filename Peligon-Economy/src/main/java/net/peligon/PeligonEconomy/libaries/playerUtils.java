package net.peligon.PeligonEconomy.libaries;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.storage.CustomConfig;
import net.peligon.PeligonEconomy.libaries.storage.SQLiteLibrary;
import net.peligon.PeligonEconomy.libaries.storage.physicalNotes;
import net.peligon.PeligonEconomy.libaries.struts.Transaction;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class playerUtils {

    // Getting instance of the main class.
    private static final Main plugin = Main.getInstance;

    // If the player does not have any data then we can use this method to create some.
    public static void createPlayerData(OfflinePlayer player) {
        // Check if the player has data first, If so then return.
        if (hasData(player)) return;

        // Setting up the query for SQL
        String SQLQuery = "INSERT INTO peligonEconomy values('" + player.getUniqueId() + "',"
                + plugin.getConfig().getDouble("Accounts.new-accounts.starting-cash-balance", 100.0) + ", "
                + plugin.getConfig().getDouble("Accounts.new-accounts.starting-bank-balance", 0.0) + ");";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                record.getConfig().set("cash", plugin.getConfig().getDouble("Accounts.new-accounts.starting-cash-balance", 100.0));
                record.getConfig().set("bankBalance", plugin.getConfig().getDouble("Accounts.new-accounts.starting-bank-balance", 0.0));
                record.saveConfig();
                break;
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Checking if the player has any data.
    public static boolean hasData(OfflinePlayer player) {
        // Setting up the query for SQL
        String SQLQuery = "SELECT 1 FROM peligonEconomy WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                return record.getConfig().contains("cash");
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }

    // Get the players cash balance
    public static double getCash(OfflinePlayer player) {
        // Check if physical cash is enabled
        if (plugin.getConfig().getBoolean("Storage.physical", false)) {
            // If player is not online, return.
            if (!player.isOnline()) return 0.0;

            // Initialize the cash variable
            double cash = 0;

            // Loop through the players inventory and add up the cash
            for (int i = 0; i < player.getPlayer().getInventory().getSize(); i++) {
                if (player.getPlayer().getInventory().getItem(i) != null) {
                    // Check if the item has persistent data
                    if (player.getPlayer().getInventory().getItem(i).getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE)) {
                        // Add the cash to the total
                        cash += player.getPlayer().getInventory().getItem(i).getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE) * player.getPlayer().getInventory().getItem(i).getAmount();
                    }
                }
            }
            return cash;
        }

        // Setting up the query for SQL
        String SQLQuery = "SELECT cash FROM peligonEconomy WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                return record.getConfig().getDouble("cash");
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.getDouble("cash");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.getDouble("cash");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return 0;
    }

    // Get the players bank balance
    public static double getBankBalance(OfflinePlayer player) {
        // Setting up the query for SQL
        String SQLQuery = "SELECT bankBalance FROM peligonEconomy WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                return record.getConfig().getDouble("bankBalance");
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.getDouble("bankBalance");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    ResultSet rs = statement.executeQuery();
                    return rs.getDouble("bankBalance");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return 0;
    }

    // Set players cash balance
    public static void setCash(OfflinePlayer player, double amount) {
        // Setting up the query for SQL
        String SQLQuery = "UPDATE peligonEconomy SET cash=" + amount + " WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                record.getConfig().set("cash", amount);
                record.saveConfig();
                break;
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Add to players cash balance
    public static void addCash(OfflinePlayer player, double amount) {
        // Check if physical cash is enabled
        if (plugin.getConfig().getBoolean("Storage.physical", false)) {
            // Check if the player is online
            if (!player.isOnline()) return;

            // Amounts 0.01, 0.05, 0.10, 0.25, 1, 5, 10, 20, 50, 100 notes are available

            // divide the amount by 100 to get the number of 100 notes
            int hundreds = (int) (amount / 100);
            // subtract the number of 100 notes from the amount
            amount -= hundreds * 100;

            // divide the amount by 50 to get the number of 50 notes
            int fifties = (int) (amount / 50);
            // subtract the number of 50 notes from the amount
            amount -= fifties * 50;

            // divide the amount by 20 to get the number of 20 notes
            int twenties = (int) (amount / 20);
            // subtract the number of 20 notes from the amount
            amount -= twenties * 20;

            // divide the amount by 10 to get the number of 10 notes
            int tens = (int) (amount / 10);
            // subtract the number of 10 notes from the amount
            amount -= tens * 10;

            // divide the amount by 5 to get the number of 5 notes
            int fives = (int) (amount / 5);
            // subtract the number of 5 notes from the amount
            amount -= fives * 5;

            // divide the amount by 1 to get the number of 1 notes
            int ones = (int) (amount / 1);
            // subtract the number of 1 notes from the amount
            amount -= ones * 1;

            // divide the amount by 0.25 to get the number of 0.25 coins
            int quarters = (int) (amount / 0.25);
            // subtract the number of 0.25 coins from the amount, round to 2 decimal places
            amount = Math.round((amount - quarters * 0.25) * 100.0) / 100.0;

            // divide the amount by 0.10 to get the number of 0.10 coins
            int dimes = (int) (amount / 0.10);
            // subtract the number of 0.10 coins from the amount, round to 2 decimal places
            amount = Math.round((amount - dimes * 0.10) * 100.0) / 100.0;

            // divide the amount by 0.05 to get the number of 0.05 coins
            int nickels = (int) (amount / 0.05);
            // subtract the number of 0.05 coins from the amount, round to 2 decimal places
            amount = Math.round((amount - nickels * 0.05) * 100.0) / 100.0;

            // divide the amount by 0.01 to get the number of 0.01 coins
            int pennies = (int) (amount / 0.01);

            // Get the players inventory
            Inventory inventory = player.getPlayer().getInventory();

            // Try and add the notes to the players inventory
            Utils.hasSpace(inventory, physicalNotes.HundredDollar(), hundreds);
            Utils.hasSpace(inventory, physicalNotes.FiftyDollar(), fifties);
            Utils.hasSpace(inventory, physicalNotes.TwentyDollar(), twenties);
            Utils.hasSpace(inventory, physicalNotes.TenDollar(), tens);
            Utils.hasSpace(inventory, physicalNotes.FiveDollar(), fives);
            Utils.hasSpace(inventory, physicalNotes.Dollar(), ones);
            Utils.hasSpace(inventory, physicalNotes.Quarter(), quarters);
            Utils.hasSpace(inventory, physicalNotes.Dime(), dimes);
            Utils.hasSpace(inventory, physicalNotes.Nickel(), nickels);
            Utils.hasSpace(inventory, physicalNotes.Penny(), pennies);
            return;
        }

        // Setting up the query for SQL
        String SQLQuery = "UPDATE peligonEconomy SET cash=cash+" + amount + " WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                record.getConfig().set("cash", getCash(player) + amount);
                record.saveConfig();
                break;
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Remove from players cash balance
    public static void removeCashServer(OfflinePlayer player, double amount) {
        if (plugin.getConfig().getBoolean("Storage.physical", false)) {
            // Check if the player is online
            if (!player.isOnline()) return;

            // Get the players inventory
            Inventory inventory = player.getPlayer().getInventory();

            // Loop through the players inventory and remove the cash needed
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) != null) {
                    // Check if the item has persistent data
                    if (inventory.getItem(i).getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE)) {
                        double value = inventory.getItem(i).getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE) * inventory.getItem(i).getAmount();
                        if (value > amount) {
                            // Remove the amount of cash needed
                            inventory.getItem(i).setAmount((int) (inventory.getItem(i).getAmount() - (amount / inventory.getItem(i).getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "cash-value"), PersistentDataType.DOUBLE))));
                            return;
                        } else {
                            // Remove the item
                            inventory.setItem(i, null);
                            amount -= value;
                        }
                    }
                }
            }
        }


        // Setting up the query for SQL
        String SQLQuery = "UPDATE peligonEconomy SET cash=cash-" + amount + " WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                record.getConfig().set("cash", getCash(player) - amount);
                record.saveConfig();
                break;
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Remove cash from players balance
    public static void removeCash(OfflinePlayer player, double amount) {
        // Checking if the player has enough cash
        if (!hasEnoughCash(player, amount)) {
            // Get amount of cash the player has
            double cash = getCash(player);
            // Get amount of cash in the bank
            double bankBalance = getBankBalance(player);
            // Get the amount of cash needed to be taken from the bank
            double cashNeeded = amount - cash;

            // Checking if the player has enough cash in the bank
            if (bankBalance >= cashNeeded) {
                // Removing the cash from the bank
                setBankBalance(player, bankBalance - cashNeeded);
                // Removing the cash from the players balance
                removeCashServer(player, cash);
            }
        } else {
            // Removing the cash from the players balance
            removeCashServer(player, amount);
        }
    }

    // Set players bank balance
    public static void setBankBalance(OfflinePlayer player, double amount) {
        // Setting up the query for SQL
        String SQLQuery = "UPDATE peligonEconomy SET bankBalance=" + amount + " WHERE uuid='" + player.getUniqueId() + "';";

        switch (plugin.storageType) {
            case "file":
                CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);
                record.getConfig().set("bankBalance", amount);
                record.saveConfig();
                break;
            case "mysql":
                try {
                    PreparedStatement statement = systemUtils.getSQLibrary().getConnection().prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sqlite":
                try {
                    PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(SQLQuery);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // Remove cash from players bank balance
    public static boolean removeBankBalance(OfflinePlayer player, double amount) {
        // Checking if the player has enough cash
        if (!hasEnoughBankBalance(player, amount)) {
            return false;
        } else {
            // Removing the cash from the players balance
            setBankBalance(player, getBankBalance(player) - amount);
            return true;
        }
    }

    // Get all transactions for a player
    public static List<Transaction> getTransactions(OfflinePlayer player) {
        // Get player data file
        CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);

        // Check if the player has any transactions
        if (!record.getConfig().contains("transactions")) return new ArrayList<>();

        // Get the list of transactions
        List<Transaction> transactions = new ArrayList<>();

        // Loop through the transactions
        for (String key : record.getConfig().getConfigurationSection("transactions").getKeys(false)) {
            // Get all the data for the transaction
            UUID transactionID = UUID.fromString(record.getConfig().getString("transactions." + key + ".transactionID"));
            Transaction.TransactionOperation operation = Transaction.TransactionOperation.valueOf(record.getConfig().getString("transactions." + key + ".operation"));
            double amount = record.getConfig().getDouble("transactions." + key + ".amount");
            Date recorded = new Date(record.getConfig().getLong("transactions." + key + ".recorded"));
            String logMessage = record.getConfig().getString("transactions." + key + ".logMessage");

            // Create the transaction
            Transaction transaction = new Transaction(transactionID, player.getPlayer(), operation, amount, recorded, logMessage);

            // Add the transaction to the list
            transactions.add(transaction);
        }
        // Return the list of transactions
        return transactions;
    }

    // Add a transaction to a players record
    public static void addTransaction(OfflinePlayer player, Transaction transaction) {
        CustomConfig record = new CustomConfig(plugin, "data/" + player.getUniqueId(), false);

        // Save the transaction
        record.getConfig().set("transactions." + transaction.getTransactionID() + ".transactionID", transaction.getTransactionID().toString());
        record.getConfig().set("transactions." + transaction.getTransactionID() + ".operation", transaction.getOperation().toString());
        record.getConfig().set("transactions." + transaction.getTransactionID() + ".amount", transaction.getAmount());
        record.getConfig().set("transactions." + transaction.getTransactionID() + ".recorded", transaction.getRecorded().getTime());
        record.getConfig().set("transactions." + transaction.getTransactionID() + ".logMessage", transaction.getLogMessage());
        record.saveConfig();
    }

    // Check if player has enough cash (Used in vault)
    public static boolean hasEnoughCashVault(OfflinePlayer player, double amount) {
        // Get the players cash
        double cash = getCash(player);

        // If the player does not have enough cash, check if they have enough in the bank
        if (cash < amount) {
            // Get the amount of cash needed from the bank
            double cashNeeded = amount - cash;
            // Get the amount of cash in the bank
            double bankBalance = getBankBalance(player);

            // Check if the player has enough cash in the bank
            return bankBalance >= cashNeeded;
        }
        return true;
    }

    // Check if player has enough cash
    public static boolean hasEnoughCash(OfflinePlayer player, double amount) {
        return getCash(player) >= amount;
    }

    // Check if player has enough bank balance
    public static boolean hasEnoughBankBalance(OfflinePlayer player, double amount) {
        return plugin.getConfig().getBoolean("Accounts.banks.enabled", true) && getBankBalance(player) >= amount ? getBankBalance(player) >= amount : getCash(player) >= amount;
    }

    // Get the amount of experience a player needs to level up.
    public static int getExpToLevelUp(int level) {
        return (level <= 15) ? 2 * level + 7 : (level <= 30) ? 5 * level - 38 : 9 * level - 158;
    }

    // Get the amount of experience at a certain level.
    public static int getExpAtLevel(int level) {
        return (level <= 16) ? (int) Math.pow(level, 2) + 6 * level : (level <= 31) ? (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360) : (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
    }

    // Get the amount of experience a player has.
    public static int getPlayerExp(Player player) {
        // Setting experience to 0
        int exp = 0;
        // Getting the players level
        int level = player.getLevel();

        // Updating the experience variable
        exp += getExpAtLevel(level);
        exp += Math.round(getExpToLevelUp(level) * player.getExp());
        // Returning the experience
        return exp;
    }

    // Remove experience from a player.
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

    // Add experience to a player.
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

    // Check if player has enough experience
    public static boolean hasEnoughExp(Player player, int exp) {
        return getPlayerExp(player) >= exp;
    }

}
