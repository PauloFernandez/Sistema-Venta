package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
//import java.sql.Connection;

public class loginDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public login log(String usuario, String pass) {
        login l = new login();
        String sql = "select * from t_usuario where usuario = ? and pass = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, pass);
            rs = ps.executeQuery();

            if (rs.next()) {
                l.setId(rs.getInt("id_usuario"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("mail"));
                l.setTelefono(rs.getString("telefono"));
                l.setUsuario(rs.getString("usuario"));
                l.setPass(rs.getString("pass"));
                l.setPermiso(rs.getString("permiso"));
                l.setEstatus(rs.getString("estatus"));
            }

        } catch (SQLException e) {
            System.out.println("Error en conexion" + e);
        }
        
        return l;
    }

    public boolean RegistrarUsuario(login user) {
        String sql = "insert into t_usuario (nombre, mail, telefono, usuario, pass, permiso, estatus) values (?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCorreo());
            ps.setString(3, user.getTelefono());
            ps.setString(4, user.getUsuario());
            ps.setString(5, user.getPass());
            ps.setString(6, user.getPermiso());
            ps.setString(7, user.getEstatus());
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
                JOptionPane.showMessageDialog(null, "Error!!, comuniquese con el administrador");
                System.out.println(e.toString());
            }
        }
    }

    public List ListarUsuario() {
        List<login> ListUser = new ArrayList();
        String sql = "select * from t_usuario";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                login user = new login();
                user.setId(rs.getInt("id_usuario"));
                user.setNombre(rs.getString("nombre"));
                user.setCorreo(rs.getString("mail"));
                user.setTelefono(rs.getString("telefono"));
                user.setUsuario(rs.getString("usuario"));
                user.setPass(rs.getString("pass"));
                user.setPermiso(rs.getString("permiso"));
                user.setEstatus(rs.getString("estatus"));
                ListUser.add(user);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion");
        }
        
        return ListUser;
    }

    public boolean EliminarUsuario(int id) {
        String sql = "delete from t_usuario where id_usuario = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en conexion.");
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error!! Comuniquese con el administrador.");
                System.out.println(e.toString());
            }
        }

    }

    public boolean ActualizarUsuario(login user) {
        String sql = "update t_usuario set nombre = ?, mail = ?, telefono = ?, usuario = ?, pass = ?, permiso = ?, estatus = ? where id_usuario = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCorreo());
            ps.setString(3, user.getTelefono());
            ps.setString(4, user.getUsuario());
            ps.setString(5, user.getPass());
            ps.setString(6, user.getPermiso());
            ps.setString(7, user.getEstatus());
            ps.setInt(8, user.getId());
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
                JOptionPane.showMessageDialog(null, "Error!!, comuniquese con el administrador");
                System.out.println(e.toString());
            }
        }

    }

}
