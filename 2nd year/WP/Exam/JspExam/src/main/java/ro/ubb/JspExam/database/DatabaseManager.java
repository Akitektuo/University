package ro.ubb.JspExam.database;

import com.mysql.jdbc.Driver;
import ro.ubb.JspExam.utils.ResultConsumer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class DatabaseManager {
    private final String CONNECTION_URL = "jdbc:mysql://localhost:3306/exam";

    private Connection sqlConnection;

    public DatabaseManager() {
        try {
            Class.forName(Driver.class.getCanonicalName());
            sqlConnection = DriverManager.getConnection(CONNECTION_URL, "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean execute(String sql) {
        try {
            sqlConnection.prepareStatement(sql).execute(sql);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public void query(String sql, ResultConsumer forEach) {
        try(var results = sqlConnection.prepareStatement(sql).executeQuery()) {
            while (results.next()) {
                forEach.call(results);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void close() {
        try {
            sqlConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
