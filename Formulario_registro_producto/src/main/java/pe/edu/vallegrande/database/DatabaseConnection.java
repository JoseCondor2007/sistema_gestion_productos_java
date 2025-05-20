package pe.edu.vallegrande.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dbVulcanizadoraJosesito";
    private static final String USER = "root";
    private static final String PASSWORD = "josesito";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error, driver no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error no se pudo conectar a la base de dastos");
            e.printStackTrace();
        }
        return null;
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✅ Conexion establecida a la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error de conexion");
        }
    }

    public static void main(String[] args) {
        testConnection();
    }
}


