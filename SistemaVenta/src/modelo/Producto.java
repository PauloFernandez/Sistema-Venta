package modelo;

public class Producto {
    private int id, stock;
    private double precio;
    private String codigo, nombre, proveedor;

    public Producto() {
    }

    public Producto(int id, int stock, double precio, String codigo, String nombre, String proveedor) {
        this.id = id;
        this.stock = stock;
        this.precio = precio;
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = proveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    
    
    
    
}
