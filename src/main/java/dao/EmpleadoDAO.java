/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package dao;
import modelo.Empleado;
import conexion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Nitro
 */

public class EmpleadoDAO {

    public ArrayList<Empleado> listarActivos() {
        ArrayList<Empleado> lista = new ArrayList<>();
        String sql = "SELECT IdEmpleado, Nombre, Apellido, Cargo, Salario, Departamento "
                   + "FROM Empleados";

        Connection con = ConexionDB.getConexion();
        if (con == null) {
            System.err.println(">>> listarActivos: no hay conexión a la BD");
            return lista;
        }

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("IdEmpleado"));
                emp.setNombre(rs.getString("Nombre"));
                emp.setApellido(rs.getString("Apellido"));
                emp.setCargo(rs.getString("Cargo"));
                emp.setSalario(rs.getDouble("Salario"));
                emp.setDepartamento(rs.getString("Departamento"));
                lista.add(emp);
            }
            System.out.println(">>> listarActivos: empleados recuperados = " + lista.size());

        } catch (SQLException e) {
            System.err.println(">>> Error en listarActivos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public double obtenerSalario(int idEmpleado) {
        String sql = "SELECT Salario FROM Empleados WHERE IdEmpleado = ?";
        Connection con = ConexionDB.getConexion();
        if (con == null) {
            return 0;
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Salario");
                }
            }
        } catch (SQLException e) {
            System.err.println(">>> Error en obtenerSalario: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public boolean insertar(Empleado emp) {
        String sql = "INSERT INTO Empleados "
                   + "(Nombre, Apellido, Cargo, Salario, Departamento) "
                   + "VALUES (?, ?, ?, ?, ?)";

        Connection con = ConexionDB.getConexion();
        if (con == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    null, "No hay conexión a la base de datos.");
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getApellido());
            ps.setString(3, emp.getCargo());
            ps.setDouble(4, emp.getSalario());
            ps.setString(5, emp.getDepartamento());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    null, "Error al guardar el empleado: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        EmpleadoDAO dao = new EmpleadoDAO();
        ArrayList<Empleado> lista = dao.listarActivos();

        System.out.println("Empleados en la base de datos");
        if (lista.isEmpty()) {
            System.out.println("No se encontraron empleados.");
        } else {
            for (Empleado e : lista) {
                System.out.println(
                    e.getIdEmpleado() + " | " +
                    e.getNombre() + " " + e.getApellido() + " | " +
                    e.getCargo() + " | $" + e.getSalario() + " | " +
                    e.getDepartamento()
                );
            }
        }
        System.out.println("Total: " + lista.size());

        ConexionDB.cerrarConexion();
    }
}