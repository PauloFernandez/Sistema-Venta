/*
 * 
 * 
 * 
 */
package modelo;

public class Cliente {
    private int id, ruc, telefono;
    private String nombre, razon, direccion;

    public Cliente() {
    }

    public Cliente(int id, int ruc, int telefono, String nombre, String razon, String direccion) {
        this.id = id;
        this.ruc = ruc;
        this.telefono = telefono;
        this.nombre = nombre;
        this.razon = razon;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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