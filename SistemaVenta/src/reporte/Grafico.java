package reporte;

import modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Grafico {

    public static void Graficar(String fechaIn, String fechaOut) {
        Connection con;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;
        try {
            String sql = "select cliente, sum(total) as total_Importe from t_venta where date(fecha) between ? and ? group by cliente";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaIn);
            ps.setString(2, fechaOut);
            rs = ps.executeQuery();
            DefaultPieDataset dateset = new DefaultPieDataset();

            while (rs.next()) {
                dateset.setValue(rs.getString("cliente"), rs.getDouble("total_Importe"));
            }

            JFreeChart jf = ChartFactory.createPieChart("Reporte de venta por cliente", dateset);
            ChartFrame f = new ChartFrame("Total Ventas por periodo", jf);
            f.setSize(1000, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion.");
            System.out.println(e.toString());
        }
    }

    public static void Grafica_Barras(String fechaIn, String fechaOut) {
        Connection con;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;
        try {
            String sql = "select codigo_producto, sum(cantidad) as total from t_detalle where date(fecha) between ? and ? group by codigo_producto";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaIn);
            ps.setString(2, fechaOut);
            rs = ps.executeQuery();
            DefaultCategoryDataset datos = new DefaultCategoryDataset();

            while (rs.next()) {
                datos.setValue(rs.getInt("total"), rs.getString("codigo_producto"), rs.getString("codigo_producto"));
            }

            JFreeChart grafico_barras = ChartFactory.createBarChart3D(
                    "Reporte de productos vendidos", //titulo del grafico
                    "Productos", //nombre de las barras o columnas "eje horizontal"
                    "Cantidad", //nombre de la numeracion "eje vertical"
                    datos, //datos de la grafica "modelo de datos"
                    PlotOrientation.VERTICAL, //orientacion
                    true, //leyenda de barras individuales por color
                    true, //herramientas
                    false); //url del grafico
            ChartFrame f = new ChartFrame("Productos vendidos por periodo", grafico_barras);
            f.setSize(1000, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion.");
            System.out.println(e.toString());
        }
    }

}
