package net.peligon.Playtime.managers;

import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.storage.SQLiteLibrary;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class mgrPlayTime {

    private final Main plugin = Main.getInstance;

    public static mgrPlayTime instance;
    public mgrPlayTime() {
        instance = this;
    }

    /**
     * Checking if player has any data in database
     *
     * @return if data exists in database
     */
    public boolean hasData(OfflinePlayer player) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT 1 FROM server WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT 1 FROM Playtime WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean hasData(UUID uuid) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT 1 FROM server WHERE uuid='" + uuid.toString() + "';";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT 1 FROM Playtime WHERE uuid='" + uuid.toString() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Creates data for the player
     */
    public void createData(OfflinePlayer player) {
        if (hasData(player)) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "INSERT INTO server values('" + player.getUniqueId() + "'," + 0 + ", " + System.currentTimeMillis() + ", 0, 0);";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "INSERT INTO Playtime values('" + player.getUniqueId() + "'," + 0 + ", " + System.currentTimeMillis() + ", 0, 0);";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds time to the player
     */
    public void addTime(OfflinePlayer player) {
        if (!hasData(player)) return;
        long difference = System.currentTimeMillis() - getLastUpdated(player);
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET timePlayed = (SELECT timePlayed FROM server WHERE uuid='" + player.getUniqueId() + "') +" + difference + " WHERE uuid= '" + player.getUniqueId() + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
                setLastUpdated(player);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE Playtime SET timePlayed = (SELECT timePlayed FROM Playtime WHERE uuid='" + player.getUniqueId() + "') +" + difference + " WHERE uuid= '" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                statement.execute();
                setLastUpdated(player);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reset time for the player
     */
    public void resetTime(OfflinePlayer player) {
        if (!hasData(player)) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET timePlayed = 0 WHERE uuid= '" + player.getUniqueId() + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
                setLastUpdated(player);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE Playtime SET timePlayed = 0 WHERE uuid= '" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                statement.execute();
                setLastUpdated(player);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the player's time played
     *
     * @return Players time played in array format
     */
    public long getTimePlayed(OfflinePlayer player) {
        if (!hasData(player)) return 0;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
            try {

                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getLong("timePlayed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM Playtime WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getLong("timePlayed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public long getTimePlayed(UUID uuid) {
        if (!hasData(uuid)) return 0;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM server WHERE uuid='" + uuid + "';";
            try {

                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getLong("timePlayed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM Playtime WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getLong("timePlayed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * Gets the player's last updated time
     *
     * @return Players last updated time in milliseconds
     */
    public long getLastUpdated(OfflinePlayer player) {
        if (!hasData(player)) return 0;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
            try {

                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getLong("lastUpdated");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM Playtime WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getLong("lastUpdated");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * Sets the player's last updated time
     *
     * @param player
     */
    public void setLastUpdated(OfflinePlayer player) {
        if (!hasData(player)) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET lastUpdated=" + System.currentTimeMillis() + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE Playtime SET lastUpdated=" + System.currentTimeMillis() + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLastUpdated(UUID uuid) {
        if (!hasData(uuid)) return;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET lastUpdated=" + System.currentTimeMillis() + " WHERE uuid='" + uuid + "';";
            try {
                Statement statement = SQLiteLibrary.connection.createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE Playtime SET lastUpdated=" + System.currentTimeMillis() + " WHERE uuid='" + uuid + "';";
            try {
                Statement statement = plugin.sqlLibrary.getConnection().createStatement();
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get if the player playtime is paused
     *
     * @param player
     *
     * @return True if the player is paused, false if not
     */
    public boolean isPaused(OfflinePlayer player) {
        if (!hasData(player)) return true;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt("paused") != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM Playtime WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt("paused") != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Toggle the paused state of the player
     *
     * @param player
     */
    public void togglePaused(OfflinePlayer player) {
        if (!hasData(player)) return;
        int paused = isPaused(player) ? 0 : 1;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET paused=" + paused + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE Playtime SET paused=" + paused + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get if the player playtime is hidden
     *
     * @param player
     *
     * @return boolean if the player playtime is hidden
     */
    public boolean isHidden(OfflinePlayer player) {
        if (!hasData(player)) return false;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt("hidden") != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM Playtime WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt("hidden") != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isHidden(UUID uuid) {
        if (!hasData(uuid)) return false;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT * FROM server WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt("hidden") != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT * FROM Playtime WHERE uuid='" + uuid + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt("hidden") != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Toggle if the player playtime is hidden
     *
     * @param player
     */
    public void toggleHidden(OfflinePlayer player) {
        if (!hasData(player)) return;
        int hidden = isHidden(player) ? 0 : 1;
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "UPDATE server SET hidden=" + hidden + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "UPDATE Playtime SET hidden=" + hidden + " WHERE uuid='" + player.getUniqueId() + "';";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the player's time played position
     *
     * @return Players time played position
     */
    public int getTimePlayedPosition(OfflinePlayer player) {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT uuid, timePlayed FROM server ORDER BY timePlayed DESC;";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                int position = rs.getFetchSize();

                while (rs.next()) {
                    if (isHidden(UUID.fromString(rs.getString("uuid")))) continue;
                    if (player.getUniqueId().toString().equals(rs.getString("uuid"))) {
                        rs.close();
                        return position;
                    }
                    position--;
                }
                rs.close();
                return -1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT uuid, timePlayed FROM Playtime ORDER BY timePlayed DESC;";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                int position = rs.getFetchSize();

                while (rs.next()) {
                    if (isHidden(UUID.fromString(rs.getString("uuid")))) continue;
                    if (player.getUniqueId().toString().equals(rs.getString("uuid"))) {
                        rs.close();
                        return position;
                    }
                    position--;
                }
                rs.close();
                return -1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * Gets all players time played and sorts them
     *
     * @return List of players played time going from smallest to largest
     */
    public HashMap<UUID, Long> getTimePlayedLeaderboard() {
        if (plugin.storageType.equalsIgnoreCase("sqlite")) {
            String query = "SELECT uuid, timePlayed FROM server ORDER BY timePlayed DESC;";
            try {
                PreparedStatement statement = SQLiteLibrary.connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                HashMap<UUID, Long> leaderboard = new HashMap<>();

                while (rs.next()) {
                    if (isHidden(UUID.fromString(rs.getString("uuid")))) continue;
                    leaderboard.put(UUID.fromString(rs.getString("uuid")), rs.getLong("timePlayed"));
                }
                rs.close();
                return leaderboard;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (plugin.storageType.equalsIgnoreCase("mysql")) {
            String query = "SELECT uuid, timePlayed FROM Playtime ORDER BY timePlayed DESC;";
            try {
                PreparedStatement statement = plugin.sqlLibrary.getConnection().prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                HashMap<UUID, Long> leaderboard = new HashMap<>();

                while (rs.next()) {
                    if (isHidden(UUID.fromString(rs.getString("uuid")))) continue;
                    leaderboard.put(UUID.fromString(rs.getString("uuid")), rs.getLong("timePlayed"));
                }
                rs.close();
                return leaderboard;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }

}
