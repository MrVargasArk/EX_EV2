package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MySqlDBConexion {

    private static final ResourceBundle rb = ResourceBundle.getBundle("database");

    static {
        try {
            Class.forName(rb.getString("driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver de MySQL", e);
        }
    }

    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(
                    rb.getString("url"),
                    rb.getString("username"),
                    rb.getString("password"));
        } catch (SQLException e) {
            System.out.println("Error al conectar con MySQL: " + e.getMessage());
            return null;
        }
    }
}
