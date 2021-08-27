package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    Connection con;

    //Conexion Local
    public Connection getConnection() {
        try {
            //conexion remota
            /*
            String url = "jdbc:mysql:";
            String user = "";
            String pass = "";
*/
            //Conexion local
            
            String url = "jdbc:mysql://localhost/";
            String user = "";
            String pass = "";
            
            
            con = DriverManager.getConnection(url, user, pass);
            return con;
        } catch (SQLException e) {
            System.out.println("Error en conexion local" + e);
        }
        return (null);
    }

}
