package net.peligon.PeligonEconomy.managers;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.storage.SQLiteLibary;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

public class mgrEconomy {

    private final Main plugin = Main.getInstance;
    private Map<UUID, Double> cash = new HashMap<>();
    private Map<UUID, Double> bank = new HashMap<>();

    public static mgrEconomy getInstance;
    public mgrEconomy() {
        getInstance = this;
    }

    /**
     * Checking if the user has an account
     *
     * @param player of the player
     * @return if user has account
     */
    public boolean hasAccount(OfflinePlayer player) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT 1 FROM plg_money WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return false;
    }

    /**
     * Creates an account for the user if they do not already have one.
     *
     * @param player  of the player
     * @param balance Amount to open account with
     */
    public void createAccount(OfflinePlayer player, double balance) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (hasAccount(player)) return;
            if (balance < 0) return;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "INSERT INTO plg_money values('" + uuid + "', " + balance + ", 0.0);";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
    }

    /**
     * Creates an account for the user if they do not already have one.
     *
     * @param player  of the player
     * @param balance Amount to open account with
     * @param bank    Amount to open bank account with
     */
    public void createAccount(OfflinePlayer player, double balance, double bank) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (hasAccount(player)) return;
            if (balance < 0 || bank < 0) return;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "INSERT INTO plg_money values('" + uuid + "'," + balance + "," + bank + ");";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
    }

    /**
     * Set a players balance - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to set
     */
    public void setAccount(OfflinePlayer player, double amount) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            if (amount < 0) return;
            String query = "UPDATE plg_money SET cash=" + amount + " WHERE uuid='" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
    }

    /**
     * Set a players balance - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to set
     */
    public void setBankAccount(OfflinePlayer player, double amount) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            if (amount < 0) return;
            String query = "UPDATE plg_money SET bank=" + amount + " WHERE uuid='" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to deposit
     */
    public void addAccount(OfflinePlayer player, double amount) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(player)) return;
            if (amount < 0) return;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET cash = (SELECT cash FROM plg_money WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to deposit
     */
    public void addBankAccount(OfflinePlayer player, double amount) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(player)) return;
            if (amount < 0) return;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET bank = (SELECT bank FROM plg_money WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
    }

    /**
     * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to withdraw
     */
    public void removeAccount(OfflinePlayer player, double amount) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(player)) return;
            if (amount < 0) return;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET cash = (SELECT cash FROM plg_money WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
    }

    /**
     * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to withdraw
     */
    public void removeBankAccount(OfflinePlayer player, double amount) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(player)) return;
            if (amount < 0) return;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET bank = (SELECT bank FROM plg_money WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
    }

    /**
     * Gets balance of a player
     *
     * @param player of the player
     * @return Amount currently held in players account
     */
    public Double getAccount(OfflinePlayer player) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(player)) return 0.0;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT * FROM plg_money WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("cash");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return 0.0;
    }

    /**
     * Gets balance of a players bank
     *
     * @param player of the player
     * @return Amount currently held in players account
     */
    public Double getBank(OfflinePlayer player) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(player)) return 0.0;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT * FROM plg_money WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("bank");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return 0.0;
    }

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount needing checked
     * @return True if player has enough money, False else wise
     */
    public boolean hasEnoughCash(OfflinePlayer player, double amount) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(player)) return false;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT cash FROM plg_money WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return hasAccount(player) && rs.getDouble("cash") >= amount;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return false;
    }

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount needing checked
     * @return True if player has enough money, False else wise
     */
    public boolean hasEnoughBank(OfflinePlayer player, double amount) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(player)) return false;
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT bank FROM plg_money WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return hasAccount(player) && rs.getDouble("bank") >= amount;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return false;
    }

    /**
     * Checking if the user has an account
     *
     * @param uuid of the player
     * @return if user has account
     */
    public boolean hasAccount(UUID uuid) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT 1 FROM plg_money WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        } else if (plugin.storageType.equalsIgnoreCase("mongodb")) {
            return hasAccount(Bukkit.getOfflinePlayer(uuid));
        }

        return false;
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param uuid   of the player
     * @param amount Amount to deposit
     */
    public void addBankAccount(UUID uuid, double amount) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(uuid)) return;
            if (amount < 0) return;
            String query = "UPDATE plg_money SET bank = (SELECT bank FROM plg_money WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }

    }


    /**
     * Gets balance of a player
     *
     * @param uuid of the player
     * @return Amount currently held in players account
     */
    public Double getAccount(UUID uuid) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(uuid)) return 0.0;
            String query = "SELECT * FROM plg_money WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("cash");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return 0.0;
    }

    /**
     * Gets balance of a players bank
     *
     * @param uuid of the player
     * @return Amount currently held in players account
     */
    public Double getBank(UUID uuid) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            if (!hasAccount(uuid)) return 0.0;
            String query = "SELECT * FROM plg_money WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("bank");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return 0.0;
    }

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
     *
     * @return List of cash going from lowest to largest
     */
    public HashMap<UUID, Double> getCashFromDescending() {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT uuid, cash FROM plg_money ORDER BY cash DESC;";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                HashMap<UUID, Double> leaderboard = new HashMap<>();

                while (rs.next()) {
                    Player player = Bukkit.getPlayer(UUID.fromString(rs.getString("uuid")));
                    if (player == null) return new HashMap<>();
                    leaderboard.put(player.getUniqueId(), rs.getDouble("cash"));
                }
                rs.close();
                return leaderboard;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return new HashMap<>();
    }

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
     *
     * @return List of bank going from lowest to largest
     */
    public HashMap<UUID, Double> getBankFromDescending() {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT uuid, bank FROM plg_money ORDER BY bank DESC;";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                HashMap<UUID, Double> leaderboard = new HashMap<>();

                while (rs.next()) {
                    Player player = Bukkit.getPlayer(UUID.fromString(rs.getString("uuid")));
                    if (player == null) continue;
                    leaderboard.put(player.getUniqueId(), rs.getDouble("bank"));
                }
                rs.close();
                return leaderboard;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return new HashMap<>();
    }

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
     *
     * @return List of bank going from lowest to largest
     */
    public Double getServerTotalCash() {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT cash FROM plg_money;";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                double total = 0.0;

                while (rs.next()) {
                    total += rs.getDouble("cash");
                }
                rs.close();
                return total;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return 0.0;
    }

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
     *
     * @return List of bank going from lowest to largest
     */
    public Double getServerTotalBank() {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT bank FROM plg_money;";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                double total = 0.0;

                while (rs.next()) {
                    total += rs.getDouble("bank");
                }
                rs.close();
                return total;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return 0.0;
    }

    /**
     * Gets all players bank inside of database
     *
     * @return List of bank going from lowest to largest
     */
    public Map<UUID, Double> getAllBanks() {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT uuid, bank FROM plg_money;";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();

                HashMap<UUID, Double> temp = new HashMap<>();

                while (rs.next()) {
                    temp.put(UUID.fromString(rs.getString("uuid")), rs.getDouble("bank"));
                }
                rs.close();
                return temp;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {

        }
        return new HashMap<>();
    }

}
