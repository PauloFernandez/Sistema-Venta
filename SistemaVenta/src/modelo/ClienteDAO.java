/*
 * 
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ClienteDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarCliente(Cliente cl) {
        String sql = "insert into t_cliente (ruc_cliente, nombre, telefono, direccion, razon_social) values (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cl.getRuc());
            ps.setString(2, cl.getNombre());
            ps.setInt(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.setString(5, cl.getRazon());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion");
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
                JOptionPane.showMessageDialog(null, "Error!!, comuniquese con el administrador");
            }
        }
    }

    public List ListarCliente() {
        List<Cliente> ListCl = new ArrayList();
        String sql = "select * from t_cliente";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cl = new Cliente();
                cl.setId(rs.getInt("id_cliente"));
                cl.setRuc(rs.getInt("ruc_cliente"));
                cl.setNombre(rs.getString("nombre"));
                cl.setRazon(rs.getString("razon_social"));
                cl.setTelefono(rs.getInt("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                ListCl.add(cl);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion");
        } 
        
        return ListCl;
    }

    public boolean EliminarCliente(int id) {
        String sql = "delete from t_cliente where id_cliente = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en conexion.");
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error!! Comuniquese con el administrador.");
            }
        }
    }

    public boolean ModificarCliente(Cliente cl) {
        String sql = "update t_cliente set ruc_cliente=?, nombre=?, razon_social=?, telefono=?, direccion=? where id_cliente=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cl.getRuc());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getRazon());
            ps.setInt(4, cl.getTelefono());
            ps.setString(5, cl.getDireccion());
            ps.setInt(6, cl.getId());
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion");
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public Cliente BuscarCliente(int ruc) {
        Cliente cl = new Cliente();
        String sql = "select * from t_cliente where ruc_cliente = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ruc);
            rs = ps.executeQuery();

            if (rs.next()) {
                cl.setNombre(rs.getString("nombre"));
                cl.setRazon(rs.getString("razon_social"));
                cl.setTelefono(rs.getInt("telefono"));
                cl.setDireccion(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion");
            System.out.println(e.toString());
        }
        
        return cl;
    }

}
