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
            String url = "jdbc:mysql://bjrxoj3hvkhmofogxies-mysql.services.clever-cloud.com:3306/bjrxoj3hvkhmofogxies";
            String user = "uvnmbvvg9ocpbafh";
            String pass = "WW4hxVPtwBTF5vamOaTC";
*/
            //Conexion local
            
            String url = "jdbc:mysql://localhost:3306/sistemaventas";
            String user = "root";
            String  pass = "";
            
            
            con = DriverManager.getConnection(url, user, pass);
            return con;
        } catch (SQLException e) {
            System.out.println("Error en conexion local" + e);
        }
        return (null);
    }

}
