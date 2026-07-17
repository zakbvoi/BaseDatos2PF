package modelo_conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    // Cadena de conexión estándar para Oracle XE. 
    // Ajusta "localhost" o "1521" si tu configuración de red o Docker es distinta.
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; 
    private static final String USER = "electoral_voting_owner";
    private static final String PASSWORD = "Password_Segura!";

    // Método estático para obtener la conexión
    public static Connection getConexion() {
        Connection con = null;
        try {
            // Registrar el driver de Oracle
            Class.forName("oracle.jdbc.OracleDriver");
            
            // Establecer la conexión
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a la BD del Sistema de Votación!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de Oracle no encontrado. Asegúrate de haber agregado el ojdbc.jar a las librerías.");
        } catch (SQLException e) {
            System.err.println("Error de conexión a Oracle: " + e.getMessage());
        }
        return con;
    }

    // Método para cerrar la conexión y liberar recursos
    public static void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}