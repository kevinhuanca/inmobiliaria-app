package com.ulp.inmobiliaria.model;

public class Inmueble {

    private int id;
    private String direccion;
    private int ambientes;
    private String tipo;
    private String uso;
    private int precio;
    private boolean disponible;
    private String imagen;
    private int propietarioId;
    private Propietario propietario;

    public Inmueble() {
    }

    public Inmueble(int id, Propietario propietario, String direccion, int ambientes, String uso, String tipo, int precio, boolean disponible, String imagen, int propietarioId) {
        this.id = id;
        this.propietario = propietario;
        this.direccion = direccion;
        this.ambientes = ambientes;
        this.uso = uso;
        this.tipo = tipo;
        this.precio = precio;
        this.disponible = disponible;
        this.imagen = imagen;
        this.propietarioId = propietarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(int propietarioId) {
        this.propietarioId = propietarioId;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
}
