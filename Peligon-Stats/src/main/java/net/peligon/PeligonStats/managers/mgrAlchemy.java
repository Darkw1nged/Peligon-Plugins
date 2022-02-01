package net.peligon.PeligonStats.managers;

import net.peligon.PeligonStats.libaries.storage.SQLite;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mgrAlchemy {

    public static mgrAlchemy getInstance;
    public mgrAlchemy() {
        getInstance = this;
    }

    public boolean hasAccount(OfflinePlayer player) {
        String uuid = String.valueOf(player.getUniqueId());
        String query = "SELECT 1 FROM plg_skill_Alchemy WHERE uuid='" + uuid + "';";
        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Creates an account for the user if they do not already have one.
     *
     * @param player of the player
     * @param level Level player starts at
     * @param experience How much experience player starts at
     */
    public void createAccount(OfflinePlayer player, int level, int experience) {
        if (hasAccount(player)) return;

        String uuid = String.valueOf(player.getUniqueId());
        String query = "INSERT INTO plg_skill_Alchemy values('" + uuid + "'," + level + "," + experience + ");";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a players level - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to set
     */
    public void setLevel(OfflinePlayer player, int amount) {
        String uuid = String.valueOf(player.getUniqueId());
        String query = "UPDATE plg_skill_Alchemy SET rank=" + amount + " WHERE uuid='" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add level(s) to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to add
     */
    public void addLevel(OfflinePlayer player, int amount) {
        if (!hasAccount(player)) return;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "UPDATE plg_skill_Alchemy SET rank = (SELECT rank FROM plg_skill_Alchemy WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove level(s) to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to remove
     */
    public void removeLevel(OfflinePlayer player, int amount) {
        if (!hasAccount(player)) return;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "UPDATE plg_skill_Alchemy SET rank = (SELECT rank FROM plg_skill_Alchemy WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a players experience - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to set
     */
    public void setExperience(OfflinePlayer player, int amount) {
        String uuid = String.valueOf(player.getUniqueId());
        String query = "UPDATE plg_skill_Alchemy SET experience=" + amount + " WHERE uuid='" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add experience to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to add
     */
    public void addExperience(OfflinePlayer player, int amount) {
        if (!hasAccount(player)) return;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "UPDATE plg_skill_Alchemy SET experience = (SELECT experience FROM plg_skill_Alchemy WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove experience to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player of the player
     * @param amount Amount to remove
     */
    public void removeExperience(OfflinePlayer player, int amount) {
        if (!hasAccount(player)) return;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "UPDATE plg_skill_Alchemy SET experience = (SELECT experience FROM plg_skill_Alchemy WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets level of player
     *
     * @param player of the player
     * @return level of the player
     */
    public Integer getLevel(OfflinePlayer player) {
        if (!hasAccount(player)) return 0;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "SELECT * FROM plg_skill_Alchemy WHERE uuid='" + uuid + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("rank");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets expreience of player
     *
     * @param player of the player
     * @return level of the player
     */
    public Integer getExperience(OfflinePlayer player) {
        if (!hasAccount(player)) return 0;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "SELECT * FROM plg_skill_Alchemy WHERE uuid='" + uuid + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("experience");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
