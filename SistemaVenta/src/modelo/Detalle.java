package modelo;


public class Detalle {
   private int id_detalle, id_venta, cantidad;
   private double precio;
   private String codigo_producto;

    public Detalle() {
    }

    public Detalle(int id_detalle, int id_venta, int cantidad, double precio, String codigo_producto) {
        this.id_detalle = id_detalle;
        this.id_venta = id_venta;
        this.cantidad = cantidad;
        this.precio = precio;
        this.codigo_producto = codigo_producto;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }
 
}
