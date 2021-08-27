package modelo;

public class Config {
    private int id_config, ruc, telefono;
    private String nombre, razon, direccion;

    public Config() {
    }

    public Config(int id_config, int ruc, int telefono, String nombre, String razon, String direccion) {
        this.id_config = id_config;
        this.ruc = ruc;
        this.telefono = telefono;
        this.nombre = nombre;
        this.razon = razon;
        this.direccion = direccion;
    }

    public int getId_config() {
        return id_config;
    }

    public void setId_config(int id_config) {
        this.id_config = id_config;
    }

    public int getRuc() {
        return ruc;
    }

    public void setRuc(int ruc) {
        this.ruc = ruc;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
