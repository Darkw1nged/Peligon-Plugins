package net.peligon.PeligonSkyblock.managers;

import net.peligon.PeligonSkyblock.Main;
import net.peligon.PeligonSkyblock.libaries.CustomConfig;
import net.peligon.PeligonSkyblock.libaries.storage.SQLite;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IslandManager {

    private final Main plugin = Main.getInstance;

    /**
     * Checking if the user has an island
     *
     * @param player of the player
     * @return if user has island
     */
    public boolean hasIsland(OfflinePlayer player) {
        String uuid = String.valueOf(player.getUniqueId());
        String query = "SELECT 1 FROM plg_users WHERE uuid='" + uuid + "';";
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
     * Creates an island for the user if they do not already have one.
     *
     * @param player of the player
     */
    public void createIsland(OfflinePlayer player) {
        if (hasIsland(player)) return;
        String uuid = String.valueOf(player.getUniqueId());
        String strID = player.getUniqueId().toString().split("-")[0] + player.getUniqueId().toString().split("-")[4];
        String islandID = String.valueOf(UUID.nameUUIDFromBytes(strID.getBytes()));

        String query = "INSERT INTO plg_islands VALUES('" + islandID + "', '" + uuid + "', 0.0, 0, 10, 0, 0, 0, 0, 0, 0, 0)";
        CustomConfig data = new CustomConfig(plugin, islandID, "data");

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);

            List<UUID> members = new ArrayList<>();
            members.add(player.getUniqueId());
            data.getConfig().set("Members", members);

            List<UUID> banned = new ArrayList<>();
            data.getConfig().set("Banned Members", banned);

            data.getConfig().set("Upgrades.Spawner Rates", 1);
            data.getConfig().set("Upgrades.Island Size", 1);
            data.getConfig().set("Upgrades.Crop Growth Rate", 1);
            data.getConfig().set("Upgrades.Sell Multiplier", 1);
            data.getConfig().set("Upgrades.Hopper Limit", 1);
            data.getConfig().set("Upgrades.Redstone Limit", 1);

            data.saveConfig();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an island for the user.
     *
     * @param player of the player
     */
    public void DeleteIsland(OfflinePlayer player) {
        if (!hasIsland(player)) return;
        String islandID = String.valueOf(getIslandID(player));
        String query = "DELETE FROM plg_islands WHERE islandID='" + islandID + "';";
        File file = new File(plugin.getDataFolder() + "/data", islandID + ".yml");

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);

            if (file.exists()) {
                file.delete();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the players island id
     *
     * @param player of the player
     * @return IslandID
     */
    public UUID getIslandID(OfflinePlayer player) {
        if (!hasIsland(player)) return UUID.nameUUIDFromBytes("".getBytes());
        String uuid = String.valueOf(player.getUniqueId());
        String query = "SELECT * FROM plg_users WHERE uuid='" + uuid + "';";

        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return UUID.nameUUIDFromBytes(rs.getString("islandID").getBytes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UUID.nameUUIDFromBytes("".getBytes());
    }

    /**
     * Returns the player's island worth
     *
     * @param player of the player
     * @Return Island Worth
     */
    public Double getIslandWorth(OfflinePlayer player) {
        if (!hasIsland(player)) return 0.0;
        String uuid = String.valueOf(getIslandID(player));
        String query = "SELECT worth FROM plg_islands WHERE uuid='" + uuid + "';";

        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getDouble("worth");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Sets the player's island worth
     *
     * @param player of the player
     */
    public void setIslandWorth(OfflinePlayer player, double amount) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET worth=" + amount + " WHERE uuid='" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the player's island worth
     *
     * @param player of the player
     */
    public void addIslandWorth(OfflinePlayer player, int amount) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET worth = (SELECT worth FROM plg_islands WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the player's island worth
     *
     * @param player of the player
     */
    public void removeIslandWorth(OfflinePlayer player, int amount) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET worth = (SELECT worth FROM plg_islands WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the player's island level
     *
     * @param player of the player
     * @Return Island Level
     */
    public Integer getIslandLevel(OfflinePlayer player) {
        if (!hasIsland(player)) return 0;
        String uuid = String.valueOf(getIslandID(player));
        String query = "SELECT islandLevel FROM plg_islands WHERE uuid='" + uuid + "';";

        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("islandLevel");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Sets the player's island level
     *
     * @param player of the player
     */
    public void setIslandLevel(OfflinePlayer player, double amount) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET islandLevel=" + amount + " WHERE uuid='" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the player's island level
     *
     * @param player of the player
     */
    public void addIslandLevel(OfflinePlayer player, int amount) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET islandLevel = (SELECT islandLevel FROM plg_islands WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the player's island level
     *
     * @param player of the player
     */
    public void removeIslandLevel(OfflinePlayer player, int amount) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET islandLevel = (SELECT islandLevel FROM plg_islands WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the player's island members amount
     *
     * @param player of the player
     * @Return Island Members Amount
     */
    public Integer getIslandMembersSize(OfflinePlayer player) {
        if (!hasIsland(player)) return 0;
        String uuid = String.valueOf(getIslandID(player));
        String query = "SELECT maximum_members FROM plg_islands WHERE uuid='" + uuid + "';";

        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("islandLevel");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Sets the player's island members amount
     *
     * @param player of the player
     */
    public void setIslandMembersSize(OfflinePlayer player, double amount) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET maximum_members=" + amount + " WHERE uuid='" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the player's island members amount
     *
     * @param player of the player
     */
    public void addIslandMembersSize(OfflinePlayer player, int amount) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET maximum_members = (SELECT maximum_members FROM plg_islands WHERE uuid='" + uuid + "') +" + amount + " WHERE uuid= '" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the player's island members amount
     *
     * @param player of the player
     */
    public void removeIslandMembersSize(OfflinePlayer player, int amount) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET maximum_members = (SELECT maximum_members FROM plg_islands WHERE uuid='" + uuid + "') -" + amount + " WHERE uuid= '" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the player's island Locked Status
     *
     * @param player of the player
     * @Return Island Locked Status
     */
    public Boolean getIslandLocked(OfflinePlayer player) {
        if (!hasIsland(player)) return true;
        String uuid = String.valueOf(getIslandID(player));
        String query = "SELECT locked FROM plg_islands WHERE uuid='" + uuid + "';";

        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("locked") == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Sets the player's island Locked Status
     *
     * @param player of the player
     */
    public void lockIsland(OfflinePlayer player, int Locked) {
        if (!hasIsland(player)) return;
        String uuid = String.valueOf(getIslandID(player));
        String query = "UPDATE plg_islands SET locked=" + Locked + " WHERE uuid='" + uuid + "';";

        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
