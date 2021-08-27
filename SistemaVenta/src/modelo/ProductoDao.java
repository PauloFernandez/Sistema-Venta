package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JComboBox;
//import javax.swing.SwingConstants;

public class ProductoDao {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarProducto(Producto pro) {
        String sql = "insert into t_producto (codigo, nombre, proveedor, stock, precio) values (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getProveedor());
            ps.setInt(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion.");
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

    public void ConsultarProveedor(JComboBox proveedor) {
        String sql = "select nombre from t_proveedor";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            proveedor.addItem("Seleccione el cliente");

            while (rs.next()) {

                proveedor.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion.");
            System.out.println(e.toString());
        }
        
    }

    public List ListarProducto() {
        List<Producto> ListPro = new ArrayList();
        String sql = "select * from t_producto";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto pro = new Producto();
                pro.setId(rs.getInt("id_producto"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setNombre(rs.getString("nombre"));
                pro.setProveedor(rs.getString("proveedor"));
                pro.setStock(rs.getInt("stock"));
                pro.setPrecio(rs.getDouble("precio"));
                ListPro.add(pro);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        
        return ListPro;
    }

    public boolean EliminarProducto(int id) {
        String sql = "delete from t_producto where id_producto = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion.");
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

    public boolean ModificarProducto(Producto pro) {
        String sql = "update t_producto set codigo=?, nombre=?, proveedor=?, stock=?, precio=? where id_producto=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getProveedor());
            ps.setInt(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
            ps.setInt(6, pro.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion.");
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

    public Producto BuscarPro(String cod) {
        Producto productos = new Producto();
        String sql = "select * from t_producto where codigo = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();

            if (rs.next()) {
                productos.setNombre(rs.getString("nombre"));
                productos.setPrecio(rs.getDouble("precio"));
                productos.setStock(rs.getInt("stock"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion.");
            System.out.println(e.toString());
        }
       
        return productos;
    }

    public Config BuscarDatos() {
        Config conf = new Config();
        String sql = "select * from t_config";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                conf.setId_config(rs.getInt("id_config"));
                conf.setRuc(rs.getInt("ruc"));
                conf.setNombre(rs.getString("nombre"));
                conf.setRazon(rs.getString("razon"));
                conf.setTelefono(rs.getInt("telefono"));
                conf.setDireccion(rs.getString("direccion"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion.");
            System.out.println(e.toString());
        }
       
        return conf;
        
    }

    public boolean ModificarDatos(Config conf) {
        String sql = "update t_config set ruc=?, nombre=?, razon=?, telefono=?, direccion=? where id_config=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, conf.getRuc());
            ps.setString(2, conf.getNombre());
            ps.setString(3, conf.getRazon());
            ps.setInt(4, conf.getTelefono());
            ps.setString(5, conf.getDireccion());
            ps.setInt(6, conf.getId_config());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion.");
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
    
    

}
