package net.peligon.Teams.libaries.storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLibrary {

    private final String host;
    private final String database;
    private final String username;
    private final String password;
    private final int port;
    private Connection connection;

    public SQLibrary(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        try {
            openConnection();
        } catch (ClassNotFoundException | SQLException e) {
            log("An error occured when trying to connect to the MySQL database.");
            log(
                    "Make sure the MySQL settings are correct in the config.yml file, and that the MySQL server is online!");
            log(
                    "If this error persists please report the following Error code to the plugin developer: ERSQL-100-A");
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    DbUtils.close(connection);
                }
            } catch (SQLException e) {
                log("An error occured when trying to close the MySQL database connection.");
                log(
                        "If this error persists please report the following Error code to the plugin developer: ERSQL-101-A");
            }
        }
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        synchronized (this) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + ":" + port + "/" + this.database, this.username,
                    this.password);
            if (connection == null || connection.isClosed()) {
                log(
                        "An error occured while trying" + " to connect to the MySQL/SQLite server/database.");
            }
        }
    }

    public Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            try {
                openConnection();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private boolean doesTableExist(String tableName) throws SQLException {
        DatabaseMetaData dbm = getConnection().getMetaData();
        ResultSet rs = dbm.getTables(null, null, tableName, null);
        return rs.next();
    }

    private boolean doesColumnExist(String tableName, String columnName) throws SQLException {
        DatabaseMetaData dbm = getConnection().getMetaData();
        ResultSet rs = dbm.getColumns(null, null, tableName, columnName);
        return rs.next();
    }

    @SuppressWarnings("unused")
    private List<String> getColumns(String tableName) throws SQLException {
        DatabaseMetaData dbm = getConnection().getMetaData();
        ResultSet rs = dbm.getColumns(null, null, tableName, null);
        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount(); // number of column
        String[] columnName = new String[count];
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnName[i - 1] = metaData.getColumnLabel(i);
            columns.add(columnName[i - 1]);
        }
        return columns;
    }

    private void log(String message) {
        System.out.println(message);
    }

    public static final class DbUtils {

        public static void close(final ResultSet rs) {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        public static void close(final Statement stmt) {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        public static void close(final Connection conn) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

}

