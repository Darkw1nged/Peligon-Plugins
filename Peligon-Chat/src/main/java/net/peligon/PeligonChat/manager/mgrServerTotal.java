package net.peligon.PeligonChat.manager;

import net.peligon.PeligonChat.libaries.storage.SQLite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mgrServerTotal {

    public static mgrServerTotal getInstance;
    public mgrServerTotal() {
        getInstance = this;
    }

    /**
     * Checking if the server is storing total
     *
     * @return if server stores total
     */
    public boolean hasTotal() {
        String query = "SELECT 1 FROM server WHERE uuid='1a2b3c';";
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
     * Creates a new total if server does not have one
     */
    public void createTotal() {
        if (hasTotal()) return;
        String query = "INSERT INTO server values('1a2b3c', 0);";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a server total - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param amount Amount to set
     */
    public void setTotal(int amount) {
        if (amount < 0) return;
        String query = "UPDATE server SET playersJoined=" + amount + " WHERE uuid='1a2b3c';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount from server total - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param amount Amount to deposit
     */
    public void addTotal(int amount) {
        if (!hasTotal()) return;
        if (amount < 0) return;
        String query = "UPDATE server SET playersJoined=(SELECT playersJoined FROM server WHERE uuid='1a2b3c') +" + amount + " WHERE uuid='1a2b3c';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes amount from server total - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param amount Amount to withdraw
     */
    public void removeTotal(int amount) {
        if (!hasTotal()) return;
        if (amount < 0) return;
        String query = "UPDATE server SET playersJoined=(SELECT playersJoined FROM server WHERE uuid='1a2b3c') -" + amount + " WHERE uuid='1a2b3c';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets joined players server total
     *
     * @return Amount of players that joined the server total
     */
    public Integer getTotal() {
        if (!hasTotal()) return 0;
        String query = "SELECT * FROM server WHERE uuid='1a2b3c';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("playersJoined");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
