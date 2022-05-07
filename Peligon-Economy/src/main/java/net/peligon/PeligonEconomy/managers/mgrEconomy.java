package net.peligon.PeligonEconomy.managers;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.storage.SQLibrary;
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT 1 FROM accounts WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        if (hasAccount(player)) return;
        if (balance < 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "INSERT INTO plg_money values('" + uuid + "', " + balance + ", 0.0);";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "INSERT INTO accounts values('" + uuid + "', " + balance + ", 0.0);";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        if (hasAccount(player)) return;
        if (balance < 0 || bank < 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "INSERT INTO plg_money values('" + uuid + "'," + balance + "," + bank + ");";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "INSERT INTO accounts values('" + uuid + "'," + balance + "," + bank + ");";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Set a players balance - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to set
     */
    public void setAccount(OfflinePlayer player, double amount) {
        if (amount < 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET cash=" + amount + " WHERE uuid='" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE accounts SET cash=" + amount + " WHERE uuid='" + uuid + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Set a players balance - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to set
     */
    public void setBankAccount(OfflinePlayer player, double amount) {
        if (amount < 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET bank=" + amount + " WHERE uuid='" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE accounts SET bank=" + amount + " WHERE uuid='" + uuid + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to deposit
     */
    public void addAccount(OfflinePlayer player, double amount) {
        if (!hasAccount(player)) return;
        if (amount < 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET cash = (SELECT cash FROM plg_money WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE accounts SET cash = (SELECT cash FROM accounts WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to deposit
     */
    public void addBankAccount(OfflinePlayer player, double amount) {
        if (!hasAccount(player)) return;
        if (amount < 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET bank = (SELECT bank FROM plg_money WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE accounts SET bank = (SELECT bank FROM accounts WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to withdraw
     */
    public void removeAccount(OfflinePlayer player, double amount) {
        if (!hasAccount(player)) return;
        if (amount < 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET cash = (SELECT cash FROM plg_money WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE accounts SET cash = (SELECT cash FROM accounts WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to withdraw
     */
    public void removeBankAccount(OfflinePlayer player, double amount) {
        if (!hasAccount(player)) return;
        if (amount < 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE plg_money SET bank = (SELECT bank FROM plg_money WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "UPDATE accounts SET bank = (SELECT bank FROM accounts WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Gets balance of a player
     *
     * @param player of the player
     * @return Amount currently held in players account
     */
    public Double getAccount(OfflinePlayer player) {
        if (!hasAccount(player)) return 0.0;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT * FROM plg_money WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("cash");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT * FROM accounts WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("cash");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        if (!hasAccount(player)) return 0.0;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT * FROM plg_money WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("bank");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT * FROM accounts WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("bank");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        if (!hasAccount(player)) return false;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT cash FROM plg_money WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return hasAccount(player) && rs.getDouble("cash") >= amount;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT cash FROM accounts WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return hasAccount(player) && rs.getDouble("cash") >= amount;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        if (!hasAccount(player)) return false;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT bank FROM plg_money WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return hasAccount(player) && rs.getDouble("bank") >= amount;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String uuid = String.valueOf(player.getUniqueId());
            String query = "SELECT bank FROM accounts WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return hasAccount(player) && rs.getDouble("bank") >= amount;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT 1 FROM accounts WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        if (!hasAccount(uuid)) return;
        if (amount < 0) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE plg_money SET bank = (SELECT bank FROM plg_money WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = SQLiteLibary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE accounts SET bank = (SELECT bank FROM accounts WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }


    /**
     * Gets balance of a player
     *
     * @param uuid of the player
     * @return Amount currently held in players account
     */
    public Double getAccount(UUID uuid) {
        if (!hasAccount(uuid)) return 0.0;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM plg_money WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("cash");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM accounts WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("cash");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        if (!hasAccount(uuid)) return 0.0;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM plg_money WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = SQLiteLibary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("bank");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM accounts WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getDouble("bank");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0.0;
    }

    /**
     * Gets all players cash from the database in descending order
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT uuid, cash FROM accounts ORDER BY cash DESC;";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                HashMap<UUID, Double> leaderboard = new HashMap<>();

                while (rs.next()) {
                    Player player = Bukkit.getPlayer(UUID.fromString(rs.getString("uuid")));
                    if (player == null) return new HashMap<>();
                    leaderboard.put(player.getUniqueId(), rs.getDouble("cash"));
                }
                rs.close();
                return leaderboard;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    /**
     * Gets all players bank from the database in descending order
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT uuid, bank FROM accounts ORDER BY bank DESC;";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                HashMap<UUID, Double> leaderboard = new HashMap<>();

                while (rs.next()) {
                    Player player = Bukkit.getPlayer(UUID.fromString(rs.getString("uuid")));
                    if (player == null) continue;
                    leaderboard.put(player.getUniqueId(), rs.getDouble("bank"));
                }
                rs.close();
                return leaderboard;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    /**
     * Gets the server total cash balance
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT cash FROM accounts;";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                double total = 0.0;

                while (rs.next()) {
                    total += rs.getDouble("cash");
                }
                rs.close();
                return total;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT bank FROM accounts;";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                double total = 0.0;

                while (rs.next()) {
                    total += rs.getDouble("bank");
                }
                rs.close();
                return total;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT uuid, bank FROM accounts;";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();

                HashMap<UUID, Double> temp = new HashMap<>();

                while (rs.next()) {
                    temp.put(UUID.fromString(rs.getString("uuid")), rs.getDouble("bank"));
                }
                rs.close();
                return temp;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return new HashMap<>();
    }

}
