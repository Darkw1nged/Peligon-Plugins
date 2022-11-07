package net.peligon.PeligonEconomy.libaries;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.storage.CustomConfig;
import net.peligon.PeligonEconomy.libaries.storage.SQLiteLibrary;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    // Check if player has enough cash
    public static boolean hasEnoughCash(OfflinePlayer player, double amount) {
        return getCash(player) >= amount;
    }

    // Check if player has enough bank balance
    public static boolean hasEnoughBankBalance(OfflinePlayer player, double amount) {
        return getBankBalance(player) >= amount;
    }

    // Get the amount of experience a player needs to level up.
    public static int getExpToLevelUp(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        } else if (level <= 30) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }

    // Get the amount of experience at a certain level.
    public static int getExpAtLevel(int level) {
        if (level <= 16) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if (level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
        } else {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
        }
    }

    // Get the amount of experience a player has.
    public static int getPlayerExp(Player player) {
        int exp = 0;
        int level = player.getLevel();

        exp += getExpAtLevel(level);

        exp += Math.round(getExpToLevelUp(level) * player.getExp());

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
