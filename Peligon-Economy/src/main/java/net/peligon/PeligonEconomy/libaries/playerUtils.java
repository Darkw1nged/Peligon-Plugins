package net.peligon.PeligonEconomy.libaries;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.storage.CustomConfig;
import net.peligon.PeligonEconomy.libaries.storage.SQLiteLibrary;
import net.peligon.PeligonEconomy.libaries.struts.Transaction;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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

    // Remove cash from players balance
    public static boolean removeCash(OfflinePlayer player, double amount) {
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
                setCash(player, 0);
                return true;
            } else {
                return false;
            }
        } else {
            // Removing the cash from the players balance
            setCash(player, getCash(player) - amount);
            return true;
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
