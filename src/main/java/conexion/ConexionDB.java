/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Nitro
 */
public class ConexionDB {

    private static final String URL = "jdbc:sqlserver://localhost:1433;" 
            + "databaseName=EmpresaDB;" 
            + "encrypt=true;" 
            + "trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "sherlymejia123";

    private static Connection connection = null;

    public static void cerrarConexion() {
        closeConnection();
    }
    public static Connection getConexion() {
        return getConnection();
    }

    private ConexionDB() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("-> Conexion Exitosa a SQL");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: no se encontro el driver mssql-jdbc -> " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con SQL -> " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
                System.out.println("-> La conexion con SQL esta cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion -> " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        Connection con = ConexionDB.getConnection();
        if (con != null) {
            System.out.println("Prueba de conexion completada");
            ConexionDB.closeConnection();
        } else {
            System.out.println("No se pudo establecer la conexion");
        }
    }
    
}