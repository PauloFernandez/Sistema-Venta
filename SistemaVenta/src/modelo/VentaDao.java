package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VentaDao {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int respuesta;

    public int IdVenta() {
        int id_ven = 0;
        String sql = "select max(id_venta) from t_venta";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                id_ven = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        
        return id_ven;
    }

    public int RegistrarVenta(Venta v) {
        String sql = "insert into t_venta (cliente, vendedor, total) values (?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            //ps.setString(4, v.getFecha());
            ps.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion.");
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return respuesta;
    }

    public int RegistrarDetalle(Detalle DetVenta) {
        String sql = "insert into t_detalle (id_venta, codigo_producto, cantidad, precio) values (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, DetVenta.getId_venta());
            ps.setString(2, DetVenta.getCodigo_producto());
            ps.setInt(3, DetVenta.getCantidad());
            ps.setDouble(4, DetVenta.getPrecio());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion.");
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return respuesta;
    }

    public boolean ActualizarStock(int cant, String cod) {
        String sql = "update t_producto set stock = ? where codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setString(2, cod);
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion.");
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

    public List ListarVentas() {
        List<Venta> ListaVenta = new ArrayList();
        String sql = "select * from t_venta";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Venta vent = new Venta();
                vent.setId(rs.getInt("id_venta"));
                vent.setCliente(rs.getString("cliente"));
                vent.setVendedor(rs.getString("vendedor"));
                vent.setTotal(rs.getDouble("total"));
                ListaVenta.add(vent);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        
        return ListaVenta;
    }

}
