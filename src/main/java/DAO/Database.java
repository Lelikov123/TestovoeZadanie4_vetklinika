package DAO;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlserver://localhost:1433;" +
            "databaseName=VeterinaryClinic;" +
            "encrypt=true;" +
            "trustServerCertificate=true";

    private static final String USER = "vetuser";
    private static final String PASSWORD = "VetClinic123";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = Database.getConnection()) {
            System.out.println("Подключение успешно!");
        } catch (SQLException e) {
            System.out.println("Ошибка подключения:");
            e.printStackTrace();
        }
    }
}